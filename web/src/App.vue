<template>
  <router-view />
</template>

<style>
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

button:hover {
  transition: all 1s;
  transform: scale(1.01);
}

* {
  padding: 0;
  margin: 0;
  box-sizing: border-box;
}

html, body, #app {
  height: 100%;
  width: 100%;
  display: flex;
  justify-content: space-between;
  flex-direction: column;
  align-items: center;
}
</style>
