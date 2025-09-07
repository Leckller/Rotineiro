<!-- eslint-disable vue/no-mutating-props -->
<template>
  <input type="checkbox" v-model="task.completed" @change="onToggleTask" />
  <div class="routine-task-info">
    <h4>{{ task.name }}</h4>
    <p>
      <small>{{ task.estimate }} min</small>
      <small v-if="run">
        {{ formattedTimer }}
      </small>
    </p>
  </div>
  <button @click="toggleStart(task)">
    <FontAwesomeIcon :icon="run ? 'stop' : 'play'" />
  </button>
</template>

<script lang="ts">
import { Task, TaskService } from '@/services/taskService';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { defineComponent } from 'vue';

export default defineComponent({
  name: "HomeRoutineTask",
  components: { FontAwesomeIcon },
  data() {
    return {
      run: false,
      timer: 0, // timer em segundos
      intervalId: undefined as number | undefined
    };
  },
  props: {
    task: { required: true, type: Object as () => Task }
  },
  computed: {
    formattedTimer(): string {
      const minutes = Math.floor(this.timer / 60).toString().padStart(2, '0');
      const seconds = (this.timer % 60).toString().padStart(2, '0');
      return `${minutes}:${seconds}`;
    }
  },
  methods: {
    async onToggleTask() {
      try {
        await TaskService.toggleTask(this.task.id);
      } catch (error) {
        console.error(error);
      }
    },
    async toggleStart(task: Task) {
      try {
        await TaskService.startTask(task.id);
        this.run = !this.run;

        if (this.run) {
          // inicia o cronômetro
          this.intervalId = window.setInterval(() => {
            this.timer++;
          }, 1000);
        } else {
          // pausa o cronômetro
          if (this.intervalId) {
            clearInterval(this.intervalId);
            this.intervalId = undefined;
          }
        }
      } catch (error) {
        console.error(error);
      }
    }
  },
  beforeUnmount() {
    // limpa intervalo quando o componente sair
    if (this.intervalId) {
      clearInterval(this.intervalId);
      this.intervalId = undefined;
    }
  }
});
</script>

<style scoped>
.routine-task-info {
  display: flex;
  flex-direction: column;
  width: 100%;
  justify-content: start;
  align-items: start;
}
</style>
