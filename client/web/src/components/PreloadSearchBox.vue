<template>
  <div
    ref="templateRoot"
    style="
      position: relative;
      width: 100%;

      display: flex;
      flex-direction: column;
      justify-content: space-between;
    "
  >
    <div
      v-if="loading"
      ref="measureFontSizeBox"
      :style="{
        fontSize: inputAreaOptions.fontSize
      }"
    >
      {{ $t("word.loading") }}
    </div>

    <div
      v-if="!loading"
      ref="searchBox"
      style="
        box-sizing: border-box;
        padding: 5px;

        border: solid 1px deepskyblue;
        flex-grow: 1;
        display: flex;
        flex-direction: row;
        /*font-size: large;*/

        overflow: hidden;
        cursor: text;
      "
      :style="{ borderRadius: borderRadius, miniHeight: searchBoxMiniHeight }"
      @click="inputAreaGetFocus"
    >
      <input
        ref="inputArea"
        v-model="searchValue"
        style="
          border: none;
          resize: none;
          outline: none;

          background-color: transparent;
        "
        :style="{
          fontSize: inputAreaOptions.fontSize,
          height: inputAreaOptions.height,
          flexGrow: isManual ? 0.8 : 1,
          borderRight: isManual ? 'solid 1px deepskyblue' : 'none',
          padding: '0 ' + arcBoxChildHorizontalPaddingAssembly.toString()
        }"
        :placeholder="$t('message.pleaseEnterSearchPrefix')"
      />
      <button
        v-if="isManual"
        type="button"
        style="
          flex-grow: 0.2;
          flex-basis: content;
          background-color: transparent;
          border: none;
          resize: none;
          outline: none;
          vertical-align: top;
        "
        :style="{
          padding: '0 ' + arcBoxChildHorizontalPaddingAssembly.toString()
        }"
      >
        {{ $t("word.search") }}
      </button>
    </div>

    <div
      ref="resultBox"
      style="position: absolute; background-color: aqua; /*border: dashed aqua 2px;*/"
      :style="{ top: resultBoxTopOffset }"
    >
      <div>
        <vxe-grid v-bind="gridOptions"></vxe-grid>
      </div>
    </div>
  </div>
</template>

<script lang="tsx">
import { defineComponent, PropType, Ref, ref } from "vue";

import { isStringNumeric } from "../utils/IsNumeric";
import { isUndefinedOrNull } from "../utils/IsUndefinedOrNull";
import { getNumberByPrefix, getStringByPrefix } from "../utils/MybigdataApi";
import { RenderLengthAssembly } from "../po/RenderLengthAssembly";
import JsonApiData from "../po/JsonApiData";

export default defineComponent({
  name: "PreloadSearchBox",
  prop: {},
  setup() {
    const templateRoot = ref<HTMLDivElement>() as Ref<HTMLDivElement>;
    const measureFontSizeBox = ref<HTMLDivElement>();
    const searchBox = ref<HTMLDivElement>();
    const inputArea = ref<HTMLTextAreaElement>();
    const resultBox = ref<HTMLDivElement>() as Ref<HTMLDivElement>;
    return {
      templateRoot,
      measureFontSizeBox,
      searchBox,
      inputArea,
      resultBox
    };
  },
  data() {
    return {
      searchValue: "",
      borderRadiusAssembly: RenderLengthAssembly.getNewInstance(30),
      arcBoxChildHorizontalPaddingAssembly: RenderLengthAssembly.getNewInstance(10),
      resultList: [] as any[],
      isManual: false,
      sentRequestCountDown: null,
      loading: true,
      inputAreaOptions: {
        height: "auto",
        fontSize: "large"
      },
      gridOptions: {
        autoResize: true,
        border: true,
        columnConfig: {
          resizable: true
        },
        columns: [],
        data: [],
        headerAlign: "center",
        stripe: true
      }
    };
  },
  computed: {
    borderRadius(): string {
      return this.borderRadiusAssembly.toString();
    },
    searchBoxMiniHeight(): string {
      // 防止 JIT 优化导致 加号运算顺序出错
      const height = this.borderRadiusAssembly.value * 2;
      return height + this.borderRadiusAssembly.unit;
    },
    resultBoxTopOffset(): string {
      const myself = this;
      return isUndefinedOrNull(myself.searchBox) ? "0" : myself.searchBox!.offsetHeight + "px";
    }
  },
  watch: {
    searchValue(inputValue, oldValue) {
      const myself = this;
      if (inputValue === "") {
        myself.resultList = [];
        return;
      }

      // let searchDataType;
      let apiResponse: Promise<JsonApiData>;
      if (isStringNumeric(inputValue) || /^[-]?\d+.{0,1}$/.test(inputValue)) {
        apiResponse = getNumberByPrefix(inputValue);
      } else {
        apiResponse = getStringByPrefix(inputValue);
      }

      apiResponse.then(
        (jsonApiData) => {
          console.log(jsonApiData);
          if (jsonApiData.data instanceof Array) {
            myself.resultList = jsonApiData.data;
          }
        },
        (reason) => {
          console.error(reason);
          myself.resultList = [];
        }
      );
    }
  },
  mounted() {
    const myself = this;
    const inputAreaHeight = myself.measureFontSizeBox!.offsetHeight + 10;
    myself.inputAreaOptions.height = inputAreaHeight + "px";
    myself.loading = false;
  },
  methods: {
    inputAreaGetFocus() {
      const myself = this;
      myself.inputArea!.focus();
    }
  }
});
</script>

<style scoped></style>
