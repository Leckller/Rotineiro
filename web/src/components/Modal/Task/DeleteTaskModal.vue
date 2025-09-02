<template>

  <TheModal title="Editar Tarefa" modalHeight="150px">

    <p>
      Tem certeza que Deseja apagar a tarefa "<strong>{{ tarefa }}</strong>"
    </p>

    <div>
      <button @click="handleCancelRmv">
        Cancelar
      </button>
      <button @click="handleRmvTask">
        Confirmar
      </button>
    </div>

  </TheModal>

</template>

<script lang="ts">
import { defineComponent } from 'vue';
import TheModal from '../TheModal.vue';
import { useModalStore } from '@/stores/modals';
import { NotificationEnum, NotificationType, useNotificationStore } from '@/stores/notification';
import { Task, TaskService } from '@/services/taskService';

export default defineComponent({
  name: "DeleteTaskModal",
  components: { TheModal },
  data() {
    return {
      tarefa: "",
      modalStore: useModalStore(),
      notificationStore: useNotificationStore()
    }
  },
  created() {
    this.tarefa = this.modalStore.infos.rmvTask.name;
  },
  methods: {
    showNotification(notification: NotificationType) {
      this.notificationStore.createNotification(notification)
    },
    handleCancelRmv() {
      this.modalStore.infos.rmvTask = new Task(0, "", 0);
      this.modalStore.closeModal()
    },
    async handleRmvTask() {
      try {
        const { message } = await TaskService.deleteTask(this.modalStore.infos.rmvTask.id);
        this.showNotification({ title: message, time: 2000 })
      } catch (error) {
        this.showNotification({ title: "Aconteceu um Erro durante a remoção da tarefa", time: 2000, type: NotificationEnum.error })
      }
    }
  }
})

</script>