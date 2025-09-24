<template>
  <TheLayout>
    <section class="content">
      <div :style="{ width: '100%', maxWidth: '900px', marginBottom: '16px', display: 'flex', alignItems: 'center' }">
        <HomeRoutineHello />
      </div>

      <div :style="{ width: '100%', maxWidth: '900px', display: 'flex', alignItems: 'center' }">
        <HomeRoutineEmpty v-if="routine.id == 0" />
      </div>

      <section v-if="routine.id != 0" class="routine-section">
        <HomeRoutineInfo :title="routine.name" />

        <HomeRoutineProgress :routine="routine" />

        <p>Tarefas de hoje</p>

        <div class="routine-task-cards">
          <article
            class="task-card"
            v-for="task in routine.tasks"
            :key="task.id"
          >
            <HomeRoutineTask :task="task" />
          </article>
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

@media (max-width: 900px) {
  #barra-lateral {
    display: none;
  }
}

.routine-section {
  display: flex;
  flex-direction: column;
  gap: 16px;
  align-items: center;
  width: 100%;
  height: 100%;
}

.routine-task-cards {
  display: flex;
  flex-direction: column;
  gap: 8px;
  width: 100%;
  align-items: center;
}

.task-card {
  display: flex;
  gap: 16px;
  width: 100%;
  align-items: center;
  justify-content: space-between;
  max-width: 300px;
  border: solid 1px rgba(128, 128, 128, 0.421);
  padding: 16px;
  border-radius: 16px;
}
</style>
