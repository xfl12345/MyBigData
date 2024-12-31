<template>
  <div
    ref="templateRoot"
    style="
      width: inherit;
      height: inherit;
      background-size: 100% auto;
      background-repeat: repeat-y;
      display: flex;
      flex-direction: row;
      justify-content: center;
    "
    :style="{ backgroundImage: cssBgImgCode }"
  >
    <CenterBox v-bind="centerBoxOptions">
      <template #contentBox>
        <div
          ref="contentBox"
          class="broderBreath"
          style="
            height: 100%;
            width: 100%;
            border-style: solid;
            border-color: hotpink;
            box-sizing: border-box;
            background-color: rgba(255, 255, 255, 0.68);
            border-radius: 25px;
            padding: 25px;
          "
        >
          <div>
            <span ref="title" style="font-size: xxx-large; font-family: '楷体', 'Consolas', serif">{{
              $t("word.mybigdata")
            }}</span>
          </div>
          <br />
          <div style="width: 500px">
            <PreloadSearchBox />
          </div>
        </div>
      </template>
    </CenterBox>
  </div>
</template>

<script lang="tsx">
import { defineComponent, PropType, ref } from "vue";
import CenterBox from "./CenterBox.vue";
import PreloadSearchBox from "./PreloadSearchBox.vue";

export default defineComponent({
  name: "IndexPage",
  components: { PreloadSearchBox, CenterBox },
  props: {
    bgImgURL: {
      type: String as PropType<string>,
      default: "https://www.z4a.net/images/2024/12/31/yourname_dusk.jpg"
    }
  },
  setup() {
    const templateRoot = ref<HTMLDivElement>();
    const title = ref<HTMLSpanElement>();
    return {
      templateRoot,
      title
    };
  },
  data() {
    return {
      centerBoxOptions: {
        // contentBoxWidth: "61.8%",
        // contentBoxHeight: "38.2%"
      }
    };
  },
  computed: {
    // 背景图片 CSS background-image: url (?)
    cssBgImgCode(): string {
      const myself = this;
      return `url('${myself.bgImgURL}')`;
    }
  },
  mounted() {
    const myself = this;
  },
  method: {}
});
</script>

<style scoped>
.broderBreath {
  /*animation: load 1.5s infinite alternate;*/
}

.broderBreath:nth-child(1) {
  /*animation-delay: -0.25s;*/
}

@keyframes load {
  0% {
    box-shadow: 0 0 10px 3px rgba(255, 105, 180, 0.5);
  }
  100% {
    box-shadow: 0 0 40px 3px rgba(255, 105, 180, 1);
  }
}
</style>
