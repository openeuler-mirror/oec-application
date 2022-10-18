<template>
    <div class="form-box">	
      <el-form size="large" class="login-content-form" :model="formData" :rules="rules" ref="loginRef"
        @keyup.enter.native="loginBtn">
          <el-form-item prop="username">
              <el-input type="text" placeholder="请输入用户名" v-model="formData.username" clearable autocomplete="off">
                <template #prefix>
                  <el-icon><User /></el-icon>
                </template>
              </el-input>
          </el-form-item>
          <el-form-item prop="password">
              <el-input :type="isShowPassword ? 'text' : 'password'" placeholder="请输入密码"
                v-model="formData.password" autocomplete="off">
                  <template #prefix>
                    <el-icon><Unlock /></el-icon>
                  </template>
                  <template #suffix>
                    <el-icon @click="isShowPassword = !isShowPassword">
                      <View v-if="isShowPassword" />
                      <Hide v-else/>
                    </el-icon>
                  </template>
              </el-input>
          </el-form-item>
          <el-form-item>
              <el-button type="primary" class="login-content-submit" round @click="loginBtn" :loading="loading">
                <span>登 录</span>
              </el-button>
          </el-form-item>
      </el-form>
    </div>
</template>

<script>
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { login as doLogin } from '@/common/api/login';
import { useStore } from 'vuex';
export default {
  name: 'accountLogin',
  setup() {
    let router = useRouter();
    let store = useStore();
    let isShowPassword = ref(false);
    let loading = ref(false); 
    const formData = reactive(
      {
        username: '',
        password: ''
      }
    );
    let loginRef = ref();
    const rules = {
      username: [
        { required: true, message: '请输入用户名', trigger: 'blur' }
      ],
      password: [
        { required: true, message: '请输入密码', trigger: 'blur' }
      ]
    };
    const loginBtn = async () => {
      await loginRef.value.validate((valid) => {
        if (valid) {
          login();
        }
      });
    };
    const login = async () => {
      loading.value = true;
      let res = null;
      try {
        res = await doLogin(formData);
      } finally {
        loading.value = false;
      }
      if (res.code === 200) {
        let token = res.data.token;
        let userInfo = res.data.userInfo;
        store.commit('userInfoModule/SET_TOKEN', token);
        store.commit('userInfoModule/SET_USERINFO', userInfo);
        router.replace('/home');
      }
    };
    return {
      loading,
      isShowPassword,
      formData,
      rules,
      loginRef,
      loginBtn
    };
  }
};
</script>

<style lang="scss">
.form-box {
  margin-top: 100px;
}
.login-content-form {
  margin-top: 20px;
  .login-content-submit {
    width: 100%;
	letter-spacing: 2px;
	font-weight: 300;
	margin-top: 15px;
  }
}
</style>