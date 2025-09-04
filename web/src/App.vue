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
  transition: all .5s;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.5);
}

* {
  padding: 0;
  margin: 0;
  box-sizing: border-box;
}

html{
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
  flex-direction: column;
  align-items: center;
}
</style>
