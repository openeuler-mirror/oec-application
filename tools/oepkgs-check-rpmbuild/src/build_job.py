import requests


def build_job(opekgstoken, jobname, jobid):
    """
    start to build job in oepkgs.net
    :param opekgstoken: the login token of the account oepkgs_management
    :param jobid: the id of the job
    :param jobname: the name of the job
    :return:
    whether successfully build the job
    """
    url2 = 'https://build.dev.oepkgs.net/api/build/task/buildJob'
    headers2 = {
        'authority': 'build.dev.oepkgs.net',
        'accept': '*/*',
        'accept-language': 'en-US,en;q=0.9,zh;q=0.8,zh-CN;q=0.7',
        'cache-control': 'no-cache',
        'content-type': 'application/json',
        'cookie':
        'auth_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2Vy\
        X2lkIjoxMDksInVzZXJfbmFtZSI6ImRpZGkiLCJUWiI6IkFzaIAvU2hhb\
        mdoYWkiLCJleHAiOjE2OTM1Mzk4MjgsImlhdCI6MTY5MzUzODAyOH0.sF\
        ZYWM-YiKKGcD2udonHmCM_kLxyb3Dt0P6xQZs49u8',
        'dnt': '1',
        'origin': 'https://build.dev.oepkgs.net',
        'pragma': 'no-cache',
        'referer': 'https://build.dev.oepkgs.net/rpm/task',
        'sec-ch-ua': '"Chromium";v="116", "Not)A;Brand";v="24", "Google Chrome";v="116"',
        'user-agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)AppleWebKit/537.36 (KHT\
        ML, like Gecko) Chrome/116.0.0.0 Safari/537.36',
        'x-auth-token': opekgstoken,
    }
    data2 = {
        'jobName': jobname,
        'jobId': jobid,
    }
    response2 = requests.post(url2, headers=headers2, json=data2)
    #print(response2.status_code)
    #print(response2.text)
    if(response2.status_code == 200):
        #print("success build")
        return 'success'
    else:
        #print("fail build")
        return 'fail'
