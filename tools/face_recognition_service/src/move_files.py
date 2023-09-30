import os
import shutil

# 指定LFW数据集文件夹路径
lfw_folder = 'D:\\pycharm_file\\openeuler\\Database\\lfw'

# 指定训练数据和验证数据文件夹路径
train_folder = 'D:\\pycharm_file\\openeuler\\Database\\train'
validate_folder = 'D:\\pycharm_file\\openeuler\\Database\\val'

# 创建训练数据和验证数据文件夹
os.makedirs(train_folder, exist_ok=True)
os.makedirs(validate_folder, exist_ok=True)

# 遍历LFW数据集中的每个人的文件夹
for person_name in os.listdir(lfw_folder):
    person_folder = os.path.join(lfw_folder, person_name)

    # 确保是文件夹
    if os.path.isdir(person_folder):
        # 获取该人的所有图像文件
        image_files = os.listdir(person_folder)

        # 确保至少有一张图像
        if len(image_files) > 0:
            # 复制第一张图像到训练文件夹，并更改文件名为人名
            first_image = image_files[0]
            shutil.copy2(os.path.join(person_folder, first_image), os.path.join(train_folder, f"{person_name}.jpg"))

            # 复制其余的图像到验证文件夹，保持原始文件名
            for image_file in image_files[1:]:
                shutil.copy2(os.path.join(person_folder, image_file), os.path.join(validate_folder, image_file))

print("数据处理完成。")
