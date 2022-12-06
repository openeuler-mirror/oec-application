<template>
  <div class="card-warpper">
    <el-config-provider :locale="locale">
      <div class="system-user-container">
        <el-card shadow="hover">
          <el-form :model="queryForm" :show-message="false" inline>
            <div class="filter-area d-flex">
              <div class="filter-area-left">
                <el-form-item label="操作系统" :show-message="false" style="min-width: 220px;">
                  <el-select
                    v-model="versionId"
                    placeholder="请选择操作系统"
                    clearable
                    @change="versionChange"
                    multiple
                    collapse-tags
                  >
                    <el-option
                      v-for="(item, index) in versionList"
                      :key="index"
                      :label="item.versionName"
                      :value="item.versionId"
                    />
                  </el-select>
                </el-form-item>
                <el-form-item label="架构" :show-message="false">
                  <el-select
                    v-model="queryForm.architecture"
                    placeholder="请选择"
                    clearable
                    @change="toQuery"
                  >
                    <el-option
                      v-for="item in architectureList"
                      :key="item"
                      :label="item"
                      :value="item"
                    />
                  </el-select>
                </el-form-item>
              </div>
              <div class="filter-area-right">
                <el-form-item label="搜索" style="margin-right: 0;">
                  <el-input
                    placeholder="厂商、CPU、机型"
                    size="default"
                    v-model="queryForm.keyword"
                    style="width: 100%"
                    @keyup.enter.native="toQuery"
                  >
                    <template #suffix>
                      <el-icon @click="toQuery" class="pointer">
                        <Search></Search>
                      </el-icon>
                    </template>
                  </el-input>
                </el-form-item>
                <el-button
                  style="margin-left: 10px"
                  type="primary"
                  v-if="$hasAuth([0, 1])"
                  @click="exportExcel"
                >导出</el-button>
                <el-button
                  style="margin-left: 10px"
                  type="success"
                  v-if="$hasAuth([0, 1])"
                  @click="router.push('/state/whole');"
                >状态详情</el-button>
              </div>
            </div>
          </el-form>
          <div class="table-main">
            <el-table
              :data="data"
              style="width: 100% !important"
              height="100%"
              :header-cell-style="{'text-align':'center'}"
              :cell-style="{'text-align':'center'}"
              v-loading="loading"
              ref="eTable"
              border
            >
              <el-table-column type="selection"></el-table-column>
              <el-table-column
                fixed
                label="整机厂商"
                show-overflow-tooltip
                width="100"
                :filters="hardwareFactoryFilterList"
                :filter-method="(value,row) => row.hardwareFactory === value"
              >
                <template #default="scope">{{scope.row.hardwareFactory || '/'}}</template>
              </el-table-column>
              <el-table-column
                fixed
                label="CPU厂商"
                width="100"
                :filters="cpuFactoryFilterList"
                :filter-method="(value,row) => row.cpuFactory === value"
              >
                <template #default="scope">{{scope.row.cpuFactory || '/'}}</template>
              </el-table-column>

              <el-table-column
                fixed
                label="CPU代次"
                width="120"
                :filters="cpuModelFilterList"
                :filter-method="(value,row) => row.cpuModel === value"
              >
                <template #default="scope">{{scope.row.cpuModel || '/'}}</template>
              </el-table-column>
              <el-table-column
                fixed
                label="典型机型"
                show-overflow-tooltip
                width="170"
                :filters="hardwareModelFilterList"
                :filter-method="(value,row) => row.hardwareModel === value"
              >
                <template #default="scope">{{scope.row.hardwareModel || '/'}}</template>
              </el-table-column>

              <el-table-column label="扩展机型" show-overflow-tooltip>
                <el-table-column label="数量" show-overflow-tooltip width="80">
                  <template #default="scope">{{scope.row.extendModelCount || '/'}}</template>
                </el-table-column>
                <el-table-column label="型号" width="336">
                  <template #default="scope">
                    <el-popover
                      v-if="scope.row.extendModel  && scope.row.extendModel.length > 40"
                      placement="top"
                      :width="400"
                      trigger="hover"
                      :content="scope.row.extendModel"
                      popper-class="customPopover"
                    >
                      <template #reference>
                        <div class="noWarp" ref="extendModelRef">{{scope.row.extendModel}}</div>
                      </template>
                    </el-popover>
                    <div v-else ref="extendModelRef">{{scope.row.extendModel || '/'}}</div>
                  </template>
                </el-table-column>
              </el-table-column>
              <el-table-column label="操作系统" show-overflow-tooltip :min-width="200">
                <template #default="scope">{{scope.row.versionName || '/'}}</template>
              </el-table-column>
              <el-table-column label="架构">
                <template #default="scope">{{scope.row.architecture || '/'}}</template>
              </el-table-column>
              <el-table-column label="典型机型适配状态" show-overflow-tooltip width="160">
                <template #header>
                  <div style="display: flex;align-items: center;justify-content: center;">
                    <span>典型机型适配状态</span>
                    <el-tooltip
                      content="<div><span style='display: inline-block;width: 10px; height: 10px; background: #909399';></span>  未适配：未加入版本计划，无适配计划</div>
                      <div><span style='display: inline-block;width: 10px; height: 10px; background: #7030a0';></span>  适配中：已加入版本计划，正在适配中</div>
                      <div><span style='display: inline-block;width: 10px; height: 10px; background: #67c23a';></span>  已适配：适配完成，已发布兼容性清单</div>
                      <div><span style='display: inline-block;width: 10px; height: 10px; background: #f56c6c';></span>  版本不支持：版本不支持该CPU代次</div>"
                      placement="top"
                      raw-content
                    >
                      <el-icon>
                        <QuestionFilled style="width: 15px; height: 15px;cursor: pointer;" />
                      </el-icon>
                    </el-tooltip>
                  </div>
                </template>
                <template #default="scope">
                  <div v-if="scope.row.hardwareModelStatus">
                    <el-button
                      size="small"
                      type="info"
                      v-if="scope.row.hardwareModelStatus === '未适配'"
                    >{{scope.row.hardwareModelStatus}}</el-button>
                    <el-button
                      size="small"
                      type="success"
                      v-if="scope.row.hardwareModelStatus === '已适配'"
                    >{{scope.row.hardwareModelStatus}}</el-button>
                    <el-button
                      color="#7030a0"
                      class="custom-btn"
                      size="small"
                      v-if="scope.row.hardwareModelStatus === '适配中'"
                    >{{scope.row.hardwareModelStatus}}</el-button>
                    <el-button
                      type="danger"
                      size="small"
                      v-if="scope.row.hardwareModelStatus === '版本不支持'"
                    >{{scope.row.hardwareModelStatus}}</el-button>
                  </div>
                  <div v-else>
                    <el-button size="small" type="info">未适配</el-button>
                  </div>
                </template>
              </el-table-column>
              <el-table-column
                label="扩展机型适配状态"
                show-overflow-tooltip
                min-width="150"
                :resizable="false"
                fixed="right"
              >
                <template #default="scope">{{scope.row.extendModelStatus || '/'}}</template>
              </el-table-column>
            </el-table>
          </div>
          <div class="example-pagination-block" style="margin-top:7px">
            <base-pagination
              :current-page="queryForm.pageIndex"
              :page-size="queryForm.pageSize"
              :total="total"
              @change="paginationChange"
            />
          </div>
        </el-card>
      </div>
    </el-config-provider>
  </div>
</template>
<script>
import { defineComponent, onMounted, ref } from 'vue';
import {
  getVersionAll,
  getArchitecturesAll,
  getAllList
} from '@/common/api/full/index';
import { ElConfigProvider } from 'element-plus';
import zhCn from 'element-plus/lib/locale/lang/zh-cn';
import { Search } from '@element-plus/icons-vue';
import BasePagination from '@/components/BasePagination.vue';
import useList from '@/hooks/useList.js';
import { useRouter } from 'vue-router';

export default defineComponent({
  name: 'fullMachine',
  components: {
    ElConfigProvider,
    BasePagination
  },

  setup() {
    const router = useRouter();
    // 数据
    // use List
    const { init, total, data, loading, set } = useList();
    const value = ref('');
    const eTable = ref();
    // 操作系统多选列表
    let versionId = ref([]);
    // 请求参数
    const url = ref('/whole-machine/queryAll');
    const queryForm = ref({
      versionId: '',
      architecture: '',
      keyword: '',
      pageSize: 15,
      pageIndex: 1
    });
    // 操作系统列表
    let versionList = ref([]);
    // 架构列表
    let architectureList = ref([]);

    // 方法
    // 导出Excel
    const exportExcel = () => {
      loading.value = true;
      require.ensure([], async () => {
        const { export_json_to_excel } = require('@/excel/Export2Excel');
        // 设置Excel表格第一行的标题
        const tHeader = [
          '整机厂商',
          'CPU厂商',
          'CPU代次',
          '典型机型',
          '扩展机型数量',
          '扩展机型型号',
          '操作系统',
          '架构',
          '典型机型适配状态',
          '扩展机型适配状态'
        ];
        // 设置标题对应的字段
        let filterVal = [
          'hardwareFactory',
          'cpuFactory',
          'cpuModel',
          'hardwareModel',
          'extendModelCount',
          'extendModel',
          'versionName',
          'architecture',
          'hardwareModelStatus',
          'extendModelStatus'
        ];

        // 把后台传过来的数据存到list
        const res = await getAllList();
        loading.value = false;
        const data = formatJson(filterVal, res.data);
        export_json_to_excel(tHeader, data, '整机展示');
      });
    };

    const formatJson = (filterVal, jsonData) => {
      return jsonData.map(v => filterVal.map(j => v[j]));
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
    // 获取架构列表
    const getArchitecturesList = () => {
      getArchitecturesAll().then(res => {
        if (res.code === 200) {
          architectureList.value = res.data;
        }
      });
    };

    // 操作系统发生改变
    const versionChange = val => {
      queryForm.value.versionId = val.join(',');
      toQuery();
      queryForm.value.versionName = '';
    };
    // 分页发生改变
    const paginationChange = e => {
      queryForm.value.pageIndex = e.currentPage;
      queryForm.value.pageSize = e.pageSize;
      toQuery();
    };
    const toQuery = async () => {
      await init(url.value, queryForm.value);
      getFilterList();
      eTable.value.clearFilter();
    };
    // 筛选
    const hardwareFactoryFilterList = ref([]);
    const cpuFactoryFilterList = ref([]);
    const cpuModelFilterList = ref([]);
    const hardwareModelFilterList = ref([]);
    onMounted(async () => {
      // 获取操作系统列表
      getVersionList();
      // 获取架构列表
      getArchitecturesList();
      // 获取列表
      toQuery();
    });
    // 重组表头筛选列表
    const getFilterList = () => {
      hardwareFactoryFilterList.value = [];
      cpuFactoryFilterList.value = [];
      cpuModelFilterList.value = [];
      hardwareModelFilterList.value = [];
      data.value.forEach(item => {
        hardwareFactoryFilterList.value.push({
          text: item.hardwareFactory,
          value: item.hardwareFactory
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
      });
      // 去重
      set(hardwareFactoryFilterList.value);
      set(cpuFactoryFilterList.value);
      set(cpuModelFilterList.value);
      set(hardwareModelFilterList.value);
    };

    return {
      value,
      data,
      locale: zhCn,
      total,
      Search,
      queryForm,
      getVersionList,
      versionList,
      versionId,
      versionChange,
      paginationChange,
      url,
      toQuery,
      loading,
      getArchitecturesList,
      architectureList,
      formatJson,
      exportExcel,
      hardwareFactoryFilterList,
      cpuFactoryFilterList,
      cpuModelFilterList,
      hardwareModelFilterList,
      router,
      eTable
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
    .filter-area {
      display: flex;
      justify-content: space-between;
      .filter-area-right,
      .filter-area-left {
        display: flex;
      }
    }
  }
  .el-checkbox {
    display: block;
  }
</style>
