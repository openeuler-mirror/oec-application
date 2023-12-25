pip3 install selenium
sudo apt-get install wget

#google-chrome --version
#chromedriver --version

# install google-chrome
wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
sudo apt install -y ./google-chrome-stable_current_amd64.deb
google-chrome --version

# install chromedriver
wget https://edgedl.me.gvt1.com/edgedl/chrome/chrome-for-testing/119.0.6045.159/linux64/chromedriver-linux64.zip
unzip chromedriver-linux64.zip
cd chromedriver-linux64
cp chromedriver /usr/bin/
chromedriver --version

#google-chrome

#python3 start.py
sh start.sh ${giteeSourceBranch} ${giteeSourceRepoURL} ${giteeTargetRepoName} ${giteePullRequestIid} ${BUILD_ID} 