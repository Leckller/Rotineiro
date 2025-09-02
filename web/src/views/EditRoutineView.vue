<template>

  <TheLayout>

    <div class="content">
      <section class="routine-header">
        <button>
          <FontAwesomeIcon icon="arrow-left" />
        </button>
        <div class="routine-info">
          <h2>{{ routine.name }}</h2>
          <p>{{ routine.description }}</p>
        </div>
        <button>
          <FontAwesomeIcon icon="pencil" />
        </button>
      </section>

      <section class="card-info">
        <article class="card">
          <p>
            <FontAwesomeIcon icon="clock" />
            Duração Total
          </p>
          <p>
            {{routine.tasks.reduce((pv, curr) => {
              return pv + curr.estimate
            }, 0)}}
          </p>
        </article>
        <article class="card">
          <p>
            <FontAwesomeIcon icon="bullseye" />
            Atividades
          </p>
          <p>
            {{ routine.tasks.length }}
          </p>
        </article>
      </section>

      <section class="tasks">

        <div class="task-header">
          <p>
            Atividades da Rotina
          </p>
          <button @click="createTask">
            + Adicionar
          </button>
        </div>

        <article v-for="task in routine.tasks" :key="task.id" class="card-task">

          <div>
            <p>{{ task.name }}</p>
            <button>
              <FontAwesomeIcon icon="pencil" />
            </button>
            <button>
              <FontAwesomeIcon icon="trash" />
            </button>
          </div>

        </article>

      </section>
    </div>

  </TheLayout>

</template>

<script lang="ts">
import TheLayout from '@/components/TheLayout.vue';
import { RoutineEntity, RoutineService } from '@/services/routineService';
import { useModalStore } from '@/stores/modals';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { defineComponent } from 'vue';


export default defineComponent({
  name: "EditRoutineView",
  components: {
    TheLayout, FontAwesomeIcon
  },
  data() {
    return {
      modalStore: useModalStore(),
      routine: {
        description: "",
        id: 0,
        name: "",
        priority: 0,
        tasks: []
      } as RoutineEntity
    }
  },
  async created() {
    this.getRoutine()
  },
  methods: {
    createTask() {
      this.modalStore.openAndSetModal("createTask")
    },
    async getRoutine() {
      try {
        const id = this.$route.params.id as string;
        this.routine = (await RoutineService.getRoutineById(+id)).response;
      } catch (error) {
      }
    }
  }
})

</script>

<style scoped>
.content {
  display: flex;
  font-size: small;
  flex-direction: column;
  gap: 8px;
  align-items: center;
  height: 100%;
  width: 100%;
  padding: 16px;
}

.routine-header {
  display: flex;
  gap: 16px;
  text-align: center;
  width: 100%;
  justify-content: space-around;
}

.routine-info {
  display: flex;
  flex-direction: column;
  gap: 16px;
  align-items: center;
}

.card-info {
  display: flex;
  width: 100%;
  align-items: center;
  justify-content: center;
  gap: 16px;
}

.card {
  display: flex;
  flex-direction: column;
  gap: 8px;
  padding: 16px;
  border-radius: 8px;
  border: solid 1px black;
  width: 100%;
  max-width: 160px;
}

.tasks {
  display: flex;
  flex-direction: column;
  gap: 16px;
  width: 100%;
}

.task-header {
  display: flex;
  width: 100%;
  align-items: center;
  justify-content: space-around;
}

</style>