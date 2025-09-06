<template>

  <div class="routine-task-cards">
    <article class="task-card" v-for="task in routine.tasks" :key="task.id">
      <input :checked="task.completed" type="checkbox" @click="toggleTask(task.id)">
      <div>
        <h4> {{ task.name }} </h4>
        <p>
          <small>
            {{ task.estimate }} min
          </small>
        </p>
      </div>
      <button @click="startTask(task.id)">
        <FontAwesomeIcon icon="play" />
      </button>
    </article>
  </div>

</template>

<script lang="ts">
import { Routine } from '@/services/routineService';
import { TaskService } from '@/services/taskService';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { defineComponent } from 'vue';

export default defineComponent({
  name: "HomeRoutineTask",
  components: {
    FontAwesomeIcon
  },
  methods: {
    async toggleTask(taskID: number) {
      try {
        await TaskService.toggleTask(taskID);
      } catch (error) {
        console.log(error);
      }
    },
    async startTask(taskID: number) {
      try {
        await TaskService.startTask(taskID);
      } catch (error) {
        console.log(error);
      }
    },
  },
  props: {
    routine: { required: true, type: Routine }
  },
})

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