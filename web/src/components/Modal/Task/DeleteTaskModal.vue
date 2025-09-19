<template>

  <TheModalConfirm title="Deseja apagar essa tarefa?" @confirm="handleRmvTask" @cancel="handleCancelRmv"
    message="Tem certeza que deseja excluir esta tarefa? Essa ação não pode ser desfeita." />

</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { useModalStore } from '@/stores/modals';
import { NotificationEnum, NotificationType, useNotificationStore } from '@/stores/notification';
import { Task, TaskService } from '@/services/taskService';
import { useRoutineStore } from '@/stores/Routine';
import TheModalConfirm from '../TheModalConfirm.vue';

export default defineComponent({
  name: "DeleteTaskModal",
  components: { TheModalConfirm },
  data() {
    return {
      tarefa: "",
      modalStore: useModalStore(),
      notificationStore: useNotificationStore(),
      routineStore: useRoutineStore()
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
      this.modalStore.infos.rmvTask = new Task(0, "", 0, false);
    },
    async handleRmvTask() {
      try {
        const { message } = await TaskService.deleteTask(this.modalStore.infos.rmvTask.id);
        this.routineStore.rmvTaskFromSelectedRoutine(this.modalStore.infos.rmvTask.id);
        this.showNotification({ title: message, time: 2000 })
        this.modalStore.closeModal()
      } catch (error) {
        this.showNotification({ title: "Aconteceu um Erro durante a remoção da tarefa", time: 2000, type: NotificationEnum.error })
        this.modalStore.closeModal()
      }
    }
  }
})

</script>