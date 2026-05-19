# Facial Recognition Project

This is a Python application for facial recognition. It uses computer vision techniques and deep learning models to recognize faces and compare them with a pre-built database. You can perform facial recognition using local image files or real-time images captured by a camera. This project can be used in the openEuler system.

## Dependencies

Before running the application, ensure that the following dependencies have been installed:

- OpenCV
- NumPy
- Faiss
- InsightFace

You can run the following command to install these dependencies:

```shell
pip install opencv-python numpy faiss-cpu insightface
```

## How to Use

You can use the following command line parameters to run the application:

- `--image_path`: path of the local photo file This option can be used to perform facial recognition on a single local image.
- `--camera`: Use the camera for recognition. This option can be used to open the camera and perform real-time facial recognition.
- `--build`: Create a database. This option can be used to build a facial database. You need to provide the address of the folder that contains the images used to build the database.
- `--img_fold`: address of the image folder used to build the database. When using the `--build` option, specify the folder that contains the images used to build the database.
- `--save_dir`: database storage address It specify the path for storing the database file.
- `--add`: Add face data. This option can be used to add new face data to an existing database. You need to provide the path of the image file to be added.

### Example Usage

- Use a local image for facial recognition:

```python
python main.py --image_path /path/to/your/image.jpg
```

- Use a camera for real-time facial recognition:

```python
python main.py --camera
```

- Create a face database:

```python
python main.py --build --img_fold /path/to/database/images --save_dir /path/to/database/database.npz
```

- Add face data to the database:

```python
python main.py --add /path/to/new/face/image.jpg --save_dir /path/to/database/database.npz
```

## Precautions

- Ensure that the path of the image file is correct and the file exists.
- When using `--build` to build a database, you need to provide a folder containing a sufficient number of face images to build a database with good recognition performance.
- When using `--add`, ensure that the provided images contain valid faces and have corresponding labels in the database.

## Prepared By

- Zhu Yijie

## License

This project uses the [MulanPSL-2.0](http://license.coscl.org.cn/MulanPSL2) license.
