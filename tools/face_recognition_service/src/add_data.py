import os
from face_detect import *
from utils import crop_and_expand
from cfg import cfg

# 先剪裁出人脸位置，再提特征，最后保存到数据库中

def add_database(image_path, save_dir):
    # 获取access_token
    token_info = get_access_toke_from_client_infos(cfg.CLIENT_ID, cfg.CLIENT_SECRET)

    # 准备FaceAnalysis模型
    model = insightface.app.FaceAnalysis()
    model.prepare(ctx_id=0, det_thresh=0.3)

    # 声明一个数据库实例
    database = FaceRecognitionDatabase()
    database.load_database("../Database/database.npz")

    # 读取图像
    image = cv2.imread(image_path)

    f = open(image_path, 'rb')
    image_data = f.read()
    image_base64 = base64.b64encode(image_data)

    # 利用获取的access_token调用推理接口
    if token_info:
        inference_ret = face_detection(INFERENCE_TYPE, token_info["access_token"], image_base64)
        image = crop_and_expand(image, inference_ret)

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
        print(f'{name}未检测到人脸')

    # 保存数据库
    database.save_database(save_dir)

    print("数据库建立完成")
