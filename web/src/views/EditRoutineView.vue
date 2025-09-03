<template>

  <TheLayout>

    <div class="content">
      <section class="routine-header">
        <button>
          <FontAwesomeIcon icon="arrow-left" />
        </button>
        <div class="routine-info">
          <h2>{{ routineStore.selectedRoutine.name }}</h2>
          <p>{{ routineStore.selectedRoutine.description }}</p>
        </div>
        <button @click="editRoutine()">
          <FontAwesomeIcon icon="pencil" />
        </button>
      </section>

      <section class="card-info">
        <article class="card">
          <p>
            <FontAwesomeIcon icon="clock" />
            Duração Total
          </p>
          <p>
            {{routineStore.selectedRoutine.tasks.reduce((pv, curr) => {
              return pv + curr.estimate
            }, 0)}}
          </p>
        </article>
        <article class="card">
          <p>
            <FontAwesomeIcon icon="bullseye" />
            Atividades
          </p>
          <p>
            {{ routineStore.selectedRoutine.tasks.length }}
          </p>
        </article>
      </section>

      <section class="tasks">

        <div class="task-header">
          <p>
            Atividades da Rotina
          </p>
          <button @click="createTask">
            + Adicionar
          </button>
        </div>

        <TaskCard v-for="task in routineStore.selectedRoutine.tasks" :key="task.id" :task="task" />

      </section>
    </div>

  </TheLayout>

</template>

<script lang="ts">
import TaskCard from '@/components/Task/TaskCard.vue';
import TheLayout from '@/components/TheLayout.vue';
import { RoutineService } from '@/services/routineService';
import { useModalStore } from '@/stores/modals';
import { useRoutineStore } from '@/stores/Routine';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { defineComponent } from 'vue';


export default defineComponent({
  name: "EditRoutineView",
  components: {
    TheLayout, FontAwesomeIcon, TaskCard
  },
  data() {
    return {
      modalStore: useModalStore(),
      routineStore: useRoutineStore()
    }
  },
  async created() {
    this.getRoutine()
  },
  methods: {
    createTask() {
      this.modalStore.openAndSetModal("createTask");
    },
    editRoutine() {
      this.modalStore.openAndSetModal("editRoutine");
    },
    async getRoutine() {
      try {
        const id = this.$route.params.id as string;
        const routine = (await RoutineService.getRoutineById(+id)).response;
        this.routineStore.selectRoutine(routine);
      } catch (error) {
      }
    },
  }
})

</script>

<style scoped>
.content {
  display: flex;
  font-size: small;
  flex-direction: column;
  gap: 8px;
  align-items: center;
  height: 100%;
  width: 100%;
  padding: 16px;
}

.routine-header {
  display: flex;
  gap: 16px;
  text-align: center;
  width: 100%;
  justify-content: space-around;
}

.routine-info {
  display: flex;
  flex-direction: column;
  gap: 16px;
  align-items: center;
}

.card-info {
  display: flex;
  flex-direction: row;
  width: 100%;
  align-items: center;
  justify-content: center;
  gap: 16px;
}

.card {
  display: flex;
  flex-direction: column;
  gap: 8px;
  padding: 16px;
  border-radius: 8px;
  border: solid 1px black;
  max-width: 160px;
}

.tasks {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 16px;
  width: 100%;
}

.task-header {
  display: flex;
  width: 100%;
  align-items: center;
  justify-content: space-around;
}
</style>