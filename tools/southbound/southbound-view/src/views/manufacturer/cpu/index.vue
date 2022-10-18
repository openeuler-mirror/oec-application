<template>
  <div class="card-warpper">
    <el-config-provider :locale="locale">
      <div class="system-user-container">
        <el-card shadow="hover">
          <el-form :model="queryForm" :show-message="false" inline>
            <div class="filter-area d-flex">
              <div class="filter-area-left">
                <el-form-item label="CPU厂商" :show-message="false">
                  <el-select v-model="queryForm.cpuFactory" placeholder="请选择CPU厂商" clearable @change="toQuery">
                    <el-option v-for="(item,index) in cpuFactoryList" :key="index" :label="item" :value="item" />
                  </el-select>
                </el-form-item>
                <el-form-item label="架构" :show-message="false">
                  <el-select v-model="queryForm.architecture" placeholder="请选择架构" clearable @change="toQuery">
                    <el-option v-for="(item, index) in architectureList" :key="index" :label="item" :value="item" />
                  </el-select>
                </el-form-item>
              </div>
              <div class="filter-area-right">
                <el-form-item label="搜索" style="width: 100%; margin-right: 0;">
                  <el-input placeholder="CPU代次、支持的OS版本" size="default" v-model="queryForm.keyword" style="width: 100%" @keyup.enter.native="toQuery">
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
            <el-table :data="data" style="width: 100%" height="100%" :header-cell-style="{backgroundColor: '#f5f7fa', 'text-align':'center'}" :cell-style="{'text-align':'center'}" @selection-change="handleSelectionChange" v-loading="loading" ref="eTable" border>
              <el-table-column type="selection"></el-table-column>
              <el-table-column fixed label="CPU厂商" show-overflow-tooltip>
                <template #default="scope">{{scope.row.cpuFactory || '/'}}</template>
              </el-table-column>
              <el-table-column fixed label="CPU代次" show-overflow-tooltip :filters="cpuModelFilterList" :filter-method="(value,row) => row.cpuModel === value">
                <template #default="scope">{{scope.row.cpuModel || '/'}}</template>
              </el-table-column>
              <el-table-column fixed label="架构" show-overflow-tooltip>
                <template #default="scope">{{scope.row.architecture || '/'}}</template>
              </el-table-column>
              <el-table-column fixed label="支持的OS版本" min-width="200">
                <template #default="scope">
                  <el-popover v-if="scope.row.versionNames && scope.row.versionNames.length > 0" placement="top" :width="400" trigger="hover" :content="scope.row.versionNames" popper-class="customPopover">
                    <template #reference>
                      <div class="noWarp">{{scope.row.versionNames}}</div>
                    </template>
                  </el-popover>
                  <div v-else>{{'/'}}</div>
                </template>
              </el-table-column>
              <el-table-column label="发布时间" show-overflow-tooltip :filters="releaseTimeFilterList" :filter-method="(value,row) => row.releaseTime === value">
                <template #default="scope">{{scope.row.releaseTime || '/'}}</template>
              </el-table-column>
              <el-table-column label="创建日期" show-overflow-tooltip :filters="createTimeFilterList" :filter-method="(value,row) => row.createTime === value" :resizable="false">
                <template #default="scope">{{scope.row.createTime || '/'}}</template>
              </el-table-column>
              <el-table-column label="操作" min-width="80" fixed="right" :resizable="false">
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
        <import-excel ref="excel" :type="1" @refreshList="refreshList" />
      </div>
    </el-config-provider>
  </div>
</template>
<script>
import { defineComponent, onMounted, ref, markRaw } from 'vue';
import { doDelete, getAllList } from '@/common/api/manufacturer/cpu';
import { getCpuFactoryAll, getArchitecturesAll } from '@/common/api/full/index';
import eForm from './form.vue';
import ImportExcel from '@/components/ImportExcel.vue';
import { ElConfigProvider, ElMessageBox, ElMessage } from 'element-plus';
import zhCn from 'element-plus/lib/locale/lang/zh-cn';
import { Search, WarningFilled } from '@element-plus/icons-vue';
import BasePagination from '@/components/BasePagination.vue';
import useList from '@/hooks/useList.js';

export default defineComponent({
  name: 'CpuManufacturers',
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
    // 请求参数
    const url = ref('/cpu-factory/queryAll');
    const queryForm = ref({
      cpuFactory: '',
      architecture: '',
      keyword: '',
      pageSize: 15,
      pageIndex: 1
    });
    // form 表单的 ref
    const form = ref();
    const excel = ref();
    // cpu 厂商列表
    let cpuFactoryList = ref();
    let architectureList = ref();
    // 新增或编辑操作的标识符
    let isAdd = ref(false);
    // 删除的 id 集合
    let delIds = ref([]);
    // 获取 CPU 厂商列表
    const getCpuFactoryList = () => {
      getCpuFactoryAll()
        .then(res => {
          if (res.code === 200) {
            cpuFactoryList.value = res.data;
          }
        })
        .catch(e => {
          return Promise.reject(e);
        });
    };
    // 获取架构列表
    const getArchitectureList = () => {
      getArchitecturesAll()
        .then(res => {
          if (res.code === 200) {
            architectureList.value = res.data;
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
        cpuFactory: row.cpuFactory, //CPU厂商
        cpuModel: row.cpuModel, //CPU型号
        architecture: row.architecture, // 架构
        versionIds: row.versionIds, //支持的OS版本
        releaseTime: row.releaseTime, //发布日期
        cpuId: row.cpuId
      };
      if (row.versionIds) {
        form.value.versionIds = row.versionIds.split(',');
      }
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
              getCpuFactoryList();
              getArchitectureList();
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
          delIds.value.push(item.cpuId);
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
          'CPU厂商',
          'CPU代次',
          '架构',
          '支持的OS版本',
          '发布时间'
        ];
        //设置标题对应的字段
        let filterVal = [
          'cpuFactory',
          'cpuModel',
          'architecture',
          'versionNames',
          'releaseTime'
        ];

        //把后台传过来的数据存到list
        const res = await getAllList();
        loading.value = false;
        const data = formatJson(filterVal, res.data);
        export_json_to_excel(tHeader, data, 'CPU厂商');
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
    const cpuModelFilterList = ref([]);
    const releaseTimeFilterList = ref([]);
    const createTimeFilterList = ref([]);
    // 重组表头筛选列表
    const getFilterList = () => {
      cpuModelFilterList.value = [];
      releaseTimeFilterList.value = [];
      createTimeFilterList.value = [];
      data.value.forEach(item => {
        cpuModelFilterList.value.push({
          text: item.cpuModel,
          value: item.cpuModel
        });
        releaseTimeFilterList.value.push({
          text: item.releaseTime,
          value: item.releaseTime
        });
        createTimeFilterList.value.push({
          text: item.createTime,
          value: item.createTime
        });
      });
      // 去重
      set(cpuModelFilterList.value);
      set(releaseTimeFilterList.value);
      set(createTimeFilterList.value);
    };
    onMounted(() => {
      // 获取cpu厂商列表
      getCpuFactoryList();
      // 获取架构列表
      getArchitectureList();
      // 获取列表
      toQuery();
    });
    const refreshList = () => {
      getCpuFactoryList();
      getArchitectureList();
      toQuery();
    };
    return {
      value,
      data,
      form,
      cpuFactoryList,
      architectureList,
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
      getCpuFactoryList,
      getArchitectureList,
      paginationChange,
      url,
      init,
      loading,
      refreshList,
      exportExcel,
      importExcel,
      excel,
      toQuery,
      eTable,
      cpuModelFilterList,
      releaseTimeFilterList,
      createTimeFilterList
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
