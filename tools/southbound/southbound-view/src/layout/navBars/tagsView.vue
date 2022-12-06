<template>
  <div>
    <el-tabs type="card" :closable="true" @tab-click="onTagsClick" @edit="removeTab" v-model="currName">
      <el-tab-pane v-for="item in menuList" :key="item.key" :label="item.meta.title" :name="item.name" />
    </el-tabs>
  </div>
</template>

<script >
import { defineComponent, reactive, watch, ref } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useStore } from 'vuex';
export default defineComponent({
  name: 'tagsView',
  components: {},
  setup () {
    let router = useRouter();
    let currName = ref('/');
    const store = useStore();
    const menuStore = store.state.routesListModule.routesList;
    const menuList = reactive(menuStore);
    const onTagsClick = (v) => {
      router.push({ name: v.props.name });
    };

    const removeTab = (targetName) => {
      let index = menuList.findIndex(item => item.name === targetName);
      if (currName.value === targetName) {
        store.commit('routesListModule/REMOVE_PAGE', index);
        const menuListLen = menuList.length;
        if (menuListLen === 0) {
          router.push({ name: 'home' });
        } else {
          router.push({ name: menuList[menuList.length - 1].name });
        }
      } else {
        store.commit('routesListModule/REMOVE_PAGE', index);
      }
    };

    let currRoute = useRoute();
    watch(() => currRoute.name, (newVal) => {
      currName.value = newVal;
    }, { immediate: true });

    return {
      router,
      menuList,
      onTagsClick,
      removeTab,
      currName
    };
  }
});
</script>