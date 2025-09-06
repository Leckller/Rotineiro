<template>

  <h2 class="routine-card">
    <p>
      Progresso do dia
    </p>
    <span>
      {{((routine.tasks.filter((t: TaskEntity) => t.completed).length / routine.tasks.length) * 100).toFixed(1)}} %
    </span>
  </h2>

</template>

<script lang="ts">
import { Routine } from '@/services/routineService';
import { TaskEntity } from '@/services/taskService';
import { defineComponent } from 'vue';

export default defineComponent({
  props: {
    routine: { required: true, type: Routine }
  },
})

</script>

<style scoped>
.routine-card {
  display: flex;
  justify-content: space-between;
  gap: 16px;
}
</style>