<template>
  <div>
    <el-table :data="rowWholeData" :border="true" show-summary :summary-method="getSummaries">
      <el-table-column prop="phase" width="120"></el-table-column>
      <el-table-column prop="total" label="计划适配总数" align="center"></el-table-column>
      <el-table-column prop="process" label="适配进度" align="center">
        <template #default="scope">{{ scope.row.process === '' ? '/' : scope.row.process + '%'}}</template>
      </el-table-column>
      <el-table-column label="风险" align="center" :resizable="false">
        <template #default="scope">
          <span :class="{'text-red': scope.row.risk}">{{scope.row.risk ? '有' : '无'}}</span>
        </template>
      </el-table-column>
    </el-table>
    <div class="chart-content">
      <ChartDisplay :beta="whole.beta" :release="whole.release" ref="chartRef"></ChartDisplay>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, watch } from 'vue';
import { queryVersionModel } from '@/common/api/wholePlan';
import ChartDisplay from './chart-display.vue';
export default {
  components: { ChartDisplay },
  props: {
    versionName: {
      type: String,
      default: ''
    },
    getSummaries: {
      type: Function
    }
  },
  setup(props, ctx) {
    watch(
      () => props.versionName,
      (newVal, oldVal) => {
        if (newVal !== oldVal) {
          getVersionModel();
        }
      }
    );
    let chartRef = ref();
    const rowWholeData = ref([
      {
        phase: '版本发布前',
        total: 0,
        adapting: [],
        adaptedList: [],
        process: '',
        risk: ''
      },
      {
        phase: '版本发布后',
        total: 0,
        adapting: [],
        adaptedList: [],
        process: '',
        risk: ''
      }
    ]);
    let whole = ref({
      beta: [0, 0],
      release: [0, 0]
    });

    const getVersionModel = async () => {
      let res = await queryVersionModel(props.versionName);
      if (res.code === 200) {
        let wholeData = [
          {
            phase: '版本发布前',
            total: 0,
            adapting: [],
            adaptedList: [],
            process: '',
            risk: ''
          },
          {
            phase: '版本发布后',
            total: 0,
            adapting: [],
            adaptedList: [],
            process: '',
            risk: ''
          }
        ];
        if (JSON.stringify(res.data) === '{}') {
          rowWholeData.value = wholeData;
          ctx.emit('alphaChange', '', '');
          chartRef.value.update([0, 0], [0, 0]);
          return;
        }
        let alphaStages = res.data.alphaTables;
        let alphaStagesText = res.data.alphaTables?.replace(/<[^>]+>/g, '');
        ctx.emit('alphaChange', alphaStages, alphaStagesText);
        if (Array.isArray(res.data.betaTables)) {
          let resObj = handleData(res.data.betaTables);
          Object.assign(wholeData[0], resObj);
          whole.value.beta = [
            resObj.adaptedList.length,
            resObj.adapting.length
          ];
        }
        wholeData[0].risk = res.data.betaTableStatus ? true : false;
        if (Array.isArray(res.data.releaseTables)) {
          let resObj = handleData(res.data.releaseTables);
          Object.assign(wholeData[1], resObj);

          whole.value.release = [
            resObj.adaptedList.length,
            resObj.adapting.length
          ];
        }
        wholeData[1].risk = res.data.releaseTableStatus ? true : false;

        rowWholeData.value = wholeData;
        chartRef.value.update(whole.value.beta, whole.value.release);
      }
    };
    const handleData = dataList => {
      let obj = { total: 0, adapting: [], adaptedList: [], process: 0 };
      let adaptedList = [],
        adapting = [];
      dataList.forEach(item => {
        if (item.model) {
          if (item.status === '1') {
            adaptedList.push(item.model);
          } else {
            adapting.push(item.model);
          }
        }
      });
      obj.total = adaptedList.length + adapting.length;
      obj.adaptedList = adaptedList;
      obj.adapting = adapting;
      if (!adaptedList.length && !adapting.length) {
        obj.process = '';
        return obj;
      }
      if (adaptedList.length) {
        obj.process = (
          (adaptedList.length / (adaptedList.length + adapting.length)) *
          100
        ).toFixed(2);
      } else {
        obj.process = '0';
      }
      return obj;
    };

    onMounted(() => {
      getVersionModel();
    });
    return {
      chartRef,
      rowWholeData,
      whole
    };
  }
};
</script>
<style lang="scss" scoped>
.chart-content {
  margin: 0 auto;
  width: 100%;
}
</style>