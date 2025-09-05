<template>

  <TheLayout>

    <section class="content">

      <div class="content-header">
        <h3>Olá! 👋</h3>
        <p><small>{{ actualDate }}</small></p>
      </div>

      <div v-if="routine.id == 0" class="empty-section">
        <img class="img" src="../assets/book.jpg" alt="livro">
        <p>Pronto para organizar seu dia?</p>
        <p><small>Comece criando sua rotina personalizada para aumentar sua produtividade!</small></p>
        <article class="empty-help">
          <FontAwesomeIcon class="help-icon" icon="bullseye" />
          <div class="empty-help-text">
            <p><strong>Crie sua primeira rotina</strong></p>
            <p>Organize suas atividades do dia</p>
          </div>
        </article>
      </div>

      <button v-if="routine.id == 0" @click="() => $router.push('/routine')" class="btn">
        <FontAwesomeIcon icon="plus" />
        Escolher Rotina
      </button>
    </section>

    <article v-for="task in routine.tasks" :key="task.id">
      {{ task.name }}
    </article>

  </TheLayout>

</template>

<script lang="ts">
import TheLayout from '@/components/TheLayout.vue';
import { PriorityEnum, Routine, RoutineService } from '@/services/routineService';
import { UserService } from '@/services/userService';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'HomeView',
  data() {
    return {
      actualDate: "",
      routine: new Routine(0, "", [], PriorityEnum.LOW, "")
    }
  },
  components: {
    TheLayout,
    FontAwesomeIcon
  },
  async created() {
    const routine = (await UserService.getActualRoutine()).response;
    this.routine = routine;
    this.getActualDate()
  },
  methods: {
    getActualDate() {
      const hoje = new Date();
      const opcoes = { weekday: 'long' as const, year: 'numeric' as const, month: 'long' as const, day: 'numeric' as const };

      this.actualDate = hoje.toLocaleDateString('pt-BR', opcoes)
        .split(" ")
        .map(word => word.charAt(0).toUpperCase() + word.slice(1))
        .join(" ");
    },
    async getRoutines() {
      try {
        const routines = await RoutineService.getAllRoutines()
        console.log(routines)
      } catch {

      }
    }
  }
})
</script>

<style scoped>
.content {
  display: flex;
  padding: 0 16px;
  flex-direction: column;
  gap: 16px;
  align-items: center;
  height: 100%;
  width: 100%;
}

.empty-help {
  display: flex;
  font-size: small;
  align-items: center;
  padding: 16px;
  border: solid 1px oklch(.809 .105 251.813);
  gap: 8px;
  border-radius: 8px;
  background-color: oklch(.97 .014 254.604);
}

.help-icon {
  background-color: oklch(.546 .245 262.881);
  color: white;
  padding: 8px;
  height: 15px;
  width: 15px;
  border-radius: 999px;
}

.empty-help-text {
  display: flex;
  flex-direction: column;
  align-items: start;
  gap: 4px;
}

.empty-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
}

.content-header {
  display: flex;
  text-align: start;
  flex-direction: column;
}

.btn {
  color: white;
  font-size: smaller;
  background-color: oklch(.623 .214 259.815);
  padding: 8px;
  border-radius: 16px;
  max-width: 200px;
}

.img {
  width: 150px;
  aspect-ratio: 1 / 1;
  object-position: -30px center;
  object-fit: cover;
  border-radius: 8px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.5);
}
</style>
