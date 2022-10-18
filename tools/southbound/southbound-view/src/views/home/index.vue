<template>
  <div class="home-container">
    <el-row :gutter="15" class="home-card-one">
      <el-col :span="4">
        <div class="home-card-item" style="background: #4777f5">
          <div class="text">
            <span class="font30">{{cardObj.wholeFactoryCount || 0}}</span>
            <div class="info">
              <span>整机厂商数量</span>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :span="4">
        <div class="home-card-item" style="background: #44aff0">
          <div class="text">
            <span class="font30">{{cardObj.wholeModelCount || 0}}</span>
            <div class="info">
              <span>整机机型数量</span>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :span="4">
        <div class="home-card-item" style="background: #45dbf7">
          <div class="text">
            <span class="font30">{{cardObj.boardModelCount || 0}}</span>
            <div class="info">
              <span>板卡型号数量</span>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :span="4">
        <div class="home-card-item" style="background: #f6d54a">
          <div class="text">
            <span class="font30">{{cardObj.chipFactoryCount || 0}}</span>
            <div class="info">
              <span>芯片厂商数量</span>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :span="4">
        <div class="home-card-item" style="background: #f69846">
          <div class="text">
            <span class="font30">{{cardObj.chipModelCount || 0}}</span>
            <div class="info">
              <span>芯片型号数量</span>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :span="4">
        <div class="home-card-item" style="background: #7056B8">
          <div class="text">
            <span class="font30">{{cardObj.cpuModelCount || 0}}</span>
            <div class="info">
              <span>CPU代次数量</span>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="20" class="home-card-two">
      <el-col :span="12" class="leftChart" v-loading="leftLoading">
        <div class="selectBox">
          <el-select v-model="factorySelect" placeholder="请选择" @change="getFactoryChartData">
            <el-option label="整机" :value="1" />
            <el-option label="板卡" :value="2" />
          </el-select>
        </div>
        <div v-show="!isShowLeftNoData" class="home-card-item" id="status" ref="homePieRef"></div>
        <el-empty class="home-card-item" v-show="isShowLeftNoData" description="暂无数据" />
      </el-col>
      <el-col :span="12" class="rightChart" v-loading="rightLoading">
        <div class="selectBox second">
          <el-select
            v-model="versionId"
            placeholder="请选择"
            multiple
            collapse-tags
            collapse-tags-tooltip
            @change="getVersionChartData"
            :multiple-limit="3"
            style="width: 260px;margin-right: 10px;"
          >
            <el-option
              v-for="item in versionList"
              :key="item.id"
              :label="item.versionName"
              :value="item.versionId"
            />
          </el-select>
          <el-select
            v-model="versionSelect"
            placeholder="请选择"
            @change="getVersionChartData"
            style="width: 80px"
          >
            <el-option label="整机" :value="1" />
            <el-option label="板卡" :value="2" />
          </el-select>
        </div>
        <div v-show="!isShowRightNoData" class="home-card-item" id="online" ref="homeBarRef"></div>
        <el-empty class="home-card-item" v-show="isShowRightNoData" description="暂无数据" />
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { defineComponent, onMounted, ref, onUnmounted, markRaw } from 'vue';
import {
  queryCardList,
  queryWholeModels,
  queryWholeModelSupports,
  queryBoardModels,
  queryBoardModelSupports,
  queryVersionWholeDetail,
  queryVersionBoardDetail
} from '@/common/api/home/index';
import { getVersionAll } from '@/common/api/full/index';
import { useRouter } from 'vue-router';

import * as echarts from 'echarts';
import 'echarts-wordcloud';

export default defineComponent({
  name: 'myHome',
  setup() {
    const router = useRouter();
    let homePieRef = ref();
    let homeBarRef = ref();
    // 厂商下拉默认选中
    let factorySelect = ref(1);
    // 操作系统下拉默认选中
    let versionSelect = ref(1);
    const cardObj = ref({});
    let pieChart = ref('');
    let barChart = ref('');
    let leftLoading = ref(true);
    let rightLoading = ref(true);
    // 操作系统列表
    let versionList = ref([]);
    let versionId = ref([]);
    // 是否显示无数据提示
    let isShowLeftNoData = ref(false);
    let isShowRightNoData = ref(false);
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
    const drawPie = (seriesData, total) => {
      pieChart.value = markRaw(echarts.init(homePieRef.value));
      pieChart.value.on('click', () => {
        router.push(
          factorySelect.value === 1 ? '/state/whole' : '/state/board'
        );
      });
      let option1 = {
        color,
        title: {
          text: '厂商'
        },
        grid: {
          left: 'center',
          top: 'middle',
          containLabel: true
        },
        series: [
          {
            name: '厂商',
            type: 'pie',
            radius: ['20%', '60%'],
            center: ['50%', '50%'],
            data: seriesData,
            zlevel: 10,
            label: {
              show: true,
              formatter: params => {
                let scale = ((params.value / total) * 100).toFixed(0);
                return `${params.name}：${params.value}（${scale}%）`;
              },
              fontSize: 12,
              position: 'outside'
            },
            labelLine: {
              show: true,
              length: 3,
              length2: 10
            },
            emphasis: {
              focus: 'self'
            }
          }
        ]
      };
      pieChart.value.setOption(option1);
    };

    const drawBar = (xData, data1, data2, data3) => {
      barChart.value = markRaw(echarts.init(homeBarRef.value));
      let option1 = {
        color,
        title: {
          text: '操作系统'
        },
        tooltip: {
          trigger: 'axis',
          formatter: params => {
            let str = '';
            params.forEach(item => {
              str =
                str +
                item.marker +
                item.seriesName +
                '：' +
                item.data.supportValue +
                '/' +
                item.data.total +
                '（' +
                (item.value === 'NaN' ? 0 : item.value) +
                '%' +
                '）' +
                '<br>';
            });
            return params[0].axisValueLabel + '<br>' + str;
          }
        },
        legend: {
          type: 'scroll',
          itemWidth: 15,
          itemHeight: 10,
          top: 35,
          right: 0,
          width: '50%',
          textStyle: {
            fontSize: 12
          },
          data: ['全部', '版本发布前', '版本发布后']
        },
        xAxis: {
          data: xData,
          axisTick: {
            show: false
          }
        },
        grid: {
          top: 70
        },
        yAxis: {
          name: '适配占比%',
          nameTextStyle: {
            fontSize: 12,
            padding: [0, 30, 0, 0]
          },
          type: 'value',
          max: 100
        },
        series: [
          {
            name: '全部',
            type: 'bar',
            data: data1,
            emphasis: {
              focus: 'series'
            }
          },
          {
            name: '版本发布前',
            type: 'bar',
            data: data2,
            emphasis: {
              focus: 'series'
            }
          },
          {
            name: '版本发布后',
            type: 'bar',
            symbolSize: 6,
            data: data3,
            emphasis: {
              focus: 'series'
            }
          }
        ]
      };
      barChart.value.setOption(option1);
    };
    // 获取厂商chart数据
    const getFactoryChartData = async () => {
      isShowLeftNoData.value = false;
      leftLoading.value = true;
      if (pieChart.value) {
        pieChart.value.dispose();
      }
      let seriesData = [];
      let total = 0;
      // 获取整机chart数据
      if (factorySelect.value === 1) {
        const { code: code1, data: data1 } = await queryWholeModels();
        const { code: code2, data: data2 } = await queryWholeModelSupports();
        if (code1 === 200 && code2 === 200 && data1.length > 0) {
          data1.forEach(item => {
            let values = Object.values(data2[item.wholeFactory]);
            let value = 0;
            let infos = [];
            values.forEach(item => {
              if (item.length > 0) {
                item.forEach(cItem => {
                  infos.push(cItem);
                });
                value++;
              }
            });
            seriesData.push({
              name: item.wholeFactory,
              value: value,
              info: infos || []
            });
          });
          data1.forEach(item => {
            total = total + item.hardwareModel.split(',').length;
          });
          drawPie(seriesData, total);
        } else {
          leftLoading.value = false;
          isShowLeftNoData.value = true;
        }
      } else {
        const { code: code1, data: data1 } = await queryBoardModels();
        const { code: code2, data: data2 } = await queryBoardModelSupports();
        if (code1 === 200 && code2 === 200 && data1.length > 0) {
          data1.forEach(item => {
            let values = Object.values(data2[item.chipFactory]);
            let value = 0;
            let infos = [];
            values.forEach(item => {
              if (item.length > 0) {
                item.forEach(cItem => {
                  infos.push(cItem);
                });
                value++;
              }
            });
            seriesData.push({
              name: item.chipFactory,
              value: value,
              total: item.typicalBoardModel.split(',').length,
              info: infos || []
            });
          });
          data1.forEach(item => {
            total = total + item.typicalBoardModel.split(',').length;
          });
          drawPie(seriesData, total);
        } else {
          leftLoading.value = false;
          isShowLeftNoData.value = true;
        }
      }
      leftLoading.value = false;
    };
    const getVersionChartData = async () => {
      isShowRightNoData.value = false;
      let str = '';
      if (versionId.value.length > 0) {
        str = versionId.value.join(',');
      }
      rightLoading.value = true;
      if (barChart.value) {
        barChart.value.dispose();
      }
      const { code, data } =
        versionSelect.value === 1
          ? await queryVersionWholeDetail(str)
          : await queryVersionBoardDetail(str);
      if (code === 200 && data) {
        let names = [];
        versionList.value.forEach(item => {
          versionId.value.forEach(id => {
            if (item.versionId === id) {
              names.push(item.versionName);
            }
          });
        });
        let keys = Object.keys(data);
        names.forEach(item => {
          if (keys.indexOf(item) === -1) {
            data[item] = {
              beatListCount: 0,
              beatListSupportCount: 0,
              releaseListCount: 0,
              releaseListSupportCount: 0
            };
          }
        });
        let xData = Object.keys(data);
        let data1 = [];
        let data2 = [];
        let data3 = [];
        Object.values(data).forEach(item => {
          data1.push({
            value: (
              ((item.beatListSupportCount + item.releaseListSupportCount) /
                (item.beatListCount + item.releaseListCount)) *
              100
            ).toFixed(0),
            supportValue:
              item.beatListSupportCount + item.releaseListSupportCount,
            total: item.beatListCount + item.releaseListCount
          });
          data2.push({
            value: (
              (item.beatListSupportCount / item.beatListCount) *
              100
            ).toFixed(0),
            supportValue: item.beatListSupportCount,
            total: item.beatListCount
          });
          data3.push({
            value: (
              (item.releaseListSupportCount / item.releaseListCount) *
              100
            ).toFixed(0),
            supportValue: item.releaseListSupportCount,
            total: item.releaseListCount
          });
        });
        drawBar(xData, data1, data2, data3);
        rightLoading.value = false;
      } else {
        rightLoading.value = false;
        isShowRightNoData.value = true;
      }
    };
    onMounted(() => {
      // 获取操作系统列表
      getVersionAll().then(res => {
        if (res.code === 200) {
          versionList.value = res.data;
          if (versionList.value.length < 3) {
            versionList.value.forEach(item => {
              versionId.value.push(item.versionId);
            });
          } else {
            versionId.value.push(versionList.value[0].versionId);
            versionId.value.push(versionList.value[1].versionId);
            versionId.value.push(versionList.value[2].versionId);
          }
          // 获取操作系统chart数据
          getVersionChartData();
        }
      });
      // 获取顶部卡片区域数据
      queryCardList().then(res => {
        if (res.code === 200) {
          cardObj.value = res.data;
        }
      });
      // 获取厂商chart数据
      getFactoryChartData();

      window.addEventListener('resize', () => {
        if (pieChart.value) {
          pieChart.value.resize();
        }
        if (barChart.value) {
          barChart.value.resize();
        }
      });
    });
    onUnmounted(() => {
      window.removeEventListener('resize', () => {});
    });
    return {
      homePieRef,
      homeBarRef,
      factorySelect,
      versionSelect,
      pieChart,
      barChart,
      cardObj,
      getFactoryChartData,
      getVersionChartData,
      leftLoading,
      rightLoading,
      versionList,
      versionId,
      isShowLeftNoData,
      isShowRightNoData
    };
  }
});
</script>
<style lang="scss">
.home-container {
  overflow: auto;
  min-width: 700px;
  .tooltipBox {
    max-height: 200px;
    overflow-y: auto;
  }
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

        .font16 {
          font-size: 16px;
          color: red;
          flex: 1;
        }

        .info {
          margin-top: 10px;
        }
      }

      .myImg {
        line-height: 100%;

        img {
          height: 35px;
        }
      }
    }
  }

  .home-card-two,
  .home-card-three {
    height: 500px;
    width: 100%;
  }
  .leftChart,
  .rightChart {
    position: relative;
    .selectBox {
      width: 150px;
      position: absolute;
      top: 15px;
      right: 50px;
      z-index: 99;
      display: flex;
    }
    .second {
      width: 350px;
    }
  }

  #status {
    width: 100%;
  }

  #online {
    width: 100%;
  }

  #ciyun {
    height: 100%;
    width: 90%;
  }

  #bar {
    height: 400px;
    width: 90%;
  }

  .home-card-one,
  .home-card-two,
  .home-card-three {
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
</style>