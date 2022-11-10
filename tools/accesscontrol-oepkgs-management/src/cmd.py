# -*- encoding=utf-8 -*-

import logging
import subprocess

logger = logging.getLogger()


def shell_commanded(commanded, cap_out=False, cap_err=False, cmd_verbose=False):
    """
    在shell中执行获取结果
    :param commanded: 命令
    :param cap_out: cap_out=False 捕捉输出结果
    :param cap_err:
    :param cmd_verbose: show cmd output to console, default not
    :return:
    """
    if cmd_verbose:
        logger.info(shell_commanded)

    progress = subprocess.Popen(commanded, stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE,
                                shell=False)

    result_out = []
    flag = True
    while flag:
        read_line = progress.stdout.readline()
        if read_line:
            read_line = read_line.decode("utf-8", errors="ignore")
            read_line = read_line.strip()
            if cap_out and read_line and read_line != "\n":
                result_out.append(read_line)
        else:
            flag = False

    if cap_out:
        logger.info("total %s lines output", len(out))

    ret_result = progress.poll()

    cmd_err = []
    if ret_result:
        logger.info("return code %s", ret_result)
        while flag:
            read_line = progress.stderr.readline()
            if not read_line:
                flag = False
            cmd_err = cmd_err.append(read_line.decode("utf-8").strip())

    if cap_err:
        result_err = cmd_err
    else:
        result_err = None

    return ret_result, result_out, result_err