from selenium import webdriver
from selenium.webdriver.common.by import By


def get_oepkgs_token():
    '''
    use selenium to emulate login
    account: oepkgs_management
    :return:
    oepkgs_token: the login token of the account oepkgs_management
    '''
    options = webdriver.ChromeOptions()
    #options.add_argument("--auto-open-devtools-for-tabs")
    options.add_argument('--headless')
    options.add_argument('--no-sandbox')
    options.add_argument('--disable-gpu')
    options.add_argument('--disable-dev-shm-usage')
    options.add_argument("--auto-open-devtools-for-tabs")

    driver = webdriver.Chrome(options=options)
    driver.get("https://build.dev.oepkgs.net/rpm/task") 
    try:
        driver.implicitly_wait(10)
        username_input = driver.find_element(By.NAME, "username")
        password_input = driver.find_element(By.NAME, "password")
        username_input.send_keys("oepkgs_management@163.com")
        password_input.send_keys("123456Aa!")
        login_button = driver.find_element(By.ID, "kc-login")
        login_button.click()
        cookies = driver.get_cookies()
        for cookie in cookies:
            None
            #print(cookie['name'], cookie['value'])
    finally:
        driver.quit()
    return cookie['value']

#get_oepkgs_token()