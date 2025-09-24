<template>
  <router-view />
</template>

<style>
:root {
  --strong-blue: #25527e;
  --medium-2-blue: #1e90ff;
  --medium-blue: #009fe6;
  --light-blue: #edf6ff;

  --strong-red: #cf0404;
  --medium-red: #f20505;
  --light-red: #ffaaaa;

  --strong-green: #458108;
  --medium-green: #64bf0a;
  --light-green: #e0f8c8;


  --medium-yellow: #f2cb05;

  --medium-orange: #f97316;

  --primary-text-color: #1b2128;
  --secondary-text-color: #323232;
  --tertiary-text-color: #656a6e;

  --strong-gray: #323232;
  --medium-2-gray: #4b5563;
  --medium-gray: #8a8a8a;
  --light-2-gray: #b8b8b8;
  --light-gray: #e2e5e9;

  --white: #ffffff;

  --small: 12px;
  --base: 14px;
  --medium: 16px;
  --large: 20px;
  --x-large: 24px;
  --xx-large: 30px;
  --xx-large: 34px;

  --tablet: 900px;
  --phone: 500px;

  --padding-base: 16px;
  --padding-small: 8px;
  --gap-base: 16px;
  --gap-small: 8px;
  --gap-large: 24px;
  --gap-giant: 64px;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

button {
  background-color: transparent;
  border: none;
  cursor: pointer;
  transition: all 1s;
}

svg {
  font-size: smaller;
}

* {
  padding: 0;
  margin: 0;
  font-size: var(--base);
  box-sizing: border-box;
}

html {
  width: 100vw;
  height: 100vh;
}

body {
  width: 100%;
  height: 100%;
}

small {
  color: rgb(88, 88, 88);
}

#app {
  height: 100%;
  width: 100%;
  display: flex;
  align-items: center;
}
</style>
