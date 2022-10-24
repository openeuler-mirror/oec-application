<template>
  <div class="menu-container">
    <el-menu router active-text-color="rgb(97, 112, 204)" background-color="#fff" :default-active="defaultActive" text-color="#777">
      <div v-for="val in filterMenu.values" :key="val.path">
        <el-sub-menu v-if="val.children" :index="val.path">
          <template #title>
            <span>{{ val.meta.title }}</span>
          </template>
          <template v-for="valchild in val.children">
            <el-menu-item :index="valchild.path" :key="valchild.path" v-if="!valchild.meta.isHide">
              <template #title>
                <span>{{ valchild.meta.title }}</span>
              </template>
            </el-menu-item>
          </template>
        </el-sub-menu>
        <!-- 一级 -->
        <el-menu-item v-else :index="val.path">
          <template #title>
            <span class="index">{{ val.meta.title }}</span>
          </template>
        </el-menu-item>
      </div>
    </el-menu>
  </div>
</template>

<script>
import { defineComponent, onMounted, reactive, watch, ref } from 'vue';
import { useRoute } from 'vue-router';

export default defineComponent({
  name: 'myVertical',
  props: ['menuShow'],
  setup (props) {
    let defaultActive = ref('/home');
    const route = useRoute();
    watch(() => route.path, (newVal) => {
      defaultActive.value = newVal;
    }, { immediate: true });
    const propsData = reactive(props);
    const menu = propsData.menuShow;
    const filterMenu = reactive([]);

    /**
     * @params listObject为对象，
     * 过滤isHide = true的元素
     */
    const initShowRouter = (listObject) => {
      filterMenu.values = listObject.filter((item) => {
        return item.meta.isHide === false;
      });
    };
    onMounted(() => {
      initShowRouter(menu);
    });
    // 将路径通过路由转发出去
    return {
      propsData,
      menu,
      filterMenu,
      defaultActive
    };
  }
});
</script>

<style lang="scss">
  .menu-container {
    width: 200px;
  }
  .el-menu-item:hover {
    background-color: rgb(233, 237, 250);
  }
  .el-menu-item {
    padding-left: 35px !important;
  }
  .index {
    margin-left: -14px !important;
  }
</style>