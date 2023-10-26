import base64
import logging
import os

import cv2
import insightface

from src.cfg import cfg
from src.face_detect import get_access_toke_from_client_infos, FaceRecognitionDatabase, face_detection
from src.utils import crop_and_expand


# 先剪裁出人脸位置，再提特征，最后保存到数据库中

def build_database(image_folder_path, save_dir):
    # 获取access_token
    token_info = get_access_toke_from_client_infos(cfg.CLIENT_ID, cfg.CLIENT_SECRET)

    # 准备FaceAnalysis模型
    model = insightface.app.FaceAnalysis()
    model.prepare(ctx_id=0, det_thresh=0.5)

    # 声明一个数据库实例
    database = FaceRecognitionDatabase()

    # 读取图片文件夹中的图片，并提取特征保存到数据库中
    for filename in os.listdir(image_folder_path):
        # 读取图像
        image_path = os.path.join(image_folder_path, filename)
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
                logging.error("Failed to get access token")

        # 利用FaceAnalysis模型提取特征
        face_result = model.get(image)

        # 去除图像名称的后缀
        name = os.path.splitext(filename)[0]

        # 仅考虑检测到单张人脸的情况
        if len(face_result) == 1:
            # 获取特征向量
            feature_vector = face_result[0].embedding
            # 将特征向量和名称添加到数据库中
            database.add_to_database(feature_vector, name)
        else:
            logging.warning(f'{name}未检测到人脸或有多张人脸，请重新拍照')

    # 保存数据库
    database.save_database(save_dir)

    logging.info("数据库建立完成")
