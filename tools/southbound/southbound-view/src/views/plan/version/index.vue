<template>
  <div class="version-plan">
    <div class="version-header">
      <el-button type="success" @click="handleAddOrEdit('add')" v-if="$hasAuth([0, 1])">新增</el-button>
    </div>
    <el-timeline>
      <el-timeline-item v-for="(item, index) of versionList" :key="index" :timestamp="item.year" placement="top">
        <el-row :gutter="20">
          <el-col :span="7" v-for="subItem in item.list" :key="subItem.versionId">
            <el-card>
              <div class="bor-blue"></div>
              <div class="version-info">
                <div class="info-title">发布版本：</div>
                <div class="info-summary">
                  <h4 class="noWarp">{{subItem.versionName}}</h4>
                </div>
              </div>
              <div class="version-info">
                <div class="info-title">技术验证：</div>
                <div class="info-summary">
                  <p style="font-size:14px;" class="noWarp">{{subItem.alphaStartDate}} ~ {{subItem.alphaEndDate}}</p>
                </div>
              </div>
              <div class="version-info">
                <div class="info-title">版本发布前：</div>
                <div class="info-summary">
                  <p style="font-size:14px;" class="noWarp">{{subItem.betaStartDate}} ~ {{subItem.betaEndDate}}</p>
                </div>
              </div>
              <div class="version-info">
                <div class="info-title">版本发布后：</div>
                <div class="info-summary">
                  <p style="font-size:14px;" class="noWarp">{{subItem.releaseStartDate}} ~ {{subItem.releaseEndDate}}</p>
                </div>
              </div>
              <div class="version-info version-detail" @click="toDetail(subItem)">
                查看详情 》
              </div>
              <div class="divider" v-if="$hasAuth([0, 1])"></div>
              <div class="btn-grounp" v-if="$hasAuth([0, 1])">
                <el-button type="primary" size="small" @click="handleAddOrEdit('edit', subItem)">编辑</el-button>
                <el-button type="danger" size="small" @click="handleDel(subItem.versionId)">删除</el-button>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </el-timeline-item>
    </el-timeline>
    <version-add-or-update ref="addOrUpdateRef" v-if="showAddOrUpdate" @close="showAddOrUpdate = false" @refreshList="queryList"></version-add-or-update>
  </div>
</template>
<script>
import { ref, markRaw, onMounted, nextTick } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessageBox, ElMessage } from 'element-plus';
import VersionAddOrUpdate from './add-or-update.vue';
import { WarningFilled } from '@element-plus/icons-vue';
import { getAllList, doDelete } from '@/common/api/versionPlan';
export default ({
  name: 'planVersion',
  components: { VersionAddOrUpdate },
  setup () {
    const router = useRouter();
    const versionList = ref({});
    const queryList = async () => {
      let res = await getAllList();
      if (res.code === 200) {
        let arr = [], year = 0;
        res.data.forEach(item => {
          year = item.alphaStartDate.slice(0, 4);
          let index = -1;
          for (let i = 0; i < arr.length; i++) {
            if (arr[i].year === year) {
              index = i;
              break;
            }
          }
          if (index !== -1) {
            arr[index].list.push(item);
          } else {
            arr.push({ year, list: [item] });
          }

        });
        versionList.value = arr;
      }
    };
    onMounted(() => {
      queryList();
    });

    const handleAddOrEdit = (type, rowdata) => {
      showAddOrUpdate.value = true;
      let data = null;
      if (type !== 'add') {
        data = {
          versionId: rowdata.versionId,
          alphaRangeDate: [rowdata.alphaStartDate, rowdata.alphaEndDate],
          betaRangeDate: [rowdata.betaStartDate, rowdata.betaEndDate],
          releaseRangeDate: [rowdata.releaseStartDate, rowdata.releaseEndDate],
          versionName: rowdata.versionName,
          alphaDetail: rowdata.alphaDetail,
          remark: rowdata.remark
        };
      }
      nextTick(() => {
        addOrUpdateRef.value.open(type, data);
      });
    };
    const handleDel = (versionId) => {
      ElMessageBox.confirm('此操作将永久删除当前数据，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info',
        icon: markRaw(WarningFilled)
      })
        .then(async () => {
          let res = await doDelete(versionId);
          if (res.code === 200) {
            ElMessage.success('删除成功');
            queryList();
          }
        })
        .catch(() => { });
    };
    const toDetail = (item) => {
      router.push({ name: 'planVersionDetail', query: { versionName: item.versionName } });
    };
    let showAddOrUpdate = ref(false);
    const addOrUpdateRef = ref();
    return {
      versionList,
      showAddOrUpdate,
      addOrUpdateRef,
      handleAddOrEdit,
      handleDel,
      queryList,
      toDetail
    };
  }
});
</script>
<style lang="scss" scoped>
  .version-plan {
    padding-left: 5px;
    :deep(.el-card .el-card__body) {
      position: relative;
      padding-bottom: 10px;
    }
    :deep(.el-button + .el-button) {
      margin-left: 12px;
    }
  }
  .bor-blue {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 20px;
    border-top: 2px solid blue;
    box-sizing: border-box;
  }
  .version-show {
    display: flex;
    align-items: center;
    .el-icon :hover {
      color: blue;
      cursor: pointer;
    }
  }
  h4 {
    font-size: 100%;
    margin: 0;
    font-weight: normal;
    vertical-align: baseline;
  }

  h4 {
    font-size: 16px;
    line-height: 30px;
    display: inline;
  }
  p {
    margin: 10px 0;
  }
  .version-edit-content {
    display: flex;
    flex-direction: column;
    margin: 3px 0px;
    align-items: center;
  }
  .version-header {
    margin-bottom: 10px;
  }

  .version-info {
    display: flex;
    align-items: center;
    .info-title {
      width: 120px;
      flex-shrink: 0;
      text-align: right;
      color: #666;
      font-size: 14px;
    }
    .info-summary {
      display: flex;
      align-items: center;
      width: calc(100% - 120px);
    }

    &.version-detail {
      color: blue;
      font-size: 10px;
      justify-content: flex-end;
      letter-spacing: 1px;
      margin-bottom: 10px;
      cursor: pointer;
    }
  }
  .version-plan {
    height: 100%;
    overflow-x: hidden;
    overflow-y: auto;
    .divider {
      width: 100%;
      height: 1px;
      margin: 10px 0;
      background-color: #f5f5f5;
    }
    .btn-grounp {
      display: flex;
      justify-content: flex-end;
    }
    :deep(.el-col) {
      margin-top: 10px;
    }
  }
</style>