<template>

  <header class="header">
    <RouterLink class="link" to="/home">
      <h2>Rotineiro</h2>
    </RouterLink>

    <RouterLink class="link" to="/routine">
      Rotinas
    </RouterLink>

    <RouterLink class="link" to="/dashboard">
      Dashboard
    </RouterLink>
  </header>

</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { RouterLink } from 'vue-router';


export default defineComponent({
  name: "TheHeader",
  components: { RouterLink }
})

</script>

<style scoped>
.header {
  display: flex;
  gap: 16px;
  align-items: end;
  justify-content: center;
  width: 100%;
  justify-content: space-around;
}

@media (max-width: 900px) {
  .header {
    position: absolute;
    z-index: -10;
    width: 0;
    visibility: hidden;
  }
}

.link {
  color: black;
  text-decoration: none;
  font-size: x-large;
  outline: none;
}
</style>