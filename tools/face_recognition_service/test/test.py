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
import logging
import os
import warnings

import cv2
import insightface

from src.build_database import build_database
from src.face_detect import FaceRecognitionDatabase

# 关闭控制台所有的警告输出
warnings.filterwarnings("ignore")

# 建立数据库database
build_database("../Database/train", "../Database/database.npz")

# 指定验证数据集的文件夹路径
VAL_FOLDER = '../Database/val'  # 请替换为您的验证数据集文件夹路径

# 创建FaceRecognitionDatabase实例并加载数据库
database = FaceRecognitionDatabase()
database.load_database("../Database/database.npz")

# 准备FaceAnalysis模型
model = insightface.app.FaceAnalysis()
model.prepare(ctx_id=0, det_thresh=0.3)

# 设置相似度阈值
COSINE_SIMILARITY_THRESHOLD = 0.5  # 根据需要调整阈值

# 计数器，用于统计正确识别的人脸数
CORRECT_COUNT = 0
TOTAL_COUNT = 0

# 遍历验证数据集中的图像
for filename in os.listdir(VAL_FOLDER):
    image_path = os.path.join(VAL_FOLDER, filename)

    # 读取验证图像
    image = cv2.imread(image_path)

    # 提取特征
    res = model.get(image)
    try:
        emb1 = res[0].embedding
    except Exception as err:
        logging.error(err)
        continue

    if emb1 is not None:
        # 使用数据库中的相似度查找函数
        result = database.search_similar_faces(emb1, COSINE_SIMILARITY_THRESHOLD)

        if result is not None:
            similar_name, _, _ = result
            # 获取验证图像的文件名（去除后缀）
            val_name = os.path.splitext(filename)[0]

            # 检查是否正确识别
            if similar_name == val_name:
                CORRECT_COUNT += 1

            TOTAL_COUNT += 1

# 计算正确率
if TOTAL_COUNT == 0:
    ACCURACY = 0  # 如果分母为0，将正确率设置为0
else:
    ACCURACY = CORRECT_COUNT / TOTAL_COUNT
logging.info(f"正确识别人脸数: {CORRECT_COUNT}")
logging.info(f"总人脸数: {TOTAL_COUNT}")
logging.info(f"识别准确率: {ACCURACY * 100:.2f}%")
