<template>
  <div class="routine-task-cards">
    <article class="task-card" v-for="task in routine.tasks" :key="task.id">
      <input 
        type="checkbox" 
        v-model="task.completed"
        @change="toggleTask(task)"
      >
      <div>
        <h4>{{ task.name }}</h4>
        <p><small>{{ task.estimate }} min</small></p>
      </div>
      <button @click="startTask(task)">
        <FontAwesomeIcon icon="play" />
      </button>
    </article>
  </div>
</template>

<script lang="ts">
import { Routine } from '@/services/routineService';
import { Task, TaskService } from '@/services/taskService';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { defineComponent } from 'vue';

export default defineComponent({
  name: "HomeRoutineTask",
  components: { FontAwesomeIcon },
  props: {
    routine: { required: true, type: Object as () => Routine }
  },
  methods: {
    async toggleTask(task: Task) {
      const prevState = task.completed;
      try {
        await TaskService.toggleTask(task.id);
      } catch (error) {
        console.error(error);
        task.completed = prevState;
      }
    },
    async startTask(task: Task) {
      try {
        await TaskService.startTask(task.id);
      } catch (error) {
        console.error(error);
      }
    }
  }
});
</script>

<style scoped>
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
}
</style>
