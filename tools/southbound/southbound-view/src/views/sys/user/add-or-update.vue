<template>
  <el-dialog v-model="dialogVisible" :before-close="closeHandle" :close-on-click-modal="false" draggable :title="type === 'add' ? '新增系统用户' : '修改系统用户'" width="40vw">
    <el-form ref="formRef" :model="formData" :rules="rules" label-width="145px" status-icon>
      <el-form-item label="用户名" prop="username" style="width: 80%">
        <el-input type="text" v-model="formData.username" placeholder="请输入用户名" :disabled="formData.role === 0" />
      </el-form-item>
      <el-form-item label="密码" prop="password" style="width: 80%">
        <el-input type="text" v-model="formData.password" placeholder="请输入密码" />
      </el-form-item>
      <el-form-item label="确认密码" prop="rePassword" style="width: 80%">
        <el-input type="text" v-model="formData.rePassword" placeholder="请再次输入密码" />
      </el-form-item>
      <el-form-item label="角色" prop="role" v-if="formData.role !== 0">
        <el-radio-group v-model="formData.role">
          <el-radio :label="1">管理员</el-radio>
          <el-radio :label="2">普通用户</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item style="width:90%;">
        <div class="form-btn">
          <el-button @click="cancelForm()">取 消</el-button>
          <el-button type="primary" @click="submitForm(formRef)">确 定</el-button>
        </div>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
import { ref } from 'vue';
import { ElMessage } from 'element-plus';
import { add, edit } from '@/common/api/sys/user';
export default {
  name: 'sysUserAddOrUpdate',
  emits: ['refreshList'],
  setup (_, { emit }) {
    let formData = ref({});
    const initForm = () => {
      formData.value = {
        id: undefined,
        username: '',
        password: '',
        rePassword: '',
        role: 1
      };
    };
    initForm();
    const validatePass = (rule, value, callback) => {
      if (!value) return callback(new Error('请再次输入密码'));
      if (value !== formData.value.password) {
        return callback(new Error('两次输入的密码不一致'));
      }
      callback();
    };
    const rules = {
      username: [
        { required: true, message: '请输入用户名', trigger: 'blur' },
        { min: 1, max: 20, message: '请输入1-20个字符', trigger: 'blur' }
      ],
      password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 1, max: 20, message: '请输入1-20个字符', trigger: 'blur' }
      ],
      rePassword: [
        { required: true, validator: validatePass, trigger: 'blur' }
      ]
    };

    let dialogVisible = ref(false);
    let type = ref('add');
    const formRef = ref();
    const open = (recType, rowdata) => {
      type.value = recType;
      if (recType === 'add') {
        initForm();
      } else {
        formData.value = rowdata;
      }
      dialogVisible.value = true;
    };
    const close = () => {
      dialogVisible.value = false;
    };
    const closeHandle = () => {
      formRef.value.resetFields();
      dialogVisible.value = false;
    };

    const submitForm = async (formEl) => {
      await formEl.validate((valid) => {
        if (valid) {
          if (type.value === 'add') {
            doAdd(formData.value);
          } else {
            doEdit(formData.value);
          }
        }
      });
    };
    const doAdd = async (data) => {
      let res = await add(data);
      if (res.code === 200) {
        ElMessage.success('添加成功');
        dialogVisible.value = false;
        emit('refreshList');
      }
    };
    const doEdit = async (data) => {
      let res = await edit(data);
      if (res.code === 200) {
        ElMessage.success('修改成功');
        dialogVisible.value = false;
        emit('refreshList');
      }
    };
    const cancelForm = () => {
      formRef.value.resetFields();
      dialogVisible.value = false;
      initForm();
    };
    return {
      dialogVisible,
      formData,
      rules,
      type,
      open,
      close,
      formRef,
      submitForm,
      cancelForm,
      closeHandle
    };
  }
};
</script>

<style lang="scss" scoped>
.form-btn {
  flex: 1;
  display: flex;
  justify-content: flex-end;
}
</style>