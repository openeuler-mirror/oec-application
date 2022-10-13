<template>
  <el-dialog v-model="dialogVisible" title="导入" :width="700" draggable>
    <el-form v-if="dialogVisible" :model="form" label-width="110px">
      <el-form-item label="上传文件">
        <el-upload
          accept=".xls, .xlsx"
          ref="upload"
          style="width: 90%"
          class="upload-demo"
          drag
          :headers="config"
          :file-list="fileList"
          :action="action"
          :limit="1"
          :on-exceed="handleExceed"
          :before-upload="beforeUpload"
          :on-success="upSuccess"
          :on-error="upError"
        >
          <UploadFilled style="width: 30px; height: 30px;" />
          <div class="el-upload__text">
            拖拽批量导入信息文件至此处，或
            <em>点击上传</em>
            <div>
              请按照
              <em @click="downloadTemp">模板表格</em> 填写导入信息，系统支持xls和xlsx两种格式文件
            </div>
          </div>
          <template #tip>
            <div class="el-upload__tip"></div>
          </template>
        </el-upload>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
import { ref, onMounted } from "vue";
import { ElMessage } from "element-plus";

export default {
  name: "wholeAddOrUpdate",
  // 传入参数集合
  props: {
    type: {
      type: Number,
      required: true
    }
  },
  // 自定义事件
  emits: ["refreshList"],
  setup(props, context) {
    let config = {
      token: sessionStorage.getItem("token")
    };
    // 表单对象
    let form = ref({});
    // 上传地址
    let action = ref("");
    // 控制表单的显示与隐藏
    let dialogVisible = ref(false);
    let upload = ref("");
    let fileList = ref([]);
    // 下载模板
    const downloadTemp = async e => {
      let type = "";
      if (props.type === 1) {
        type = "cpu";
      } else if (props.type === 2) {
        type = "whole";
      } else if (props.type === 3) {
        type = "chip";
      } else if (props.type === 4) {
        type = "driver";
      } else if (props.type === 5) {
        type = "board";
      }
      e.stopPropagation();
      window.location.href = `${process.env.VUE_APP_BASEURL}/template/downloadExcel?templateType=${type}`;
    };
    const handleExceed = () => {
      ElMessage.error("超出最大文件数量限制");
    };
    const beforeUpload = file => {
      const fileSuffix = file.name.substring(file.name.lastIndexOf(".") + 1);
      const whiteList = ["xls", "xlsx"];
      if (whiteList.indexOf(fileSuffix) === -1) {
        ElMessage({
          message: "上传文件只能是 xls,xlsx格式",
          type: "warning"
        });
        return false;
      }
    };
    // 上传成功
    const upSuccess = res => {
      if (res.code === "200") {
        ElMessage({ message: "上传成功", type: "success" });
        context.emit("refreshList");
        dialogVisible.value = false;
      } else {
        ElMessage({ message: res.msg, type: "warning" });
      }
      fileList.value = [];
    };
    // 上传失败
    const upError = () => {
      ElMessage.error("上传失败");
    };

    onMounted(() => {
      let keyword = "";
      if (props.type === 1) {
        keyword = "cpu-factory";
      } else if (props.type === 2) {
        keyword = "whole-factory";
      } else if (props.type === 3) {
        keyword = "chip-factory";
      } else if (props.type === 4) {
        keyword = "driver-manage";
      } else if (props.type === 5) {
        keyword = "board-factory";
      }
      action.value = `${process.env.VUE_APP_BASEURL}/${keyword}/excel/upload`;
    });
    const getType = val => {
      let keyword = "";
      if (val === 1) {
        keyword = "chip-factory";
      } else {
        keyword = "driver-manage";
      }
      action.value = `${process.env.VUE_APP_BASEURL}/${keyword}/excel/upload`;
    };
    return {
      dialogVisible,
      form,
      downloadTemp,
      handleExceed,
      upload,
      beforeUpload,
      upSuccess,
      upError,
      action,
      fileList,
      config,
      getType
    };
  }
};
</script>

<style lang="scss" scoped>
.tagItem {
  margin-right: 5px;
}
</style>