<template>
  <div class="card-warpper">
    <el-card shadow="hover">
      <!-- 头部过滤 -->
      <el-form :model="queryForm" :show-message="false" inline >
        <div class="filter-area d-flex">
          <div class="filter-area-left">
            <el-form-item label="操作系统" :show-message="false">
              <el-select v-model="queryForm.versionId" multiple collapse-tags collapse-tags-tooltip 
                placeholder="请选择操作系统" clearable @change="queryList">
                <el-option v-for="item in versionList" :key="item" :label="item.versionName" :value="item.versionId"/>
              </el-select>
            </el-form-item>
          </div>
          <div class="filter-area-right">
            <el-form-item label="搜索" style="width: 320px">
              <el-input @keyup.enter.native="queryList" :show-message="false" v-model="queryForm.keyword"
                placeholder="芯片厂商、典型板卡、扩展板卡" style="width: 100%">
                <template #suffix>
                  <el-icon @click="queryList" class="pointer">
                    <Search></Search>
                  </el-icon>
                </template>
              </el-input>
            </el-form-item>
            <template v-if="$hasAuth([0, 1])">
              <el-button type="success" class="ml-10" @click="handleAddOrUpdate('add', '')">新增</el-button>
              <el-button type="danger" @click="handleDel()" :disabled="multipleSelection.length === 0">删除</el-button>
              <el-button type="primary" @click="exportExcel()">导出</el-button>
            </template>
          </div>
        </div>
      </el-form>
      <!-- 表格 -->
      <div class="table-main">
        <el-table height="100%" :data="renderData" :highlight-current-row="false" :border="true" ref="tableRef"
          v-loading="loading"
          :header-cell-style="{backgroundColor: '#f5f7fa', 'text-align':'center'}"
          :cell-style="{'text-align':'center'}"
          @header-dragend="headerDragend"
          @expand-change="expandChange"
          @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" v-if="$hasAuth([0, 1])"/>
          <el-table-column type="expand" fixed="left">
            <template #default="props">
              <div class="expand-area">
                <PlanBreakdown v-if="expandIndexList.includes(props.$index)" :betaData="props.row.betaListArr"
                 :releaseData="props.row.releaseListArr"></PlanBreakdown>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="chipFactory" label="芯片厂商" width="100" fixed="left"
            :filters="filterOptions.chipFactoryList" :filter-method="(value,row) => row.chipFactory === value">
            <template v-slot="scope">
              {{scope.row['chipFactory'] || '/'}}
            </template>
          </el-table-column>
          <el-table-column prop="system" label="操作系统" width="220" show-overflow-tooltip>
            <template v-slot="scope">{{scope.row['versionName'] || '/'}}</template>
          </el-table-column>
          <el-table-column label="版本发布前">
            <el-table-column prop="betaList" label="适配型号" width="400">
              <template v-slot="scope">
                  <el-popover v-if="scope.row.betaList && scope.row.betaList.length > 50" placement="top"
                    :width="400" trigger="hover" :content="scope.row.betaList" popper-class="customPopover">
                  <template #reference>
                    <div class="noWarp">{{scope.row.betaList}}</div>
                  </template>
                </el-popover>
                <div v-else>{{scope.row.betaList || '/'}}</div>
              </template>
            </el-table-column>
            <el-table-column prop="betaProcess" label="适配进度" width="100" show-overflow-tooltip></el-table-column>
          </el-table-column>
          <el-table-column label="版本发布后">
            <el-table-column prop="releaseList" label="适配型号" width="400">
              <template v-slot="scope">
                  <el-popover v-if="scope.row.releaseList && scope.row.releaseList.length > 50" placement="top"
                    :width="400" trigger="hover" :content="scope.row.releaseList" popper-class="customPopover">
                  <template #reference>
                    <div class="noWarp">{{scope.row.releaseList}}</div>
                  </template>
                </el-popover>
                <div v-else>{{scope.row.releaseList || '/'}}</div>
              </template>
            </el-table-column>
            <el-table-column prop="releaseProcess" label="适配进度" width="100" show-overflow-tooltip></el-table-column>
          </el-table-column>
         
          <el-table-column prop="remark" label="备注" show-overflow-tooltip :resizable="false"></el-table-column>
          <el-table-column label="操作" fixed="right" width="80" align="center" :resizable="false" v-if="$hasAuth([0, 1])">
            <template v-slot="scope">
                <el-button type="primary" size="small" @click="handleAddOrUpdate('edit', scope.row)">编辑</el-button>
            </template>
          </el-table-column>
        </el-table>      
      </div>
      <div class="pagination-block">
        <base-pagination :current-page="pagination.currentPage" :page-size="pagination.pageSize" 
        :total="pagination.total" @change="paginationChange"/>
      </div>
    </el-card>
    <board-add-or-update ref="boardAddOrUpdate" @refreshList="queryList"></board-add-or-update>
  </div>
</template>

<script>
import { reactive, ref, markRaw, onMounted} from 'vue';
import { ElMessageBox, ElMessage } from 'element-plus';
import { WarningFilled } from '@element-plus/icons-vue';
import BasePagination from '@/components/BasePagination.vue';
import boardAddOrUpdate from './add-or-update.vue';
import { getAllList, doDelete, getAllListWithExport } from '@/common/api/boardPlan';
import { queryVersionNames } from '@/common/api/versionPlan';
import PlanBreakdown from './plan-breakdown.vue';
export default {
  name: 'planBoard',
  components: {BasePagination, boardAddOrUpdate, PlanBreakdown},
  setup() {
    const queryForm = reactive({
      keyword: '',
      versionId: []
    });
    const pagination = reactive({
      currentPage: 1,
      pageSize: 15
    });

    const paginationChange = (data) => {
      pagination.currentPage = data.currentPage;
      pagination.pageSize = data.pageSize;
      queryList();
    };
    let loading = ref(false);
    let renderData = ref([]);
    const filterOptions = reactive({
      chipFactoryList: []
    });
    let tableRef = ref();
    const queryList = async () => {
      loading.value = true;
      let params = {
        pageIndex: pagination.currentPage,
        pageSize: pagination.pageSize,
        versionId: queryForm.versionId.length ? queryForm.versionId.join(',') : undefined,
        keyword: queryForm.keyword
      };
      let res = await getAllList(params);
      tableRef.value.clearFilter();
      loading.value = false;
      if (res.code === 200) {
        let data = [], chipFactorySet = new Set();
        res.data.list.forEach(item => {
          chipFactorySet.add(item.chipFactory);
          data.push({
            chipFactory: item.chipFactory,
            id: item.id,
            betaList: item.betaList,
            releaseList: item.releaseList,
            remark: item.remark,
            versionId: item.versionId,
            versionName: item.versionName,
            betaProcess: handleProcess(item.jsonBetaList),
            releaseProcess: handleProcess(item.jsonReleaseList),
            betaListArr: item.jsonBetaList?.jsonObjectList || [],
            releaseListArr: item.jsonReleaseList?.jsonObjectList || []
          });
        });
        renderData.value = data;
        pagination.total = res.data.total;
        filterOptions.chipFactoryList = Array.from(chipFactorySet).map(item => ({text: item, value: item}));
      }
    };
    const handleProcess = (obj) => {
      if (!obj || JSON.stringify(obj) === '{}') {
        return '/';
      }
      if (!obj.fitCount || !obj.sum) {
        return '0 %';
      }
      return ((obj.fitCount  / obj.sum) * 100).toFixed(2) + '%';
    };
   
    const boardAddOrUpdate = ref();
    /**
     * 添加或修改 
     */
    const handleAddOrUpdate = (type, rowdata) => {
      let data = '';
      if (type === 'edit') {
        data = {
          id: rowdata.id,
          chipFactory: rowdata.chipFactory,
          architecture: rowdata.architecture,
          driverName: rowdata.driverName,
          driverVersion: rowdata.driverVersion,
          versionId: rowdata.versionId,
          betaList: rowdata.betaList ? rowdata.betaList.split(',') : [],  
          releaseList: rowdata.releaseList ? rowdata.releaseList.split(',') : [],
          remark: rowdata.remark
        }; 
      }
      boardAddOrUpdate.value.open(type, data);
    };
    let multipleSelection = ref([]);
    const handleSelectionChange = (val) => {
      multipleSelection.value = val;
    };
    // 删除
    const handleDel = async () => {
      await ElMessageBox.confirm('此操作将永久删除选中数据，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info',
        icon: markRaw(WarningFilled)
      })
        .then(async () => {
          let ids = multipleSelection.value.reduce((preval, item) => {preval.push(item.id); return preval;}, []);
          let res = await doDelete(ids.join(','));
          if (res.code === 200) {
            ElMessage.success('删除成功');
            queryList();
          }
        })
        .catch(() => {});

      
    };
    const versionList = ref([]);
   
    
    // 查询操作系统
    const queryOptions = async () => {
      let res = await queryVersionNames();
      if (res.code === 200) {
        versionList.value = res.data;
      }
    };
    
    onMounted(() => {
      queryList();
      queryOptions();
    });

    let expandIndexList = ref([]);
    const expandChange = (row) => {
      let index = renderData.value.findIndex(item => item.id === row.id);
      let arrIndex = expandIndexList.value.indexOf(index);
      if (arrIndex === -1) {
        expandIndexList.value.push(index);
      }
    };
    const headerDragend = (newWidth, oldWidth, column) => {
      if (newWidth < 100 ) {
        column.width = 100;
      }
    };
    
    // 导出
    const exportExcel = () => {
      loading.value = true;
      require.ensure([], async () => {
        const { export_json_to_excel } = require('@/excel/Export2Excel');
        const tHeader = [
          '芯片厂商',
          '操作系统',
          '版本发布前适配机型',
          '版本发布前适配进度',
          '版本发布后适配机型',
          '版本发布后适配进度'
        ];
        const res = await getAllListWithExport();
        if (res.code === 200) {
          const data = formatJson(res.data);
          loading.value = false;
          export_json_to_excel(tHeader, data, '板卡计划');
        }
      });
    };

    const formatJson = (jsonData) => {
      let data = [];
      jsonData.forEach(item => {
        data.push([item.chipFactory, item.versionName, item.betaList || '/', handleProcess(item.jsonBetaList),
        item.releaseList || '/', handleProcess(item.jsonReleaseList)]);
      });
      return data;
    };
    return {
      queryForm,
      loading,
      renderData,
      pagination,
      boardAddOrUpdate,
      handleDel,
      handleAddOrUpdate,
      multipleSelection,
      handleSelectionChange,
      queryList,
      versionList,
      expandIndexList,
      expandChange,
      headerDragend,
      filterOptions,
      exportExcel,
      paginationChange,
      tableRef
      
    };
  }
};
 
</script>
<style lang="scss" scoped>
.expand-area {
  padding: 10px 20px;
}
.filter-area-right {
  .el-form-item {
    margin-right: 0px;
  }
}

</style>