<template>
  <div ref="templateRoot" class="templateRootStyle"></div>
</template>

<script lang="tsx">
import { ref } from "vue";

export default {
  name: "XXXXXXXXXXXXXXXXXXXXXXXXXPage",
  setup() {
    const templateRoot = ref<HTMLDivElement>();
    return {
      templateRoot
    };
  }
};
</script>

<style scoped>
.templateRootStyle {
  width: 100%;
}
</style>
