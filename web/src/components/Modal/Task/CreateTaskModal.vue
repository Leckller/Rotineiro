<template>
  <TheModal
    :title="`${createTaskModal ? 'Criar Tarefa' : 'Adicionar Tarefas'}`"
  >
    <div class="select-form">
      <button
        :style="{
          width: '100%',
          padding: '8px',
          filter: createTaskModal ? '' : 'opacity(0.7)',
          borderBottom: createTaskModal ? 'solid 1px #1A65FE' : '',
        }"
        @click="createTaskModal = true"
      >
        Criar
      </button>
      <button
        :style="{
          width: '100%',
          padding: '8px',
          filter: createTaskModal ? 'opacity(0.7)' : '',
          borderBottom: createTaskModal ? '' : 'solid 1px #1A65FE',
        }"
        @click="createTaskModal = false"
      >
        Adicionar
      </button>
    </div>

    <form
      class="criar-rotina"
      v-if="createTaskModal"
      @submit="createTask($event)"
    >
      <div class="inputs">
        <TheInput label="Nome da Tarefa" :min="3" v-model="name" />
        <TheInput
          label="Estimativa da Tarefa ( Minutos )"
          :min="3"
          type="number"
          v-model="estimate"
        />
      </div>

      <button type="submit" :disabled="(name.length < 3 && estimate.length < 3)" class="confirm">Criar Tarefa</button>
    </form>

    <form class="adicionar-tarefa" v-if="!createTaskModal" @submit="addTasks($event)">
      <p v-if="tasks.length <= 0">Nenhuma tarefa disponível</p>
      <section class="suggested-tasks" v-if="tasks.length > 0">
        <h3>Tarefas Já Criadas</h3>

        <div class="card-list">
          <article class="card-add" v-for="task in tasks" :key="task.id">
            <div class="card-info">
              <p>
                <strong>{{ task.name }}</strong>
              </p>
              <p>{{ task.estimate }} minutos</p>
            </div>
            <button
              class="card-btn blue"
              v-if="!selectedTasks.some((t) => t == task.id)"
              @click="handleAddTask(task)"
            >
              <FontAwesomeIcon icon="add" />
            </button>
            <button
              class="card-btn red"
              v-if="selectedTasks.some((t) => t == task.id)"
              @click="handleRmvTask(task)"
            >
              <FontAwesomeIcon icon="trash" />
            </button>
          </article>
        </div>
      </section>

      <button v-if="tasks.length > 0" :disabled="!(selectedTasks.length > 0)" type="submit" class="confirm">
        Adicionar Tarefas
      </button>
    </form>

    <div class="space" />
    <div class="space" />
  </TheModal>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import TheModal from "../TheModal.vue";
import TheInput from "@/components/TheInput.vue";
import { useModalStore } from "@/stores/modals";
import { TaskEntity, TaskService } from "@/services/taskService";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import {
  NotificationEnum,
  NotificationType,
  useNotificationStore,
} from "@/stores/notification";
import { RoutineService } from "@/services/routineService";
import { useRoutineStore } from "@/stores/Routine";

export default defineComponent({
  name: "RoutineModal",
  data() {
    return {
      createTaskModal: true,
      tasks: [] as TaskEntity[],
      selectedTasks: [] as number[],
      name: "",
      estimate: "",
      modalStore: useModalStore(),
      notificationStore: useNotificationStore(),
      routineStore: useRoutineStore(),
    };
  },
  components: { TheModal, TheInput, FontAwesomeIcon },
  async created() {
    try {
      const tasks = (
        await TaskService.getAvailableTasks(+this.$route.params.id)
      ).response;
      this.tasks = tasks;
    } catch (error) {}
  },
  methods: {
    showNotification(notification: NotificationType) {
      this.notificationStore.createNotification(notification);
    },
    handleAddTask(task: TaskEntity) {
      if (!this.selectedTasks.includes(task.id)) {
        this.selectedTasks.push(task.id);
      }
    },
    handleRmvTask(task: TaskEntity) {
      this.selectedTasks = this.selectedTasks.filter((t) => t != task.id);
    },
    async addTasks(e: Event) {
      e.preventDefault();

      try {
        await RoutineService.assingTasksToRoutine(
          { tasks: this.selectedTasks },
          +this.$route.params.id
        );
        this.showNotification({
          title: "Tarefas Adicionadas Com Sucesso!",
          time: 2000,
        });

        const attRoutine = await RoutineService.getRoutineById(
          +this.$route.params.id
        );
        this.routineStore.selectRoutine(attRoutine.response);

        this.modalStore.closeModal();
      } catch (error: any) {
        this.showNotification({
          title: error.response.data.message,
          time: 2000,
          type: NotificationEnum.error,
        });
        this.modalStore.closeModal();
      }
    },
    async createTask(e: Event) {
      e.preventDefault();

      try {
        const task = await TaskService.createTask({
          routine_id: +this.$route.params.id,
          estimate: +this.estimate,
          name: this.name,
        });

        this.routineStore.addTaskToSelectedRoutine(task.response);

        this.showNotification({ title: task.message, time: 2000 });
        this.modalStore.closeModal();
      } catch (error) {
        console.log(error);
        this.showNotification({
          title: "Erro durante a criação da tarefa.",
          time: 2000,
          type: NotificationEnum.error,
        });
        this.modalStore.closeModal();
      }
    },
  },
});
</script>

<style>
.inputs {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.confirm {
  width: 100%;
  background-color: #1a65fe;
  padding: 8px;
  color: white;
  border-radius: 8px;
}
.confirm:disabled {
  cursor: not-allowed;
  background-color: #8bb0fe;
}

.criar-rotina, .adicionar-tarefa {
  display: flex;
  padding: 24px 8px;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  height: 100%;
}

.select-form {
  display: flex;
  width: 100%;
  justify-content: space-around;
}

.suggested-tasks {
  display: flex;
  flex-direction: column;
  height: 100%;
  width: 100%;
  align-items: center;
  padding: 16px;
}

.card-add {
  display: flex;
  gap: 16px;
  align-items: center;
  justify-content: space-between;
  padding: 16px;
  border-radius: 16px;
  border: solid 1px black;
  font-size: small;
  width: 100%;
  max-width: 150px;
}

.card-info {
  display: flex;
  gap: 8px;
  text-align: start;
  flex-direction: column;
}

.card-list {
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