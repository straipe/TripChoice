/** @type {import('tailwindcss').Config} */
// @ts-nocheck
export default {
  content: [
    "index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
    "./node_modules/flowbite/**/*.js",
  ],
  theme: {
    extend: {
      colors: {
        limeGreen: "#a3e635",
        darkLimeGreen: "#65a30d",
      },
    },
  },
  plugins: [require("flowbite/plugin"), require("tailwind-scrollbar")],
};
