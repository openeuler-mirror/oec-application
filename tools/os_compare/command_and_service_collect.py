import os
import subprocess
import json
from pathlib import Path


ENVIRON_PATHS = os.environ['PATH'].split(os.pathsep)
SERVICE_PATHS = ['/lib/systemd/system']


class Collect:
    collect_commands = True
    collect_services = True
    collect = {}

    @staticmethod
    def collect_file(collect_paths):
        if not collect_paths:
            raise ValueError('Collect path should not be empty')
        for collect_path in collect_paths:
            collect_path_obj = Path(collect_path)
            all_files = collect_path_obj.glob('**/*')
            for file in all_files:
                if file.is_file() and (os.access(
                        file.as_posix(), os.X_OK) or file.suffix == '.service'):
                    try:
                        rpm = subprocess.check_output(['rpm', '-qf', file.absolute().as_posix()],
                                                      universal_newlines=True, timeout=5).strip()
                    except subprocess.CalledProcessError:
                        rpm = ''
                    except subprocess.TimeoutExpired:
                        rpm = ''
                    except Exception:
                        raise
                    if rpm:
                        Collect.collect.setdefault(
                            file.name, {}).setdefault(
                            'dir', file.parent.absolute().as_posix())
                        print(
                            'Start collect file: "%s", it belong to "%s"' %
                            (file.name, rpm))
                        Collect.collect.setdefault(
                            file.name, {}).setdefault(
                            'rpmName', rpm)

    @staticmethod
    def write2json(file_name='data.json'):
        flag = os.O_WRONLY | os.O_CREAT | os.O_TRUNC
        with open(os.open(file_name, flag, 0o640), 'w') as fp:
            json.dump(Collect.collect, fp, indent=2)

    def run(self):
        target_path = ENVIRON_PATHS + SERVICE_PATHS
        self.collect_file(target_path)
        self.write2json()


Collect().run()
