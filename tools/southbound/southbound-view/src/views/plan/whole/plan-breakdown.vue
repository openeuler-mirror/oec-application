<template>
  <div>
    <el-tabs v-model="activeName">
      <el-tab-pane label="版本发布前" name="beta">
        <el-table :data="betaData" min-height="200" max-height="300" style="width:540px;" 
          :header-cell-style="{backgroundColor: '#f5f7fa', 'text-align':'center'}"
          :cell-style="{'text-align':'center'}"
          :border="true" :highlight-current-row="false">
            <el-table-column prop="model" label="整机机型" width="200" :resizable="false">
            </el-table-column>
            <el-table-column label="CPU厂商" width="120" :resizable="false">
              <template v-slot="scope">
                {{scope.row.cpuFactory || '/'}}
              </template>
            </el-table-column>
            <el-table-column prop="cpuModel" label="CPU代次" width="120" :resizable="false">
              <template v-slot="scope">
                {{scope.row.cpuModel || '/'}}
              </template>
            </el-table-column>
            <el-table-column prop="status" label="适配状态" width="100" :resizable="false">
              <template v-slot="scope">
                <el-button size="small" type="success" v-if="scope.row.status === 1">已适配</el-button>
                <el-button color="#7030a0" size="small" class="custom-btn" v-if="scope.row.status === 0">适配中
                </el-button>
              </template>
            </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="版本发布后" name="release">
        <el-table :data="releaseData" min-height="100" max-height="200" style="width:540px;" 
          :header-cell-style="{backgroundColor: '#f5f7fa', 'text-align':'center'}" 
          :cell-style="{'text-align':'center'}" :border="true"
          :highlight-current-row="false">
            <el-table-column prop="model" label="整机机型" width="200">
            </el-table-column>
            <el-table-column prop="cpuFactory" label="CPU厂商" width="120">
              <template v-slot="scope">
                {{scope.row.cpuFactory || '/'}}
              </template>
            </el-table-column>
            <el-table-column prop="cpuModel" label="CPU代次" width="120">
              <template v-slot="scope">
                {{scope.row.cpuModel || '/'}}
              </template>
            </el-table-column>
            <el-table-column prop="status" label="适配状态" width="100" :resizable="false">
              <template v-slot="scope">
                <el-button size="small" type="success" v-if="scope.row.status === 1">已适配</el-button>
                <el-button color="#7030a0" size="small" class="custom-btn" v-if="scope.row.status === 0">适配中
                </el-button>
              </template>
            </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { ref } from 'vue';
export default {
  name: 'planBreakdown',
  props: {
    betaData: {
      type: Array,
      default: () => []
    },
    releaseData: {
      type: Array,
      default: () => []
    }
  },
  setup() {
    let activeName = ref('beta');
    return {
      activeName
    };
  }
};
</script>