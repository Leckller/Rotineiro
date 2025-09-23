<template>
  <TheModal title="Editar Tarefa">
    <div class="inputs">
      <TheInput label="Nome" :min="3" v-model="name" />
      <TheInput label="Nome" :min="5" v-model="estimate" type="Number" />
    </div>
    <div class="opcoes">
      <button class="cancel" @click="handleCancelChanges">Cancelar</button>
      <button class="confirm" :disabled="(name.length < 3 && estimate.length < 5)" @click="handleSaveChanges">Confirmar Alterações</button>
    </div>
  </TheModal>
</template>

<script lang="ts">

import { defineComponent } from "vue";
import TheModal from "../TheModal.vue";
import TheInput from "@/components/TheInput.vue";
import { useModalStore } from "@/stores/modals";
import { TaskService } from "@/services/taskService";
import {
  NotificationEnum,
  NotificationType,
  useNotificationStore,
} from "@/stores/notification";

export default defineComponent({
  name: "EditTaskModal",
  components: { TheModal, TheInput },
  data() {
    return {
      name: "",
      estimate: "",
      modalStore: useModalStore(),
      notificationStore: useNotificationStore(),
    };
  },
  created() {
    this.name = this.modalStore.infos.editTask.name;
    this.estimate = this.modalStore.infos.editTask.estimate.toString();
  },
  methods: {
    showNotification(notification: NotificationType) {
      this.notificationStore.createNotification(notification);
    },
    handleCancelChanges() {
      this.name = "";
      this.estimate = "";
      this.modalStore.closeModal();
    },
    async handleSaveChanges() {
      try {
        const taskId = +this.modalStore.infos.editTask.id;

        await TaskService.editTask(taskId, {
          name: this.name,
          estimate: +this.estimate,
        });
        this.showNotification({
          title: "Tarefa editada com sucesso",
          time: 2000,
        });
        this.modalStore.closeModal();
      } catch (error) {
        this.showNotification({
          title: "Ocorreu um Erro durante a edição da tarefa",
          time: 2000,
          type: NotificationEnum.error,
        });
      }
    },
  },
});
</script>

<style scoped>
.inputs {
  display: flex;
  flex-direction: column;
  align-items: center;
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

.cancel {
  width: 50%;
  background-color: #fd3a3a;
  padding: 8px;
  color: white;
  border-radius: 8px;
}

.opcoes{
  display: flex;
  width: 100%;
  justify-content: space-between;
  gap: 8px;
}

</style>