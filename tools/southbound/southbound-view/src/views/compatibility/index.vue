<template>
  <div class="compatibility home-container">
    <div class="filter-area d-flex">
      <el-form-item label="时间" :show-message="false">
        <el-date-picker v-model="dateRangleValue" :shortcuts="shortcuts" type="daterange" start-placeholder="开始时间" end-placeholder="结束时间" size="small" @change="dateRangleChange" />
      </el-form-item>
    </div>
    <el-row :gutter="15" class="home-card-one">
      <el-col :span="6" @click="openModelDisplay('新增CPU型号', 'cpuModel')">
        <div class="home-card-item" style="background: #4777f5">
          <div class="text">
            <span class="font30">{{cardObj.cpuModelCount || 0}}</span>
            <div class="mt-10">
              <span>新增CPU型号数量</span>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :span="6" @click="openModelDisplay('新增整机机型', 'wholeModel')">
        <div class="home-card-item" style="background: #44aff0">
          <div class="text">
            <span class="font30">{{cardObj.wholeModelCount || 0}}</span>
            <div class="mt-10">
              <span>新增整机机型数量</span>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :span="6" @click="openModelDisplay('新增芯片型号', 'chipModel')">
        <div class="home-card-item" style="background: #45dbf7">
          <div class="text">
            <span class="font30">{{cardObj.chipModelCount || 0}}</span>
            <div class="mt-10">
              <span>新增芯片型号数量</span>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :span="6" @click="openModelDisplay('新增板卡型号', 'boardModel')">
        <div class="home-card-item" style="background: #f6d54a">
          <div class="text">
            <span class="font30">{{cardObj.boardModelCount || 0}}</span>
            <div class="mt-10">
              <span>新增板卡型号数量</span>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
    <!-- 统计图 -->
    <el-row :gutter="20" class="home-card-two">
      <el-col :span="24" class="rightChart">
        <div class="home-card-item" id="online" ref="chartRef" v-loading="loading"></div>
      </el-col>
    </el-row>
    <div class="model-box" @click="closeModelDisplay" v-if="lookShow">
      <div class="model-box-center" @click.stop="">
        <el-row :gutter="20" justify="center">
          <el-col :span="12">
            <el-card>
              <template #header>
                <div class="card-header">
                  <span>{{lookTitle}}</span>
                  <el-icon class="pointer" @click="closeModelDisplay">
                    <Close></Close>
                  </el-icon>
                </div>
              </template>
              <el-tag v-for="(item, index) in lookItems" :key="index" type="success" class="ma-1" effect="light">
                {{ item }}
              </el-tag>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>
<script>
import { ref, reactive, onMounted, onUnmounted, toRefs } from 'vue';
import * as echarts from 'echarts';
import { formatDate } from '@/common/utils';
import { getCommunityWholeData, getCommunityBoardData } from '@/common/api/compatibility';
export default {
  name: 'compatibilityList',
  setup () {
    let dateRangleValue = ref([]);
    const getDateRangle = (day = 30) => {
      const end = new Date();
      const start = new Date();
      start.setTime(start.getTime() - 3600 * 1000 * 24 * day);
      return [start, end];
    };
    const initDate = () => {
      dateRangleValue.value = getDateRangle(365);
    };
    initDate();
    const shortcuts = [
      {
        text: '最近一周',
        value: () => {
          return getDateRangle(7);
        }
      },
      {
        text: '最近一月',
        value: () => {
          return getDateRangle(30);
        }
      },
      {
        text: '最近一季',
        value: () => {
          return getDateRangle(90);
        }
      },
      {
        text: '最近一年',
        value: () => {
          return getDateRangle(365);
        }
      }
    ];
    let cardObj = reactive({
      cpuModelCount: 0,
      wholeModelCount: 0,
      chipModelCount: 0,
      boardModelCount: 0
    });
    const total = {
      cpuModelCount: 0,
      wholeModelCount: 0,
      chipModelCount: 0,
      boardModelCount: 0
    };
    const totalList = {
      cpuModel: [],
      wholeModel: [],
      chipModel: [],
      boardModel: []
    };
    const color = [
      '#4777f5',
      '#f69846',
      '#f845f1',
      '#f6d54a',
      '#ad46f3',
      '#7056B8',
      '#5045f6',
      '#ad46f3',
      '#f845f1'
    ];
    const queryCommunityWholeData = async (startTime, endTime) => {
      let res = await Promise.all([getCommunityWholeData(), getCommunityWholeData(startTime, endTime)]);
      if (res[0].code === 200) {
        total.cpuModelCount = handleStr(res[0].data.cpu).length;
        total.wholeModelCount = handleStr(res[0].data.hardwareModel).length;
      }
      if (res[1].code === 200) {
        totalList.cpuModel = handleStr(res[1].data.cpu);
        totalList.wholeModel = handleStr(res[1].data.hardwareModel);
        cardObj.cpuModelCount = totalList.cpuModel.length;
        cardObj.wholeModelCount = totalList.wholeModel.length;
      }
    };
    const queryCommunityBoardData = async (startTime, endTime) => {
      let res = await Promise.all([getCommunityBoardData(), getCommunityBoardData(startTime, endTime)]);
      if (res[0].code === 200) {
        total.boardModelCount = handleStr(res[0].data.boardModel).length;
        total.chipModelCount = handleStr(res[0].data.chipModel).length;
      }
      if (res[1].code === 200) {
        totalList.boardModel = handleStr(res[1].data.boardModel);
        totalList.chipModel = handleStr(res[1].data.chipModel);
        cardObj.boardModelCount = totalList.boardModel.length;
        cardObj.chipModelCount = totalList.chipModel.length;
      }
      loading.value = false;
    };
    const handleStr = (modelStr) => {
      if (!modelStr) {
        return [];
      }
      return modelStr.split(',').filter(item => item);
    };
    let pieChart = null;
    let chartRef = ref();
    let loading = ref(false);
    const drawPie = (cardObj, total) => {
      pieChart = echarts.init(chartRef.value);
      if (!pieChart.hasClick) {
        pieChart.on('click', (e) => {
          if (e.name.includes('新增')) {
            openModelDisplay(e.name, e.data.key);
          }
        });
        pieChart.hasClick = true;
      }
      let option = {
        legend: {
          type: 'scroll',
          data: ['总CPU型号', '新增CPU型号', '总整机机型', '新增整机机型', '总芯片型号', '新增芯片型号', '总板卡型号', '新增板卡型号']
        },
        tooltip: {
          formatter (params) {
            let value = params.value;
            let str = '';
            if (params.dataIndex === 1) {
              value = params.data.total;
              str = `<div>
                <div>${params.marker} ${params.name}</div>
                <div>数量： ${value}</div>
              </div>`;
            } else if (params.data.type) {
              value = params.data.total;
              str = `<div>
                <div>${params.marker} ${params.name}</div>
                <div>数量： ${value}</div>
              </div>`;
            } else {
              str = `<div>
                <div>${params.marker} ${params.name}</div>
                <div>数量： ${value}</div>
                <div>比例： ${params.percent + '%'}</div>
              </div>`;
            }
            return str;
          }
        },
        color,
        series: [
          {
            type: 'pie',
            radius: '32%',
            center: ['35%', '30%'],
            startAngle: -90,
            data: [{
              name: '总CPU型号', type: 'total', total: total.cpuModelCount,
              value: total.cpuModelCount, emphasis: { disabled: true }
            }]
          },
          {
            type: 'pie',
            radius: '32%',
            center: ['65%', '30%'],
            startAngle: -90,
            data: [{
              name: '总整机机型', type: 'total', total: total.wholeModelCount,
              value: total.wholeModelCount, emphasis: { disabled: true }
            }]
          },
          {
            type: 'pie',
            radius: '32%',
            center: ['35%', '75%'],
            startAngle: -90,
            data: [{
              name: '总芯片型号', type: 'total', total: total.chipModelCount,
              value: total.chipModelCount, emphasis: { disabled: true }
            }]
          },
          {
            type: 'pie',
            radius: '32%',
            center: ['65%', '75%'],
            startAngle: -90,
            data: [{
              name: '总板卡型号', type: 'total', total: total.boardModelCount,
              value: total.boardModelCount, emphasis: { disabled: true }
            }]
          },
          {
            type: 'pie',
            radius: '30%',
            center: ['35%', '30%'],
            startAngle: 0,
            data: [
              { name: '新增CPU型号', value: cardObj.cpuModelCount, key: 'cpuModel' },
              {
                name: '总CPU型号', total: total.cpuModelCount, value: total.cpuModelCount - cardObj.cpuModelCount,
                label: { show: false }, emphasis: { disabled: true }
              }]
          },
          {
            type: 'pie',
            radius: '30%',
            center: ['65%', '30%'],
            startAngle: 0,
            data: [
              { name: '新增整机机型', value: cardObj.wholeModelCount, key: 'wholeModel' },
              {
                name: '总整机机型', total: total.wholeModelCount, value: total.wholeModelCount - cardObj.wholeModelCount,
                label: { show: false }, emphasis: { disabled: true }
              }]
          },
          {
            type: 'pie',
            radius: '30%',
            center: ['35%', '75%'],
            startAngle: 0,
            data: [
              { name: '新增芯片型号', value: cardObj.chipModelCount, key: 'chipModel' },
              {
                name: '总芯片型号', total: total.chipModelCount, value: total.chipModelCount - cardObj.chipModelCount,
                label: { show: false }, emphasis: { disabled: true }
              }]
          },
          {
            type: 'pie',
            radius: '30%',
            center: ['65%', '75%'],
            startAngle: 0,
            data: [
              { name: '新增板卡型号', value: cardObj.boardModelCount, key: 'boardModel' },
              {
                name: '总板卡型号', total: total.boardModelCount, value: total.boardModelCount - cardObj.boardModelCount,
                label: { show: false }, emphasis: { disabled: true }
              }]
          }
        ]
      };
      pieChart.setOption(option);
    };
    const getData = async (startTime, endTime) => {
      loading.value = true;
      await queryCommunityWholeData(startTime, endTime);
      await queryCommunityBoardData(startTime.replace(/\//g, '.'), endTime.replace(/\//g, '.'));
      drawPie(cardObj, total);
    };
    onMounted(async () => {
      let startTime = formatDate(dateRangleValue.value[0], 'yyyy/MM/dd'),
        endTime = formatDate(dateRangleValue.value[1], 'yyyy/MM/dd');
      getData(startTime, endTime);
      window.addEventListener('resize', () => {
        pieChart && pieChart.resize();
      });
    });
    onUnmounted(() => {
      window.removeEventListener('resize', () => { });
    });
    const dateRangleChange = async (value) => {
      let startTime = formatDate(value[0], 'yyyy/MM/dd'),
        endTime = formatDate(value[1], 'yyyy/MM/dd');
      getData(startTime, endTime);
    };

    let lookModel = reactive({
      lookShow: false,
      lookTitle: '查看',
      lookItems: []
    });
    const openModelDisplay = (title, key) => {
      if (!totalList[key].length) {
        return;
      }
      lookModel.lookTitle = title;
      lookModel.lookItems = totalList[key];
      lookModel.lookShow = true;
    };
    const closeModelDisplay = () => {
      lookModel.lookShow = false;
    };
    return {
      cardObj,
      chartRef,
      loading,
      dateRangleValue,
      shortcuts,
      dateRangleChange,
      ...toRefs(lookModel),
      openModelDisplay,
      closeModelDisplay
    };
  }
};
</script>

<style lang="scss" scoped>
  .home-container {
    overflow: auto;
    min-width: 700px;
    .home-card-one {
      height: 130px;
      width: 100%;
      .home-card-item {
        .text {
          width: 70%;
          height: 100%;
          color: #fff;
          .font30 {
            font-size: 30px;
            margin-right: 5px;
            display: inline-block;
          }
        }
      }
    }
    .home-card-two {
      height: 500px;
      width: 100%;
    }
    #online {
      width: 100%;
    }
    .home-card-one,
    .home-card-two {
      margin: 20px 0;
      margin-top: 10px;

      .home-card-item {
        height: 100%;
        padding: 20px;
        display: flex;
        border-radius: 4px;
        border: 1px solid #c8c9cc;
        background-color: white;

        box-sizing: border-box;
        &:hover {
          box-shadow: 5px 10px 10px gray;
          transition: all ease 0.3s;
        }
      }
    }
  }

  .model-box {
    position: fixed;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    background-color: rgba($color: #000000, $alpha: 0.6);
    z-index: 100;
    .model-box-center {
      position: absolute;
      left: 0;
      right: 0;
      top: 0;
      bottom: 0;
      margin: auto;
      height: 300px;
      width: 60%;
      display: flex;
      justify-content: center;
      align-items: center;
    }
    :deep(.el-row) {
      min-width: 90%;
    }
    :deep(.el-card__body) {
      height: 200px;
      overflow: auto;
    }
  }
  .ma-1 {
    margin: 4px;
  }
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
</style>