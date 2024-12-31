import { createApp } from "vue";
import { createStore } from "vuex";
import { createRouter, createWebHashHistory, RouteRecordRaw } from "vue-router";
import { i18n } from "./i18n/i18n";
// import ElementPlus from "element-plus";
// import "xe-utils";
// import VXETable from "vxe-table";
// import "vxe-table/lib/style.css";
// import "element-plus/dist/index.css";
import App from "./App.vue";

import AjaxTest from "./components/AjaxTest.vue";
import HelloWorld from "./components/HelloWorld.vue";
import IndexPage from "./components/IndexPage.vue";

const app = createApp(App);

const store = createStore({
  state: {},
  mutations: {}
});
app.use(store);
type routeNode = Array<RouteRecordRaw>;
const routes: routeNode = [
  { path: "/", component: IndexPage },
  { path: "/hello-world", component: HelloWorld, props: { msg: "Hello Vue 3 + TypeScript + Vite" } },
  { path: "/ajax-test", component: AjaxTest }
];
const router = createRouter({
  history: createWebHashHistory(),
  routes
});

app.use(router);
// app.use(ElementPlus);
// app.use(VXETable);
app.use(i18n);

app.mount("#app");
