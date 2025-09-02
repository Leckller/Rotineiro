<template>

  <TheLayout>

    <section>
      <h3>Tarefas de hoje</h3>
      <article v-for="task in tasks" :key="task.id">
        {{ task.name }}
      </article>
    </section>

  </TheLayout>

</template>

<script lang="ts">
import TheLayout from '@/components/TheLayout.vue';
import { RoutineService } from '@/services/routineService';
import { TaskEntity } from '@/services/taskService';
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'HomeView',
  data() {
    return {
      tasks: [] as TaskEntity[]
    }
  },
  components: {
    TheLayout
  },
  async created() {
    await this.getRoutines()
  },
  methods: {
    async getRoutines() {
      try {
        const routines = await RoutineService.getAllRoutines()
        console.log(routines)
      } catch {

      }
    }
  }
})
</script>
