<template>

  <TheLayout>

    <div class="content">

      <section class="header">
        <button>
          <FontAwesomeIcon icon="arrow-left" />
        </button>
        <div class="header-title">
          <h2>{{ routineStore.selectedRoutine.name }}</h2>
          <p>{{ routineStore.selectedRoutine.description }}</p>
        </div>
        <button @click="editRoutine()">
          <FontAwesomeIcon icon="pencil" />
        </button>
      </section>

      <section class="routine-info">
        <article class="card info">
          <p>
            <strong>
              <FontAwesomeIcon icon="clock" />
              Duração Total
            </strong>
          </p>
          <p>
            {{routineStore.selectedRoutine.tasks.reduce((pv, curr) => {
              return pv + curr.estimate
            }, 0)}}
          </p>
        </article>
        <article class="card info">
          <p>
            <strong>
              <FontAwesomeIcon icon="bullseye" />
              Atividades
            </strong>
          </p>
          <p>
            {{ routineStore.selectedRoutine.tasks.length }}
          </p>
        </article>
      </section>

      <section class="tasks">

        <div class="task-header">
          <h3>
            Atividades da Rotina
          </h3>
          <button class="add-task" @click="createTask">
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

.header {
  display: flex;
  gap: 16px;
  text-align: center;
  width: 100%;
  justify-content: space-around;
}

.header-title {
  display: flex;
  flex-direction: row;
  width: 100%;
  align-items: center;
  justify-content: center;
  gap: 16px;
}

.routine-info {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  gap: 16px;
  align-items: center;
  justify-content: center;
}

.card {
  display: flex;
  flex-direction: column;
  align-items: start;
  padding: 16px;
  border-radius: 8px;
  gap: 8px;
}

.info {
  align-items: center !important;
  font-size: large;
  width: 40%;
  flex-grow: 1;
}

.tasks {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 16px;
  width: 100%;
  max-width: 300px;
}

.task-header {
  display: flex;
  width: 100%;
  align-items: center;
  justify-content: space-between;
}

.add-task {
  color: white;
  font-size: smaller;
  background-color: oklch(.623 .214 259.815);
  padding: 8px;
  border-radius: 8px;
  max-width: 200px;
}
</style>