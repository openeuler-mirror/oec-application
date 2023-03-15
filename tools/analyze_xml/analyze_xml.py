import pandas as pd
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.naive_bayes import MultinomialNB
from sklearn.pipeline import make_pipeline
from build_log import Log

logger = Log()

# 加载数据
FILE_PATH = 'all.csv'  # 将此路径替换为你的csv文件的路径
data = pd.read_csv(FILE_PATH)

# 检查数据是否包含NaN值
missing_values = data.isnull().sum()
unspecified_counts = data.apply(lambda x: x.str.count("Unspecified")).sum()

# 如果有未分类的软件包 (NaN在'b'列,一下abc列皆为表头)
if missing_values['group'] > 0:
    # 分割数据为训练集和未分类集
    train_data = data[data['group'].notna()]
    unclassified_data = data[data['group'].isna()]

    # 创建一个模型来预测类别
    model = make_pipeline(CountVectorizer(), MultinomialNB())

    # 使用列a和c作为特征，列b作为目标变量来训练模型
    X_train = train_data['name'] + ' ' + train_data['summary'] + train_data['description']
    y_train = train_data['group']
    model.fit(X_train, y_train)

    # 预测未分类的软件包的类别
    X_unclassified = unclassified_data['name'] + ' ' + unclassified_data['summary'] + unclassified_data['description']
    predicted_categories = model.predict(X_unclassified)
    # print(X_unclassified)
#
#     # 将预测的类别添加到表格
    data.loc[data['group'].isna(), 'group'] = predicted_categories

    # 保存更新后的表格到新的csv文件
    data.to_csv('updated_file1.csv', index=False)
    logger.info("Categories have been predicted and the updated file has been saved as 'updated_file.csv'")
else:
    logger.info("All packages are already categorized.")