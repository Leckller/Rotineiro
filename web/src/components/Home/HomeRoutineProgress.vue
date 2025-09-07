<template>
  <div class="routine-progress">
    <p>
      Progresso do dia
      <span class="percent">
        {{ getPercent() }} %
      </span>
    </p>
    <ProgressBar :color="'black'" :value="getPercent()" />
    <p class="qtd-tasks-status">
        {{ getCompletedTasks() }} de {{ routine.tasks.length }} tarefas concluídas
    </p>
  </div>
</template>

<script lang="ts">
import { Routine } from '@/services/routineService';
import { TaskEntity } from '@/services/taskService';
import { defineComponent } from 'vue';
import ProgressBar from '../ProgressBar.vue';

export default defineComponent({
  name: "HomeRoutineProgress",
  components: {
    ProgressBar
  },
  props: {
    routine: { required: true, type: Routine }
  },
  methods: {
    getCompletedTasks() {
      return this.routine.tasks.filter((t: TaskEntity) => t.completed).length
    },
    getPercent() {
      if (!this.routine.tasks.length) return 0;
      return (
        (this.routine.tasks.filter((t: TaskEntity) => t.completed).length /
          this.routine.tasks.length) *
        100
      ).toFixed(0);
    }
  }
});
</script>

<style scoped>
.qtd-tasks-status {
  font-size: 12px;
  color: rgb(100, 100, 100);
}

.routine-progress {
  display: flex;
  flex-direction: column;
  width: 100%;
  max-width: 300px;
  gap: 8px;
  border: solid 1px rgb(163, 163, 255);
  border-radius: 8px;
  padding: 16px;
  background: linear-gradient(to right, #acd8ff5b, #c3ffd658);
}

.routine-progress p {
  display: flex;
  justify-content: space-between;
}

.percent {
  font-size: large;
  color: rgb(109, 109, 248);
}
</style>
