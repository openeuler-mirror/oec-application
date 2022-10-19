<template>
  <el-dialog v-model="dialogVisible" :title="isAdd ? '新增CPU厂商' : '修改CPU厂商'" width="40%" :before-close="closeHandle" draggable>
    <el-form v-if="dialogVisible" ref="cpuFormRef" :model="form" :rules="rules" label-width="120px">
      <el-row>
        <el-col :span="22" :xl="11">
          <el-form-item label="CPU厂商" prop="cpuFactory">
            <el-input v-model.trim="form.cpuFactory" placeholder="请输入CPU厂商" :disabled="!isAdd" />
          </el-form-item>
        </el-col>
        <el-col :span="22" :xl="11">
          <el-form-item label="CPU代次" prop="cpuModel">
            <el-input v-model.trim="form.cpuModel" placeholder="请输入CPU代次" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="22" :xl="11">
          <el-form-item label="架构" prop="architecture">
            <el-input v-model="form.architecture" placeholder="请输入架构" />
          </el-form-item>
        </el-col>
        <el-col :span="22" :xl="11">
          <el-form-item label="发布日期" prop="releaseTime">
            <el-date-picker v-model="form.releaseTime" type="date" placeholder="请选择发布日期" format="YYYY/MM/DD" value-format="YYYY-MM-DD" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="22">
          <el-form-item label="支持的OS版本" prop="versionIds">
            <el-select v-model="versionIds" style="width:100%;" placeholder="请选择操作系统" @change="versionChange" clearable multiple collapse-tags>
              <el-option v-for="(item, index) in versionList" :key="index" :label="item.versionName" :value="item.versionId + ''" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <div style="text-align: right; margin-right: 20px">
        <el-button @click="resetForm">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-form>
  </el-dialog>
</template>

<script>
import { ref, onMounted } from 'vue';
import { getVersionAll } from '@/common/api/full/index';
import { add, edit } from '@/common/api/manufacturer/cpu';
import { ElMessage } from 'element-plus';
import { isNumberletter } from '@/common/utils/';

export default {
  name: 'cpuAddOrUpdate',
  // 传入参数集合
  props: {
    isAdd: {
      type: Boolean,
      required: true
    }
  },
  // 自定义事件
  emits: ['refreshList'],
  setup (props, context) {
    // 表单对象
    let form = ref({
      cpuFactory: '', // CPU厂商
      cpuModel: '', // CPU代次
      architecture: '', // 架构
      releaseTime: '', //发布日期
      versionIds: ''// 支持的OS版本
    });
    const validateCpuModel = (rule, value, callback) => {
      if (!isNumberletter(value)) {
        return callback(new Error('架构只能由字母、数字、_组成'));
      }
      callback();
    };
    const validateCpuFactory = (rule, value, callback) => {
      let reg = /^([\u4e00-\u9fa5a-zA-Z0-9])+$/;
      if (!reg.test(value)) {
        return callback(new Error('CPU厂商只能由汉字、字母、数字组成'));
      }
      callback();
    };
    // 表单校验规则
    const rules = {
      cpuFactory: [
        { required: true, message: '请填写CPU厂商', trigger: 'blur' },
        { min: 0, max: 20, message: '请输入0-20个字符', trigger: 'blur' },
        { required: true, validator: validateCpuFactory, trigger: 'blur' }
      ],
      cpuModel: [
        { required: true, message: '请填写CPU代次', trigger: 'blur' },
        { min: 0, max: 20, message: '请输入0-20个字符', trigger: 'blur' }
      ],
      architecture: [
        { required: true, message: '请填写架构', trigger: 'blur' },
        { min: 0, max: 20, message: '请输入0-20个字符', trigger: 'blur' },
        { validator: validateCpuModel, trigger: 'blur' }
      ],
      releaseTime: [
        { required: true, message: '请选择发布日期', trigger: 'change' }
      ],
      versionIds: [
        { required: true, message: '请选择支持的OS版本', trigger: 'change' }
      ]
    };
    // 操作系统选择项
    let versionIds = ref([]);
    let versionList = ref([]);
    // 控制表单的显示与隐藏
    let dialogVisible = ref(false);
    // 关闭表单前触发
    const closeHandle = () => {
      dialogVisible.value = false;
      resetForm();
    };

    // 获取操作系统列表
    const getVersionList = () => {
      getVersionAll()
        .then(res => {
          if (res.code === 200) {
            versionList.value = res.data;
          }
        })
        .catch(e => {
          return Promise.reject(e);
        });
    };

    // 操作系统发生改变
    const versionChange = val => {
      form.value.versionIds = val.join(',');
    };
    // 表单 ref
    const cpuFormRef = ref();
    // 确认表单
    const submitForm = () => {
      cpuFormRef.value.validate(valid => {
        if (valid) {
          // 新增操作
          if (props.isAdd) {
            doAdd();
          } else {
            // 编辑操作
            doEdit();
          }
        }
      });
    };
    // 新增
    const doAdd = () => {
      add(form.value).then(res => {
        if (res.code === 200) {
          ElMessage({
            message: res.msg || '新增成功',
            grouping: true,
            type: 'success'
          });
          context.emit('refreshList');
          resetForm();
        }
      });
    };
    // 编辑
    const doEdit = () => {
      edit(form.value).then(res => {
        if (res.code === 200) {
          ElMessage({
            message: res.msg || '修改成功',
            grouping: true,
            type: 'success'
          });
          context.emit('refreshList');
          resetForm();
        }
      });
    };
    // 重置表单
    const resetForm = () => {
      cpuFormRef.value.resetFields();
      dialogVisible.value = false;
      form.value = {
        cpuFactory: '', // CPU厂商
        cpuModel: '', // CPU型号
        architecture: '', // 架构
        releaseTime: '', //发布日期
        versionIds: ''// 支持的OS版本
      };
      versionIds.value = [];
    };
    onMounted(() => {
      // 获取操作系统列表
      getVersionList();
    });
    return {
      dialogVisible,
      form,
      rules,
      cpuFormRef,
      versionIds,
      versionList,
      getVersionList,
      versionChange,
      submitForm,
      resetForm,
      closeHandle,
      doAdd,
      doEdit
    };
  }
};
</script>

<style lang="scss" scoped>
  .tagItem {
    margin-right: 5px;
  }
</style>