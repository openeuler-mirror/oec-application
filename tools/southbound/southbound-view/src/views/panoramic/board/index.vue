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
                <el-form-item label="芯片厂商" :show-message="false">
                  <el-select
                    v-model="queryForm.chipFactory"
                    placeholder="请选择"
                    clearable
                    @change="toQuery"
                  >
                    <el-option
                      v-for="item in chipFactoryList"
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
                    placeholder="类型、型号"
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
                  @click="router.push('/state/board');"
                >状态详情</el-button>
              </div>
            </div>
          </el-form>
          <div class="table-main">
            <el-table
              :data="data"
              style="width: 100%"
              height="100%"
              :header-cell-style="{'text-align':'center'}"
              :cell-style="{'text-align':'center'}"
              v-loading="loading"
              ref="eTable"
              border
            >
              <el-table-column type="selection"></el-table-column>
              <el-table-column fixed label="芯片厂商" show-overflow-tooltip width="100">
                <template #default="scope">{{scope.row.chipFactory || '/'}}</template>
              </el-table-column>
              <el-table-column
                fixed
                label="板卡类型"
                show-overflow-tooltip
                width="100"
                :filters="boardTypeFilterList"
                :filter-method="(value,row) => row.boardType === value"
              >
                <template #default="scope">{{scope.row.boardType || '/'}}</template>
              </el-table-column>

              <el-table-column
                fixed
                label="芯片型号"
                show-overflow-tooltip
                width="120"
                :filters="chipModelFilterList"
                :filter-method="(value,row) => row.chipModel === value"
              >
                <template #default="scope">{{scope.row.chipModel || '/'}}</template>
              </el-table-column>
              <el-table-column
                fixed
                label="典型板卡型号"
                show-overflow-tooltip
                width="130"
                :filters="typicalBoardModelFilterList"
                :filter-method="(value,row) => row.typicalBoardModel === value"
              >
                <template #default="scope">{{scope.row.typicalBoardModel || '/'}}</template>
              </el-table-column>
              <el-table-column label="扩展板卡" show-overflow-tooltip>
                <el-table-column label="数量" show-overflow-tooltip width="80">
                  <template #default="scope">{{scope.row.extendBoardModelCount || '/'}}</template>
                </el-table-column>
                <el-table-column label="型号" width="336">
                  <template #default="scope">
                    <el-popover
                      v-if="scope.row.extendBoardModel  && scope.row.extendBoardModel.length > 40"
                      placement="top"
                      :width="400"
                      trigger="hover"
                      :content="scope.row.extendBoardModel"
                      popper-class="customPopover"
                    >
                      <template #reference>
                        <div class="noWarp" ref="extendBoardModelRef">{{scope.row.extendBoardModel}}</div>
                      </template>
                    </el-popover>
                    <div
                      v-else
                      class="noWarp"
                      ref="extendBoardModelRef"
                    >{{scope.row.extendBoardModel || '/'}}</div>
                  </template>
                </el-table-column>
              </el-table-column>
              <el-table-column label="操作系统" show-overflow-tooltip min-width="150">
                <template #default="scope">{{scope.row.versionName || '/'}}</template>
              </el-table-column>

              <el-table-column>
                <template #header>
                  <div style="display: flex;align-items: center;justify-content: center;">
                    <span>适配状态</span>
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
                <el-table-column
                  label="典型板卡(arm)"
                  show-overflow-tooltip
                  min-width="120"
                  :resizable="false"
                >
                  <template #default="scope">
                    <div v-if="scope.row.typicalBoardArm">
                      <el-button
                        size="small"
                        type="info"
                        v-if="scope.row.typicalBoardArm === '未适配'"
                      >{{scope.row.typicalBoardArm}}</el-button>
                      <el-button
                        size="small"
                        type="success"
                        v-if="scope.row.typicalBoardArm === '已适配'"
                      >{{scope.row.typicalBoardArm}}</el-button>
                      <el-button
                        color="#7030a0"
                        size="small"
                        class="custom-btn"
                        v-if="scope.row.typicalBoardArm === '适配中'"
                      >{{scope.row.typicalBoardArm}}</el-button>
                      <el-button
                        type="danger"
                        size="small"
                        v-if="scope.row.typicalBoardArm === '版本不支持'"
                      >{{scope.row.typicalBoardArm}}</el-button>
                    </div>
                    <div v-else>
                      <el-button size="small" type="info">未适配</el-button>
                    </div>
                  </template>
                </el-table-column>
                <el-table-column
                  label="典型板卡(x86)"
                  show-overflow-tooltip
                  min-width="120"
                  :resizable="false"
                >
                  <template #default="scope">
                    <div v-if="scope.row.typicalBoardX86">
                      <el-button
                        size="small"
                        type="info"
                        v-if="scope.row.typicalBoardX86 === '未适配'"
                      >{{scope.row.typicalBoardX86}}</el-button>
                      <el-button
                        size="small"
                        type="success"
                        v-if="scope.row.typicalBoardX86 === '已适配'"
                      >{{scope.row.typicalBoardX86}}</el-button>
                      <el-button
                        color="#7030a0"
                        class="custom-btn"
                        size="small"
                        v-if="scope.row.typicalBoardX86 === '适配中'"
                      >{{scope.row.typicalBoardX86}}</el-button>
                      <el-button
                        type="danger"
                        size="small"
                        v-if="scope.row.typicalBoardX86 === '版本不支持'"
                      >{{scope.row.typicalBoardX86}}</el-button>
                    </div>
                    <div v-else>
                      <el-button size="small" type="info">未适配</el-button>
                    </div>
                  </template>
                </el-table-column>
                <el-table-column
                  label="扩展板卡(arm)"
                  show-overflow-tooltip
                  min-width="120"
                  :resizable="false"
                >
                  <template #default="scope">{{scope.row.extendBoardArm || '/'}}</template>
                </el-table-column>
                <el-table-column
                  label="扩展板卡(x86)"
                  show-overflow-tooltip
                  min-width="120"
                  :resizable="false"
                >
                  <template #default="scope">{{scope.row.extendBoardX86 || '/'}}</template>
                </el-table-column>
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
import { getVersionAll } from '@/common/api/full/index';
import { getChipFactoryNames } from '@/common/api/manufacturer/chip';
import { ElConfigProvider } from 'element-plus';
import { getAllList } from '@/common/api/board/index';
import BasePagination from '@/components/BasePagination.vue';
import zhCn from 'element-plus/lib/locale/lang/zh-cn';
import { Search } from '@element-plus/icons-vue';
import useList from '@/hooks/useList.js';
import { useRouter } from 'vue-router';

export default defineComponent({
  name: 'fullMachine',
  components: {
    ElConfigProvider,
    BasePagination
  },
  setup() {
    // 数据
    // use List
    const router = useRouter();
    const { init, total, data, loading, set } = useList();
    const value = ref('');
    const eTable = ref();
    // 操作系统多选列表
    let versionId = ref([]);
    // 请求参数
    const url = ref('/board/queryAll');
    const queryForm = ref({
      versionId: '',
      boardType: '',
      keyword: '',
      chipFactory: '',
      pageSize: 15,
      pageIndex: 1
    });
    // form 表单的 ref
    const form = ref();
    // 操作系统列表
    let versionList = ref([]);
    // 芯片厂商列表
    let chipFactoryList = ref([]);
    // 获取芯片厂商列表
    const getChipFactoryList = () => {
      getChipFactoryNames().then(res => {
        if (res.code === 200) {
          chipFactoryList.value = res.data;
        }
      });
    };
    // 方法
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
    // 导出Excel
    const exportExcel = () => {
      loading.value = true;
      require.ensure([], async () => {
        const { export_json_to_excel } = require('@/excel/Export2Excel');
        // 设置Excel表格第一行的标题
        const tHeader = [
          '芯片厂商',
          '板卡类型',
          '芯片型号',
          '典型板卡型号',
          '扩展板卡数量',
          '扩展板卡型号',
          '操作系统',
          '典型板卡（arm）',
          '典型板卡（x86）',
          '扩展板卡（arm）',
          '扩展板卡（x86）'
        ];
        // 设置标题对应的字段
        let filterVal = [
          'chipFactory',
          'boardType',
          'chipModel',
          'typicalBoardModel',
          'extendBoardModelCount',
          'extendBoardModel',
          'versionName',
          'typicalBoardArm',
          'typicalBoardX86',
          'extendBoardArm',
          'extendBoardX86'
        ];

        // 把后台传过来的数据存到list
        const res = await getAllList();
        loading.value = false;
        const data = formatJson(filterVal, res.data);
        export_json_to_excel(tHeader, data, '板卡展示');
      });
    };

    const formatJson = (filterVal, jsonData) => {
      return jsonData.map(v => filterVal.map(j => v[j]));
    };
    // 筛选
    const boardTypeFilterList = ref([]);
    const chipModelFilterList = ref([]);
    const typicalBoardModelFilterList = ref([]);
    // 重组表头筛选列表
    const getFilterList = () => {
      boardTypeFilterList.value = [];
      chipModelFilterList.value = [];
      typicalBoardModelFilterList.value = [];
      data.value.forEach(item => {
        boardTypeFilterList.value.push({
          text: item.boardType,
          value: item.boardType
        });
        chipModelFilterList.value.push({
          text: item.chipModel,
          value: item.chipModel
        });
        typicalBoardModelFilterList.value.push({
          text: item.typicalBoardModel,
          value: item.typicalBoardModel
        });
      });
      // 去重
      set(boardTypeFilterList.value);
      set(chipModelFilterList.value);
      set(typicalBoardModelFilterList.value);
    };
    const toQuery = async () => {
      await init(url.value, queryForm.value);
      getFilterList();
      eTable.value.clearFilter();
    };
    onMounted(() => {
      // 获取操作系统列表
      getVersionList();
      // 获取芯片厂商列表
      getChipFactoryList();
      // 获取列表
      toQuery();
    });

    return {
      value,
      data,
      url,
      form,
      init,
      exportExcel,
      locale: zhCn,
      paginationChange,
      Search,
      queryForm,
      getVersionList,
      versionList,
      versionId,
      versionChange,
      total,
      loading,
      getChipFactoryList,
      chipFactoryList,
      router,
      toQuery,
      boardTypeFilterList,
      chipModelFilterList,
      typicalBoardModelFilterList,
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
