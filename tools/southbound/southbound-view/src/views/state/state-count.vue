<template>
  <div class="header-summary">
    <div class="item">
      <p>已适配</p>
      <p>{{adaptCount}}</p>
    </div>
    <div class="item">
      <p>未适配</p>
      <p>{{notAdaptCount}}</p>
    </div>
    <div class="item mr-10">
      <p>适配中</p>
      <p>{{planAdaptCount}}</p>
    </div>
    <div class="item">
      <p>版本不支持</p>
      <p>{{notSupportCount}}</p>
    </div>
</div>
</template>

<script>
export default {
  props: {
    adaptCount: {
      type: Number,
      default: 0
    },
    notAdaptCount: {
      type: Number,
      default: 0
    },
    planAdaptCount: {
      type: Number,
      default: 0
    },
    notSupportCount: {
      type: Number,
      default: 0
    }
  }
};
</script>

<style lang="scss" scoped>
.header-summary {
  display: flex;
  margin-bottom: 10px;
  .item {
    width: 200px;
    padding: 8px 16px;
    border-left: 5px solid #409eff;
    border-radius: 4px;
    p {
      font-size: 14px;
      color: #999;
      line-height: 1.5em;
    }
    p:nth-child(2) {
      font-size: 20px;
      color: #000;
    }
  }
  .item:nth-child(1) {
    border-color: rgb(103, 194, 58);
    background-color: rgba(103, 194, 58, 0.1);
  }
  .item:nth-child(2) {
    border-color: rgb(144, 147, 153);
    background-color: rgba(144, 147, 153, 0.1);
    margin: 0 10px;
  }
  .item:nth-child(3) {
    border-color: rgb(112, 48, 160);
    background-color: rgba(112, 48, 160, 0.1);
  }
  .item:nth-child(4) {
    border-color: rgb(245, 108, 108);
    background-color: rgba(245, 108, 108, 0.1);
  }
}
</style>