<template>
  <el-table :data="data" :border="true" row-key="id" height="100%" :summary-method="getSummaries" show-summary>
    <el-table-column :prop="rowProps.prop" :label="rowProps.label" :width="width"></el-table-column>
    <el-table-column label="典型板卡适配状态" align="center">
      <el-table-column prop="typicalModelAdaptList" label="已适配" align="center" :resizable="false">
        <template v-slot="scope">
          <hover-display :list="scope.row.typicalModelAdaptList" type="board"></hover-display>
        </template>
      </el-table-column>
      <el-table-column prop="typicalModelPlanAdaptList" label="适配中" align="center" :resizable="false">
        <template v-slot="scope">
          <hover-display :list="scope.row.typicalModelPlanAdaptList" type="board"></hover-display>
        </template>
      </el-table-column>
      <el-table-column prop="typicalModelNotAdaptList" label="未适配" align="center" :resizable="false">
        <template v-slot="scope">
          <hover-display :list="scope.row.typicalModelNotAdaptList" type="board"></hover-display>
        </template>
      </el-table-column>
    </el-table-column>
    <el-table-column label="扩展板卡适配状态" align="center">
      <el-table-column prop="extendModelAdaptList" label="已适配" align="center" :resizable="false">
        <template v-slot="scope">
          <hover-display :list="scope.row.extendModelAdaptList" type="board"></hover-display>
        </template>
      </el-table-column>
      <el-table-column prop="extendModelPlanAdaptList" label="适配中" align="center" :resizable="false">
        <template v-slot="scope">
          <hover-display :list="scope.row.extendModelPlanAdaptList" placement="left" type="board"></hover-display>
        </template>
      </el-table-column>
      <el-table-column prop="extendModelNotAdaptList" label="未适配" align="center" :resizable="false">
        <template v-slot="scope">
          <hover-display :list="scope.row.extendModelNotAdaptList" placement="left" type="board"></hover-display>
        </template>
      </el-table-column>
    </el-table-column>
    <el-table-column prop="modelNotSupList" label="版本不支持" align="center" :resizable="false">
      <template v-slot="scope">
        <hover-display :list="scope.row.modelNotSupList" placement="left" type="board"></hover-display>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import { h } from 'vue';
import hoverDisplay from '../../hover-display.vue';
export default {
  name: 'boardMainTable',
  components: {hoverDisplay},
  props: {
    total: {
      type: Number,
      default: 0
    },
    data: {
      type: Array,
      default: () => []
    },
    rowProps: {
      type: Object,
      default: () => ({
        prop: 'chipFactory',
        label: '芯片厂商'
      })
    },
    width: {
      type: String,
      default: '120'
    }
  },
  setup(prop){
    const getSummaries = (param) => {
      const { columns, data } = param;
      const sums = [];
      let process = '0 %';
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = sums[index] = h('div', null, [
            h('div', {class: 'dv-top'}, '总计'),
            h('div', {class: 'dv-center'}, ''),
            h('div', {class: 'dv-bottom'}, '比例')
          ]);
          return;
        }
        const values = data.map((item) => item[column.property].length);
        const sum = values.reduce((prev, curr) => {
          return prev + curr;
        }, 0);
        process = '0 %';
        if(sum && prop.total) {
          process = ((sum / prop.total) * 100).toFixed(2) + '%';
        } 
        sums[index] = h('div', null, [
          h('div', {class: 'dv-top'}, sum),
          h('div', {class: 'dv-center'}, ''),
          h('div', {class: 'dv-bottom'}, process)
        ]);
      });
      return sums;
    };
    return {
      getSummaries
    };
  }
};
</script>

<style lang="scss" scoped>
:deep(.dv-center) {
  position: absolute;
  left: 0;
  right: 0;
  height: 1px;
  background-color: #eee;
}
:deep(.dv-top) {
  padding-bottom: 8px;
}
:deep(.dv-bottom) {
  padding-top: 8px;
}
</style>