import numpy as np
import cv2
from PIL import Image, ImageDraw

def cosine_similarity(vector_a, vector_b):
    # 计算向量的余弦相似度
    dot_product = np.dot(vector_a, vector_b)  # 计算点积
    norm_a = np.linalg.norm(vector_a)  # 计算向量A的模长
    norm_b = np.linalg.norm(vector_b)  # 计算向量B的模长
    # 添加除零保护
    if norm_a * norm_b == 0:
        similarity = 0  # 如果分母为0，将相似度设置为0
    else:
        similarity = dot_product / (norm_a * norm_b)  # 计算余弦相似度
    return similarity

def draw_detection_boxes(inference_ret, frame, name):

    # 如果检测到人脸以及相应的位置，就在结果图上画框并显示出来
    if len(inference_ret["result"]) > 0:
        for i in range(len(inference_ret["result"])):
            for j in range(len(inference_ret["result"][i]["infos"])):
                # 把一次检测出的多张人脸都画在一张图上，并显示出来
                location = inference_ret["result"][i]["infos"][j]["location"]
                height, top_left_x, top_left_y, width = location["height"], location["top_left_x"], \
                    location["top_left_y"], location["width"]

                # 绘制人脸框
                img = frame.copy()  # 创建图像的副本以免影响原图像
                cv2.rectangle(img, (top_left_x, top_left_y), (top_left_x + width, top_left_y + height), (0, 0, 255), 2)

                # chinese_font_path = "../SimHei.ttf"
                chinese_font = cv2.FONT_HERSHEY_SIMPLEX
                text_position = (top_left_x, top_left_y - 10)  # 文本位置稍微上移一些
                cv2.putText(img, name, text_position, chinese_font, 0.6, (0, 0, 255), 2)

                # 显示图像
                cv2.imshow('FaceDetect', img)

            if cv2.waitKey(1) & 0xFF == ord('q'):
                break

def crop_and_expand(image, detection_result):
    # 检查检测是否成功以及是否检测到任何人脸
    if "resCode" in detection_result and detection_result["resCode"] == "0" and "result" in detection_result:
        faces = detection_result["result"]
        if len(faces) > 0 and "infos" in faces[0] and len(faces[0]["infos"]) > 0:
            face_info = faces[0]["infos"][0]
            location = face_info["location"]
            top_left_x = int(location["top_left_x"])
            top_left_y = int(location["top_left_y"])
            width = int(location["width"])
            height = int(location["height"])

            # 计算扩展区域
            expand_width = width // 2
            expand_height = height // 2

            # 确保扩展区域在图像边界内
            expanded_top_left_x = max(top_left_x - expand_width, 0)
            expanded_top_left_y = max(top_left_y - expand_height, 0)
            expanded_bottom_right_x = min(top_left_x + width + expand_width, image.shape[1])
            expanded_bottom_right_y = min(top_left_y + height + expand_height, image.shape[0])

            # 裁剪扩展区域
            cropped_image = image[expanded_top_left_y:expanded_bottom_right_y, expanded_top_left_x:expanded_bottom_right_x]

            return cropped_image

    # 如果未检测到人脸或检测失败，则返回None
    return image
