<template>
  <div
    ref="templateRoot"
    style="
      background-color: transparent;
      width: 100%;
      height: 100%;
      display: flex;
      flex-direction: row;
      justify-content: center;
    "
  >
    <div
      v-if="calcDone"
      ref="columnCenterBox"
      style="background-color: transparent; display: flex; flex-direction: column; justify-content: center"
    >
      <div ref="rowCenterBox" style="background-color: transparent">
        <div ref="centerBox" style="background-color: transparent; flex-grow: 1">
          <slot name="contentBox">
            <div></div>
          </slot>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="tsx">
import { defineComponent, PropType, ref } from "vue";

export default defineComponent({
  name: "CenterBox",
  props: {
    contentBoxWidth: {
      type: String as PropType<string>,
      // default: "content"
      default: "0%"
    },
    contentBoxHeight: {
      type: String as PropType<string>,
      default: "0%"
    }
  },
  setup() {
    const templateRoot = ref<HTMLDivElement>();
    const columnCenterBox = ref<HTMLDivElement>();
    const rowCenterBox = ref<HTMLDivElement>();
    const centerBox = ref<HTMLDivElement>();
    return {
      templateRoot,
      columnCenterBox,
      rowCenterBox,
      centerBox
    };
  },
  data() {
    return {
      calcDone: true
    };
  },
  watch: {
    contentBoxWidth(inputValue, oldValue) {
      this.initColumnCenterBox();
    },
    contentBoxHeight(inputValue, oldValue) {
      this.initRowCenterBox();
    }
  },
  mounted() {
    const myself = this;
    // const templateRoot = myself.templateRoot as HTMLDivElement;
    // const columnCenterBox = myself.columnCenterBox as HTMLDivElement;
    // const rowCenterBox = myself.rowCenterBox as HTMLDivElement;
    // const centerBox = myself.centerBox as HTMLDivElement;

    // const rootWidth = templateRoot.offsetWidth;
    // const rootHeight = templateRoot.offsetHeight;

    this.initColumnCenterBox();
    this.initRowCenterBox();
  },
  methods: {
    initRowCenterBox() {
      const myself = this;
      const rowCenterBox = myself.rowCenterBox as HTMLDivElement;
      const contentBoxHeight = myself.contentBoxHeight;
      if (contentBoxHeight!.endsWith("%")) {
        const percent = contentBoxHeight!.substring(0, contentBoxHeight!.length - 1);
        rowCenterBox.style.flexGrow = "" + Number.parseFloat(percent) / 100;
      } else {
        rowCenterBox.style.maxHeight = contentBoxHeight;
      }
    },
    initColumnCenterBox() {
      const myself = this;
      const columnCenterBox = myself.columnCenterBox as HTMLDivElement;
      const contentBoxWidth = myself.contentBoxWidth;
      if (contentBoxWidth!.endsWith("%")) {
        const percent = contentBoxWidth!.substring(0, contentBoxWidth!.length - 1);
        columnCenterBox.style.flexGrow = "" + Number.parseFloat(percent) / 100;
      } else {
        columnCenterBox.style.maxWidth = contentBoxWidth;
      }
    }
  }
});
</script>

<style scoped></style>
