<template>
  <div class="not_found">
    <h1>404</h1>
    <p>您访问的页面丢失了</p>
    <el-button type="primary" @click="back">
      <template #icon>
        <Back></Back>
      </template>
      返回主页
    </el-button>
  </div>
</template>

<script>
import { useRouter } from 'vue-router';
export default {
  name: 'notFound',
  setup () {
    const router = useRouter();
    const back = () => {
      router.replace({ name: 'home' });
    };
    return {
      back
    };
  }
};
</script>
<style scoped lang='scss'>
  .not_found {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
  }
  h1 {
    margin: 0;
    font-size: 160px;
    line-height: 1.2em;
    letter-spacing: 10px;
  }
  p {
    font-size: 16px;
    margin-bottom: 20px;
    letter-spacing: 1px;
  }
</style>
