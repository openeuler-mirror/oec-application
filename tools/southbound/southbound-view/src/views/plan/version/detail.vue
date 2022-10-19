<template>
  <div class="card-warpper">
    <el-card>
      <div class="filter-area d-flex">
        <el-form-item label="操作系统" :show-message="false">
          <el-select v-model="versionName" placeholder="请选择操作系统" clearable>
            <el-option v-for="item in versionList" :key="item" :label="item.versionName" :value="item.versionName" />
          </el-select>
        </el-form-item>
      </div>
      <div style="margin-bottom:20px;">
        <el-table :border="true" :data="rowData" :show-header="false" style="width:45%" :highlight-current-row="false" row-class-name="row">
          <el-table-column prop="phase" width="120">
            <template #default="scope">
              <div>{{scope.row.phase}}</div>
            </template>
          </el-table-column>
          <el-table-column prop="alphaHtml" align="center">
            <template #default="scope">
              <div v-if="alphaStagesText" v-html="scope.row.alphaHtml" class="rich-wrapper rich-box"></div>
              <div v-else>/</div>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="expand-area">
        <el-row :gutter="10">
          <el-col :span="11">
            <el-card>
              <template #header>
                <div class="card-header">
                  <span>整 机</span>
                  <span class="detail pointer" @click="navToState('whole')">查看状态 》</span>
                </div>
              </template>
              <detail-whole :versionName="versionName" @alphaChange="alphaChange" :getSummaries="getSummaries"></detail-whole>
            </el-card>
          </el-col>
          <el-col :span="11">
            <el-card>
              <template #header>
                <div class="card-header">
                  <span>板 卡</span>
                  <span class="detail pointer" @click="navToState('board')">查看状态 》</span>
                </div>
              </template>
              <detail-borad :versionName="versionName" @alphaChange="alphaChange" :getSummaries="getSummaries"></detail-borad>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </el-card>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import DetailWhole from './detail-whole.vue';
import DetailBorad from './detail-board.vue';
import { queryVersionNames } from '@/common/api/versionPlan';
export default {
  name: 'versionPlanDetail',
  components: { DetailWhole, DetailBorad },
  setup () {
    const router = useRouter();
    const route = useRoute();
    let versionName = ref('');
    versionName.value = route.query.versionName;
    const tabName = ref('whole');

    const versionList = ref([]);
    const queryOptions = async () => {
      let res = await queryVersionNames();
      if (res.code === 200) {
        versionList.value = res.data;
      }
    };
    onMounted(() => {
      queryOptions();
    });
    let alphaStagesText = ref('');
    let rowData = ref([]);
    const alphaChange = (e, text) => {
      alphaStagesText.value = text;
      rowData.value = [{ phase: '技术验证', alphaHtml: e }];
    };
    const getSummaries = (param) => {
      const { columns, data } = param;
      const sums = [];
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = '总计';
        }
        let sum = 0;
        if (index === 1) {
          sum = 0;
          sum = data[0].total || 0;
          sum += data[1].total || 0;
          sums[index] = sum;
        }
        if (index === 2) {
          sum = 0;
          sum = Number(data[0].process) || 0;
          sum += Number(data[1].process) || 0;
          sums[index] = (sum / 2).toFixed(2) + '%';
        }
        if (index === 3) {
          sums[index] = '';
        }
      });
      return sums;
    };
    // 跳转到状态
    const navToState = (type) => {
      if (type === 'whole') {
        router.push({ name: 'stateWhole', query: { versionName: versionName.value } });
      } else {
        router.push({ name: 'stateBoard', query: { versionName: versionName.value } });
      }
    };
    return {
      tabName,
      versionName,
      versionList,
      alphaChange,
      rowData,
      alphaStagesText,
      getSummaries,
      navToState
    };
  },
  beforeRouteEnter (to) {
    if (!to.query.versionName) {
      let query = JSON.parse(sessionStorage.getItem('query'));
      if (query) {
        to.query.versionName = query.versionName;
        to.fullPath += `?versionName=${query.versionName}`;
        to.href += `?versionName=${query.versionName}`;
      }
    }
  },
  beforeRouteLeave (to, from) {
    if (from.query.versionName) {
      sessionStorage.setItem('query', JSON.stringify({ versionName: from.query.versionName }));
    }
  }
};
</script>
<style lang="scss" scoped>
  .rich-box {
    max-width: 500px;
    max-height: 200px;
    overflow-y: auto;
    text-align: left;
  }
  :deep(.el-table tbody tr:hover td) {
    background-color: #fff !important;
  }
  :deep(.el-card__body) {
    overflow: auto;
  }
  .card-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    span {
      font-size: 20px;
    }
    .detail {
      color: blue;
      font-size: 10px;
    }
  }
  .expand-area {
    margin-top: 30px;
  }
</style>