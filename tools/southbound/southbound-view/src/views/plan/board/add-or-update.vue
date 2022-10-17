<template>
  <el-dialog v-model="dialogVisible" :before-close="closeHandle" :close-on-click-modal="false" draggable
    :title="type === 'add' ? '新增板卡计划' : '修改板卡计划'" width="40vw">
    <el-form v-if="dialogVisible" ref="formRef" :model="formData" :rules="rules" label-width="145px" status-icon >
        <el-row>
          <el-col :span="11">
            <el-form-item label="芯片厂商" prop="chipFactory">
              <el-select v-model="formData.chipFactory" placeholder="请选择芯片厂商" clearable @change="chipFactoryChange">
                <el-option v-for="(item, index) in chipFactoryOptions" :key="index" :label="item" :value="item"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="操作系统" prop="versionId" label-width="100px">
              <el-select v-model="formData.versionId" placeholder="请选择操作系统" @change="versionChange" clearable>
                <el-option v-for="item in versionOptions" :key="item.versionId" :label="item.versionName"
                  :value="item.versionId + ''" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
    
        <!-- 版本发布前阶段 -->
        <el-form-item label="版本发布前适配型号" prop="betaList">
          <el-select v-model="formData.betaList" multiple collapse-tags collapse-tags-tooltip style="width: 50%;"
            @change="handleListChange($event, 'beta')" placeholder="请选择适配型号" clearable>
            <el-option-group v-for="(item, index) in typeFilterOptions.betaOptions" :key="index" :label="item.label">
              <el-option v-for="(opitem, opindex) in item.options" :key="opindex" :label="opitem" :value="opitem"/>
            </el-option-group>
          </el-select>
        </el-form-item>
        <!-- 版本发布后阶段 -->
        <el-form-item label="版本发布后适配型号" prop="releaseList">
          <el-select v-model="formData.releaseList" multiple collapse-tags collapse-tags-tooltip style="width: 50%;"
            @change="handleListChange($event, 'release')" placeholder="请选择适配型号" clearable>
             <el-option-group  v-for="(item, index) in typeFilterOptions.releaseOptions"
              :key="index" :label="item.label">
              <el-option v-for="(opitem, opindex) in item.options" :key="opindex" :label="opitem" :value="opitem"/>
            </el-option-group>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark" style="width: 90%">
          <el-input type="textarea" v-model="formData.remark" placeholder="请填写备注"/>
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
import { ref, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import { edit, add, queryModels, queryVersionListByChipFactory  } from '@/common/api/boardPlan';
import { getChipFactoryNames } from '@/common/api/manufacturer/chip.js';
export default {
  name: 'planBoardAddOrUpdate',
  components: {},
  emits: ['refreshList'],
  setup(_, {emit}) {
    let formData = ref();
    const initForm = () => {
      formData.value = {
        id: undefined,
        chipFactory: '',
        versionId: '',  
        betaList: [],
        releaseList: [],
        remark: ''
      };
    };
    initForm();
    const rules = 
      {
        chipFactory: [
          { required: true, message: '请选择芯片厂商', trigger: 'change' }
        ],
        versionId: [
          { required: true, message: '请选择操作系统', trigger: 'change' }
        ]         
      };
    
    let dialogVisible = ref(false);
    let type = ref('add');
    const open = (recType, rowdata) => {
      type.value = recType;
      if (recType === 'add') {
        versionOptions.value = []; 
      } else {
        formData.value = rowdata;
        queryVersionOptionsByFactory(rowdata.chipFactory);
        queryModelByFactory(rowdata.chipFactory, rowdata.versionId);
      }
      dialogVisible.value = true;
    };
    const close = () => {
      dialogVisible.value = false;
    };
    const closeHandle = () => {
      dialogVisible.value = false;
      initForm();
      formRef.value.resetFields();
    };
    const formRef = ref();
    const submitForm = async (formEl) => {
      await formEl.validate(async (valid) => {
        if (valid) {
          let data = {...formData.value};
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
        dialogVisible.value = false;
        emit('refreshList');
        closeHandle();
      }
    };
    const cancelForm = () => {
      formRef.value.resetFields();
      dialogVisible.value = false;
      initForm();
    };

    let chipFactoryOptions = ref([]);
    let versionOptions = ref([]);
    let driverNameOptions = ref([]);
    let driverVersionOptions = ref([]);
    // 查询芯片厂商 
    const queryChipFactoryOptions = async () => {
      let res = await getChipFactoryNames();
      if (res.code === 200) {
        chipFactoryOptions.value = res.data;
      }
    };
 
    // 查询操作系统列表  
    const queryVersionOptionsByFactory = async (chipFactory) => {
      let res = await queryVersionListByChipFactory (chipFactory);
      if (res.code === 200) {
        let data = [];
        res.data.forEach(item => {
          if (item) {
            data.push({versionId:item.versionId, versionName:item.versionName});
          }
        });
        versionOptions.value = data;
      }
    };
   
    onMounted(() => {
      queryChipFactoryOptions();
    });
    const typeFilterOptions = ref({});
    const initModelOptions = () => {
      typeFilterOptions.value = {
        betaOptions: [
          { label: '典型型号', options: []},
          { label: '扩展型号', options: []}
        ],
        releaseOptions: [
          { label: '典型型号', options: []},
          { label: '扩展型号', options: []}
        ]
      };
    };
    initModelOptions();
    let hardwareModelList = [];
    let extendModelList = [];
    const queryModelByFactory = async (chipFactory, versionId) => {
      let res = await queryModels(chipFactory, versionId);
      if (res.code === 200) {
        hardwareModelList = res.data.typicalModelList ? res.data.typicalModelList.split(/,|、/): [];
        extendModelList = res.data.extendBoardModelList ? res.data.extendBoardModelList.split(/,|、/): [];
      }
      handleListChange([], 'factoryChanage');
    };
    const handleListChange = (e, type) => {
      if (type === 'factoryChanage') {
        typeFilterOptions.value.betaOptions = [
          { label: '典型型号', options: hardwareModelList},
          { label: '扩展型号', options: extendModelList
          }
        ];
        typeFilterOptions.value.releaseOptions = [
          { label: '典型型号', options: hardwareModelList},
          { label: '扩展型号', options: extendModelList}
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
        
      } else if (type === 'release') {
        formData.value.betaList = formData.value.betaList.filter(item => !e.includes(item));
      }

    };

    const chipFactoryChange = (value) => {
      formData.value.versionId = '';
      versionOptions.value = [];
      versionChange();
      if (!value) {
        return;
      } 
      queryVersionOptionsByFactory(value);
    };

    const versionChange = (versionId) => {
      formData.value.betaList = [];
      formData.value.releaseList = [];
      if (!versionId) {
        initModelOptions();
        return;
      }
      queryModelByFactory(formData.value.chipFactory, versionId);
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

      chipFactoryOptions,
      versionOptions,
      driverNameOptions,
      driverVersionOptions,
      typeFilterOptions,
    
      handleListChange,
    
      chipFactoryChange,
      versionChange
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