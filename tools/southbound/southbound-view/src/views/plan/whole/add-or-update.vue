<template>
  <el-dialog v-model="dialogVisible" :before-close="closeHandle" :close-on-click-modal="false" draggable :title="type === 'add' ? '新增整机计划' : '修改整机计划'" width="40vw">
    <el-form v-if="dialogVisible" ref="formRef" :model="formData" :rules="rules" label-width="145px" status-icon>
      <el-row>
        <el-col :span="11">
          <el-form-item label="整机厂商" prop="wholeFactory">
            <el-select v-model="formData.wholeFactory" @change="wholeFactoryChange" placeholder="请选择整机厂商" clearable>
              <el-option v-for="(item, index) in wholeFactoryOptions" :key="index" :label="item" :value="item" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="操作系统" prop="versionId" label-width="100px">
            <el-select v-model="formData.versionId" placeholder="请选择操作系统" @change="versionChange" clearable>
              <el-option v-for="item in versionOptions" :key="item.versionId" :label="item.versionName" :value="(item.versionId + '')" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <!-- 版本发布前阶段 -->
      <el-form-item label="版本发布前适配机型" prop="betaList">
        <el-select v-model="formData.betaList" multiple collapse-tags collapse-tags-tooltip @change="handleListChange($event, 'beta')" style="width: 50%;" placeholder="请选择适配机型" clearable>
          <el-option-group v-for="(item, index) in typeFilterOptions.betaOptions" :key="index" :label="item.label">
            <el-option v-for="(opitem, opindex) in item.options" :key="opindex" :label="opitem" :value="opitem" />
          </el-option-group>
        </el-select>
      </el-form-item>
      <!-- 版本发布后阶段 -->
      <el-form-item label="版本发布后适配机型" prop="releaseList">
        <el-select v-model="formData.releaseList" multiple collapse-tags collapse-tags-tooltip @change="handleListChange($event, 'release')" style="width: 50%;" placeholder="请选择适配机型" clearable>
          <el-option-group v-for="(item, index) in typeFilterOptions.releaseOptions" :key="index" :label="item.label">
            <el-option v-for="(opitem, opindex) in item.options" :key="opindex" :label="opitem" :value="opitem" />
          </el-option-group>
        </el-select>
      </el-form-item>
      <el-form-item label="备注" prop="remark" style="width: 90%">
        <el-input type="textarea" v-model="formData.remark" placeholder="请输入备注" />
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
import { ref, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import { add, edit, queryModels, queryFactoryNames, queryVersionListByWholeFactory } from '@/common/api/wholePlan';
export default {
  name: 'planWholeAddOrUpdate',
  emits: ['refreshList'],
  setup (_, { emit }) {
    let formData = ref({});
    const initForm = () => {
      formData.value = {
        id: undefined,
        versionId: '',
        wholeFactory: '',
        cpuFactory: '',
        cpuModel: '',
        alphaStages: '',
        betaList: [],
        releaseList: [],
        remark: ''
      };
    };
    initForm();
    const rules = {
      wholeFactory: [
        { required: true, message: '请选择整机厂商', trigger: 'change' }
      ],
      versionId: [
        { required: true, message: '请选择操作系统', trigger: 'change' }
      ]
    };

    let dialogVisible = ref(false);
    let type = ref('add');
    const formRef = ref();
    let wholeFactoryOptions = ref([]);
    let versionOptions = ref([]);
    // 查询整机厂商
    const quertWholeFactoryOptions = async () => {
      let res = await queryFactoryNames();
      if (res.code === 200) {
        wholeFactoryOptions.value = res.data;
      }
    };

    // 查询操作系统列表  
    const queryVersionOptionsByFactory = async (wholeFactory) => {
      let res = await queryVersionListByWholeFactory(wholeFactory);
      if (res.code === 200) {
        let data = [];
        res.data.forEach(item => {
          if (item) {
            data.push({ versionId: item.versionId, versionName: item.versionName });
          }
        });
        versionOptions.value = data;
      }
    };
    let hardwareModelList = [];
    let extendModelList = [];
    const wholeFactoryChange = (value) => {
      formData.value.versionId = '';
      versionOptions.value = [];
      versionChange();
      if (!value) {
        return;
      }
      queryVersionOptionsByFactory(formData.value.wholeFactory);
    };
    const versionChange = (versionId) => {
      formData.value.betaList = [];
      formData.value.releaseList = [];
      if (!versionId) {
        initModelOptions();
        return;
      }
      queryModelByFactory(formData.value.wholeFactory, versionId);
    };
    const queryModelByFactory = async (wholeFactory, versionName) => {
      let res = await queryModels(wholeFactory, versionName);
      if (res.code === 200) {
        hardwareModelList = res.data.hardwareModelList ? res.data.hardwareModelList.split(/,|、/) : [];
        extendModelList = res.data.extendModelList ? res.data.extendModelList.split(/,|、/) : [];
      }
      handleListChange([], 'factoryChanage');
    };
    onMounted(async () => {
      quertWholeFactoryOptions();
    });
    const typeFilterOptions = ref({
    });
    const initModelOptions = () => {
      typeFilterOptions.value = {
        betaOptions: [
          { label: '典型机型', options: [] },
          { label: '扩展机型', options: [] }
        ],
        releaseOptions: [
          { label: '典型机型', options: [] },
          { label: '扩展机型', options: [] }
        ]
      };
    };
    initModelOptions();

    const handleListChange = (e, type) => {
      if (type === 'factoryChanage') {
        typeFilterOptions.value.betaOptions = [
          { label: '典型机型', options: hardwareModelList },
          { label: '扩展机型', options: extendModelList }
        ];
        typeFilterOptions.value.releaseOptions = [
          { label: '典型机型', options: hardwareModelList },
          { label: '扩展机型', options: extendModelList }
        ];
        return;
      }
      if (type === 'beta') {
        let surplusEx = hardwareModelList.filter(item => !e.includes(item));
        let surplusEx1 = extendModelList.filter(item => !e.includes(item));
        typeFilterOptions.value.releaseOptions = [
          { label: '典型机型', options: surplusEx },
          { label: '扩展机型', options: surplusEx1 }
        ];
        formData.value.releaseList = [];
      }
      if (type === 'release') {
        formData.value.betaList = formData.value.betaList.filter(item => !e.includes(item));
      }
    };

    const open = (recType, rowdata) => {
      type.value = recType;
      if (recType === 'add') {
        versionOptions.value = [];
      } else {
        formData.value = rowdata;
        queryVersionOptionsByFactory(rowdata.wholeFactory);
        queryModelByFactory(rowdata.wholeFactory, rowdata.versionId);
      }
      dialogVisible.value = true;
    };
    const close = () => {
      dialogVisible.value = false;
    };
    const closeHandle = () => {
      dialogVisible.value = false;
      formRef.value.resetFields();
      initForm();
    };

    const submitForm = async (formEl) => {
      await formEl.validate((valid) => {
        if (valid) {
          let data = { ...formData.value };
          data.betaList = data.betaList.join(',');
          data.releaseList = data.releaseList.join(',');
          if (type.value === 'add') {
            doAdd(data);
          } else {
            doEdit(data);
          }
        }
      });
    };
    const doAdd = async (data) => {
      let res = await add(data);
      if (res.code === 200) {
        ElMessage.success('添加成功');
        emit('refreshList');
        closeHandle();
      }
    };
    const doEdit = async (data) => {
      let res = await edit(data);
      if (res.code === 200) {
        ElMessage.success('修改成功');
        emit('refreshList');
        closeHandle();
      }
    };
    const cancelForm = () => {
      formRef.value.resetFields();
      initForm();
      dialogVisible.value = false;
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
      closeHandle,
      wholeFactoryOptions,
      typeFilterOptions,
      handleListChange,
      versionOptions,
      wholeFactoryChange,
      versionChange,
      queryModelByFactory
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