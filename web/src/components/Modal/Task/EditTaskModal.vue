<template>

  <TheModal title="Editar Tarefa">

    <TheInput label="Nome" :min="3" v-model="name" />
    <TheInput label="Nome" :min="5" v-model="estimate" type="Number" />

    <div>
      <button @click="handleCancelChanges">
        Cancelar
      </button>
      <button @click="handleSaveChanges">
        Confirmar Alterações
      </button>
    </div>

  </TheModal>

</template>

<script lang="ts">
import { defineComponent } from 'vue';
import TheModal from '../TheModal.vue';
import TheInput from '@/components/TheInput.vue';
import { useModalStore } from '@/stores/modals';
import { TaskService } from '@/services/taskService';
import { NotificationEnum, NotificationType, useNotificationStore } from '@/stores/notification';

export default defineComponent({
  name: "EditTaskModal",
  components: { TheModal, TheInput },
  data() {
    return {
      name: "",
      estimate: "",
      modalStore: useModalStore(),
      notificationStore: useNotificationStore()
    }
  },
  created() {
    this.name = this.modalStore.infos.editTask.name;
    this.estimate = this.modalStore.infos.editTask.estimate.toString();
  },
  methods: {
    showNotification(notification: NotificationType) {
      this.notificationStore.createNotification(notification)
    },
    handleCancelChanges() {
      this.name = "";
      this.estimate = "";
      this.modalStore.closeModal();
    },
    async handleSaveChanges() {
      try {
        const taskId = +this.modalStore.infos.editTask.id

        await TaskService.editTask(taskId, { name: this.name, estimate: +this.estimate })
        this.showNotification({ title: "Tarefa editada com sucesso", time: 2000 })
        this.modalStore.closeModal();

      } catch (error) {
        this.showNotification({ title: "Ocorreu um Erro durante a edição da tarefa", time: 2000, type: NotificationEnum.error })
      }
    },
  }
})


</script>