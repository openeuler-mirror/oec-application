<template>
  <div class="card-warpper">
    <el-card>
      <el-tabs tab-position="top" v-model="tabValue">
        <el-tab-pane label="整机厂商" name="1"></el-tab-pane>
        <el-tab-pane label="CPU厂商" name="2"></el-tab-pane>
        <el-tab-pane label="CPU型号" name="3"></el-tab-pane>
        <el-tab-pane label="架构" name="4"></el-tab-pane>
        <el-tab-pane label="系统版本" name="5"></el-tab-pane>
      </el-tabs>
      <div class="filter-area d-flex">
        <el-form-item label="操作系统" :show-message="false" v-if="tabValue !== '5'">
          <el-select v-model="queryForm.versionName" placeholder="请选择操作系统">
            <el-option v-for="item in versionList" :key="item" :label="item.versionName" :value="item.versionName" />
          </el-select>
        </el-form-item>
      </div>
      <div class="content-main" :class="{'version': tabValue === '5'}">
        <keep-alive>
          <hareware-factory v-if="tabValue === '1'" :versionName="queryForm.versionName"></hareware-factory>
          <cpu-factory v-else-if="tabValue === '2'" :versionName="queryForm.versionName"></cpu-factory>
          <cpu-model v-else-if="tabValue === '3'" :versionName="queryForm.versionName"></cpu-model>
          <architecture v-else-if="tabValue === '4'" :versionName="queryForm.versionName"></architecture>
          <os-version v-else-if="tabValue === '5'" :versionName="queryForm.versionName"></os-version>
        </keep-alive>
      </div>
    </el-card>
  </div>
</template>
<script>
import { reactive, ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import HarewareFactory from './components/hardware-factory.vue';
import CpuFactory from './components/cpu-factory.vue';
import CpuModel from './components/cpu-model.vue';
import Architecture from './components/architecture.vue';
import OsVersion from './components/version.vue';

import { queryVersionNames } from '@/common/api/versionPlan';
export default {
  name: 'stateWhole',
  components: { HarewareFactory, CpuFactory, CpuModel, Architecture, OsVersion },
  setup () {
    const route = useRoute();
    const queryForm = reactive({
      versionName: '',
      all: '全部'
    });
    const versionList = ref([]);
    const queryOptions = async () => {
      let res = await queryVersionNames();
      if (res.code === 200) {
        versionList.value = res.data;
        queryForm.versionName = route.query.versionName || res.data[0].versionName;
      }
    };
    onMounted(() => {
      queryOptions();
    });
    let tabValue = ref('1');
    return {
      tabValue,
      queryForm,
      versionList
    };
  },
  beforeRouteEnter (to) {
    if (!to.query.versionName) {
      let query = JSON.parse(sessionStorage.getItem('queryState'));
      if (query) {
        to.query.versionName = query.versionName;
        to.fullPath += `?versionName=${query.versionName}`;
        to.href += `?versionName=${query.versionName}`;
      }
    }
  },
  beforeRouteLeave (to, from) {
    if (from.query.versionName) {
      sessionStorage.setItem('queryState', JSON.stringify({ versionName: from.query.versionName }));
    }
  }
};
</script>

<style lang="scss" scoped>
  .content-main {
    height: calc(100% - 104px);
    &.version {
      height: calc(100% - 54px);
    }
  }
  :deep(.el-tabs__header) {
    position: relative;
  }
</style>