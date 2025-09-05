<template>
  <TheLayout>

    <div class="content">
      <div class="title">
        <h2> Suas Rotinas </h2>
        <p><small>Gerencie e organize suas rotinas diárias</small></p>
      </div>

      <button @click="selectRoutine" class="card" v-for="routine in getRoutines()" :key="routine.id">

        <div class="card-header">
          <div class="card-title">
            <p>
              {{ routine.name }}
            </p>
            <button class="edit-routine" @click.stop="editRoutine(routine.id)">
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

      <button class="card create-routine" @click="createRoutine()">
        <FontAwesomeIcon icon="plus" class="plus-icon" />
        <p>
          Criar Nova Rotina
        </p>
        <p>
          <small>
            Monte uma rotina personalizada para seus objetivos
          </small>
        </p>
      </button>
    </div>

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
    selectRoutine() {
      this.modalStore.openAndSetModal("confirmSelectedRoutine")
    },
    getRoutines() {
      return this.routineStore.routines;
    },
    editRoutine(id: number) {
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
.content {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
}

.title {
  display: flex;
  flex-direction: column;
  align-items: start;
  width: 100%;
  gap: 8px;
  padding: 8px 0;
}

.card-title {
  display: flex;
  justify-content: space-between;
  width: 100%;
}

.plus-icon {
  background-color: rgb(216, 216, 216);
  padding: 8px;
  height: 15px;
  width: 15px;
  border-radius: 999px;
}

.create-routine:hover {
  border: solid 2px oklch(.809 .105 251.813);
  background-color: oklch(.97 .014 254.604);
}

.create-routine {
  align-items: center !important;
  border-style: dashed !important;
  border: solid 2px rgb(216, 216, 216);
  box-shadow: none;
}

.card {
  display: flex;
  flex-direction: column;
  align-items: start;
  padding: 16px;
  border-radius: 8px;
  gap: 8px;
}

.card:hover {
  transition: all .5s;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.5);
}

.card-header {
  display: flex;
  flex-direction: column;
  align-items: start;
  justify-content: center;
  width: 100%;
}
</style>
