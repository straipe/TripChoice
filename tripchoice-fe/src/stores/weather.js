import { ref } from "vue";
import { defineStore } from "pinia";

import clearSky from "@/assets/icon/clear-sky.png";
import darkClearSky from "@/assets/icon/clear-sky-dark.png";
import cloud from "@/assets/icon/cloud.png";
import darkCloud from "@/assets/icon/cloud-dark.png";
import rain from "@/assets/icon/rainy.png";
import darkRain from "@/assets/icon/rain-dark.png";
import snow from "@/assets/icon/snowflake.png";
import darkSnow from "@/assets/icon/snowflake-dark.png";
import sun from "@/assets/icon/sun.png";
import darkSun from "@/assets/icon/sun-dark.png";

export const useWeatherStore = defineStore("weather", () => {
  const address = "서울시 강남구";
  const nowWeather = ref({});
  const weatherList = ref([]);

  function getWindChill() {
    let tmp = nowWeather.value.tmp;
    let wsd = nowWeather.value.wsd * 3.6;
    let ctmp =
      13.12 +
      0.6215 * tmp -
      11.37 * Math.pow(wsd, 0.16) +
      0.3965 * Math.pow(wsd, 0.16) * tmp;

    return Math.round((ctmp * 10) / 10);
  }

  function getWeatherName(sky, pty) {
    switch (sky) {
      case 1:
        return "맑음";
      case (3, 4):
        switch (pty) {
          case 0:
            if (sky === 3) return "구름많음";
            else return "흐림";
          case (1, 5):
            return "비";
          case (2, 6):
            return "비/눈";
          case (3, 7):
            return "눈";
        }
    }
  }

  function getWeatherImg(mode, sky, pty) {
    switch (sky) {
      case 1:
        return mode === 0 ? sun : darkSun;
      case (3, 4):
        switch (pty) {
          case 0:
            if (sky === 3) return mode === 0 ? clearSky : darkClearSky;
            else return mode === 0 ? cloud : darkCloud;
          case (1, 5):
            return mode === 0 ? rain : darkRain;
          case (2, 6):
            return mode === 0 ? rain : darkRain;
          case (3, 7):
            return mode === 0 ? snow : darkSnow;
        }
    }
  }

  function getWindChillBoolean() {
    return nowWeather.value.tmp > 25 && nowWeather.value.reh > 60;
  }

  return {
    address,
    nowWeather,
    weatherList,
    getWeatherName,
    getWeatherImg,
    getWindChill,
    getWindChillBoolean,
  };
});
