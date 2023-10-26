import base64
import json
from urllib import parse

import insightface
import requests
from PIL import Image, ImageDraw
import cv2
import numpy as np
from .face_detect import FaceRecognitionDatabase, get_access_toke_from_client_infos, face_detection
from .utils import crop_and_expand, draw_detection_boxes
from .cfg import cfg
import warnings

# 关闭控制台所有的警告输出
warnings.filterwarnings("ignore")


def use_camera():
    """
    调用摄像头，并使用接口进行人脸检测
    :return:
    """
    # INFERENCE_TYPE = "face_detection"

    # 准备模型
    model = insightface.app.FaceAnalysis()
    model.prepare(ctx_id=0, det_thresh=0.3)

    # 创建FaceRecognitionDatabase实例并加载数据库
    database = FaceRecognitionDatabase()
    database.load_database("./Database/database.npz")

    cap = cv2.VideoCapture(0)
    frame_count = 0  # 计数器，用于控制每10帧进行一次推理
    while True:
        ret, frame = cap.read()

        if ret:
            frame_count += 1
            if frame_count == 10:  # 每10帧进行推理
                frame_count = 0  # 重置计数器

                # 获取摄像头的一帧图像并编码
                img_data = cv2.imencode('.jpg', frame)[1]
                image_base64 = base64.b64encode(img_data)

                # 获取access_token
                token_info = get_access_toke_from_client_infos(cfg.CLIENT_ID, cfg.CLIENT_SECRET)
                # 利用获取的access_token调用推理接口
                if token_info:
                    inference_ret = face_detection("face_detection", token_info["access_token"], image_base64)
                    image = crop_and_expand(frame, inference_ret)
                try:
                    print(f"Inference result is: {inference_ret}")
                    # 提取特征
                    res = model.get(image)
                    try:
                        emb1 = res[0].embedding
                    except Exception as err:
                        continue
                    if emb1 is not None:
                        # 使用数据库中的相似度查找函数
                        db_result = database.search_similar_faces(emb1, cfg.cosine_similarity_threshold)
                        if db_result is not None:
                            name, _, similarity = db_result
                            print(f"检测到人脸，人脸名称为:{name}，概率为:{similarity:.2f}")
                            flag = True
                        else:
                            flag = False
                            print("未检测到人脸")
                    # 如果检测到人脸以及相应的位置，就在结果图上画框并显示出来
                    if flag:
                        draw_detection_boxes(inference_ret, frame, name)
                    else:
                        draw_detection_boxes(inference_ret, frame, "None")

                except Exception as err:
                    print(err)
                    break
        else:
            break
    cap.release()
    cv2.destroyAllWindows()


def no_camera(image_path):
    INFERENCE_TYPE = "face_detection"

    # 准备模型
    model = insightface.app.FaceAnalysis()
    model.prepare(ctx_id=0, det_thresh=0.3)

    # 创建FaceRecognitionDatabase实例并加载数据库
    database = FaceRecognitionDatabase()
    database.load_database("../Database/database.npz")

    # 读取验证图像
    image = cv2.imread(image_path)

    with open(image_path, 'rb') as f:
        image_data = f.read()
    image_base64 = base64.b64encode(image_data)

    # 获取access_token
    token_info = get_access_toke_from_client_infos(cfg.CLIENT_ID, cfg.CLIENT_SECRET)
    # 利用获取的access_token调用推理接口
    if token_info:
        inference_ret = face_detection(INFERENCE_TYPE, token_info["access_token"], image_base64)
        image = crop_and_expand(image, inference_ret)
    try:
        print(f"Inference result is: {inference_ret}")
        # 提取特征
        res = model.get(image)
        try:
            emb1 = res[0].embedding
        except Exception as err:
            print(err)
        if emb1 is not None:
            # 使用数据库中的相似度查找函数
            db_result = database.search_similar_faces(emb1, cfg.cosine_similarity_threshold)
            if db_result is not None:
                name, _, _ = db_result
                flag = True
            else:
                flag = False
            if flag:
                print(f"检测到人脸，人脸名称为:{name}")
            else:
                print("未检测到人脸")

    except Exception as err:
        print(err)


if __name__ == "__main__":
    use_camera()
