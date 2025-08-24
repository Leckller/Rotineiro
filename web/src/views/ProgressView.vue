<template>

  <TheLayout>

    <section class="progresso">
      <article>Rotinas Completas</article>
      <article>Tarefas no Mês</article>
      <article>Tempo Total</article>
      <article>Tarefas Totais</article>
    </section>

    <section class="atividade-semanal">

      <h3>Progresso Semanal</h3>
      <p>Seus treinos nos últimos 7 dias</p>

      <span>Dashboard</span>

    </section>

    <section class="atividade-mensal">

      <h3>Progresso Mensal</h3>
      <p>Evolução do seu desempenho</p>

      <span>Dashboard</span>

    </section>

    <section class="conquistas">

      <h3>Progresso Mensal</h3>
      <p>Evolução do seu desempenho</p>

      <article>

        <span>icone</span>

        <div class="conquista-info">
          <h4>Titulo da conquista</h4>
          <p>Descrição da conquista</p>
        </div>

        <div v-if="false" class="conquista-concluida">
          <span>
            icone
          </span>
          <p>
            Conquista
          </p>
        </div>

      </article>

    </section>

  </TheLayout>

</template>

<script lang="ts">
import TheLayout from '@/components/TheLayout.vue';
import { defineComponent } from 'vue';

export default defineComponent({
  name: "ProgressView",
  components: {
    TheLayout
  }
})

</script>
