<template>
  <div ref="templateRoot" class="templateRootStyle" :style="{ backgroundImage: cssBgImgCode }">
    <div ref="columnCenterBox" class="columnCenterBox">
      <div ref="rowCenterBox" class="rowCenterBox broderBreath">
        <div ref="centerBox" class="centerBox">
          <div ref="contentBox" class="contentBox">666</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="tsx">
import { defineComponent, PropType, ref } from "vue";

export default defineComponent({
  name: "LoginPage",
  props: {
    bgImgURL: {
      type: String as PropType<string>,
      default: "https://www.z4a.net/images/2024/12/31/yourname_dusk.jpg"
    }
  },
  setup() {
    const templateRoot = ref<HTMLDivElement>();
    return {
      templateRoot
    };
  },
  data() {
    return {};
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
  }
});
</script>

<style scoped>
.templateRootStyle {
  width: 100%;
  height: 100%;
  background-size: 100% auto;
  background-repeat: repeat-y;
  display: flex;
  flex-direction: row;
  justify-content: center;
}

.columnCenterBox {
  flex-basis: 300px;
  flex-grow: calc((1 - 0.618) / 2);
  /*background-color: blue;*/
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.rowCenterBox {
  flex-basis: 500px;
  flex-grow: calc(1 - 0.618);
  background-color: rgba(255, 255, 255, 0.68);
  border-radius: 25px;
  display: flex;
}

.centerBox {
  flex-grow: 1;
  padding: 25px;
}

.broderBreath {
  animation: load 1.5s infinite alternate;
}

.broderBreath:nth-child(1) {
  animation-delay: -0.25s;
}

@keyframes load {
  0% {
    box-shadow: 0 0 10px 3px rgba(255, 105, 180, 0.5);
  }
  100% {
    box-shadow: 0 0 40px 3px rgba(255, 105, 180, 1);
  }
}

.contentBox {
  height: 100%;
  width: 100%;
  border-style: solid;
  border-color: hotpink;
  box-sizing: border-box;
}
</style>
