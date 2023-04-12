import os
import logging.config


def init_logger():
    """
    初始化logger
    :return:
    """
    conf_path = os.path.realpath(os.path.join(os.path.dirname(__file__), "logger.conf"))
    defaults = {'args': str(('obsolete.log', 'a+', 50 * 1024 * 1024, 5))}

    logging.config.fileConfig(conf_path, defaults=defaults)