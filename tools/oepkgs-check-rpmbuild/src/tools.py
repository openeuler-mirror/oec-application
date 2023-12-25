import requests 


def get_requests(method, url, body=None, timeout=30, obj=None):
    """
    http request
    :param method: http method
    :param url: http[s] schema
    :param body: json
    :param timeout: second
    :param obj: callback object, support list/dict/object
    :return:
    """
    get_function = getattr(requests, method.lower())
    result = get_function(url, json=body, timeout=timeout)
    
    result_list = [requests.codes.ok, requests.codes.created, requests.codes.no_content]
    if result.status_code not in result_list:
        return 2

    if obj is not None:
        if isinstance(obj, dict):
            obj.update(result.json())
    return 0


def get_branch(gitbranch):
    """
    get build branch
    :param gitbranch: the branch of the repo
    :return:
    the build branch,which version of openeuler
    """
    branch = ''
    if gitbranch == 'master':
        branch = '20.03-LTS-SP1'
    else:
        branch = gitbranch[10:]
    return branch

#t1=get_branch('openEuler-20.03-LTS-SP3')
#print(t1)