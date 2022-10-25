<template>
  <div class="flex-shrink-0 aside-wrapper" :style="{width: width + 'px'}">
    <el-aside :width="width + 'px'">
      <Vertical :menuShow="menuList"></Vertical>
    </el-aside>
  </div>
</template>
<script>
import { defineComponent, reactive, ref, computed } from 'vue';
import { useStore } from 'vuex';
import Vertical from './vertical.vue';

export default defineComponent({
  name: 'layoutAside',
  components: { Vertical },
  setup () {
    let store = useStore();
    const menuStore = store.state.routesListModule.dynamicRoutes;
    const menuList = reactive(menuStore);

    let isExpand = ref(true);
    const toggleBtn = () => {
      isExpand.value = !isExpand.value;
    };
    let width = computed(() => {
      return isExpand.value ? 200 : 100;
    });

    return {
      menuList,
      width,
      isExpand,
      toggleBtn
    };
  }
});
</script>

<style lang="scss">
  .aside-wrapper {
    width: 200px;
    position: relative;
    .toggle-btn {
      position: absolute;
      top: 16px;
      right: 10px;
    }
  }
  .el-aside {
    background-color: #fff;
    height: calc(100vh - 60px);
    position: fixed;
    box-shadow: 1px 0px 4px 0px rgba($color: #000, $alpha: 0.2);
  }
  .flex-shrink-0 {
    flex-shrink: 0;
  }
</style>