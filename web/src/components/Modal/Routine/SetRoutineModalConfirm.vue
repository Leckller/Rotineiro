<template>

  <TheModalConfirm title="Deseja utilizar essa rotina?" @confirm="setActualRoutine"
    message="Tem certeza que deseja utilizar esta tarefa? Caso tenha uma em andamento ela será finalizada." />

</template>

<script lang="ts">
import { defineComponent } from 'vue';
import TheModalConfirm from '../TheModalConfirm.vue';
import { NotificationEnum, useNotificationStore } from '@/stores/notification';
import { NotificationType } from '@/stores/notification';
import { useModalStore } from '@/stores/modals';


export default defineComponent({
  name: 'setRoutineModalConfirm',
  components: { TheModalConfirm },
  data() {
    return {
      notifcationStore: useNotificationStore(),
      modalStore: useModalStore()
    }
  },
  methods: {
    showNotification(notification: NotificationType) {
      this.notifcationStore.createNotification(notification)
    },
    async setActualRoutine() {
      try {
        this.showNotification({title: "Rotina selecionada com sucesso!", time: 2000})
      } catch (error) {
        this.showNotification({title: "Ocorreu um erro durante a seleção da rotina.", time: 3000, type: NotificationEnum.error})
      }
    }
  }
})

</script>