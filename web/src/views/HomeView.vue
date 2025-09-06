<template>

  <TheLayout>

    <section class="content">

      <HomeRoutineHello />

      <HomeRoutineEmpty v-if="routine.id == 0" />

      <section v-if="routine.id != 0" class="routine-section">

        <HomeRoutineInfo />

        <HomeRoutineProgress :routine="routine" />

        <HomeRoutineTask :routine="routine" />

      </section>

    </section>

  </TheLayout>

</template>

<script lang="ts">

import HomeRoutineEmpty from '@/components/Home/HomeRoutineEmpty.vue';
import HomeRoutineHello from '@/components/Home/HomeRoutineHello.vue';
import HomeRoutineInfo from '@/components/Home/HomeRoutineInfo.vue';
import HomeRoutineProgress from '@/components/Home/HomeRoutineProgress.vue';
import HomeRoutineTask from '@/components/Home/HomeRoutineTask.vue';
import TheLayout from '@/components/TheLayout.vue';
import { PriorityEnum, Routine } from '@/services/routineService';
import { UserService } from '@/services/userService';
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'HomeView',
  data() {
    return {
      routine: new Routine(0, "", [], PriorityEnum.LOW, "")
    }
  },
  components: {
    TheLayout,
    HomeRoutineInfo,
    HomeRoutineProgress,
    HomeRoutineEmpty,
    HomeRoutineTask,
    HomeRoutineHello
  },
  async created() {
    const routine = (await UserService.getActualRoutine()).response;
    if (routine == null) {
      return
    }
    this.routine = routine;
  },
})
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

.routine-section {
  display: flex;
  flex-direction: column;
  gap: 16px;
  align-items: center;
  width: 100%;
  height: 100%;
}
</style>
