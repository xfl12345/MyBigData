// i18n.ts

// import elementUILocale from "element-plus/lib/locale";
import { createI18n } from "vue-i18n";
import index from "./langs";

export const i18n = createI18n({
  messages: index,
  locale: localStorage.lang,
  fallbackLocale: "cn"
});
// elementUILocale.i18n((key, value) => i18n.t(key, value));
