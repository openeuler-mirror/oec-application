<template>
  <el-dialog v-model="dialogVisible" :title="isAdd ? '新增芯片厂商' : '修改芯片厂商'" width="40%" :before-close="closeHandle" draggable>
    <el-form v-if="dialogVisible" ref="chipFormRef" :model="form" :rules="rules" label-width="120px">
      <el-row>
        <el-col :span="22" :xl="11">
          <el-form-item label="芯片厂商" prop="chipFactory">
            <el-input v-model.trim="form.chipFactory" placeholder="请输入芯片厂商" :disabled="!isAdd" />
          </el-form-item>
        </el-col>
        <el-col :span="22" :xl="11">
          <el-form-item label="芯片型号" prop="chipModel">
            <el-input v-model.trim="form.chipModel" placeholder="请输入芯片型号" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="22" :xl="11">
          <el-form-item label="支持的OS版本" prop="versionIds">
            <el-select v-model="versionIds" placeholder="请选择操作系统" @change="versionChange" clearable multiple collapse-tags>
              <el-option v-for="(item,index) in versionList" :key="index" :label="item.versionName" :value="item.versionId + ''" />
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
import { add, edit } from '@/common/api/manufacturer/chip';
import { ElMessage } from 'element-plus';
export default {
  name: 'chipAddOrUpdate',
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
      chipFactory: '', //芯片厂商
      chipModel: '', //芯片型号
      versionIds: '' //支持的OS版本
    });
    // 操作系统多选列表
    let versionIds = ref([]);
    // 操作系统列表
    let versionList = ref([]);
    // 表单校验规则
    const rules = {
      chipFactory: [
        { required: true, message: '请填写芯片厂商', trigger: 'blur' },
        { min: 0, max: 20, message: '请输入0-20个字符', trigger: 'blur' }
      ],
      chipModel: [
        { required: true, message: '请填写芯片型号', trigger: 'blur' },
        { min: 0, max: 20, message: '请输入0-40个字符', trigger: 'blur' }
      ],
      versionIds: [
        { required: true, message: '请选择支持的OS版本', trigger: 'change' }
      ]
    };
    // 控制表单的显示与隐藏
    let dialogVisible = ref(false);
    // 关闭表单前触发
    const closeHandle = () => {
      dialogVisible.value = false;
      resetForm();
    };
    // 表单 ref
    const chipFormRef = ref();

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
    // 确认表单
    const submitForm = () => {
      chipFormRef.value.validate(valid => {
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
      chipFormRef.value.resetFields();
      dialogVisible.value = false;
      form.value = {
        chipFactory: '', //芯片厂商
        chipModel: '', //芯片型号
        versionIds: '' //支持的OS版本
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
      chipFormRef,
      submitForm,
      resetForm,
      closeHandle,
      doAdd,
      doEdit,
      versionIds,
      versionList,
      getVersionList,
      versionChange
    };
  }
};
</script>

<style lang="scss" scoped>
  .tagItem {
    margin-right: 5px;
  }
</style>