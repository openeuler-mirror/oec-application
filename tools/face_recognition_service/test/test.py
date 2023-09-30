import os
import cv2
import numpy as np
from src.face_detect import FaceRecognitionDatabase
import faiss
import insightface
from src.build_Database import build_database
import warnings

# 关闭控制台所有的警告输出
warnings.filterwarnings("ignore")

#建立数据库database
build_database("../Database/train", "../Database/database.npz")

# 指定验证数据集的文件夹路径
val_folder = '../Database/val'  # 请替换为您的验证数据集文件夹路径

# 创建FaceRecognitionDatabase实例并加载数据库
database = FaceRecognitionDatabase()
database.load_database("../Database/database.npz")

# 准备FaceAnalysis模型
model = insightface.app.FaceAnalysis()
model.prepare(ctx_id=0, det_thresh=0.3)

# 设置相似度阈值
cosine_similarity_threshold = 0.5  # 根据需要调整阈值

# 计数器，用于统计正确识别的人脸数
correct_count = 0
total_count = 0

# 遍历验证数据集中的图像
for filename in os.listdir(val_folder):
    image_path = os.path.join(val_folder, filename)

    # 读取验证图像
    image = cv2.imread(image_path)

    # 提取特征
    res = model.get(image)
    try:
        emb1 = res[0].embedding
    except:
        print(res)
        continue

    if emb1 is not None:
        # 使用数据库中的相似度查找函数
        result = database.search_similar_faces(emb1, cosine_similarity_threshold)

        if result is not None:
            similar_name, _, _ = result
            # 获取验证图像的文件名（去除后缀）
            val_name = os.path.splitext(filename)[0]

            # 检查是否正确识别
            if similar_name == val_name:
                correct_count += 1

            total_count += 1

# 计算正确率
accuracy = correct_count / total_count if total_count > 0 else 0
print(f"正确识别人脸数: {correct_count}")
print(f"总人脸数: {total_count}")
print(f"识别准确率: {accuracy * 100:.2f}%")
