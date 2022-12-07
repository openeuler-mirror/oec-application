<template>
  <el-dialog v-model="dialogVisible" :title="isAdd ? '新增整机厂商' : '修改整机厂商'" width="40%" :before-close="closeHandle" draggable>
    <el-form v-if="dialogVisible" ref="wholeFormRef" :model="form" :rules="rules" label-width="110px">
      <el-form-item label="整机厂商" prop="wholeFactory">
        <el-input v-model.trim="form.wholeFactory" :disabled="!isAdd" placeholder="请输入整机厂商" style="width: 90%" />
      </el-form-item>
      <el-row>
        <el-col :span="11">
          <el-form-item label="CPU厂商" prop="cpuFactory">
            <el-select v-model="form.cpuFactory" placeholder="请选择CPU厂商" @change="cpuFactoryChanged" clearable>
              <el-option v-for="(item,index) in cpuFactoryList" :key="index" :label="item" :value="item" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="CPU代次" prop="cpuModel">
            <el-select v-model="form.cpuModel" size="default" placeholder="请选择CPU代次" clearable>
              <el-option v-for="(item, index) in cpuModelList" :key="index" :label="item" :value="item" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="11">
          <el-form-item label="典型机型" prop="hardwareModel">
            <el-input v-model="form.hardwareModel" placeholder="请输入典型机型" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="22">
          <el-form-item label="扩展机型型号" prop="extendModel">
            <el-tag v-for="(item,index) in dynamicTags" :key="index" class="tagItem" closable @close="handleClose(item)">
              {{ item }}
            </el-tag>
            <el-input v-if="inputVisible" ref="InputRef" v-model="inputValue" size="small" type="text" maxlength="40" @keyup.enter="handleInputConfirm" @blur="handleInputConfirm" style="width: 150px" show-word-limit oninput="this.value=this.value.replace(/,/g,'')" />
            <el-button v-else class="button-new-tag ml-1" size="small" @click="showInput">+</el-button>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="11">
          <el-form-item label="接口人" prop="interfacePerson">
            <el-input v-model="form.interfacePerson" placeholder="请输入接口人" />
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="对应BD" prop="middleman">
            <el-input v-model="form.middleman" placeholder="请输入对应BD" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="联系方式" prop="contact">
        <el-input v-model="form.contact" placeholder="请输入手机号/微信号/邮箱，并以'/'分隔" type="textarea" style="width: 90%" maxlength="50" show-word-limit />
      </el-form-item>
      <div style="text-align: right; margin-right: 20px">
        <el-button @click="resetForm">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-form>
  </el-dialog>
</template>

<script>
import { ref, nextTick, onMounted } from 'vue';
import { getCpuFactoryNames, getCpuFactoryModel } from '@/common/api/manufacturer/cpu';
import { add, edit } from '@/common/api/manufacturer/whole';
import { ElMessage } from 'element-plus';
export default {
  name: 'wholeAddOrUpdate',
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
      wholeFactory: '', // 整机厂商
      cpuFactory: '', // CPU厂商
      cpuModel: '', // CPU代次
      hardwareModel: '', // 典型机型
      createTime: '', // 更新时间
      extendModel: '', // 型号
      interfacePerson: '', // 接口人
      contact: '', // 电话
      middleman: '' // 对应BD
    });
    // CPU厂商列表
    let cpuFactoryList = ref([]);
    // CPU代次列表
    let cpuModelList = ref([]);
    // 扩展机型列表
    let dynamicTags = ref([]);
    // 扩展机型动态 input 值
    let inputValue = ref('');
    // 联系方式校验规则
    const validateContant = (rule, value, callback) => {
      if (!value) {
        return callback();
      }
      callback();
    };
    // 表单校验规则
    const rules = {
      wholeFactory: [
        { required: true, message: '请填写整机厂商', trigger: 'blur' },
        { min: 0, max: 20, message: '请输入0-20个字符', trigger: 'blur' }
      ],
      cpuFactory: [
        { required: true, message: '请选择CPU厂商', trigger: 'change' }
      ],
      cpuModel: [{ required: true, message: '请选择CPU代次', trigger: 'change' }],
      hardwareModel: [
        { required: true, message: '请填写典型机型', trigger: 'blur' },
        { min: 0, max: 40, message: '请输入0-40个字符', trigger: 'blur' }
      ],
      interfacePerson: [
        { min: 0, max: 20, message: '请输入0-20个字符', trigger: 'blur' }
      ],
      contact: [
        { min: 0, max: 50, message: '请输入0-50个字符', trigger: 'blur' },
        { validator: validateContant, trigger: 'blur' }
      ],
      middleman: [
        { min: 0, max: 20, message: '请输入0-20个字符', trigger: 'blur' }
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
    const wholeFormRef = ref();
    // 控制 input 的显示与隐藏
    const inputVisible = ref(false);
    const InputRef = ref();
    // 点击 + 号按钮显示输入框，并自动获取焦点
    const showInput = () => {
      inputVisible.value = true;
      nextTick(() => {
        InputRef.value.focus();
      });
    };
    // 删除 tag
    const handleClose = tag => {
      dynamicTags.value.splice(dynamicTags.value.indexOf(tag), 1);
      form.value.extendModel = dynamicTags.value.join(',');
    };
    // enter 键按下以及失焦触发
    const handleInputConfirm = () => {
      if (inputValue.value.trim().length > 0) {
        dynamicTags.value.push(inputValue.value);
      }
      inputVisible.value = false;
      inputValue.value = '';
      form.value.extendModel = dynamicTags.value.join(',');
    };
    // 获取CPU厂商列表
    const getCpuFactoryList = () => {
      getCpuFactoryNames()
        .then((res) => {
          cpuFactoryList.value = res.data;
        })
        .catch(e => {
          return Promise.reject(e);
        });
    };
    // 根据CPU厂商获取CPU代次列表
    const getChipModelList = (val) => {
      getCpuFactoryModel(val)
        .then((res) => {
          cpuModelList.value = res.data;
        })
        .catch(e => {
          return Promise.reject(e);
        });
    };
    // cpu厂商下拉选中
    const cpuFactoryChanged = () => {
      cpuModelList.value = [];
      form.value.cpuModel = '';
      getChipModelList(form.value.cpuFactory);
    };
    // 确认表单
    const submitForm = () => {
      wholeFormRef.value.validate(valid => {
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
      wholeFormRef.value.resetFields();
      dialogVisible.value = false;
      form.value = {
        wholeFactory: '', // 整机厂商
        cpuFactory: '', // CPU厂商
        cpuModel: '', // CPU代次
        hardwareModel: '', // 典型机型
        createTime: '', // 更新时间
        extendModel: '', // 型号
        interfacePerson: '', // 接口人
        contact: '', // 电话
        middleman: '' // 对应BD
      };
      dynamicTags.value = [];
      cpuModelList.value = [];
    };
    onMounted(() => {
      // 获取CPU厂商
      getCpuFactoryList();
    });
    return {
      dialogVisible,
      form,
      rules,
      wholeFormRef,
      submitForm,
      resetForm,
      closeHandle,
      cpuFactoryChanged,
      doAdd,
      doEdit,
      inputVisible,
      InputRef,
      handleClose,
      handleInputConfirm,
      showInput,
      dynamicTags,
      inputValue,
      cpuModelList,
      cpuFactoryList,
      getChipModelList
    };
  }
};
</script>

<style lang="scss" scoped>
  .tagItem {
    margin-right: 5px;
  }
</style>