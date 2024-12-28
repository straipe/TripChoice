import { createApp } from "vue";
import { createPinia } from "pinia";
import App from "./App.vue";
import router from "./router";
import "flowbite";
import "flowbite/dist/flowbite.min.css";
import { useKakao } from "vue3-kakao-maps/@utils";
import "@/assets/css/index.css";

const apiKey = import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY;

const app = createApp(App);

useKakao(apiKey);
app.use(createPinia());
app.use(router);
app.mount("#app");
