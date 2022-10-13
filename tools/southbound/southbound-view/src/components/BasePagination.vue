<template>
  <el-pagination
    :currentPage="currentPage"
    :page-size="pageSize"
    :page-sizes="pageSizes"
    :background="background"
    :layout="layout"
    :total="total"
    @size-change="handleSizeChange"
    @current-change="handleCurrentChange"
  />
</template>

<script>
import { defineComponent, reactive, toRefs } from 'vue';
export default defineComponent({
  name: 'base-pagination',
  emits: ['change'],
  props: {
    currentPage: {
      type: Number,
      default: 1
    },
    pageSize: {
      type: Number,
      default: 15
    },
    pageSizes: {
      type: Array,
      default: () => [15, 30, 45, 60, 100, 200]
    },
    layout: {
      type: String,
      default: () => 'total, sizes, prev, pager, next, jumper'
    },
    background: {
      type: Boolean,
      default: true
    },
    total: {
      type: Number,
      default: 0
    }
  },
  setup(props, ctx) {
    let pageInfo = reactive({
      currentPage: props.currentPage,
      pageSize: props.pageSize
    });

    const handleSizeChange = e => {
      pageInfo.pageSize = e;
      ctx.emit('change', {
        currentPage: pageInfo.currentPage,
        pageSize: pageInfo.pageSize
      });
    };
    const handleCurrentChange = e => {
      pageInfo.currentPage = e;
      ctx.emit('change', {
        currentPage: pageInfo.currentPage,
        pageSize: pageInfo.pageSize
      });
    };
    return {
      ...toRefs(pageInfo),
      handleSizeChange,
      handleCurrentChange
    };
  }
});
</script>
