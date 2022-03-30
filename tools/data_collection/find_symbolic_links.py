#!/usr/bin/env python3

"""
Copyright: Copyright © Huawei Technologies Co., Ltd. 2021. All rights reserved.
Create: 2021-06-16
"""

import json
import os
from pathlib import Path
import logging
from subprocess import run, PIPE

logging.basicConfig(format='%(asctime)s %(message)s', level=logging.INFO)

# Notice `/proc`, may throw `FileNotFoundError` because of the process over.
start_path = Path('/')
result_file = 'symbolic-links.json'

all_paths = start_path.glob('**/*')
all_symbolic_links = {}

while True:
    try:
        p = next(all_paths)
        if p.is_symlink():
            resolved_path = p.resolve()  # may loop to find symbolic link
            cmd_result = run(['file', str(resolved_path)],
                             stdout=PIPE, stderr=PIPE, universal_newlines=True)
            if 'shared object' in cmd_result.stdout:
                all_symbolic_links[p.name] = resolved_path.name

            logging.info(p)
    except StopIteration:
        print('Done!')
        break
    except Exception as e:
        print(e)

with os.fdopen(os.open(result_file, os.O_WRONLY | os.O_CREAT | os.O_TRUNC, 0o600), 'w') as f:
    json.dump(all_symbolic_links, f, indent=2)
