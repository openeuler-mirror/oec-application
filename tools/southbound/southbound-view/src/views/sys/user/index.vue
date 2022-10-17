<template>
  <div class="card-warpper">
    <el-card shadow="hover">
      <!-- 头部过滤 -->
      <el-form :model="queryForm" :show-message="false" inline >
        <div class="filter-area d-flex">
            <div class="filter-area-left">
              <el-form-item label="用户角色" :show-message="false">
                <el-select v-model="queryForm.role" placeholder="请选择用户角色" clearable @change="queryList">
                  <el-option label="管理员" :value="1" />
                  <el-option label="普通用户" :value="2" />
                </el-select>
              </el-form-item>
            </div>
            <div class="filter-area-right">
              <el-form-item label="搜索" style="width: 100%">
                <el-input @keyup.enter.native="queryList" :show-message="false" v-model="queryForm.keyword"
                placeholder="用户名" style="width: 100%">
                  <template #suffix>
                    <el-icon @click="queryList" class="pointer">
                      <Search></Search>
                    </el-icon>
                  </template>
                </el-input>
              </el-form-item>
              <el-button type="success" @click="handleAddOrUpdate('add')">新增</el-button>
              <el-button type="danger" @click="handleDel()" :disabled="multipleSelection.length === 0">删除</el-button>
            </div>
          </div>
      </el-form>
      <!-- 表格 -->
      <div class="table-main">
        <el-table :data="renderData" height="100%" :highlight-current-row="false" :border="true"
          v-loading="loading"
          :header-cell-style="{backgroundColor: '#f5f7fa', 'text-align':'center'}"
          :cell-style="{'text-align':'center'}" 
          @selection-change="handleSelectionChange" row-key="id">
          <el-table-column type="selection" width="55" :selectable="selectableHandle"/>
          <el-table-column prop="username" label="用户名" :resizable="false">
            <template v-slot="scope">{{scope.row['username'] || '/'}}</template>
          </el-table-column>
          <el-table-column prop="role" label="角色">
            <template v-slot="scope">
                <span v-if="scope.row.role === 0">超级管理员</span>
                <span v-else-if="scope.row.role === 1">管理员</span>
                <span v-else>普通用户</span>
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间" :resizable="false">
            <template v-slot="scope">{{scope.row['createTime'] || '/'}}</template>
          </el-table-column>
          <el-table-column label="操作" fixed="right" width="80" align="center" :resizable="false">
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
    <whole-add-or-update ref="wholeAddOrUpdateRef" @refreshList="queryList"></whole-add-or-update>
  </div>
</template>

<script>
import { reactive, ref, onMounted, markRaw} from 'vue';
import { ElMessageBox, ElMessage } from 'element-plus';
import { WarningFilled } from '@element-plus/icons-vue';
import WholeAddOrUpdate from './add-or-update.vue';
import BasePagination from '@/components/BasePagination.vue';
import { getAllList ,doDelete } from '@/common/api/sys/user.js';
export default {
  name: 'sysUser',
  components: {BasePagination, WholeAddOrUpdate},
  setup() {
    const queryForm = reactive({
      role: '',
      keyword: ''
    });
    const pagination = reactive({
      currentPage: 1,
      pageSize: 15,
      total: 0
    });
    const paginationChange = (data) => {
      pagination.currentPage = data.currentPage;
      pagination.pageSize = data.pageSize;
      queryList();
    };
    const loading = ref(false);
    const renderData = ref([]);
    const queryList = async () => {
      loading.value = true;
      let params = {
        ...queryForm,
        pageIndex: pagination.currentPage,
        pageSize: pagination.pageSize
      };
      let res = await getAllList(params);
      if (res.code === 200) {
        pagination.total = res.data.total;
        renderData.value = res.data.list;
        loading.value = false;
      }
    };

    const wholeAddOrUpdateRef = ref('');
    const handleAddOrUpdate = (type, rowdata) => {
      let data = '';
      if (type === 'edit') {
        data = {
          id: rowdata.id,
          username: rowdata.username, 
          password: rowdata.password,
          role: rowdata.role
        };
      }
      wholeAddOrUpdateRef.value.open(type, data);
    };
    
    let multipleSelection = ref([]);
    const handleSelectionChange = (val) => {
      multipleSelection.value = val;
    };

    const handleDel = () => {
      ElMessageBox.confirm('此操作将永久删除选中数据，是否继续？', '提示', {
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
    
    onMounted(() => {
      queryList();
    });
    let selectableHandle = (row) => {
      return row.role === 0 ? false : true; 
    };
    return {
      queryForm,
      loading,
      renderData,
      pagination,
      wholeAddOrUpdateRef,
      handleAddOrUpdate,
      paginationChange,
      multipleSelection,
      handleSelectionChange,
      queryList,
      handleDel,
      selectableHandle
    };
  }
};
</script>

<style lang="scss" scoped>
.filter-area-right {
  .el-form-item {
    margin-right: 10px;
  }
}
</style>

