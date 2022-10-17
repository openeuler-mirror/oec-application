<template>
  <div class="chart-box" ref="chartRef"></div>
</template>

<script>
import { ref, onMounted, onUnmounted } from 'vue';
import * as echarts from 'echarts';
export default {
  name: 'chartDisplay',
  props: {
    beta: {
      type: Array,
      default: () => [0,0]
    },
    release: {
      type: Array,
      default: () => [0,0]
    }  
  },
  setup() {
    let myChart = null;
    let chartRef = ref('');
    const init = () => {
      myChart = echarts.init(chartRef.value);
      const option = {
        title: [
          {text:'版本发布前',show: true, left: '17%', bottom: '5%'},
          {text:'版本发布后',show: true, left: '63%', bottom: '5%'}
        ],
        grid: {
          left: '10%',
          top: '10%'
        },
        series: [
          {
            label: {
              show: true,
              position: 'inside',
              formatter(params) {
                return `${params.name}\n(${params.percent}%)`;
              }
            },
            name: '版本发布前',
            type: 'pie',
            radius: '70%',
            center: ['25%', '50%'],
            bottom: '30px',
            itemStyle: {
              borderRadius: 5
            },
            data: []
          },
          {
            label: {
              show: true,
              position: 'inside',
              formatter(params) {
                return `${params.name}\n(${params.percent}%)`;
              }
            },
            name: '版本发布后',
            type: 'pie',
            radius: '70%',
            bottom: '30px',
            center: ['70%', '50%'],
            itemStyle: {
              borderRadius: 5
            },
            data: [
            
            ]
          }
        ]
      };
      myChart.setOption(option);
    };

    const update = (beta, release) => {
      myChart.setOption({
        series: [
          {
            data: [
              { value: beta[0], name: '已适配' },
              { value: beta[1], name: '适配中' }
            ]
          },
          {
            data: [
              { value: release[0], name: '已适配' },
              { value: release[1], name: '适配中' }
            ]
          }
        ]
      });
    };
  
    onMounted(() => {
      init();
      window.addEventListener('resize', () => {
        if (myChart) {
          myChart.resize();
        }
      });
    }); 
    onUnmounted(() => {
      if (myChart) {
        myChart.dispose();
      }
      window.removeEventListener('resize', () => {});
    });
    return {
      chartRef,
      update
    };
  }
};
</script>

<style lang="scss" scoped>
.chart-box {
  height: 300px;
}
</style>