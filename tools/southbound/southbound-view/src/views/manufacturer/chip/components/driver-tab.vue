<template>
  <el-table :data="data" style="width: 100%" height="100%" :header-cell-style="{backgroundColor: '#f5f7fa','text-align':'center'}" :cell-style="{'text-align':'center'}" @selection-change="handleSelectionChange" border>
    <el-table-column type="selection"></el-table-column>
    <el-table-column fixed label="芯片厂商" show-overflow-tooltip width="110">
      <template #default="scope">{{scope.row.chipFactory || '/'}}</template>
    </el-table-column>
    <el-table-column fixed label="驱动名称" show-overflow-tooltip width="110">
      <template #default="scope">{{scope.row.driverName || '/'}}</template>
    </el-table-column>
    <el-table-column label="内核驱动发布" show-overflow-tooltip width="80">
      <template #default="scope">{{scope.row.kernelDriverPublish || '/'}}</template>
    </el-table-column>
    <el-table-column label="内核驱动发布版本" show-overflow-tooltip width="100">
      <template #default="scope">{{scope.row.kernelDriverVersion || '/'}}</template>
    </el-table-column>
    <el-table-column label="外部驱动发布" show-overflow-tooltip>
      <template #default="scope">{{scope.row.exteriorDriverPublish || '/'}}</template>
    </el-table-column>
    <el-table-column label="外部驱动发布日期" show-overflow-tooltip width="100">
      <template #default="scope">{{scope.row.exteriorDriverPublishTime || '/'}}</template>
    </el-table-column>
    <el-table-column label="外部驱动版本" show-overflow-tooltip width="100">
      <template #default="scope">{{scope.row.exteriorDriverVersion || '/'}}</template>
    </el-table-column>
    <el-table-column label="官网驱动链接" width="450">
      <template #default="scope">
        <div class="preWarp">{{scope.row.webDriverUrl || '/'}}</div>
      </template>
    </el-table-column>
    <el-table-column label="软件所驱动链接" width="450" :resizable="false">
      <template #default="scope">
        <div class="preWarp">{{scope.row.allDriverUrl || '/'}}</div>
      </template>
    </el-table-column>
    <el-table-column label="操作" fixed="right" :resizable="false">
      <template v-slot="scope">
        <el-button size="small" type="primary" @click="edit(scope.row)">编辑</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import { ref } from 'vue';
export default {
  name: 'DriverManufacturersTab',
  components: {},
  props: {
    data: {
      type: Object
    }
  },
  // 自定义事件
  emits: ['editDriver'],
  setup (props, context) {
    // 删除列表Ids
    const delIds = ref([]);
    // 列选中发生改变
    const handleSelectionChange = val => {
      delIds.value = [];
      if (val.length > 0) {
        val.forEach(item => {
          delIds.value.push(item.driverId);
        });
      }
      context.emit('deleteChip', delIds.value);
    };

    const edit = row => {
      let form = {
        chipFactory: row.chipFactory, //芯片厂商
        driverName: row.driverName, //驱动名称
        kernelDriverPublish: row.kernelDriverPublish, //内核驱动发布
        kernelDriverVersion: row.kernelDriverVersion, //内核驱动版本
        exteriorDriverPublish: row.exteriorDriverPublish, //外部驱动发布
        exteriorDriverPublishTime: row.exteriorDriverPublishTime, //外部驱动发布日期
        exteriorDriverVersion: row.exteriorDriverVersion, //外核驱动版本
        versionId: row.versionId, //支持的OS版本
        webDriverUrl: row.webDriverUrl, //官网驱动链接
        allDriverUrl: row.allDriverUrl, //软件所驱动链接
        driverId: row.driverId
      };
      context.emit('editDriver', form);
    };
    return {
      handleSelectionChange,
      edit
    };
  }
};
</script>