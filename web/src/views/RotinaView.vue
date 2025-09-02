<template>
  <TheLayout>

    <button class="card" v-for="routine in getRoutines()" :key="routine.id">

      <div class="card-header">
        <div class="card-title">
          <p>
            {{ routine.name }}
          </p>
          <button @click="editRoutine(routine.id)">
            <FontAwesomeIcon icon="pen-to-square" />
          </button>
        </div>
        <p>
          {{ routine.description }}
        </p>
      </div>

      <p>
        <FontAwesomeIcon icon="clock" />
        {{ routine.tasks.length }} Atividades
      </p>

    </button>

    <button class="card" @click="createRoutine()">
      Criar Rotina
    </button>
  </TheLayout>
</template>

<script lang="ts">
import TheLayout from '@/components/TheLayout.vue';
import router from '@/router';
import { RoutineService } from '@/services/routineService';
import { useModalStore } from '@/stores/modals';
import { useRoutineStore } from '@/stores/Routine';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'RotinaView',
  data() {
    return {
      modalStore: useModalStore(),
      routineStore: useRoutineStore(),
    }
  },
  components: {
    TheLayout,
    FontAwesomeIcon
  },
  async created() {
    this.setRoutines();
  },
  methods: {
    getRoutines() {
      return this.routineStore.routines;
    },
    editRoutine(id: number){
      router.push(`/routine/${id}`)
    },
    createRoutine() {
      this.modalStore.openAndSetModal("createRoutine");
    },
    async setRoutines() {
      const routines = (await RoutineService.getAllRoutines()).response;
      this.routineStore.setRoutines(routines);
    }
  },
})
</script>

<style scoped>
.card-title {
  display: flex;
  justify-content: space-between;
  width: 100%;
}

.card {
  display: flex;
  flex-direction: column;
  padding: 16px;
  border-radius: 8px;
  width: 100%;
  border: solid 1px black;
  max-width: 320px;
  background-color: transparent;
  flex-direction: column;
  align-items: start;
  justify-content: center;
  gap: 24px;
}

.card-header {
  display: flex;
  flex-direction: column;
  align-items: start;
  justify-content: center;
  width: 100%;
}
</style>
