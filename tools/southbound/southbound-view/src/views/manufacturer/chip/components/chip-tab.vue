<template>
  <el-table :data="data" style="width: 100%" height="100%" :header-cell-style="{backgroundColor: '#f5f7fa','text-align':'center'}" :cell-style="{'text-align':'center'}" @selection-change="handleSelectionChange" border>
    <el-table-column type="selection"></el-table-column>
    <el-table-column fixed label="芯片厂商" show-overflow-tooltip>
      <template #default="scope">{{scope.row.chipFactory || '/'}}</template>
    </el-table-column>
    <el-table-column fixed label="芯片型号" show-overflow-tooltip>
      <template #default="scope">{{scope.row.chipModel || '/'}}</template>
    </el-table-column>
    <el-table-column label="支持的OS版本" :resizable="false">
      <template #default="scope">
        <el-popover v-if="scope.row.versionNames && scope.row.versionNames.length > 0" placement="top" :width="400" trigger="hover" :content="scope.row.versionNames" popper-class="customPopover">
          <template #reference>
            <div class="noWarp">{{scope.row.versionNames}}</div>
          </template>
        </el-popover>
        <div v-else>{{'/'}}</div>
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
  name: 'ChipManufacturersTab',
  components: {},
  props: {
    data: {
      type: Object
    }
  },
  // 自定义事件
  emits: ['editChip', 'deleteChip'],
  setup (props, context) {
    // 删除列表Ids
    const delIds = ref([]);
    // 列选中发生改变
    const handleSelectionChange = val => {
      delIds.value = [];
      if (val.length > 0) {
        val.forEach(item => {
          delIds.value.push(item.chipId);
        });
      }
      context.emit('deleteChip', delIds.value);
    };

    const edit = row => {
      let form = {
        chipFactory: row.chipFactory, //芯片厂商
        chipModel: row.chipModel, //芯片型号
        driverName: row.driverName, //驱动名称
        driverVersion: row.driverVersion, //驱动版本
        versionIds: row.versionIds, //支持的OS版本
        webDriverUrl: row.webDriverUrl, //官网驱动链接
        allDriverUrl: row.allDriverUrl, //软件所驱动链接
        chipId: row.chipId
      };
      context.emit('editChip', form);
    };
    return {
      handleSelectionChange,
      edit
    };
  }
};
</script>