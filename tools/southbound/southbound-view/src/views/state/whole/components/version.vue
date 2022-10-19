<template>
  <div style="height:100%;">
    <state-count :adaptCount="adaptCount" :notAdaptCount="notAdaptCount" :planAdaptCount="planAdaptCount" :notSupportCount="notSupportCount"></state-count>
    <div class="state-table-main">
      <main-table :data="rowData" :total="total" :rowProps="{prop: 'osVersion', label: '系统版本'}" width="200">
      </main-table>
    </div>
  </div>
</template>
<script>
import { ref, onMounted, watch, reactive, toRefs } from 'vue';
import { getOsVersionStatus } from '@/common/api/wholeStatus';
import StateCount from '../../state-count';
import MainTable from './main-table';
export default {
  name: 'stateWholeVersion',
  components: { StateCount, MainTable },
  props: {
    versionName: {
      type: String,
      default: ''
    }
  },
  setup (props) {
    watch(() => props.versionName, (newVal, oldVal) => {
      if (newVal !== oldVal) {
        queryList();
      }
    });
    let stateCount = reactive({
      adaptCount: 0,
      notAdaptCount: 0,
      planAdaptCount: 0,
      notSupportCount: 0
    });
    let total = ref(0);
    const rowData = ref([]);
    const queryList = async () => {
      let res = await getOsVersionStatus(props.versionName);
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
    onMounted(() => {
      queryList();
    });
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