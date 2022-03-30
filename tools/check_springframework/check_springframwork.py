import subprocess
import os
import sys
import tempfile
import re
import shutil
from pathlib import Path

WORK_DIR = "/tmp/check_springframework"


def perform_cpio(package):
    """
    解压rpm包
    @param package: rpm包路径
    @return: None
    """
    stdin = subprocess.Popen(['rpm2cpio', package], stdout=subprocess.PIPE)
    p = subprocess.Popen(['cpio',
                          '-d',
                          '-i',
                          '-v'],
                         stdin=stdin.stdout,
                         stdout=subprocess.DEVNULL,
                         stderr=subprocess.STDOUT)
    p.communicate()


def depress_rpm_package(package, decompression_dir):
    """
    解压rpm包
    @param package: rpm包路径
    @param decompression_dir: 解压缩路径
    @return: None
    """
    cwd = os.getcwd()
    os.chdir(decompression_dir)
    perform_cpio(package)
    os.chdir(cwd)


def unzip_jar_package(jar_package, unzip_dir):
    try:
        p = subprocess.Popen(["unzip", jar_package, "-d", unzip_dir], stdout=subprocess.DEVNULL,
                             stderr=subprocess.STDOUT)
        p.communicate()
    except subprocess.CalledProcessError as e:
        print(e)
        sys.exit(1)


def analyse_jar_package(jar_package: Path) -> bool:
    unzip_dir = tempfile.TemporaryDirectory(prefix=jar_package.name + "_", suffix="_unzip", dir=WORK_DIR)
    unzip_jar_package(jar_package.absolute().as_posix(), unzip_dir.name)
    sub_all_jar_path = Path(unzip_dir.name).glob("**/*.jar")
    for sub_jar_path in sub_all_jar_path:
        match_obj = re.match("spring-beans-.*\.jar", sub_jar_path.name)
        if match_obj:
            print(f"{jar_package.name}->{match_obj.group()}")
            unzip_dir.cleanup()
            return False

    all_class_file_path = Path(unzip_dir.name).glob("**/*.class")
    for class_file in all_class_file_path:
        if class_file.name == "CachedIntrospectionResuLts.class":
            print(f"{jar_package}->{class_file.absolute().as_posix()}")
            unzip_dir.cleanup()
            return False
    unzip_dir.cleanup()
    return True


def exec_analysis(rpm_path: Path) -> bool:
    decompression_dir = tempfile.TemporaryDirectory(prefix=rpm_path.name + "_", suffix="_decompress",
                                                    dir=WORK_DIR)
    depress_rpm_package(rpm_path.absolute().as_posix(), decompression_dir.name)
    all_jar_path = Path(decompression_dir.name).glob("**/*.jar")
    for jar_path in all_jar_path:
        print("----" + jar_path.name)
        match_obj = re.match("spring-beans-.*\.jar", jar_path.name)
        if match_obj:
            print(f"{rpm_path.name}->{match_obj.group()}")
            return False
        result_bool = analyse_jar_package(jar_path)
        if not result_bool:
            print(f"problem happens in {rpm_path.name}")
            decompression_dir.cleanup()
            return result_bool
    decompression_dir.cleanup()
    return True


if __name__ == '__main__':
    if os.path.exists(WORK_DIR):
        shutil.rmtree(WORK_DIR)
    os.mkdir(WORK_DIR)

    scan_path = sys.argv[1]
    all_rpm_path = Path(scan_path).glob("**/*.rpm")
    problem_rpm_list = []
    for rpm_path in all_rpm_path:
        print(rpm_path.name)
        result_bool = exec_analysis(rpm_path)
        if not result_bool:
            problem_rpm_list.append(rpm_path.name)
    print("problem rpm list as follows:")
    print(problem_rpm_list)
