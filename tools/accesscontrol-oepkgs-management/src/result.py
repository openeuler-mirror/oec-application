# -*- encoding=utf-8 -*-

class AssResult(object):
    """
    result assignment
    """

    def __init__(self, value):
        self._value = value

    @property
    def get_value(self):
        return self.get_value

    @property
    def get_status(self):
        return ["SUCCESS", "WARNING", "FAILED"][self.get_value]

    @property
    def get_emoji(self):
        return [":white_check_mark:", ":x:"][self.get_value]

    @classmethod
    def value_instance(cls, value):
        """
        :param value: 0/1/2/3/True/False/success/fail/warn
        :return: instance of ACResult
        """
        if isinstance(value, int):
            return {0: SUCCESS, 1: WARNING, 2: FAILED}.get(value)
        if isinstance(value, bool):
            return {True: SUCCESS, False: FAILED}.get(value)

        try:
            value = int(value)
            return {0: SUCCESS, 1: WARNING, 2: FAILED}.get(value)
        except ValueError:
            return {"success": SUCCESS, "fail": FAILED, "failed": FAILED, "failure": FAILED,
                    "warn": WARNING, "warning": WARNING}.get(value.lower(), FAILED)


FAILED = AssResult(2)
WARNING = AssResult(1)
SUCCESS = AssResult(0)