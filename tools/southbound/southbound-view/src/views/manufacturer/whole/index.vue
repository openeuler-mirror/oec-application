<template>
  <div class="card-warpper">
    <el-config-provider :locale="locale">
      <div class="system-user-container">
        <el-card shadow="hover">
          <el-form :model="queryForm" :show-message="false" inline>
            <div class="filter-area d-flex">
              <div class="filter-area-left">
                <el-form-item label="整机厂商" :show-message="false">
                  <el-select v-model="queryForm.wholeFactory" placeholder="请选择整机厂商" clearable @change="toQuery">
                    <el-option v-for="(item, index) in wholeFactoryList" :key="index" :label="item" :value="item" />
                  </el-select>
                </el-form-item>
              </div>
              <div class="filter-area-right">
                <el-form-item label="搜索" style="width: 100%;margin-right: 0;">
                  <el-input placeholder="CPU厂商、代次、典型机型、扩展机型" v-model="queryForm.keyword" style="width: 280px" @keyup.enter.native="toQuery">
                    <template #suffix>
                      <el-icon @click="toQuery" class="pointer">
                        <Search></Search>
                      </el-icon>
                    </template>
                  </el-input>
                </el-form-item>
                <el-popover placement="bottom" popper-class="customBounced" trigger="click">
                  <template #reference>
                    <el-button type="primary" class="ml-10">操作</el-button>
                  </template>
                  <el-button type="success" @click="add">新增</el-button>
                  <el-button type="danger" @click="del" :disabled="delIds.length === 0">删除</el-button>
                  <el-button type="success" v-if="$hasAuth([0, 1])" @click="importExcel">导入</el-button>
                  <el-button type="primary" v-if="$hasAuth([0, 1])" @click="exportExcel">导出</el-button>
                </el-popover>
              </div>
            </div>
          </el-form>
          <div class="table-main">
            <el-table :data="data" style="width: 100%" height="100%" :header-cell-style="{'text-align':'center'}" :cell-style="{'text-align':'center'}" @selection-change="handleSelectionChange" v-loading="loading" ref="eTable" border>
              <el-table-column type="selection"></el-table-column>
              <el-table-column fixed label="整机厂商" show-overflow-tooltip width="100" min-width="80" :filters="wholeFactoryFilterList" :filter-method="(value,row) => row.wholeFactory === value">
                <template #default="scope">{{scope.row.wholeFactory || '/'}}</template>
              </el-table-column>
              <el-table-column fixed label="CPU厂商" show-overflow-tooltip width="100" :filters="cpuFactoryFilterList" :filter-method="(value,row) => row.cpuFactory === value">
                <template #default="scope">{{scope.row.cpuFactory || '/'}}</template>
              </el-table-column>
              <el-table-column fixed label="CPU代次" show-overflow-tooltip width="120" :filters="cpuModelFilterList" :filter-method="(value,row) => row.cpuModel === value">
                <template #default="scope">{{scope.row.cpuModel || '/'}}</template>
              </el-table-column>
              <el-table-column fixed label="典型机型" show-overflow-tooltip width="190" :filters="hardwareModelFilterList" :filter-method="(value,row) => row.hardwareModel === value">
                <template #default="scope">{{scope.row.hardwareModel || '/'}}</template>
              </el-table-column>
              <el-table-column label="扩展机型" show-overflow-tooltip>
                <el-table-column label="数量" show-overflow-tooltip width="80">
                  <template #default="scope">{{scope.row.extendModel ? scope.row.extendModel.split(',').length : '/'}}</template>
                </el-table-column>
                <el-table-column label="型号" width="400">
                  <template #default="scope">
                    <el-popover v-if="scope.row.extendModel && scope.row.extendModel.length > 40" placement="top" :width="400" trigger="hover" :content="scope.row.extendModel" popper-class="customPopover">
                      <template #reference>
                        <div class="noWarp">{{scope.row.extendModel}}</div>
                      </template>
                    </el-popover>
                    <div v-else>{{scope.row.extendModel || '/'}}</div>
                  </template>
                </el-table-column>
              </el-table-column>
              <el-table-column label="接口人" show-overflow-tooltip width="110">
                <template #default="scope">{{scope.row.interfacePerson || '/'}}</template>
              </el-table-column>
              <el-table-column label="联系方式" show-overflow-tooltip width="170">
                <template #default="scope">{{scope.row.contact || '/'}}</template>
              </el-table-column>
              <el-table-column label="对应BD" show-overflow-tooltip>
                <template #default="scope">{{scope.row.middleman || '/'}}</template>
              </el-table-column>
              <el-table-column label="创建日期" show-overflow-tooltip width="110" :filters="createTimeFilterList" :filter-method="(value,row) => row.createTime === value" :resizable="false">
                <template #default="scope">{{scope.row.createTime || '/'}}</template>
              </el-table-column>
              <el-table-column label="操作" fixed="right" :resizable="false">
                <template v-slot="scope">
                  <el-button size="small" type="primary" @click="edit(scope.row)">编辑</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <div class="example-pagination-block" style="margin-top:7px">
            <base-pagination :current-page="queryForm.pageIndex" :page-size="queryForm.pageSize" :total="total" @change="paginationChange" />
          </div>
        </el-card>
        <eForm ref="form" :isAdd="isAdd" @refreshList="refreshList" />
        <import-excel ref="excel" :type="2" @refreshList="refreshList" />
      </div>
    </el-config-provider>
  </div>
</template>
<script>
import { defineComponent, onMounted, ref, markRaw } from 'vue';
import { doDelete, getAllList } from '@/common/api/manufacturer/whole';
import { getWholeNames } from '@/common/api/full';
import eForm from './form.vue';
import ImportExcel from '@/components/ImportExcel.vue';
import { ElConfigProvider, ElMessageBox, ElMessage } from 'element-plus';
import zhCn from 'element-plus/lib/locale/lang/zh-cn';
import { Search, WarningFilled } from '@element-plus/icons-vue';
import BasePagination from '@/components/BasePagination.vue';
import useList from '@/hooks/useList.js';

export default defineComponent({
  name: 'WholeManufacturers',
  components: {
    eForm,
    ElConfigProvider,
    BasePagination,
    ImportExcel
  },

  setup () {
    // 数据
    // use List
    const { init, total, data, loading, set } = useList();
    const value = ref('');
    const eTable = ref();
    // 操作系统多选列表
    let versionIds = ref([]);
    // 请求参数
    const url = ref('/whole-factory/queryAll');
    const queryForm = ref({
      wholeFactory: '',
      keyword: '',
      pageSize: 15,
      pageIndex: 1
    });
    // form 表单的 ref
    const form = ref();
    const excel = ref();
    // 新增或编辑操作的标识符
    let isAdd = ref(false);
    // 删除的 id 集合
    let delIds = ref([]);
    // 整机厂商列表
    let wholeFactoryList = ref([]);
    // 获取整机厂商列表
    const getWholeFactoryList = () => {
      getWholeNames()
        .then(res => {
          if (res.code === 200) {
            wholeFactoryList.value = res.data;
          }
        })
        .catch(e => {
          return Promise.reject(e);
        });
    };
    // 分页发生改变
    const paginationChange = e => {
      queryForm.value.pageIndex = e.currentPage;
      queryForm.value.pageSize = e.pageSize;
      toQuery();
    };
    //新增整机方法
    const add = () => {
      isAdd.value = true;
      form.value.dialogVisible = true;
    };
    // 修改
    const edit = row => {
      isAdd.value = false;
      form.value.dialogVisible = true;
      form.value.form = {
        wholeFactory: row.wholeFactory, // 整机厂商
        cpuFactory: row.cpuFactory, // CPU厂商
        cpuModel: row.cpuModel, // CPU代次
        hardwareModel: row.hardwareModel, // 典型机型
        createTime: row.createTime, // 更新时间
        extendModel: row.extendModel, // 型号
        interfacePerson: row.interfacePerson, // 接口人
        contact: row.contact, // 微信
        middleman: row.middleman, // 对应BD
        wholeId: row.wholeId
      };
      if (row.extendModel) {
        form.value.dynamicTags = row.extendModel.split(',');
      }
      form.value.getChipModelList(row.cpuFactory);
    };

    // 删除某一行
    const del = () => {
      ElMessageBox.confirm('此操作将永久删除选中数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info',
        icon: markRaw(WarningFilled)
      })
        .then(() => {
          doDelete(delIds.value.join(',')).then(res => {
            if (res.code === 200) {
              ElMessage({
                message: res.msg || '删除成功',
                grouping: true,
                type: 'success'
              });
              getWholeFactoryList();
              toQuery();
            }
          });
        })
        .catch(() => { });
    };

    // 列选中发生改变
    const handleSelectionChange = val => {
      delIds.value = [];
      if (val.length > 0) {
        val.forEach(item => {
          delIds.value.push(item.wholeId);
        });
      }
    };
    // 导入 Excel
    const importExcel = () => {
      excel.value.dialogVisible = true;
    };
    // 导出 Excel
    const exportExcel = () => {
      loading.value = true;
      require.ensure([], async () => {
        const { export_json_to_excel } = require('@/excel/Export2Excel');
        //设置Excel表格第一行的标题
        const tHeader = [
          '整机厂商',
          'CPU厂商',
          'CPU代次',
          '典型机型',
          '扩展机型数量',
          '扩展机型型号',
          '接口人',
          '联系方式',
          '对应BD',
          '创建日期'
        ];
        //设置标题对应的字段
        let filterVal = [
          'wholeFactory',
          'cpuFactory',
          'cpuModel',
          'hardwareModel',
          'extendModelCount',
          'extendModel',
          'interfacePerson',
          'contact',
          'middleman',
          'createTime'
        ];

        //把后台传过来的数据存到list
        const res = await getAllList();
        res.data.forEach(item => {
          if (item.extendModel.trim().length > 0) {
            item.extendModelCount = item.extendModel.split(',').length;
          } else {
            item.extendModelCount = 0;
          }
        });
        loading.value = false;
        const data = formatJson(filterVal, res.data);
        export_json_to_excel(tHeader, data, '整机厂商');
      });
    };

    const formatJson = (filterVal, jsonData) => {
      return jsonData.map(v => filterVal.map(j => v[j]));
    };
    const toQuery = async () => {
      await init(url.value, queryForm.value);
      getFilterList();
      eTable.value.clearFilter();
    };
    // 筛选
    const wholeFactoryFilterList = ref([]);
    const cpuFactoryFilterList = ref([]);
    const cpuModelFilterList = ref([]);
    const hardwareModelFilterList = ref([]);
    const createTimeFilterList = ref([]);
    // 重组表头筛选列表
    const getFilterList = () => {
      wholeFactoryFilterList.value = [];
      cpuFactoryFilterList.value = [];
      cpuModelFilterList.value = [];
      hardwareModelFilterList.value = [];
      createTimeFilterList.value = [];
      data.value.forEach(item => {
        wholeFactoryFilterList.value.push({
          text: item.wholeFactory,
          value: item.wholeFactory
        });
        cpuFactoryFilterList.value.push({
          text: item.cpuFactory,
          value: item.cpuFactory
        });
        cpuModelFilterList.value.push({
          text: item.cpuModel,
          value: item.cpuModel
        });
        hardwareModelFilterList.value.push({
          text: item.hardwareModel,
          value: item.hardwareModel
        });
        createTimeFilterList.value.push({
          text: item.createTime,
          value: item.createTime
        });
      });
      // 去重
      set(wholeFactoryFilterList.value);
      set(cpuFactoryFilterList.value);
      set(cpuModelFilterList.value);
      set(hardwareModelFilterList.value);
      set(createTimeFilterList.value);
    };
    onMounted(() => {
      // 获取cpu厂商列表
      getWholeFactoryList();
      // 获取列表
      toQuery();
    });
    const refreshList = () => {
      getWholeFactoryList();
      toQuery();
    };
    return {
      value,
      data,
      form,
      add,
      edit,
      del,
      locale: zhCn,
      total,
      Search,
      isAdd,
      handleSelectionChange,
      delIds,
      queryForm,
      wholeFactoryList,
      getWholeFactoryList,
      versionIds,
      paginationChange,
      url,
      init,
      loading,
      refreshList,
      getFilterList,
      toQuery,
      eTable,
      exportExcel,
      wholeFactoryFilterList,
      cpuFactoryFilterList,
      cpuModelFilterList,
      hardwareModelFilterList,
      createTimeFilterList,
      importExcel,
      excel
    };
  }
});
</script>
<style lang="scss" scoped>
  .system-user-container {
    height: 100%;
    .operation {
      display: flex;
      align-items: center;
      justify-content: center;
    }
  }
  .el-checkbox {
    display: block;
  }
</style>
