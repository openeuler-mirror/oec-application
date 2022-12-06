<template>
  <div class="wrapper">
    <el-container class="layout-container">
      <el-header>
        <NavHeader></NavHeader>
      </el-header>
      <el-container>
        <elAside />
        <el-container class="main-wrapper">
          <TagsView />
          <el-main class="layout-main">
            <elMain />
          </el-main>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>
<script>
import { defineComponent } from 'vue';
import elAside from '@/layout/navItem/aside.vue';
import elMain from '@/layout/main.vue';
import TagsView from '@/layout/navBars/tagsView.vue';
import NavHeader from '@/layout/navBars/header.vue';

export default defineComponent({
  name: 'layoutClassic',
  components: { elAside, elMain, TagsView, NavHeader },
  setup () {
    return {};
  }
});
</script>

<style lang="scss" scoped>
  .layout-container {
    width: 100%;
    height: 100%;
    background-color: #f8f8f8;
  }
  .main-wrapper {
    display: flex;
    flex-direction: column;
    height: calc(100vh - 60px);
    padding: 10px;
    box-sizing: border-box;
  }
  .layout-main {
    height: calc(100% - 56px);
  }
</style>
