<template>
  <div class="card-warpper">
    <el-card>
      <el-tabs tab-position="top" v-model="tabValue">
        <el-tab-pane label="芯片厂商" name="1"></el-tab-pane>
        <el-tab-pane label="芯片型号" name="2"></el-tab-pane>
        <el-tab-pane label="板卡类型" name="3"></el-tab-pane>
        <el-tab-pane label="架构" name="4"></el-tab-pane>
        <el-tab-pane label="系统版本" name="5"></el-tab-pane>
      </el-tabs>
      <div class="filter-area d-flex">
        <el-form-item label="操作系统" :show-message="false" v-if="tabValue !== '5'">
          <el-select v-model="queryForm.versionName" placeholder="请选择操作系统">
            <el-option v-for="item in versionList" :key="item" :label="item.versionName" :value="item.versionName"/>
          </el-select>
        </el-form-item>
      </div>
      <div class="content-main" :class="{'version': tabValue === '5'}">
        <keep-alive>
          <chip-factory v-if="tabValue === '1'" :versionName="queryForm.versionName"></chip-factory>
          <chip-model v-else-if="tabValue === '2'" :versionName="queryForm.versionName"></chip-model>
          <board-type v-else-if="tabValue === '3'" :versionName="queryForm.versionName"></board-type>
          <Architecture v-else-if="tabValue === '4'" :versionName="queryForm.versionName"></Architecture>
          <Version v-else-if="tabValue === '5'" :versionName="queryForm.versionName"></Version>
        </keep-alive>
      </div>
    </el-card>
  </div>
</template>
<script>
import { reactive, ref, onMounted} from 'vue';
import { useRoute } from 'vue-router';
import ChipFactory from './components/chip-factory.vue';
import ChipModel from './components/chip-model.vue';
import BoardType from './components/board-type.vue';
import Version from './components/version.vue';
import Architecture from './components/architecture.vue';
import { queryVersionNames } from '@/common/api/versionPlan';

export default {
  name: 'stateBoard',
  components: { ChipFactory, ChipModel, BoardType, Version, Architecture },
  setup() {
    const route = useRoute(); 
    const queryForm = reactive({
      versionName: ''
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
  beforeRouteEnter(to) {
    if (!to.query.versionName) {
      let query = JSON.parse(sessionStorage.getItem('queryBoardState'));
      if (query) {
        to.query.versionName = query.versionName;
        to.fullPath += `?versionName=${query.versionName}`;
        to.href += `?versionName=${query.versionName}`;
      }
    } 
  },
  beforeRouteLeave(to, from) {
    if(from.query.versionName) {
      sessionStorage.setItem('queryBoardState', JSON.stringify({versionName: from.query.versionName}));
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
</style>