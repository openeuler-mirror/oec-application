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
import base64
import logging
import os

import cv2
import insightface

from .cfg import cfg
from .face_detect import get_access_toke_from_client_infos, face_detection, FaceRecognitionDatabase
from .utils import crop_and_expand


# 先剪裁出人脸位置，再提特征，最后保存到数据库中

def add_database(image_path, save_dir):
    # 获取access_token
    token_info = get_access_toke_from_client_infos(cfg.CLIENT_ID, cfg.CLIENT_SECRET)

    # 准备FaceAnalysis模型
    model = insightface.app.FaceAnalysis()
    model.prepare(ctx_id=0, det_thresh=0.3)

    # 声明一个数据库实例
    database = FaceRecognitionDatabase()
    database.load_database("./Database/database.npz")

    # 读取图像
    image = cv2.imread(image_path)

    with open(image_path, 'rb') as f:
        image_data = f.read()
    image_base64 = base64.b64encode(image_data)

    # 利用获取的access_token调用推理接口
    if token_info:
        access_token = token_info.get("access_token", None)
        if access_token:
            inference_ret = face_detection("face_detection", access_token, image_base64)
            image = crop_and_expand(image, inference_ret)
        else:
            logging.error("Failed to obtain access")

    # 利用FaceAnalysis模型提取特征
    face_result = model.get(image)

    # 仅考虑检测到单张人脸的情况
    if len(face_result) == 1:
        # 获取特征向量
        feature_vector = face_result[0].embedding
        # 去除图像名称的后缀
        file_name = os.path.basename(image_path)
        name = os.path.splitext(file_name)[0]

        # 将特征向量和名称添加到数据库中
        database.add_to_database(feature_vector, name)
    else:
        logging.error(f'未检测到人脸或有多张人脸，请重新拍照')

    # 保存数据库
    database.save_database(save_dir)

    logging.info("数据库添加完成")
