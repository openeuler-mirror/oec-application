<template>
  <div>
    <div style="border: 1px solid #ccc;">
      <Toolbar style="border-bottom: 1px solid #ccc" :editor="editorRef" :defaultConfig="toolbarConfig" :mode="mode" />
      <Editor class="editor" :style="{'height': height + 'px'}" v-model="valueHtml" :defaultConfig="editorConfig" :mode="mode" @onCreated="handleCreated" @onChange="handleChange" />
    </div>
  </div>
</template>

<script>
import { ref, shallowRef, onBeforeUnmount, watch } from 'vue';
import { Editor, Toolbar } from '@wangeditor/editor-for-vue';
export default {
  name: 'WangEditor',
  props: {
    modelValue: {
      type: String,
      default: ''
    },
    placeholder: {
      type: String,
      default: '请输入内容...'
    },
    height: {
      type: Number,
      default: 300
    }
  },
  emits: ['update:modelValue'],
  components: { Editor, Toolbar },
  setup (props, ctx) {
    const editorRef = shallowRef();
    const valueHtml = ref('');
    watch(() => props.modelValue, (newVal, oldVal) => {
      if (newVal !== oldVal) {
        valueHtml.value = newVal;
      }
    }, { immediate: true });
    const toolbarConfig = {
      excludeKeys: [
        'blockquote',
        'header1',
        'group-more-style',
        'group-image',
        'group-video',
        'fullScreen',
        'codeBlock',
        'todo'
      ],
      MENU_CONF: {
        'headerSelect': ['header2']
      }
    };
    const editorConfig = {
      placeholder: props.placeholder,
      MENU_CONF: {
        fontSize: {
          fontSizeList: ['12px', '14px', '16px', '18px', '20px', '24px', '32px']
        },
        lineHeight: {}
      }
    };

    // 组件销毁时，也及时销毁编辑器
    onBeforeUnmount(() => {
      const editor = editorRef.value;
      if (!editor) return;
      editor.destroy();
    });

    const handleCreated = (editor) => {
      editorRef.value = editor;
    };

    const handleChange = (e) => {
      ctx.emit('update:modelValue', e.getHtml());
    };

    return {
      editorRef,
      valueHtml,
      mode: 'default',
      toolbarConfig,
      editorConfig,
      handleCreated,
      handleChange
    };
  }
};
</script>

<style lang="scss" scoped>
  :deep(h1) {
    font-size: 26px !important;
  }
  :deep(a) {
    text-decoration: underline;
    color: -webkit-link;
  }
</style>