<template>
  <el-dialog v-model="dialogVisible" :title="isAdd ? '新增板卡' : '修改板卡'" width="40%" :before-close="closeHandle" draggable>
    <el-form v-if="dialogVisible" ref="boardFormRef" :model="form" :rules="rules" label-width="120px">
      <el-form-item label="芯片厂商" prop="chipFactory">
        <el-select style="width: 90%" v-model="form.chipFactory" placeholder="请选择芯片厂商" @change="chipFactoryChanged" :disabled="!isAdd" clearable>
          <el-option v-for="(item,index) in chipFactoryList" :key="index" :label="item" :value="item" />
        </el-select>
      </el-form-item>
      <el-row>
        <el-col :span="11">
          <el-form-item label="板卡类型" prop="boardType">
            <el-input v-model.trim="form.boardType" placeholder="请输入板卡类型" />
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="芯片型号" prop="chipModel">
            <el-select v-model="form.chipModel" placeholder="请选择芯片型号" clearable>
              <el-option v-for="(item,index) in chipModelList" :key="index" :label="item" :value="item" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="11">
          <el-form-item label="典型板卡型号" prop="typicalBoardModel">
            <el-input v-model.trim="form.typicalBoardModel" placeholder="请输入典型板卡型号" maxlength="40" show-word-limit />
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="物料编码" prop="boardItem">
            <el-input v-model="form.boardItem" placeholder="请输入物料编码" maxlength="10" show-word-limit />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="11">
          <el-form-item label="x86_64优先级" prop="x86Priority">
            <el-input-number v-model="form.x86Priority" :min="1" :max="5" />
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="aarch64优先级" prop="armPriority">
            <el-input-number v-model="form.armPriority" :min="1" :max="5" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="22">
          <el-form-item label="型号/物料编码" prop="extendBoardModelArr">
            <table-input v-model="extendBoardModelArr"></table-input>
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
          <el-form-item label="需求来源" prop="demandSource">
            <el-select v-model="form.demandSource" placeholder="请选择需求来源" clearable>
              <el-option label="项目需求" value="项目需求" />
              <el-option label="版本规划" value="版本规划" />
              <el-option label="例行化适配" value="例行化适配" />
              <el-option label="芯片使能" value="芯片使能" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
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
import { ref, onMounted } from 'vue';
import { getChipFactoryNames, getChipFactoryModel } from '@/common/api/manufacturer/chip';
import { add, edit } from '@/common/api/manufacturer/board';
import { ElMessage } from 'element-plus';
import TableInput from '@/components/TableInput.vue';

export default {
  name: 'BoardAddOrUpdate',
  components: { TableInput },
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
    let extendBoardModelArr = ref([{ model: '', code: '' }]);
    // 表单对象
    let form = ref({
      chipFactory: '', // 芯片厂商
      boardType: '', // 板卡类型
      chipModel: '', // 芯片型号
      typicalBoardModel: '', // 典型板卡型号
      boardItem: '', // 编码item
      createTime: '', // 更新时间
      extendBoardModel: '', // 扩展型号
      extendBoardModelItem: '', // 扩展编码item
      x86Priority: 1, // x86_64优先级
      armPriority: 1, // aarch64优先级
      interfacePerson: '', // 接口人
      contact: '', // 电话
      middleman: '', // 对应BD
      demandSource: '' // 需求来源,
    });
    // 芯片厂商列表
    let chipFactoryList = ref([]);
    // 芯片型号列表
    let chipModelList = ref([]);
    // 操作系统列表
    let versionList = ref([]);
    // 扩展机型列表
    let dynamicTags = ref([]);
    // 扩展机型动态 input 值
    let inputValue = ref('');
    // 适配状态列表
    let planPhase1Model = ref([]);
    // 联系方式校验规则
    const validateContant = (rule, value, callback) => {
      if (!value) {
        return callback();
      }
      callback();
    };
    // 表单校验规则
    const rules = {
      chipFactory: [
        { required: true, message: '请选择芯片厂商', trigger: 'change' }
      ],
      boardType: [
        { required: true, message: '请填写板卡类型', trigger: 'blur' },
        { min: 0, max: 10, message: '请输入0-10个字符', trigger: 'blur' }
      ],
      chipModel: [
        { required: true, message: '请选择芯片型号', trigger: 'change' }
      ],
      typicalBoardModel: [
        { required: true, message: '请填写典型板卡型号', trigger: 'blur' },
        { min: 0, max: 40, message: '请输入0-40个字符', trigger: 'blur' }
      ],
      boardItem: [
        { min: 0, max: 10, message: '请输入0-10个字符', trigger: 'blur' }
      ],
      x86Priority: [
        { required: true, message: '请选择x86_64优先级', trigger: 'blur' }
      ],
      armPriority: [
        { required: true, message: '请选择aarch64优先级', trigger: 'change' }
      ],
      interfacePerson: [
        { min: 0, max: 10, message: '请输入0-10个字符', trigger: 'blur' }
      ],
      contact: [
        { min: 0, max: 50, message: '请输入0-50个字符', trigger: 'blur' },
        { validator: validateContant, trigger: 'blur' }
      ],
      middleman: [
        { min: 0, max: 10, message: '请输入0-10个字符', trigger: 'blur' }
      ],
      extendBoardModel: [
        {
          required: true,
          message: '请添加扩展机型',
          trigger: ['change', 'blur']
        }
      ],
      demandSource: [
        { required: true, message: '请选择需求来源', trigger: 'change' }
      ]
    };
    // 控制表单的显示与隐藏
    let dialogVisible = ref(false);
    // 关闭表单前触发
    const closeHandle = () => {
      dialogVisible.value = false;
      resetForm();
    };
    // 获取芯片厂商列表
    const getChipFactoryList = () => {
      getChipFactoryNames()
        .then(res => {
          if (res.code === 200) {
            chipFactoryList.value = res.data;
          }
        })
        .catch(e => {
          return Promise.reject(e);
        });
    };
    // 获取芯片型号列表
    const getChipModelList = () => {
      getChipFactoryModel(form.value.chipFactory)
        .then(res => {
          if (res.code === 200) {
            chipModelList.value = res.data;
          }
        })
        .catch(e => {
          return Promise.reject(e);
        });
    };
    // 芯片厂商下拉选中
    const chipFactoryChanged = () => {
      chipModelList.value = [];
      form.value.chipModel = '';
      getChipModelList();
    };
    // 表单 ref
    const boardFormRef = ref();

    // 版本适配发生改变
    const versionChange = val => {
      form.value.boardAdaptationList = [];
      val.forEach(item => {
        let obj = {
          releaseVersion: {
            id: item
          }
        };
        form.value.boardAdaptationList.push(obj);
      });
    };
    // 确认表单
    const submitForm = () => {
      boardFormRef.value.validate(valid => {
        if (valid) {
          let extendBoardModelList = [], extendBoardModelCodeList = [];
          extendBoardModelArr.value.forEach(item => {
            if (item.model) {
              extendBoardModelList.push(item.model);
              if (item.code) {
                extendBoardModelCodeList.push(`${item.model}/${item.code}`);
              } else {
                extendBoardModelCodeList.push(item.model);
              }
            }
          });
          form.value.extendBoardModel = extendBoardModelList.join(',');
          form.value.extendBoardModelItem = extendBoardModelCodeList.join(',');
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
      boardFormRef.value.resetFields();
      dialogVisible.value = false;
      form.value = {
        chipFactory: '', // 芯片厂商
        boardType: '', // 板卡类型
        chipModel: '', // 芯片型号
        typicalBoardModel: '', // 典型板卡型号
        boardItem: '', // 编码item
        updateTime: '', // 更新时间
        extendBoardModel: '', // 扩展型号
        extendBoardModelItem: '', // 扩展编码item
        x86Priority: 1, // x86_64优先级
        armPriority: 1, // aarch64优先级
        interfacePerson: '', // 接口人
        contact: '', // 电话
        middleman: '', // 对应BD
        demandSource: '' // 需求来源
      };
      dynamicTags.value = [];
      chipModelList.value = [];
      planPhase1Model.value = [];
      extendBoardModelArr.value = [{ model: '', code: '' }];
    };
    onMounted(() => {
      getChipFactoryList();
    });
    return {
      dialogVisible,
      form,
      rules,
      boardFormRef,
      submitForm,
      resetForm,
      closeHandle,
      doAdd,
      doEdit,
      dynamicTags,
      inputValue,
      planPhase1Model,
      versionChange,
      versionList,
      chipFactoryList,
      chipModelList,
      chipFactoryChanged,
      getChipModelList,
      extendBoardModelArr
    };
  }
};
</script>

<style lang="scss" scoped>
  .tagItem {
    margin-right: 5px;
  }
</style>