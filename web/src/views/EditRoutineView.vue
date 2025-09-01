<template>

  <TheLayout>

    <section class="routine-header">
      <button>
        <FontAwesomeIcon icon="arrow-left" />
      </button>
      <div>
        <h2>{{ title }}</h2>
        <p>{{ description }}</p>
      </div>
      <button>
        <FontAwesomeIcon icon="pencil" />
      </button>
    </section>

    <section class="info">
      <article>
        <p>
          <FontAwesomeIcon icon="clock" />
          Duração Total
        </p>
      </article>
      <article>
        <p>
          <FontAwesomeIcon icon="bullseye" />
          Atividades {{ atividades }}
        </p>
      </article>
    </section>

    <section class="tasks">

      <article v-for="task in routine.tasks" :key="task.id" class="card-task">

        <div>
          <p>{{ task.title }}</p>
          <button>
            <FontAwesomeIcon icon="pencil" />
          </button>
          <button>
            <FontAwesomeIcon icon="trash" />
          </button>
        </div>

      </article>

    </section>

  </TheLayout>

</template>

<script lang="ts">
import TheLayout from '@/components/TheLayout.vue';
import { RoutineEntity, RoutineService } from '@/services/routineService';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { defineComponent } from 'vue';


export default defineComponent({
  name: "EditRoutineView",
  components: {
    TheLayout, FontAwesomeIcon
  },
  data() {
    return {
      routine: {} as RoutineEntity
    }
  },
  async created() {
    this.getRoutine()
  },
  methods: {
    async getRoutine() {
      const id = 0;
      this.routine = (await RoutineService.getRoutineById(id)).response;
    }
  }
})

</script>