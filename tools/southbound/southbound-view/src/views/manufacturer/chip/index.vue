<template>
  <div class="card-warpper">
    <el-config-provider :locale="locale">
      <div class="system-user-container">
        <el-card shadow="hover">
          <el-form :model="queryForm" :show-message="false" inline>
            <div class="filter-area d-flex">
              <div class="filter-area-left">
                <el-form-item label="芯片厂商" :show-message="false" v-show="tabInfo[activeTab - 1].addQueryItem.hasOwnProperty('chipFactory')">
                  <el-select v-model="tabInfo[activeTab - 1].addQueryItem['chipFactory'] " placeholder="请选择芯片厂商" clearable @change="init(url, tabInfo[activeTab - 1 ].addQueryItem)">
                    <el-option v-for="(item,index) in chipFactoryList" :key="index" :label="item" :value="item" />
                  </el-select>
                </el-form-item>
                <el-form-item label="操作系统" prop="versionIds" v-show=" tabInfo[activeTab - 1].addQueryItem.hasOwnProperty('versionId')">
                  <el-select v-model="tabInfo[activeTab - 1 ].addQueryItem['versionId']" placeholder="请选择操作系统" clearable collapse-tags @change="init(url, tabInfo[activeTab - 1 ].addQueryItem)">
                    <el-option v-for="(item,index) in versionList" :key="index" :label="item.versionName" :value="item.versionId + ''" />
                  </el-select>
                </el-form-item>
              </div>
              <div class="filter-area-right">
                <el-form-item label="搜索" style="width: 100%; margin-right: 0;">
                  <el-input :placeholder="activePlaceholder" size="default" v-model="tabInfo[activeTab - 1 ].addQueryItem['keyword']" style="width: 100%" @keyup.enter.native="init(url, tabInfo[activeTab - 1 ].addQueryItem)">
                    <template #suffix>
                      <el-icon @click="init(url, tabInfo[activeTab - 1 ].addQueryItem)" class="pointer">
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
            <el-tabs tab-position="top" v-model="activeTab" @tab-change="handleChange">
              <el-tab-pane v-for="item in tabInfo" :key="item.id" :label="item.label" :name="item.id"></el-tab-pane>
            </el-tabs>
            <!-- 添加表格 -->
            <div class="table-main-special">
              <chip-tab v-if="activeTab === 1" :data="data" @editChip="edit" @deleteChip="getSelectionIds"></chip-tab>
              <driver-tab v-else :data="data" @editDriver="edit" @deleteChip="getSelectionIds"></driver-tab>
            </div>
          </div>

          <div class="example-pagination-block" style="margin-top:7px">
            <base-pagination ref="paginationRef" :current-page="queryForm.pageIndex" :page-size="queryForm.pageSize" :total="total" @change="paginationChange" />
          </div>
        </el-card>
        <ChipForm ref="chipFormRef" :isAdd="isAdd" @refreshList="refreshList()" />
        <DriverForm ref="driverFormRef" :isAdd="isAdd" @refreshList="refreshList()" />
        <import-excel ref="excel" :type="activeTab === 1 ? 3 : 4" @refreshList="refreshList" />
      </div>
    </el-config-provider>
  </div>
</template>
<script>
import { defineComponent, onMounted, ref, markRaw } from 'vue';
import {
  doDelete as deleteChip,
  getChipAllList
} from '@/common/api/manufacturer/chip';
import {
  doDelete as deleteDriver,
  getDriverAllList
} from '@/common/api/manufacturer/driver';
import { getChipFactoryNames } from '@/common/api/manufacturer/chip';
import { getVersionAll } from '@/common/api/full/index';
import ChipForm from './components/chip-update-add.vue';
import ChipTab from './components/chip-tab.vue';
import DriverTab from './components/driver-tab.vue';
import DriverForm from './components/driver-update-add.vue';
import ImportExcel from '@/components/ImportExcel.vue';
import { ElConfigProvider, ElMessageBox, ElMessage } from 'element-plus';
import zhCn from 'element-plus/lib/locale/lang/zh-cn';
import { Search, WarningFilled } from '@element-plus/icons-vue';
import BasePagination from '@/components/BasePagination.vue';
import useList from '@/hooks/useList.js';

export default defineComponent({
  name: 'ChipManufacturers',
  components: {
    ChipForm,
    ChipTab,
    DriverTab,
    DriverForm,
    ElConfigProvider,
    BasePagination,
    ImportExcel
  },

  setup () {
    const { init, total, data, loading } = useList();
    const value = ref('');
    let type = ref(3);
    // 操作系统多选列表
    let versionDriverQuery = ref([]);
    // Tab组件信息
    const tabInfo = ref([
      {
        id: 1,
        baseTabUrl: '/chip-factory/queryAll',
        label: '芯片厂商',
        placeholderName: '芯片型号、支持的OS版本',
        addQueryItem: { chipFactory: '' }
      },
      {
        id: 2,
        baseTabUrl: '/driver-manage/queryAll',
        label: '驱动管理',
        placeholderName: '驱动名称、驱动版本',
        addQueryItem: { chipFactory: '', versionId: '' }
      }
    ]);
    // 选中的Tab组件，默认第一个选中
    const activeTab = ref(1);
    // 请求查询的url
    const url = ref(tabInfo.value[activeTab.value - 1].baseTabUrl);
    // 搜索框的占位符属性
    const activePlaceholder = ref(
      tabInfo.value[activeTab.value - 1].placeholderName
    );
    // 请求参数
    const queryForm = ref({
      keyword: '',
      pageSize: 15,
      pageIndex: 1
    });
    // 驱动管理的操作系统保留值
    let versionIdreserved = ref(tabInfo.value[1].addQueryItem.versionId);
    Object.assign(
      tabInfo.value[activeTab.value - 1].addQueryItem,
      queryForm.value
    );
    // form 芯片厂商表单的 ref
    const chipFormRef = ref();
    // 驱动管理表单的ref
    const driverFormRef = ref();
    const excel = ref();
    const paginationRef = ref();
    // 新增或编辑操作的标识符
    let isAdd = ref(false);
    // 删除的 id 集合
    let delIds = ref([]);
    // 操作系统列表
    let versionList = ref([]);
    // 芯片厂商列表
    let chipFactoryList = ref([]);

    // 获取 芯片厂商列表
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
    // tab选中切换表格
    const handleChange = val => {
      url.value = tabInfo.value[activeTab.value - 1].baseTabUrl;

      activePlaceholder.value =
        tabInfo.value[activeTab.value - 1].placeholderName;
      versionIdreserved.value =
        tabInfo.value[activeTab.value - 1].addQueryItem['versionId'];
      // 清空芯片厂商下拉框、搜索栏文字以及分页恢复默认
      queryForm.value.keyword = '';
      queryForm.value.pageIndex = 1;
      paginationRef.value.currentPage = 1;
      delIds.value = [];
      // 初始化addQueryItem
      Object.keys(tabInfo.value[activeTab.value - 1].addQueryItem).forEach(
        val => {
          if (val === 'versionId') {
            tabInfo.value[activeTab.value - 1].addQueryItem[val] = versionIdreserved.value;
          } else {
            tabInfo.value[activeTab.value - 1].addQueryItem[val] = '';
          }
        }
      );

      Object.assign(
        tabInfo.value[activeTab.value - 1].addQueryItem,
        queryForm.value
      );
      init(url.value, tabInfo.value[activeTab.value - 1].addQueryItem);
      excel.value.getType(val);
    };
    // 列选中发生改变
    const getSelectionIds = val => {
      delIds.value = val;
    };
    // 分页发生改变
    const paginationChange = e => {
      queryForm.value.pageIndex = e.currentPage;
      queryForm.value.pageSize = e.pageSize;
      init(url.value, queryForm.value);
    };
    // 新增整机方法
    const add = () => {
      isAdd.value = true;
      if (activeTab.value === 1) {
        chipFormRef.value.dialogVisible = true;
      } else if (activeTab.value === 2) {
        driverFormRef.value.dialogVisible = true;
        driverFormRef.value.chipFactoryList = chipFactoryList.value;
        driverFormRef.value.versionList = versionList.value;
      }
    };

    const edit = editData => {
      isAdd.value = false;
      if (activeTab.value === 1) {
        chipFormRef.value.dialogVisible = true;
        chipFormRef.value.versionIds = editData.versionIds
          ? editData.versionIds.split(',')
          : editData.versionIds;
        chipFormRef.value.form = { ...editData };
      } else if (activeTab.value === 2) {
        driverFormRef.value.dialogVisible = true;
        driverFormRef.value.chipFactoryList = chipFactoryList.value;
        driverFormRef.value.versionList = versionList.value;
        // 编辑的支持OS版本是单选，传递字符串类型
        driverFormRef.value.versionIds = editData.versionId;
        driverFormRef.value.form = { ...editData };

        driverFormRef.value.kPublishChanged(editData.kernelDriverPublish);
        driverFormRef.value.ePublishChanged(editData.exteriorDriverPublish);
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
          activeTab.value === 1 ? toChipDelete() : toDriverDelete();
        })
        .catch(() => { });
    };
    // 删除驱动列表
    const toDriverDelete = () => {
      deleteDriver(delIds.value.join(',')).then(res => {
        if (res.code === 200) {
          ElMessage({
            message: res.msg || '删除成功',
            grouping: true,
            type: 'success'
          });
          init(url.value, tabInfo.value[activeTab.value - 1].addQueryItem);
        }
      });
    };
    // 删除芯片列表
    const toChipDelete = () => {
      deleteChip(delIds.value.join(',')).then(res => {
        if (res.code === 200) {
          ElMessage({
            message: res.msg || '删除成功',
            grouping: true,
            type: 'success'
          });
          getChipFactoryList();
          init(url.value, tabInfo.value[activeTab.value - 1].addQueryItem);
        }
      });
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
        // 设置Excel表格第一行的标题
        const chipHeader = ['芯片厂商', '芯片型号', '支持的OS版本'];
        const driverHeader = [
          '芯片厂商',
          '驱动名称',
          '内核驱动发布',
          '内核驱动版本',
          '外部驱动发布',
          '外部驱动发布日期',
          '外部驱动版本',
          '官网驱动链接',
          '软件所驱动链接'
        ];
        const tHeader = activeTab.value === 1 ? chipHeader : driverHeader;
        // 设置标题对应的字段
        let chipFilterVal = ['chipFactory', 'chipModel', 'versionNames'];
        let driverFilterVal = [
          'chipFactory',
          'driverName',
          'kernelDriverPublish',
          'kernelDriverVersion',
          'exteriorDriverPublish',
          'exteriorDriverPublishTime',
          'exteriorDriverVersion',
          'webDriverUrl',
          'allDriverUrl'
        ];
        let filterVal = activeTab.value === 1 ? chipFilterVal : driverFilterVal;

        // 把后台传过来的数据存到list
        const res =
          activeTab.value === 1
            ? await getChipAllList()
            : await getDriverAllList();
        loading.value = false;
        const data = formatJson(filterVal, res.data);
        export_json_to_excel(
          tHeader,
          data,
          activeTab.value === 1 ? '芯片厂商' : '驱动管理'
        );
      });
    };

    const formatJson = (filterVal, jsonData) => {
      return jsonData.map(v => filterVal.map(j => v[j]));
    };
    onMounted(() => {
      // 获取cpu厂商列表
      getChipFactoryList();
      // 获取操作系统列表
      getVersionList();
      // 获取列表
      init(url.value, tabInfo.value[activeTab.value - 1].addQueryItem);
    });
    const refreshList = () => {
      getVersionList();
      getChipFactoryList();
      init(url.value, tabInfo.value[activeTab.value - 1].addQueryItem);
    };

    return {
      value,
      data,
      chipFormRef,
      driverFormRef,
      paginationRef,
      add,
      edit,
      del,
      locale: zhCn,
      total,
      Search,
      isAdd,
      getSelectionIds,
      delIds,
      queryForm,
      chipFactoryList,
      getChipFactoryList,
      versionList,
      versionDriverQuery,
      getVersionList,
      paginationChange,
      url,
      init,
      loading,
      activeTab,
      toChipDelete,
      toDriverDelete,
      tabInfo,
      activePlaceholder,
      refreshList,
      exportExcel,
      importExcel,
      excel,
      type,
      handleChange
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
  .table-main-special {
    height: calc(100% - 54px);
  }
  .preWarp {
    white-space: pre-wrap;
  }
</style>
