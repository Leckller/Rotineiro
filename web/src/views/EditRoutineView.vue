<template>
  <TheLayout>
    <div class="content">
      <section class="header">
        <div
          :style="{
            display: 'flex',
            gap: 'var(--gap-base)',
            alignItems: 'center',
          }"
        >
          <TheButton variant="tertiary" @click="() => $router.back()">
            <FontAwesomeIcon icon="arrow-left" />
          </TheButton>
          <div id="routine-title">
            <h2>{{ routineStore.selectedRoutine.name }}</h2>
            <p>{{ routineStore.selectedRoutine.description }}</p>
          </div>
        </div>
        <TheButton variant="tertiary" @click="editRoutine()">
          <FontAwesomeIcon icon="pencil" />
        </TheButton>
      </section>

      <section class="routine-info">
        <article class="card info info-blue">
          <p
            :style="{
              display: 'flex',
              alignItems: 'center',
              gap: 'var(--gap-small)',
            }"
          >
            <FontAwesomeIcon
              :style="{ fontSize: 'var(--large)' }"
              icon="clock"
            />
            Duração Total
          </p>
          <strong>
            {{
              routineStore.selectedRoutine.tasks.reduce((pv, curr) => {
                return pv + curr.estimate;
              }, 0)
            }}
          </strong>
        </article>
        <article class="card info info-green">
          <p
            :style="{
              display: 'flex',
              alignItems: 'center',
              gap: 'var(--gap-small)',
            }"
          >
            <FontAwesomeIcon
              :style="{ fontSize: 'var(--large)' }"
              icon="bullseye"
            />
            Atividades
          </p>
          <strong>
            {{ routineStore.selectedRoutine.tasks.length }}
          </strong>
        </article>
      </section>

      <section class="tasks">
        <div class="task-header">
          <h3>Atividades da Rotina</h3>
          <button class="add-task" @click="createTask">+ Adicionar</button>
        </div>

        <TaskCard
          v-for="task in routineStore.selectedRoutine.tasks"
          :key="task.id"
          :task="task"
        />
      </section>
    </div>
  </TheLayout>
</template>

<script lang="ts">
import TheButton from "@/components/Forms/TheButton.vue";
import TaskCard from "@/components/Task/TaskCard.vue";
import TheLayout from "@/components/TheLayout.vue";
import { RoutineService } from "@/services/routineService";
import { useModalStore } from "@/stores/modals";
import { useRoutineStore } from "@/stores/Routine";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { defineComponent } from "vue";

export default defineComponent({
  name: "EditRoutineView",
  components: {
    TheLayout,
    FontAwesomeIcon,
    TheButton,
    TaskCard,
  },
  data() {
    return {
      modalStore: useModalStore(),
      routineStore: useRoutineStore(),
    };
  },
  async created() {
    this.getRoutine();
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
      } catch (error) {}
    },
  },
});
</script>

<style scoped>
.content {
  display: flex;
  font-size: small;
  flex-direction: column;
  gap: 8px;
  align-items: center;
  max-width: 900px;
  height: 100%;
  width: 100%;
  padding: 16px;
}

.header {
  display: flex;
  gap: 16px;
  text-align: center;
  align-items: center;
  width: 100%;
  justify-content: space-between;
}

#routine-title {
  display: flex;
  flex-direction: column;
  width: 100%;
  align-items: start;
  text-align: start;
  gap: var(--gap-small);
}
#routine-title p {
  font-size: var(--medium);
}
#routine-title h2 {
  font-size: var(--x-large);
}

.routine-info {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  gap: 16px;
  align-items: center;
  width: 100%;
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

.info-blue {
  background-color: var(--light-blue);
  border: solid 1px var(--medium-blue);
  color: var(--strong-blue);
}

.info-green {
  background-color: var(--light-green);
  border: solid 1px var(--medium-green);
  color: var(--strong-green);
}

.info {
  align-items: center !important;
  font-size: var(--medium);
  font-size: large;
  width: 40%;
  flex-grow: 1;
}

.info strong {
  font-size: var(--large);
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
  background-color: oklch(0.623 0.214 259.815);
  padding: 8px;
  border-radius: 8px;
  max-width: 200px;
}
</style>