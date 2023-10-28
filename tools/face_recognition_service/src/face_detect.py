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
import json
import logging
from urllib import parse

import faiss
import numpy as np
import requests


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
        logging.error(f"Exception occurred in face_detection: {err}")
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
        logging.error(f"Exception occurred in face_detection: {err}")
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

        # 添加除零保护
        q_f_norm = query_vector_norm * feature_vector_norm
        if q_f_norm == 0:
            cosine_similarity = 0  # 如果分母为0，将余弦相似度设置为0
        else:
            cosine_similarity = np.dot(query_vector, self.feature_matrix[indices[0][0]]) / q_f_norm

        if cosine_similarity < cosine_similarity_threshold:
            logging.info(f"相似度低于阈值，相似度为{cosine_similarity:.2f}")
            flag = None
            return flag
        else:
            similar_name = self.face_names[indices[0][0]]
            similar_vector = self.feature_matrix[indices[0][0]]
            return similar_name, similar_vector, cosine_similarity

    def add_to_database(self, new_feature_vector, face_name):
        # assert new_feature_vector.shape[0] == self.feature_matrix.shape[1], "维度不匹配"
        if self.feature_matrix.shape[0] != self.feature_matrix.shape[1]:
            raise ValueError("维度不匹配")
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
        self.face_names = list(data['face_names'])
        self.build_faiss_database()
