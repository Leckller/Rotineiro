<template>
  <main>
    <slot />
  </main>

  <footer>
    <RouterLink to="/" class="router">
      <span>icone</span>
      Hoje
    </RouterLink>
    <RouterLink to="/rotina" class="router">
      <span>icone</span>
      Rotinas
    </RouterLink>
    <RouterLink to="/calendario" class="router">
      <span>icone</span>
      Calendário
    </RouterLink>
    <RouterLink to="/dashboard" class="router">
      <span>icone</span>
      Dashboard
    </RouterLink>
  </footer>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { RouterLink } from "vue-router";

export default defineComponent({
  name: "TheLayout",
  components: {
    RouterLink,
  },
});
</script>

<style scoped>
.main {
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
}

.footer {
  display: flex;
  width: 100%;
  padding: 24px;
  justify-content: space-around;
}
</style>
