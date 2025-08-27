<template>

  <TheLayout>

    <section class="recomendado">
      <h2>Rotina de hoje!</h2>
      <article class="rotina-recomendada">
        <div class="header-rotina-recomendada">
          <h3>RECOMENDADO</h3>
          <span>icone</span>
        </div>
        <p>Estudos</p>
        <div class="tarefas-rotina-recomendada">
          <p>8 Tarefas</p>
          <p>4 horas</p>
        </div>
        <button class="comecar-rotina-recomendada">
          Começar Rotina
        </button>
      </article>
    </section>

    <section class="rotinas">
      <div class="header-rotinas">
        <h2>Suas Rotinas</h2>
        <button>Nova Rotina</button>
      </div>
      <div class="body-rotinas">
        <article class="card-rotina">
          <h3>Nome</h3>
          <div>
            <p>5 horas</p>
            <p>10 Tarefas</p>
          </div>
          <div class="categorias">
            <p>
              Matemática
            </p>
            <p>
              Leitura
            </p>
          </div>
        </article>
      </div>
    </section>

  </TheLayout>

</template>

<script lang="ts">
import TheLayout from '@/components/TheLayout.vue';
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'HomeView',
  components: {
    TheLayout
  }
})
</script>
