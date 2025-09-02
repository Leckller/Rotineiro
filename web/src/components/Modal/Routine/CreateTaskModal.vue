<template>

  <TheModal :title="`${createTaskModal ? 'Criar Tarefa' : 'Adicionar Tarefas'}`">

    <div class="select-form">
      <button :style="{ filter: createTaskModal ? '' : 'opacity(0.7)' }" @click="createTaskModal = true">
        Criar
      </button>
      <button :style="{ filter: createTaskModal ? 'opacity(0.7)' : '' }" @click="createTaskModal = false">
        Adicionar
      </button>
    </div>

    <form v-if="createTaskModal" @submit="createTask($event)">

      <TheInput label="Nome da Tarefa" :min="3" v-model="name" />
      <TheInput label="Estimativa da Tarefa ( Minutos )" :min="3" type="number" v-model="estimate" />

      <button type="submit" class="confirm">
        Criar Tarefa
      </button>

    </form>

    <form v-if="!createTaskModal" @submit="addTasks($event)">
      <section class="suggested-tasks" v-if="tasks.length > 0">
        <h3>Tarefas Já Criadas</h3>

        <div class="cards">
          <article class="card" v-for="task in tasks" :key="task.id">
            <div class="card-info">
              <p>
                <strong>{{ task.name }}</strong>
              </p>
              <p>
                {{ task.estimate }} minutos
              </p>
            </div>
            <button class="card-btn blue" v-if="!selectedTasks.some(t => t == task.id)" @click="handleAddTask(task)">
              <FontAwesomeIcon icon="add" />
            </button>
            <button class="card-btn red" v-if="selectedTasks.some(t => t == task.id)" @click="handleRmvTask(task)">
              <FontAwesomeIcon icon="trash" />
            </button>
          </article>
        </div>

      </section>

      <button type="submit" class="confirm">
        Adicionar Tarefas
      </button>
    </form>

    <div class="space" />
    <div class="space" />
  </TheModal>


</template>

<script lang="ts">
import { defineComponent } from 'vue';
import TheModal from '../TheModal.vue';
import TheInput from '@/components/TheInput.vue';
import { useModalStore } from '@/stores/modals';
import { TaskEntity, TaskService } from '@/services/taskService';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

export default defineComponent({
  name: "RoutineModal",
  data() {
    return {
      createTaskModal: true,
      tasks: [] as TaskEntity[],
      selectedTasks: [] as number[],
      name: "",
      estimate: "",
      modalStore: useModalStore()
    }
  },
  components: { TheModal, TheInput, FontAwesomeIcon },
  async created() {
    try {
      const tasks = (await TaskService.getAvailableTasks(+this.$route.params.id)).response;
      this.tasks = tasks;
    } catch (error) {

    }
  },
  methods: {
    handleAddTask(task: TaskEntity) {
      this.selectedTasks.push(task.id);
    },
    handleRmvTask(task: TaskEntity) {
      this.selectedTasks = this.selectedTasks.filter((t) => t != task.id);
    },
    async addTasks(e: Event) {
      e.preventDefault();

      try {

        await TaskService.assingTasksToRoutine({ routine_id: +this.$route.params.id, tasks: this.selectedTasks })

      } catch {

      }
    },
    async createTask(e: Event) {
      e.preventDefault();

      try {

        await TaskService.createTask({ routine_id: +this.$route.params.id, estimate: +this.estimate, name: this.name });

      } catch {

      }

    }
  }
})

</script>

<style>
.select-form {
  display: flex;
  width: 100%;
  justify-content: space-around;
}

.suggested-tasks {
  display: flex;
  flex-direction: column;
  align-items: start;
  justify-content: center;
  padding: 16px;
}

.card {
  display: flex;
  gap: 16px;
  align-items: center;
  justify-content: center;
  padding: 16px;
  border-radius: 16px;
  border: solid 1px black;
  font-size: small;
  width: 100%;
  min-width: 200px;
  max-width: 300px;
}

.card-info {
  display: flex;
  gap: 8px;
  flex-direction: column;
}

.cards {
  display: flex;
  width: 100%;
  padding: 16px 0;
  gap: 8px;
  overflow-x: scroll;
}

.card-btn {
  border: solid 1px black;
  border-radius: 8px;
  padding: 8px;
}

.red {
  background-color: red;
}

.blue {
  background-color: blue;
  color: white;
}
</style>