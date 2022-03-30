"""
Copyright: Copyright © Huawei Technologies Co., Ltd. 2021. All rights reserved.
Create: 2021-06-16
"""

import os
import shutil
import sys
import re
import time
import json
from concurrent.futures import ThreadPoolExecutor, as_completed

PATH = '/'


def tmp_library(paths):
    os.mkdir(PATH + '/tmp_cxx')
    for f in paths:
        if os.path.splitext(f)[-1] in ['.h', '.hpp', '.hxx']:
            shutil.copy(f, PATH + '/tmp_cxx')


def api_extraction_by_dir(paths):
    apis = []
    begin = time.time()
    with ThreadPoolExecutor(128) as pool:
        obj_list = []
        for files in paths:
            if os.path.splitext(files)[-1] in ['.cpp',
                                               '.c',
                                               '.cc',
                                               '.cxx',
                                               '.C',
                                               '.c++',
                                               '.C++',
                                               '.cp']:
                obj = pool.submit(api_extraction_by_cxxfile, files)
                obj_list.append(obj)
        for future in as_completed(obj_list):
            api = future.result()
            apis.append(api)
    times = time.time() - begin
    print(times)
    return apis


def api_extraction_by_cxxfile(file_path):

    clang1 = os.popen(
        'clang -Xclang -ast-dump -fsyntax-only {0} -I {1} | '
        'sed -r "s:\x1B\\[[0-9;]*[mK]::g" | grep FunctionDecl'.format(
            file_path, PATH + '/tmp_cxx'))
    clang_result1 = clang1.read()
    clang2 = os.popen(
        'clang -Xclang -ast-dump -fsyntax-only {0} -I {1} |'
        ' sed -r "s:\x1B\\[[0-9;]*[mK]::g" | grep CXXMethodDecl'.format(
            file_path, PATH + '/tmp_cxx'))
    clang_result2 = clang2.read()
    clang_result1 = map(
        lambda x: x.strip(),
        clang_result1.strip('\n').split('\n'))
    clang_result2 = map(
        lambda x: x.strip(),
        clang_result2.strip('\n').split('\n'))
    # Regular expressions for extracting function names, parameter names, and
    # return value types
    clang_pattern1 = re.compile("FunctionDecl .* (.*) '(.*)'")
    clang_pattern2 = re.compile("CXXMethodDecl .* (.*) '(.*)'")
    all_api = [clang_pattern1.search(value).groups() for value in clang_result1 if clang_pattern1.search(
        value)] + [clang_pattern2.search(value).groups() for value in clang_result2 if clang_pattern2.search(value)]
    return all_api


def append2json(result, file_path):
    if not isinstance(result, dict):
        raise ValueError('{} should be a dict')

    if os.path.exists(file_path):

        with open(file_path, 'r') as f0:
            try:
                data = json.load(f0)
            except json.decoder.JSONDecodeError:
                data = {}

        with open(file_path, 'w') as f1:
            result.update(data)
            json.dump(result, f1, indent=2)
    else:
        with open(file_path, 'w') as f2:
            json.dump(result, f2, indent=2)


def main(package, path):

    global PATH
    PATH = path
    all_api = ''
    if os.path.isfile(PATH) and os.path.splitext(
            PATH)[-1] in ['.cpp', '.h', '.hpp', '.c', '.cc', '.cxx', '.C', '.c++', '.hxx', '.C++', '.cp']:
        all_api = api_extraction_by_cxxfile(PATH)

    elif os.path.isdir(PATH):
        paths = [os.path.join(p, file)
                 for (p, d, f) in os.walk(PATH) for file in f]
        tmp_library(paths)
        all_api = api_extraction_by_dir(paths)
    result = {package: all_api}
    append2json(result, 'api.json')
    shutil.rmtree(PATH + '/tmp_cxx')


if __name__ == '__main__':
    main(sys.argv[1], sys.argv[2])
