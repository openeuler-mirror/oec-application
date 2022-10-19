<template>
  <el-dialog v-model="dialogVisible" :title="isAdd ? '新增驱动管理' : '修改驱动管理'" width="40%" :before-close="closeHandle" draggable>
    <el-form v-if="dialogVisible" ref="chipFormRef" :model="form" :rules="rules" label-width="120px">
      <el-row>
        <el-col :span="11">
          <el-form-item label="芯片厂商" prop="chipFactory">
            <el-select v-model.trim="form.chipFactory" placeholder="请选择芯片厂商" clearable collapse-tags :disabled="!isAdd">
              <el-option v-for="(item,index) in chipFactoryList" :key="index" :label="item" :value="item" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="驱动名称" prop="driverName">
            <el-input v-model.trim="form.driverName" placeholder="请输入驱动名称" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="22" :xl="11">
          <el-form-item label="内核驱动发布" prop="kernelDriverPublish">
            <el-radio-group v-model="form.kernelDriverPublish" @change="kPublishChanged">
              <el-radio v-for="item in exteriorRadio" :key="item.id" :label="item.label">{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-if="showkernelDriverInfo">
        <el-col :span="22" :xl="11">
          <el-form-item label="内核驱动版本" prop="kernelDriverVersion">
            <el-input v-model.trim="form.kernelDriverVersion" placeholder="请输入内核驱动版本" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="22" :xl="11">
          <el-form-item label="外部驱动发布" prop="exteriorDriverPublish">
            <el-radio-group v-model="form.exteriorDriverPublish" @change="ePublishChanged">
              <el-radio v-for="item in exteriorRadio" :key="item.id" :label="item.label">{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-if="showExterDriverInfo">
        <el-col :span="22" :xl="11">
          <el-form-item label="外部驱动版本" prop="exteriorDriverVersion">
            <el-input v-model.trim="form.exteriorDriverVersion" placeholder="请输入外部驱动版本" />
          </el-form-item>
        </el-col>
        <el-col :span="22" :xl="11">
          <el-form-item label="外部发布日期" prop="exteriorDriverPublishTime">
            <el-date-picker v-model="form.exteriorDriverPublishTime" type="date" placeholder="请选择外部驱动发布日期" format="YYYY/MM/DD" value-format="YYYY-MM-DD" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="22" :xl="11">
          <el-form-item label="支持的OS版本" prop="versionId">
            <el-select v-model="versionIds" placeholder="请选择操作系统" @change="versionChange" clearable :disabled="!isAdd">
              <el-option v-for="(item,index) in versionList" :key="index" :label="item.versionName" :value="item.versionId + ''" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="官网驱动链接" prop="webDriverUrl">
        <el-input type="textarea" v-model.trim="form.webDriverUrl" placeholder="请输入官网驱动链接,多个请以','分割" style="width: 90%" maxlength="1000" show-word-limit />
      </el-form-item>
      <el-form-item label="软件所驱动链接" prop="allDriverUrl">
        <el-input type="textarea" v-model.trim="form.allDriverUrl" placeholder="请输入软件所驱动链接,多个请以','分割" style="width: 90%" maxlength="1000" show-word-limit />
      </el-form-item>
      <div style="text-align: right; margin-right: 20px">
        <el-button @click="resetForm">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-form>
  </el-dialog>
</template>

<script>
import { ref, onMounted } from 'vue';
import { add, edit } from '@/common/api/manufacturer/driver';
import { ElMessage } from 'element-plus';
export default {
  name: 'driverAddOrUpdate',
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
      driverName: '', //驱动名称
      kernelDriverPublish: '未发布', //内核驱动发布
      kernelDriverVersion: '', //内核驱动版本
      exteriorDriverPublish: '未发布', //外部驱动发布
      exteriorDriverPublishTime: '',//外部驱动发布日期
      exteriorDriverVersion: '', //外核驱动版本
      versionId: '', //支持的OS版本
      webDriverUrl: '', //官网驱动链接
      allDriverUrl: '' //软件所驱动链接
    });

    // 芯片厂商列表
    let chipFactoryList = ref([]);
    // 操作系统多选列表
    let versionIds = ref('');
    // 操作系统列表
    let versionList = ref([]);
    // 外核驱动发布单选按钮
    let exteriorRadio = [
      { id: 0, label: '未发布' },
      { id: 1, label: '已发布' }
    ];
    // 内核驱动版本显示
    let showkernelDriverInfo = ref(0);
    // 触发内核驱动发布事件
    const kPublishChanged = (val) => {
      exteriorRadio.forEach((item) => {
        if (item.label === val) {
          showkernelDriverInfo.value = item.id;
        }
      });
      if (!showkernelDriverInfo.value) {
        form.value.kernelDriverVersion = '';
      }
    };
    // 外核驱动版本显示
    let showExterDriverInfo = ref(0);
    // 触发外核驱动发布事件
    const ePublishChanged = (val) => {
      exteriorRadio.forEach((item) => {
        if (item.label === val) {
          showExterDriverInfo.value = item.id;
        }
      });
      if (!showExterDriverInfo.value) {
        form.value.exteriorDriverVersion = '';
        form.value.exteriorDriverPublishTime = '';
      }
    };

    // 表单校验规则
    const rules = {
      chipFactory: [
        { required: true, message: '请选择芯片厂商', trigger: 'change' }
      ],
      driverName: [
        { required: true, message: '请填写驱动名称', trigger: 'blur' },
        { min: 0, max: 20, message: '请输入0-20个字符', trigger: 'blur' }
      ],
      kernelDriverPublish: [
        { required: true, message: '请选择内核驱动发布', trigger: 'change' }
      ],
      kernelDriverVersion: [
        { required: true, message: '请填写内核驱动版本', trigger: 'blur' },
        { min: 0, max: 20, message: '请输入0-20个字符', trigger: 'blur' }
      ],
      exteriorDriverPublish: [
        { required: true, message: '请选择外核驱动发布', trigger: 'change' }
      ],
      exteriorDriverVersion: [
        { required: true, message: '请填写外核驱动版本', trigger: 'blur' },
        { min: 0, max: 20, message: '请输入0-20个字符', trigger: 'blur' }
      ],
      exteriorDriverPublishTime: [
        { required: true, message: '请填写外核驱动发布', trigger: 'change' }
      ],
      versionId: [
        { required: true, message: '请选择支持的OS版本', trigger: 'change' }
      ],
      webDriverUrl: [
        { min: 0, max: 1000, message: '请输入0-1000个字符', trigger: 'blur' }
      ],
      allDriverUrl: [
        { min: 0, max: 1000, message: '请输入0-1000个字符', trigger: 'blur' }
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

    // 操作系统发生改变
    const versionChange = val => {
      form.value.versionId = val;
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
        driverName: '', //驱动名称
        kernelDriverPublish: '未发布', //内核驱动发布
        kernelDriverVersion: '', //内核驱动版本
        exteriorDriverPublish: '未发布', //外部驱动发布
        exteriorDriverPublishTime: '', //外部驱动发布日期
        exteriorDriverVersion: '', //外核驱动版本
        versionId: '', //支持的OS版本
        webDriverUrl: '', //官网驱动链接
        allDriverUrl: '' //软件所驱动链接
      };
      versionIds.value = '';
      showkernelDriverInfo.value = 0;
      showExterDriverInfo.value = 0;
    };
    onMounted(() => {
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
      chipFactoryList,
      versionIds,
      versionList,
      versionChange,
      exteriorRadio,
      showkernelDriverInfo,
      showExterDriverInfo,
      ePublishChanged,
      kPublishChanged
    };
  }
};
</script>

<style lang="scss" scoped>
  .tagItem {
    margin-right: 5px;
  }
</style>