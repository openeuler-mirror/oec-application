<template>
  <div class="card-warpper">
    <el-card shadow="hover">
      <el-form :model="queryForm" :show-message="false" inline >
        <div class="filter-area d-flex">
            <div class="filter-area-right">
              <el-form-item label="搜索" style="width: 100%">
                <el-input @keyup.enter.native="queryList" :show-message="false" v-model="queryForm.keyword"
                  placeholder="用户名、操作名称、详情" style="width: 100%">
                  <template #suffix>
                    <el-icon @click="queryList" class="pointer">
                      <Search></Search>
                    </el-icon>
                  </template>
                </el-input>
              </el-form-item>
              <el-button type="success" @click="logDownLoad()">导出</el-button>
            </div>
          </div>
      </el-form>
      <div class="table-main">
        <el-table :data="data" style="width: 100%" height="100%"
          :header-cell-style="{backgroundColor: '#f5f7fa', 'text-align':'center'}"
          :cell-style="{'text-align':'center'}" v-loading="loading" border>
          <el-table-column label="用户名" show-overflow-tooltip width="100" :resizable="false">
            <template #default="scope">{{scope.row.userName || '/'}}</template>
          </el-table-column>
          <el-table-column label="IP" show-overflow-tooltip width="180" :resizable="false">
            <template #default="scope">{{scope.row.host || '/'}}</template>
          </el-table-column>
          <el-table-column label="操作名称" show-overflow-tooltip width="280" :resizable="false">
            <template #default="scope">{{scope.row.operation || '/'}}</template>
          </el-table-column>
          <el-table-column label="操作结果" show-overflow-tooltip width="180" :resizable="false">
            <template #default="scope" >
              <span class="table-main-circle" :style="{'--circleBgc': scope.row.result==='1'?'#67c23a':'#f56c6c'}">
                {{ scope.row.result==='1'?'Successful':'Failed' }}
              </span>
            </template> 
          </el-table-column>
          <el-table-column label="操作时间" show-overflow-tooltip width="180" :resizable="false">
            <template #default="scope">{{scope.row.dateTime || '/'}}</template>
          </el-table-column>
          <el-table-column label="详情" show-overflow-tooltip :resizable="false">
            <template #default="scope">{{scope.row.detail || '/'}}</template>
          </el-table-column>
        </el-table>
      </div>
      <div class="example-pagination-block" style="margin-top:7px">
          <base-pagination :current-page="pagination.currentPage"
            :page-size="pagination.pageSize" :total="pagination.total" @change="paginationChange"/>
        </div>
    </el-card>
  </div>
</template>

<script>
import { ref, reactive, onMounted } from 'vue';
import { getAllList } from '@/common/api/sys/log';
import BasePagination from '@/components/BasePagination.vue';

export default {
  name: 'sysLog',
  components: {BasePagination },
  setup(){
    const data = ref();
    const queryForm = ref({
      keyword: ''
    });
    const pagination = reactive({
      currentPage: 1,
      pageSize: 15,
      total: 0
    });
    // 分页发生改变
    const paginationChange = e => {
      pagination.currentPage = e.currentPage;
      pagination.pageSize = e.pageSize;
      queryList();
    };
    // 获取日志数据
    const queryList = ()=>{
      let params = {
        keyword: queryForm.value.keyword,
        pageIndex: pagination.currentPage,
        pageSize: pagination.pageSize
      };
      getAllList(params).then((res)=>{
        if(res.code === 200){
          data.value = res.data.list;
          pagination.total = res.data.total;
        }
      }).catch(e => {
        return Promise.reject(e);
      });
    };  
    // 导出日志文件
    const logDownLoad = ()=>{
      exportExcel();
    }; 
    let loading = ref(false);
    //导出Excel
    const exportExcel = () => {
      loading.value = true;
      require.ensure([], async () => {
        const { export_json_to_excel } = require('@/excel/Export2Excel');
        //设置Excel表格第一行的标题
        const tHeader = [
          '用户名',
          'IP',
          '操作名称',
          '操作结果',
          '操作时间',
          '详情'
        ];
        //设置标题对应的字段
        let filterVal = [
          'userName',
          'host',
          'operation',
          'result',
          'dateTime',
          'detail'
        ];

        //把后台传过来的数据存到list
        const res = await getAllList({pageIndex:1, pageSize: 10000});
       
        loading.value = false;
        const data = formatJson(filterVal, res.data.list);
        export_json_to_excel(tHeader, data, '系统日志');
      });
    };
    const formatJson = (filterVal, jsonData) => {
      return jsonData.map(v => filterVal
        .map(j => {
          if(j === 'result') {
            return v[j] === '0' ? 'Failed' : 'Successful';
          }else {
            return v[j];
          }
        })
      );
    };

    onMounted(()=> {
      queryList();
    });
    return{
      data,
      queryForm,
      pagination,
      paginationChange,
      queryList,
      logDownLoad,
      loading
    };
  }
};
</script>

<style lang="scss" scoped>
.filter-area-right {
  margin-left: auto;
  .el-form-item {
    margin-right: 10px;
  }
}
 .table-main-circle {
  position: relative;
  display: inline-block;
  width: 100px;
  text-align: left;
 } 
.table-main-circle::before {
    content: '';
    position: absolute;
    left: -16px;
    top: 50%;
    transform: translateY(-50%);
    display: inline-block; 
    background-color: var(--circleBgc);
    width: 8px;
    height: 8px;
    border-radius: 50%;
    margin-right: 12px;
}
</style>