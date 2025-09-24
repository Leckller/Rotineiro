<template>
  <TheLayout>
    <section class="content">
      <div
        :style="{
          width: '100%',
          maxWidth: '900px',
          marginBottom: '16px',
          display: 'flex',
          alignItems: 'center',
        }"
      >
        <HomeRoutineHello />
      </div>

      <div
        :style="{
          width: '100%',
          maxWidth: '900px',
          display: 'flex',
          alignItems: 'center',
        }"
      >
        <HomeRoutineEmpty v-if="routine.id == 0" />
      </div>

      <section v-if="routine.id != 0" class="routine-section">
        <HomeRoutineInfo :title="routine.name" />

        <div id="tasks-board">
          <HomeRoutineProgress :routine="routine" />

          <div class="routine-task-cards">
            <p>Tarefas de hoje</p>
              <HomeRoutineTask
                v-for="task in routine.tasks"
                :key="task.id"
                :task="task"
              />
          </div>
        </div>
      </section>
    </section>
  </TheLayout>
</template>

<script lang="ts">
import HomeRoutineEmpty from "@/components/Home/HomeRoutineEmpty.vue";
import HomeRoutineHello from "@/components/Home/HomeRoutineHello.vue";
import HomeRoutineInfo from "@/components/Home/HomeRoutineInfo.vue";
import HomeRoutineProgress from "@/components/Home/HomeRoutineProgress.vue";
import HomeRoutineTask from "@/components/Home/HomeRoutineTask.vue";
import TheLayout from "@/components/TheLayout.vue";
import { PriorityEnum, Routine } from "@/services/routineService";
import { UserService } from "@/services/userService";
import { defineComponent } from "vue";

export default defineComponent({
  name: "HomeView",
  data() {
    return {
      routine: new Routine(0, "", [], PriorityEnum.LOW, ""),
    };
  },
  components: {
    TheLayout,
    HomeRoutineInfo,
    HomeRoutineProgress,
    HomeRoutineEmpty,
    HomeRoutineTask,
    HomeRoutineHello,
  },
  async created() {
    await this.getRoutine();
  },
  methods: {
    async getRoutine() {
      const routine = (await UserService.getActualRoutine()).response;
      if (routine == null) {
        return;
      }
      this.routine = routine;
    },
  },
});
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

#tasks-board {
  display: flex;
  width: 100%;
  gap: var(--gap-base);
}

@media (max-width: 900px) {
  #tasks-board {
    flex-direction: column;
  }
}

.routine-section {
  display: flex;
  flex-direction: column;
  gap: 16px;
  align-items: center;
  max-width: 900px;
  width: 100%;
  height: 100%;
}

.routine-task-cards {
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  gap: 8px;
  align-items: start;
}

</style>
