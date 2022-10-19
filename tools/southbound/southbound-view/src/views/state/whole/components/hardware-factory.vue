<template>
  <div style="height:100%;">
    <state-count :adaptCount="adaptCount" :notAdaptCount="notAdaptCount" :planAdaptCount="planAdaptCount" :notSupportCount="notSupportCount"></state-count>
    <div class="state-table-main">
      <main-table :data="rowData" :total="total" :rowProps="{prop: 'wholeFactory', label: '整机厂商'}"></main-table>
    </div>
  </div>
</template>

<script>
import { ref, reactive, toRefs, watch } from 'vue';
import { getWholeFactoryStatus } from '@/common/api/wholeStatus';
import StateCount from '../../state-count';
import MainTable from './main-table';

export default {
  name: 'stateWholeHardwareFactory',
  props: {
    versionName: {
      type: String,
      default: ''
    }
  },
  components: { StateCount, MainTable },
  setup (props) {
    watch(() => props.versionName, (newVal, oldVal) => {
      if (newVal !== oldVal) {
        queryList();
      }
    });
    let total = ref(0);
    const rowData = ref([]);
    let stateCount = reactive({
      adaptCount: 0,
      notAdaptCount: 0,
      planAdaptCount: 0,
      notSupportCount: 0
    });
    const queryList = async () => {
      let res = await getWholeFactoryStatus(props.versionName);
      if (res.code === 200) {
        rowData.value = res.data;
        let adaptCount = 0, notAdaptCount = 0, planAdaptCount = 0, notSupportCount = 0;
        res.data.forEach(item => {
          adaptCount += (item.typicalModelAdaptList.length + item.extendModelAdaptList.length);
          notAdaptCount += (item.typicalModelNotAdaptList.length + item.extendModelNotAdaptList.length);
          planAdaptCount += (item.typicalModelPlanAdaptList.length + item.extendModelPlanAdaptList.length);
          notSupportCount += item.modelNotSupList.length;
        });
        total.value = adaptCount + notAdaptCount + planAdaptCount + notSupportCount;
        stateCount.adaptCount = adaptCount;
        stateCount.notAdaptCount = notAdaptCount;
        stateCount.planAdaptCount = planAdaptCount;
        stateCount.notSupportCount = notSupportCount;
      }
    };
    return {
      total,
      rowData,
      ...toRefs(stateCount)
    };
  }
};
</script>

<style lang="scss" scoped>
  .state-table-main {
    height: calc(100% - 77px);
  }
</style>