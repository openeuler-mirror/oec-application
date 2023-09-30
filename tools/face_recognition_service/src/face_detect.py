# -*- coding: utf-8 -*-
import base64
import json
from urllib import parse
import insightface
import cv2
import requests
import numpy as np
import faiss

INFERENCE_TYPE = "face_detection"

def get_access_toke_from_client_infos(client_id, client_secret):
    try:
        headers = {
        "Content-Type": "application/x-www-form-urlencoded"
        }
        send_data = {"grant_type": 'client_credentials',
        "client_id": client_id,
        "client_secret": client_secret}
        resp = requests.post(url="https://studio.e.huawei.com/baas/auth/v1.0/oauth2/token",
        headers=headers,
        data=parse.urlencode(send_data),
        verify=False)
        return resp.json()
    except requests.exceptions.RequestException as err:
        print(err)
    return {}


def face_detection(inference_type, access_token, image_base64):
    # 请求body体
    send_data = {
    "name": inference_type,
    "dataInputs": {"images": [{"image": image_base64.decode(), "type": "base64"}]}
    }

    # 请求header
    headers = {
    "access-Token": access_token,
    "Content-Type": "application/json"
    }
    try:
        resp = requests.post(url="https://studio.e.huawei.com/ai-enable/v1.0/services/invoke",
        headers=headers,
        data=json.dumps(send_data),
        verify=False)
        return resp.json()
    except Exception as err:
        print(err)
        return {}

class FaceRecognitionDatabase:
    def __init__(self):
        self.feature_matrix = np.empty((0, 512), dtype=np.float32)
        self.face_names = []
        self.faiss_index = None

    def build_faiss_database(self):
        if self.feature_matrix.shape[0] > 0:
            d = self.feature_matrix.shape[1]
            self.faiss_index = faiss.IndexFlatIP(d)
            self.faiss_index.add(self.feature_matrix)


    def search_similar_faces(self, query_vector, cosine_similarity_threshold):
        if self.faiss_index is None:
            raise ValueError("Faiss index is not built. Please add vectors to the database and build the index.")

        k = 1
        _, indices = self.faiss_index.search(np.expand_dims(query_vector, axis=0), k)

        query_vector_norm = np.linalg.norm(query_vector)
        feature_vector_norm = np.linalg.norm(self.feature_matrix[indices[0][0]])

        cosine_similarity = np.dot(query_vector, self.feature_matrix[indices[0][0]]) / (query_vector_norm * feature_vector_norm)

        if cosine_similarity < cosine_similarity_threshold:
            return None
        else:
            similar_name = self.face_names[indices[0][0]]
            similar_vector = self.feature_matrix[indices[0][0]]
            return similar_name, similar_vector, cosine_similarity

    def add_to_database(self, new_feature_vector, face_name):
        assert new_feature_vector.shape[0] == self.feature_matrix.shape[1], "维度不匹配"
        self.feature_matrix = np.vstack([self.feature_matrix, new_feature_vector])
        self.face_names.append(face_name)
        if self.faiss_index is None:
            self.build_faiss_database()
        else:
            self.faiss_index.add(np.expand_dims(new_feature_vector, axis=0))

    def save_database(self, save_dir):
        np.savez(save_dir, feature_matrix=self.feature_matrix, face_names=self.face_names)

    def load_database(self, load_dir):
        data = np.load(load_dir)
        self.feature_matrix = data['feature_matrix']
        self.face_names = data['face_names']
        self.build_faiss_database()


if __name__ == '__main__':
    # 先剪裁出人脸位置，提特征，最后比较

    # 根据用户的aksk获取access_token
    token_info = get_access_toke_from_client_infos(CLIENT_ID, CLIENT_SECRET)
    # 利用获取的access_token调用推理接口
    if token_info:
        inference_ret = face_detection(INFERENCE_TYPE, token_info["access_token"], "C:\\Users\\qinxialo\\Desktop\\2.jpg")
        # print(f"Inference result is: {inference_ret}")
        img1 = crop_and_expand("C:\\Users\\qinxialo\\Desktop\\2.jpg", inference_ret)

        model = insightface.app.FaceAnalysis()
        model.prepare(ctx_id=0, det_thresh=0.45)

        # img1 = cv2.imread("C:\\Users\\qinxialo\\Desktop\\2.jpg")
        res = model.get(img1)
        emb1 = res[0].embedding

        inference_ret = face_detection(INFERENCE_TYPE, token_info["access_token"], "C:\\Users\\qinxialo\\Desktop\\5.jpg")
        # print(f"Inference result is: {inference_ret}")
        img2 = crop_and_expand("C:\\Users\\qinxialo\\Desktop\\5.jpg", inference_ret)

        # img2 = cv2.imread("C:\\Users\\qinxialo\\Desktop\\5.jpg")
        res = model.get(img2)
        emb2 = res[0].embedding

        similarity_score = cosine_similarity(emb1, emb2)
        print("余弦相似度：", similarity_score)
#
# if __name__ == '__main__':
#     # 不剪裁出人脸位置，提特征，最后比较
#
#     Database = FaceRecognitionDatabase()
#     model = insightface.app.FaceAnalysis()
#     model.prepare(ctx_id=0, det_thresh=0.45)
#
#     img1 = cv2.imread("C:\\Users\\qinxialo\\Desktop\\2.jpg")
#     res = model.get(img1)
#     emb1 = res[0].embedding
#
#     img2 = cv2.imread("C:\\Users\\qinxialo\\Desktop\\5.jpg")
#     res = model.get(img2)
#     emb2 = res[0].embedding
#
#     img3 = cv2.imread("C:\\Users\\qinxialo\\Desktop\\4.jpg")
#     res = model.get(img3)
#     emb3 = res[0].embedding
#
#     # 添加到数据库中
#     Database.add_to_database(emb1, "张三")
#     Database.add_to_database(emb2, "张三")
#     data = Database.search_similar_faces(emb3, 0.5)
#     print(data)