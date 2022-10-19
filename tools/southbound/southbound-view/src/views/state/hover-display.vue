<template>
  <div>
    <el-popover v-if="list.length > 0" :placement="placement" :width="270" trigger="hover"> 
      <template #default>
        <ul>
          <div class="ul-header" v-if="type === 'whole'">
            整机厂商 - CPU厂商 - CPU型号 - 整机机型
          </div>
          <div class="ul-header" v-if="type === 'board'">
            芯片厂商 - 板卡类型 - 芯片型号 - 板卡型号
          </div>
          <li v-for="(item, index) in list" :key="index">{{item}}</li>
        </ul>
      </template>
      <template #reference>
        <span class="text-blue">{{list.length}}</span>
      </template>
    </el-popover>
    <span v-else>{{list.length}}</span>
  </div>
</template>

<script>
export default {
  name: 'hoverDisplay',
  props: {
    type: {
      type: String,
      default: 'whole'
    },
    placement: {
      type: String,
      default: 'right'
    },
    list: {
      type: Array,
      default: () => []
    }
  }
};
</script>

<style lang="scss" scoped>
ul {
  max-height: 240px;
  overflow-y: auto;
  position: relative;
  padding-top: 0px;
}
.ul-header {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 30px;
}
li {
  margin: 5px 0;
}
ul li:nth-of-type(1) {
  margin-top: 25px;
}
</style>