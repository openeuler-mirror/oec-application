# 人脸识别项目

这个项目是一个用于人脸识别的Python应用程序。它利用计算机视觉技术和深度学习模型来识别人脸并与预先构建的数据库进行比较。您可以通过本地图像文件或相机捕获的实时图像来执行人脸识别。本项目可以在openEuler系统下正常使用。

## 依赖项

在运行该应用程序之前，请确保已安装以下依赖项：

- OpenCV
- NumPy
- Faiss
- InsightFace

您可以使用以下命令来安装这些依赖项：

```
pip install opencv-python numpy faiss-cpu insightface
```

## 使用方法

您可以使用以下命令行参数来运行该应用程序：

- `--image_path`: 本地照片文件路径。使用此选项可以对单个本地图像进行人脸识别。
- `--camera`: 使用相机识别。使用此选项可以打开摄像头并进行实时人脸识别。
- `--build`: 建立数据库。使用此选项可以构建人脸数据库，需要提供包含用于建立数据库的图像的文件夹地址。
- `--img_fold`: 构建数据库所使用的图像文件夹地址。在使用 `--build` 选项时，指定包含用于建立数据库的图像的文件夹。
- `--save_dir`: 数据库保存地址。指定数据库文件的保存路径。
- `--add`: 添加人脸数据。使用此选项可以将新的人脸数据添加到现有的数据库中，需要提供要添加的图像文件的路径。

### 示例用法

- 使用本地照片进行人脸识别：

```
python main.py --image_path /path/to/your/image.jpg
```

- 使用相机进行实时人脸识别：

```
python main.py --camera
```

- 建立人脸数据库：

```
python main.py --build --img_fold /path/to/database/images --save_dir /path/to/database/database.npz
```

- 添加人脸数据到数据库：

```
python main.py --add /path/to/new/face/image.jpg --save_dir /path/to/database/database.npz
```

## 注意事项

- 请确保图像文件的路径正确，并且文件存在。
- 当使用 `--build` 选项构建数据库时，您需要提供包含足够多人脸图像的文件夹，以便构建一个具有良好识别性能的数据库。
- 使用 `--add` 选项时，请确保提供的图像包含有效的人脸，并且已经在数据库中有相应的标签。

## 作者

- 朱一杰

## 许可证

此项目采用 [MulanPSL-2.0](http://license.coscl.org.cn/MulanPSL2) 许可。