<template>
  <div class="header">
    <p class="panel-name">南向看板系统</p>
    <div>
      <el-dropdown trigger="hover" @command="handleMore">
        <span class="el-dropdown-link mr-10">
          <span>{{formData.username}}</span>
          <el-icon color="#fff">
            <ArrowDown></ArrowDown>
          </el-icon>
        </span>
        <template v-slot:dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="modifyPassword">修改密码</el-dropdown-item>
            <el-dropdown-item command="loginOut">退出</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
    <el-dialog v-model="dialogVisible" :close-on-click-modal="false" :before-close="closeHandle" draggable title="修改密码" width="40vw">
      <el-form ref="formRef" :model="formData" :rules="rules" label-width="145px" status-icon>
        <el-form-item label="用户名" prop="username" style="width: 80%">
          <el-input type="text" v-model="formData.username" disabled placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="旧密码" prop="oldPassword" style="width: 80%">
          <el-input :type="isShowOldPassword ? 'text' : 'password'" v-model="formData.oldPassword" a autocomplete="password" placeholder="请输入旧密码">
            <template #suffix>
              <el-icon @click="isShowOldPassword = !isShowOldPassword">
                <View v-if="isShowOldPassword" />
                <Hide v-else />
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword" style="width: 80%">
          <el-input :type="isShowNewPassword ? 'text' : 'password'" v-model="formData.newPassword" placeholder="请输入新密码">
            <template #suffix>
              <el-icon @click="isShowNewPassword = !isShowNewPassword">
                <View v-if="isShowNewPassword" />
                <Hide v-else />
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="确认新密码" prop="reNewPassword" style="width: 80%">
          <el-input :type="isShowReNewPassword ? 'text' : 'password'" v-model="formData.reNewPassword" placeholder="请再次输入新密码">
            <template #suffix>
              <el-icon @click="isShowReNewPassword = !isShowReNewPassword">
                <View v-if="isShowReNewPassword" />
                <Hide v-else />
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item style="width: 90%">
          <div class="form-btn">
            <el-button @click="cancelForm()">取 消</el-button>
            <el-button type="primary" @click="submitForm(formRef)">确 定</el-button>
          </div>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { ref, markRaw, reactive, toRefs } from 'vue';
import { useStore } from 'vuex';
import { ElMessageBox, ElMessage } from 'element-plus';
import { WarningFilled } from '@element-plus/icons-vue';
import { modifyPassword, logout } from '@/common/api/login';
import router from '../../router';
export default {
  name: 'navHeader',
  setup () {
    const store = useStore();
    const formData = ref({});
    const initForm = () => {
      formData.value = {
        username: store.state.userInfoModule.userInfo.username,
        oldPassword: '',
        newPassword: '',
        reNewPassword: ''
      };
    };
    initForm();
    let showPassword = reactive({
      isShowOldPassword: false,
      isShowNewPassword: false,
      isShowReNewPassword: false
    });
    let formRef = ref();
    const validatePass = (rule, value, callback) => {
      if (!value) return callback(new Error('请再次输入新密码'));
      if (value !== formData.value.newPassword) {
        return callback(new Error('两次输入的密码不一致'));
      }
      callback();
    };
    const rules = {
      username: [
        { required: true, message: '请输入用户名', trigger: 'blur' }
      ],
      oldPassword: [
        { required: true, message: '请输入旧密码', trigger: 'blur' }
      ],
      newPassword: [
        { required: true, message: '请输入新密码', trigger: 'blur' }
      ],
      reNewPassword: [
        { required: true, validator: validatePass, trigger: 'blur' }
      ]
    };
    let dialogVisible = ref(false);
    const handleMore = async (e) => {
      if (e === 'modifyPassword') {
        dialogVisible.value = true;
      } else {
        await ElMessageBox.confirm('确定退出吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'info',
          icon: markRaw(WarningFilled)
        });
        await logout();
        store.commit('userInfoModule/CLEAR');
        store.commit('routesListModule/CLEAR_PAGE');
        router.options.dynamicHandled = false;
        router.replace('/login');
      }
    };
    const cancelForm = () => {
      formRef.value.resetFields();
      dialogVisible.value = false;
      initForm();
    };
    // 修改密码
    const submitForm = async () => {
      await formRef.value.validate((valid) => {
        if (valid) {
          modify();
        }
      });
    };
    const modify = async () => {
      let res = await modifyPassword(formData.value);
      if (res.code === 200) {
        ElMessage.success(res.data);
        store.commit('userInfoModule/CLEAR');
        store.commit('routesListModule/CLEAR_PAGE');
        router.options.dynamicHandled = false;
        router.replace('/login');
        dialogVisible.value = false;
      }
    };
    const closeHandle = () => {
      formRef.value.resetFields();
      dialogVisible.value = false;
      initForm();
    };
    return {
      formRef,
      formData,
      rules,
      handleMore,
      dialogVisible,
      cancelForm,
      submitForm,
      closeHandle,
      ...toRefs(showPassword)
    };
  }
};
</script>

<style lang="scss" scoped>
  .header {
    position: absolute;
    left: 0;
    right: 0;
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 60px;
    padding: 0 50px;
    background-color: rgb(51, 56, 84);
    color: #fff;
    .panel-name {
      font-size: 20px;
      letter-spacing: 2px;
    }
    .el-dropdown-link {
      color: #fff;
      cursor: pointer;
      display: flex;
      align-items: center;
      & > span {
        margin-right: 3px;
      }
    }
    .dropdown-link {
      position: relative;
      top: 2px;
    }
  }
  .form-btn {
    flex: 1;
    display: flex;
    justify-content: flex-end;
  }
</style>