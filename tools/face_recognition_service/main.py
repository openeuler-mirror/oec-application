# -*- encoding=utf-8 -*-
"""
# **********************************************************************************
# Copyright (c) Huawei Technologies Co., Ltd. 2020-2020. All rights reserved.
# [oec-application] is licensed under the Mulan PSL v2.
# You can use this software according to the terms and conditions of the Mulan PSL v2.
# You may obtain a copy of Mulan PSL v2 at:
#     http://license.coscl.org.cn/MulanPSL2
# THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
# PURPOSE.
# See the Mulan PSL v2 for more details.
# Author: CoderJackZhu
# Create: 2023-10-27
# Description: face recognition
# **********************************************************************************
"""
import argparse
import warnings

from src.add_data import add_database
from src.build_database import build_database
from src.camera import use_camera, no_camera

# 关闭控制台所有的警告输出
warnings.filterwarnings("ignore")

parser = argparse.ArgumentParser(description="人脸识别程序")
parser.add_argument("--image_path", help="本地照片文件路径")
parser.add_argument("--camera", action="store_true", help="使用相机识别")
parser.add_argument("--build", action="store_true", help="建立database")
parser.add_argument("--img_fold", default="./Database/train", help="build所使用的图像文件夹地址")
parser.add_argument("--save_dir", default="./Database/database.npz", help="数据库保存地址")
parser.add_argument("--add", help="添加人脸数据，输入图像路径")

args = parser.parse_args()

if args.camera:
    use_camera()
elif args.image_path:
    no_camera(args.image_path)
elif args.build:
    build_database(args.img_fold, args.save_dir)
elif args.add:
    add_database(args.add, args.save_dir)
