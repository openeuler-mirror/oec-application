<template>
  <el-dialog v-model="dialogVisible" :before-close="closeHandle" :close-on-click-modal="false" draggable 
    :title="type === 'add' ? '新增版本计划' : '修改版本计划'" width="40vw">
    <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="150px"
        status-icon
    >
        <el-form-item label="版本名称" prop="versionName" style="width: 80%">
          <el-input v-model="form.versionName" placeholder="请填写版本名称" maxlength="50" show-word-limit/>
        </el-form-item>
        <el-form-item label="技术验证" prop="alphaRangeDate" style="width: 80%">
          <el-date-picker
            v-model="form.alphaRangeDate"
            value-format="YYYY-MM-DD"
            size="small"
            type="daterange"
            range-separator="-"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
          />
        </el-form-item>
        <el-form-item label="技术验证说明" prop="alphaDetail" style="width: 90%">
          <WangEditor v-model="form.alphaDetail" placeholder="请输入技术验证说明" :height="240"></WangEditor>
        </el-form-item>
        <el-form-item label="版本发布前" prop="betaRangeDate" style="width: 80%">
          <el-date-picker
            v-model="form.betaRangeDate"
            value-format="YYYY-MM-DD"
            size="small"
            type="daterange"
            range-separator="-"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
          />
        </el-form-item>
        <el-form-item label="版本发布后" prop="releaseRangeDate" style="width: 80%">
          <el-date-picker
            v-model="form.releaseRangeDate"
            value-format="YYYY-MM-DD"
            size="small"
            type="daterange"
            range-separator="-"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
          />
        </el-form-item>        
        <el-form-item style="width: 90%">
          <div class="form-btn">
            <el-button @click="cancelForm()">取 消</el-button>
            <el-button type="primary" @click="submitForm(formRef)">确 定</el-button>
          </div>
        </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
import {  ref } from 'vue';
import { ElMessage } from 'element-plus';
import { add, edit } from '@/common/api/versionPlan';
import { isIncludeCh } from '@/common/utils';
import WangEditor from '@/components/WangEditor';

export default {
  name: 'boardAddOrUpdate',
  components:  {WangEditor },
  emits: ['refreshList', 'close'],
  setup(_, {emit}) {
    let form = ref();
    const initForm = () => {
      form.value = {
        versionId: undefined,
        versionName: '', 
        alphaDetail: '',
        remark: '',
        alphaRangeDate: [],
        betaRangeDate: [],
        releaseRangeDate: [],
        alphaStages: ''
      };
    };
    const validatePass = (rule, value, callback) => {
      if (isIncludeCh(value)) {
        callback(new Error('版本名称不能包含中文'));
      } else {
        callback();
      }
    };
    const validateBetaRangeDate = (rule, value, callback) => {
      let alphaEndTimestamp = new Date(form.value.alphaRangeDate[1]).getTime();
      let betaStartTimestamp = new Date(value[0]).getTime();
      if (betaStartTimestamp < alphaEndTimestamp) {
        return callback(new Error('版本发布前阶段不能小于技术验证阶段'));
      }
      callback();
    };
    const validateReleaseRangeDate = (rule, value, callback) => {
      let betaEndTimestamp = new Date(form.value.betaRangeDate[1]).getTime();
      let releaseStartTimestamp = new Date(value[0]).getTime();
      if (releaseStartTimestamp < betaEndTimestamp) {
        return callback(new Error('版本发布后阶段不能小于版本发布前阶段'));
      }
      callback();
    };
    initForm();
    const rules = {
      versionName: [
        { required: true, message: '请填写版本名称', trigger: 'blur' },
        { min: 0, max: 50, message: '请输入0-50个字符', trigger: 'blur' },
        { validator: validatePass, trigger: 'blur' }
      ],
      alphaRangeDate: [
        { required: true, message: '请选择技术验证阶段', trigger: 'blur' }
      ],
      betaRangeDate: [
        { required: true, message: '请选择版本发布前阶段', trigger: 'blur' },
        { validator: validateBetaRangeDate, trigger: 'blur' }
      ],  
      releaseRangeDate: [
        { required: true, message: '请选择版本发布后阶段', trigger: 'blur' },
        { validator: validateReleaseRangeDate, trigger: 'blur' }
      ]               
    };
    let dialogVisible = ref(false);
    let type = ref('add');
    const open = (recType, rowdata) => {
      type.value = recType;
      if (recType !== 'add') {
        form.value = {...rowdata};
      } else {
        initForm();
      }
      dialogVisible.value = true;
    };
    const close = () => {
      dialogVisible.value = false;
    };
    const closeHandle = () => {
      formRef.value.resetFields();
      dialogVisible.value = false;
      emit('close');
    };
    const formRef = ref();
    const submitForm = async (formEl) => {
      await formEl.validate((valid) => {
        if (valid) {
          form.value.alphaStartDate = form.value.alphaRangeDate[0];
          form.value.alphaEndDate = form.value.alphaRangeDate[1];
          form.value.betaStartDate = form.value.betaRangeDate[0];
          form.value.betaEndDate = form.value.betaRangeDate[1];
          form.value.releaseStartDate = form.value.releaseRangeDate[0];
          form.value.releaseEndDate = form.value.releaseRangeDate[1];
          if (type.value === 'add') {
            doAdd();
          } else {
            doEdit();
          }
        }
      });
    };
    const doAdd = async () => {
      let res = await add(form.value);
      if (res.code === 200) {
        ElMessage.success('添加成功');
        dialogVisible.value = false;
        emit('refreshList');
      }
    };
    const doEdit = async () => {
      let res = await edit(form.value.id, form.value);
      if (res.code === 200) {
        ElMessage.success('修改成功');
        dialogVisible.value = false;
        emit('close');
        emit('refreshList');
      }
    };
    const cancelForm = () => {
      formRef.value.resetFields();
      dialogVisible.value = false;
      emit('close');
      initForm();
    };
   
    return {
      dialogVisible,
      form,
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
:deep(.el-button+.el-button) {
  margin-left: 12px!important;
}
.form-btn {
  flex: 1;
  display: flex;
  justify-content: flex-end;
}
</style>