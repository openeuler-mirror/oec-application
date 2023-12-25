import logging.config

logging.basicConfig(level=logging.DEBUG) 
logger = logging.getLogger("info")


def log_info(ss):
    logger.info(ss)


def log_error(ss):
    logger.error(ss)


def log_warning(ss):
    logger.warning(ss)