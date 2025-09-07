<template>
  <div class="hello-content">
      <h3>Olá! 👋</h3>
      <p><small>{{ actualDate }}</small></p>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: "HomeRoutineHello",
  data() {
    return {
      actualDate: "",
    }
  },
  created() {
    this.getActualDate();
  },
  methods: {
    getActualDate() {
      const hoje = new Date();
      const opcoes = {
        weekday: 'long' as const,
        year: 'numeric' as const,
        month: 'long' as const,
        day: 'numeric' as const
      };

      this.actualDate = hoje.toLocaleDateString('pt-BR', opcoes)
        .split(" ")
        .map(word => word.charAt(0).toUpperCase() + word.slice(1))
        .join(" ");
    },
  }
})
</script>

<style scoped>
.hello-content {
  display: flex;
  align-items: start;
  max-width: 300px;
  width: 100%;
  flex-direction: column;
}
</style>
