<template>
  <el-table :data="tableData" :border="true" max-height="200">
    <el-table-column label="扩展板卡型号">
      <template #default="scope">
        <el-input v-model="scope.row.model" placeholder="请输入型号" @input="handleInput" maxlength="40" show-word-limit></el-input>
      </template>
    </el-table-column>
    <el-table-column label="物料编码">
      <template #default="scope">
        <el-input v-model="scope.row.code" placeholder="请输入物料编码" @input="handleInput" maxlength="10" show-word-limit></el-input>
      </template>
    </el-table-column>
    <el-table-column label="操作" width="120">
      <template #default="scope">
        <el-button circle :icon="Plus" type="primary" @click="add(scope.$index)"></el-button>
        <el-button circle :icon="Delete" type="danger" @click="del(scope.$index)" v-if="scope.$index > 0"></el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import { ref, watch } from 'vue';
import { Plus, Delete } from '@element-plus/icons-vue';
export default {
  name: 'tableInput',
  props: {
    modelValue: {
      type: Array,
      default: () => []
    }
  },
  emits: ['update:modelValue'],
  setup (props, ctx) {
    let tableData = ref([]);
    watch(() => props.modelValue, (newVal, oldVal) => {
      if (newVal !== oldVal) {
        tableData.value = newVal;
      }
    }, { immediate: true, deep: true });
    const add = (index) => {
      tableData.value.splice(index + 1, 0, { model: '', code: '' });
      ctx.emit('update:modelValue', tableData.value);
    };
    const del = (index) => {
      tableData.value.splice(index, 1);
      ctx.emit('update:modelValue', tableData.value);
    };
    const handleInput = () => {
      ctx.emit('update:modelValue', tableData.value);
    };
    return {
      tableData,
      add,
      del,
      Plus,
      Delete,
      handleInput
    };
  }
};
</script>