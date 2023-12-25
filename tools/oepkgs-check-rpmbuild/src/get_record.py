import time
import json
from get_token import get_oepkgs_token
from logger import log_info
import requests


def get_build_record(opekgstoken, jobname, jobid):
    """
    start to build job in oepkgs.net
    :param opekgstoken: the login token of the account oepkgs_management
    :param jobid: the id of the job
    :param jobname: the name of the job
    :return:
    the status of the job
    """
    iid = str(jobid)
    url = 'https://build.dev.oepkgs.net/api/build/task/getBuildRecord/' + iid + '?pageNum=1&pageSize=5'
    headers = {
        'authority': 'build.dev.oepkgs.net',
        'accept': '*/*',
        'accept-language': 'en-US,en;q=0.9,zh;q=0.8,zh-CN;q=0.7',
        'cache-control': 'no-cache',
        'content-type': 'application/json',
        'cookie':
        'auth_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxMDksInVzZXJfbmFtZSI6ImR\
        pZGkiLCJUWiI6IkFzaWEvU2hhbmdoYWkiLCJleHAiOjE2OTM1Mzk4MjgsImlhdCI6MTY5MzUzODAyOH0.sFZYWM-\
        YiKKGcD2udonHmCM_kLxyb3Dt0P6xQZs49u8',
        'dnt': '1',
        'pragma': 'no-cache',
        'referer': 'https://build.dev.oepkgs.net/rpm/task',
        'sec-ch-ua': '"Chromium";v="116", "Not)A;Brand";v="24", "Google Chrome";v="116"',
        'x-auth-token': opekgstoken,
    }
    params = {
        "jobName": jobname,
    }
    response = requests.get(url, headers=headers, params=params)
    #print(response)
    res_data = json.loads(response.text)
    #print(res_data['data'])

    wait_time = 0
    while res_data['data']['list'] is None:
        log_info("Time:" + str(wait_time) + "; Failed to get build record")
        time.sleep(30)
        wait_time = wait_time + 1
        if wait_time > 5:
            return "no build list"
        
    if len(res_data['data']['list']) == 0:
        return "no build task"
    
    build_result = res_data['data']['list'][0]
    #print(build_result)
    log_info(str(build_result))
    re = build_result['result']
    #print(re)
    return re

#token=get_oepkgs_token()
#re=get_build_record(token, "13369748-64fe-4e63-b2cd-aee9cc6eb315",1015)
#print(re)
