# coding:utf-8

import logging
import colorlog


class BuildLog:
    def __init__(self, name="Check", log_level=logging.INFO):
        """
        @param name: log name
        @log_level: log level
        """
        self.logger = logging.getLogger(name)

        self.logger.handlers = []

        self.logger.setLevel(log_level)

        console_fmt = '[%(asctime)s]-[%(name)s]-[line:%(lineno)d]-[%(levelname)s]: %(log_color)s%(message)s'

        color_config = {
            'DEBUG': 'black',
            'INFO': 'green',
            'WARNING': 'yellow',
            'ERROR': 'red',
            'CRITICAL': 'blue',
        }

        console_fmt = colorlog.ColoredFormatter(fmt=console_fmt, log_colors=color_config)

        console_handler = logging.StreamHandler()
        console_handler.setFormatter(console_fmt)
        self.logger.addHandler(console_handler)

    def debug_log(self, msg):
        self.logger.debug(msg)

    def info_log(self, msg):
        self.logger.info(msg)

    def warning_log(self, msg):
        self.logger.warning(msg)

    def error_log(self, msg):
        self.logger.error(msg)

    def critical_log(self, msg):
        self.logger.critical(msg)
