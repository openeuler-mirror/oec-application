import { initData } from '@/common/api/common';
import { ref } from 'vue';
export default function () {
  // 列表总数
  const total = ref(0);
  // 列表
  const data = ref([]);
  // 加载状态
  let loading = ref(false);
  // 初始化列表
  function init(url, params) {
    loading.value = true;
    return new Promise((resolve, reject) => {
      initData(url, params).then(res => {
        loading.value = false;
        if (res.code === 200) {
          total.value = res.data.total;
          data.value = res.data.list;
        }
        resolve(res);
      }).catch(err => {
        loading.value = false;
        reject(err);
      });
    });
  }
  function set(a) {
    for (let i = 0; i < a.length; i++) {
      for (let j = i + 1; j < a.length; j++) {
        if (a[i].text === a[j].text) {
          a.splice(j, 1);
          j--;
        }
      }
    }
    return a;
  }
  return {
    init,
    total,
    data,
    loading,
    set
  };
}