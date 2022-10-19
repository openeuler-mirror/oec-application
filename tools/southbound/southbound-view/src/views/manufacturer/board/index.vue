<template>
  <div class="card-warpper">
    <el-config-provider :locale="locale">
      <div class="system-user-container">
        <el-card shadow="hover">
          <el-form :model="queryForm" :show-message="false" inline>
            <div class="filter-area d-flex">
              <div class="filter-area-left">
                <el-form-item label="芯片厂商" :show-message="false">
                  <el-select v-model="queryForm.chipFactory" placeholder="请选择芯片厂商" clearable @change="toQuery">
                    <el-option v-for="(item,index) in chipFactoryList" :key="index" :label="item" :value="item" />
                  </el-select>
                </el-form-item>
              </div>
              <div class="filter-area-right">
                <el-form-item label="搜索" style="width: 100%; margin-right: 0;">
                  <el-input placeholder="板卡类型、芯片型号、典型板卡、扩展板卡" size="default" v-model="queryForm.keyword" style="width: 280px" @keyup.enter.native="toQuery">
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
            <el-table :data="data" ref="eTable" style="width: 100%" height="100%" :header-cell-style="{'text-align':'center'}" :cell-style="{'text-align':'center'}" @selection-change="handleSelectionChange" v-loading="loading" border>
              <el-table-column type="selection"></el-table-column>
              <el-table-column fixed label="芯片厂商" show-overflow-tooltip width="120" min-width="85">
                <template #default="scope">{{scope.row.chipFactory || '/'}}</template>
              </el-table-column>
              <el-table-column fixed label="板卡类型" show-overflow-tooltip width="100" min-width="85" :filters="boardTypeFilterList" :filter-method="(value,row) => row.boardType === value">
                <template #default="scope">{{scope.row.boardType || '/'}}</template>
              </el-table-column>
              <el-table-column fixed label="芯片型号" show-overflow-tooltip width="120" min-width="85" :filters="chipModelFilterList" :filter-method="(value,row) => row.chipModel === value">
                <template #default="scope">{{scope.row.chipModel || '/'}}</template>
              </el-table-column>
              <el-table-column fixed label="典型板卡型号" show-overflow-tooltip width="150" min-width="110" :filters="typicalBoardModelFilterList" :filter-method="(value,row) => row.typicalBoardModel === value">
                <template #default="scope">{{scope.row.typicalBoardModel || '/'}}</template>
              </el-table-column>
              <el-table-column label="物料编码" show-overflow-tooltip width="100" :filters="boardItemFilterList" :filter-method="(value,row) => row.boardItem === value">
                <template #default="scope">{{scope.row.boardItem || '/'}}</template>
              </el-table-column>
              <el-table-column label="扩展板卡型号" show-overflow-tooltip>
                <el-table-column label="数量" show-overflow-tooltip width="80">
                  <template #default="scope">
                    {{scope.row.extendBoardModel ? scope.row.extendBoardModel.split(',').length : '/'}}</template>
                </el-table-column>
                <el-table-column label="型号/物料编码" width="445" min-width="300">
                  <template #default="scope">
                    <el-popover v-if="scope.row.extendBoardModelItem && scope.row.extendBoardModelItem.length > 40" placement="top" :width="400" trigger="hover" :content="scope.row.extendBoardModelItem" popper-class="customPopover">
                      <template #reference>
                        <div class="noWarp">{{scope.row.extendBoardModelItem}}</div>
                      </template>
                    </el-popover>
                    <div v-else>{{scope.row.extendBoardModelItem || '/'}}</div>
                  </template>
                </el-table-column>
              </el-table-column>
              <el-table-column label="x86_64优先级" show-overflow-tooltip width="80">
                <template #default="scope">{{ scope.row.x86Priority || '/'}}</template>
              </el-table-column>
              <el-table-column label="aarch64优先级" show-overflow-tooltip width="80">
                <template #default="scope">{{ scope.row.armPriority || '/'}}</template>
              </el-table-column>
              <el-table-column label="需求来源" show-overflow-tooltip width="110" min-width="85" :filters="demandSourceFilterList" :filter-method="(value,row) => row.demandSource === value">
                <template #default="scope">{{ scope.row.demandSource || '/'}}</template>
              </el-table-column>
              <el-table-column label="接口人" show-overflow-tooltip width="110">
                <template #default="scope">{{scope.row.interfacePerson || '/'}}</template>
              </el-table-column>
              <el-table-column label="联系方式" show-overflow-tooltip width="170" min-width="85">
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
        <eForm ref="form" :isAdd="isAdd" @refreshList="toQuery" />
        <import-excel ref="excel" :type="5" @refreshList="toQuery" />
      </div>
    </el-config-provider>
  </div>
</template>
<script>
import { defineComponent, onMounted, ref, markRaw } from 'vue';
import { getChipFactory, doDelete, getAllList } from '@/common/api/manufacturer/board';
import eForm from './form.vue';
import ImportExcel from '@/components/ImportExcel.vue';
import { ElConfigProvider, ElMessageBox, ElMessage } from 'element-plus';
import BasePagination from '@/components/BasePagination.vue';
import zhCn from 'element-plus/lib/locale/lang/zh-cn';
import { Search, WarningFilled } from '@element-plus/icons-vue';
import useList from '@/hooks/useList.js';

export default defineComponent({
  name: 'BoardManufacturers',
  components: {
    eForm,
    ElConfigProvider,
    BasePagination,
    ImportExcel
  },
  setup () {
    const { init, total, data, loading, set } = useList();
    const value = ref('');
    const eTable = ref();
    // 操作系统多选列表
    let versionIds = ref([]);
    // 请求参数
    const url = ref('/board-factory/queryAll');
    const queryForm = ref({
      chipFactory: '',
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
    // 芯片厂商列表
    let chipFactoryList = ref([]);

    // 获取芯片厂商列表
    const getChipFactoryList = () => {
      getChipFactory()
        .then(res => {
          if (res.code === 200) {
            chipFactoryList.value = res.data;
          }
        })
        .catch(e => {
          return Promise.reject(e);
        });
    };

    // 操作系统发生改变
    const versionChange = val => {
      queryForm.value.versionIds = val.join(',');
      toQuery();
      queryForm.value.versionIds = '';
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
    const edit = row => {
      isAdd.value = false;
      form.value.dialogVisible = true;
      form.value.form = {
        boardId: row.boardId,
        chipFactory: row.chipFactory, // 芯片厂商
        boardType: row.boardType, // 板卡类型
        chipModel: row.chipModel, // 芯片型号
        typicalBoardModel: row.typicalBoardModel, // 典型板卡型号
        boardItem: row.boardItem, // 编码item
        updateTime: row.createTime, // 更新时间
        extendBoardModel: row.extendBoardModel, // 扩展型号
        extendBoardModelItem: row.extendBoardModelItem, // 扩展编码item
        x86Priority: Number(row.x86Priority), // x86_64优先级
        armPriority: Number(row.armPriority), // aarch64优先级
        interfacePerson: row.interfacePerson, // 接口人
        contact: row.contact,
        middleman: row.middleman, // 对应BD
        demandSource: row.demandSource // 需求来源
      };
      if (row.extendBoardModel) {
        form.value.dynamicTags = row.extendBoardModelItem.split(',');
      }
      if (row.extendBoardModelItem) {
        let extendBoardModelArr = [];
        row.extendBoardModelItem.split(',').forEach(item => {
          let itemArr = item.split('/');
          extendBoardModelArr.push({
            model: itemArr[0],
            code: itemArr[1] || ''
          });
        });
        form.value.extendBoardModelArr = extendBoardModelArr;
      }

      form.value.getChipModelList(row.cpuFactory);
    };

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
          delIds.value.push(item.boardId);
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
          '芯片厂商',
          '板卡类型',
          '芯片型号',
          '典型板卡型号',
          '物料编码',
          '扩展板卡型号数量',
          '扩展板卡型号/物料编码',
          'x86_64优先级',
          'aarch64优先级',
          '需求来源',
          '接口人',
          '联系方式',
          '对应BD',
          '创建日期'
        ];
        //设置标题对应的字段
        let filterVal = [
          'chipFactory',
          'boardType',
          'chipModel',
          'typicalBoardModel',
          'boardItem',
          'extendBoardModelItemCount',
          'extendBoardModelItem',
          'x86Priority',
          'armPriority',
          'demandSource',
          'interfacePerson',
          'contact',
          'middleman',
          'createTime'
        ];

        //把后台传过来的数据存到list
        const res = await getAllList();
        res.data.forEach(item => {
          if (item.extendBoardModelItem.trim().length > 0) {
            item.extendBoardModelItemCount = item.extendBoardModelItem.split(',').length;
          } else {
            item.extendBoardModelItemCount = 0;
          }
        });
        loading.value = false;
        const data = formatJson(filterVal, res.data);
        export_json_to_excel(tHeader, data, '板卡厂商');
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
    const boardTypeFilterList = ref([]);
    const chipModelFilterList = ref([]);
    const typicalBoardModelFilterList = ref([]);
    const boardItemFilterList = ref([]);
    const x86PriorityFilterList = ref([]);
    const armPriorityFilterList = ref([]);
    const demandSourceFilterList = ref([]);
    const createTimeFilterList = ref([]);
    // 重组表头筛选列表
    const getFilterList = () => {
      boardTypeFilterList.value = [];
      chipModelFilterList.value = [];
      typicalBoardModelFilterList.value = [];
      boardItemFilterList.value = [];
      x86PriorityFilterList.value = [];
      armPriorityFilterList.value = [];
      demandSourceFilterList.value = [];
      createTimeFilterList.value = [];
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
        boardItemFilterList.value.push({
          text: item.boardItem,
          value: item.boardItem
        });
        x86PriorityFilterList.value.push({
          text: item.x86Priority,
          value: item.x86Priority
        });
        armPriorityFilterList.value.push({
          text: item.armPriority,
          value: item.armPriority
        });
        demandSourceFilterList.value.push({
          text: item.demandSource,
          value: item.demandSource
        });
        createTimeFilterList.value.push({
          text: item.createTime,
          value: item.createTime
        });
      });
      // 去重
      set(boardTypeFilterList.value);
      set(chipModelFilterList.value);
      set(typicalBoardModelFilterList.value);
      set(boardItemFilterList.value);
      set(x86PriorityFilterList.value);
      set(armPriorityFilterList.value);
      set(demandSourceFilterList.value);
      set(createTimeFilterList.value);
    };
    onMounted(() => {
      // 获取芯片厂商列表
      getChipFactoryList();
      // 获取列表
      toQuery();
    });

    return {
      boardTypeFilterList,
      chipModelFilterList,
      typicalBoardModelFilterList,
      boardItemFilterList,
      x86PriorityFilterList,
      armPriorityFilterList,
      demandSourceFilterList,
      createTimeFilterList,
      value,
      data,
      url,
      form,
      init,
      add,
      edit,
      del,
      locale: zhCn,
      paginationChange,
      Search,
      isAdd,
      handleSelectionChange,
      delIds,
      queryForm,
      chipFactoryList,
      getChipFactoryList,
      versionIds,
      versionChange,
      total,
      loading,
      importExcel,
      excel,
      exportExcel,
      toQuery,
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
