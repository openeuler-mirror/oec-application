import uuid
import json
import requests
#from get_token import get_oepkgs_token


def create_job(opekgstoken, repo_url, bb, build_id, buildbranch):
    """
    create a job in oepkgs.net
    :param opekgstoken: the login token of the account oepkgs_management
    :param repo_url: the repo url containing the source code of the rpm
    :param ccArch: build arch: aarch64 or x86_64
    :param tbranch: the branch of the url
    :param build_id: the repo id
    :param buildbranch: the version of openeuler to build rpm
    :return:
    jobid: the id of the job
    jobname: the name of the job
    """
    url = 'https://build.dev.oepkgs.net/api/build/task/create'
    headers = {
        'authority': 'build.dev.oepkgs.net',
        'accept': '*/*',
        'accept-language': 'en-US,en;q=0.9,zh;q=0.8,zh-CN;q=0.7',
        'cache-control': 'no-cache',
        'content-type': 'application/json',
        'cookie': 'auth_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxMDks\
        InVzZXJfbmFtZSI6ImRpZGkiLCJUWiI6IkFzaWEvU2hhbmdoYWkiLCJleHAiOjE2OTMzNzAwMzMsIml\
        hdCI6MTY5MzM2ODIzM30.szb3CB_U3DJOVG94JzHkUBqTjD0x8WM0JCJz3fNODuc',
        'dnt': '1',
        'origin': 'https://build.dev.oepkgs.net',
        'pragma': 'no-cache',
        'referer': 'https://build.dev.oepkgs.net/rpm/task/create',
        'sec-ch-ua': '"Chromium";v="116", "Not)A;Brand";v="24", "Google Chrome";v="116"',
        'sec-ch-ua-mobile': '?0',
        'sec-ch-ua-platform': '"Windows"',
        'sec-fetch-dest': 'empty',
        'sec-fetch-mode': 'cors',
        'sec-fetch-site': 'same-origin',
        'user-agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)AppleWebKit/537.36 (KHT\
        ML, like Gecko) Chrome/116.0.0.0 Safari/537.36',
        'x-auth-token': opekgstoken,
    }
    data = {
        'builder': 1,
        'jobName': str(uuid.uuid4()),
        'os': 'openEuler',
        'osFull': buildbranch,
        'ccArch': bb[0],
        'repoId': build_id,
        'scmRepo': repo_url,
        'branch': bb[1]
    }
    response = requests.post(url, headers=headers, json=data)
    #print(response.status_code)
    #print(response.text)
    res_data = json.loads(response.text)
    #print(res_data)
    if(response.status_code == 200):
        return 'success', data['jobName'], res_data['data']['jobId']
    else:
        return 'fail', 'nothing', 'nothing'
    
#token=get_oepkgs_token()
#re=create_job(token, 'https://gitee.com/src-oepkgs/solr', 'x86_64', 'openEuler-22.03-LTS-SP1', 127,'22.03-LTS-SP1')
#print(re)
