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
  flex-wrap: wrap;
  /* permite quebrar linha no mobile */
  gap: 16px;
  align-items: center;
  justify-content: space-around;
  width: 100%;
  padding: 8px;
  /* ajuda a não colar nas bordas */
  box-sizing: border-box;
}

@media (max-width: 900px) {
  .header {
    display: none;
  }
}

.link {
  color: black;
  text-decoration: none;
  font-size: clamp(1rem, 2vw, 1.25rem);
  /* responsivo */
  outline: none;
  white-space: nowrap;
  /* evita quebra feia em duas linhas */
}
</style>