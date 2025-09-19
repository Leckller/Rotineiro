<template>
  <TheModalConfirm
    title="Deseja forçar a finalização da rotina?"
    @confirm="forceFinish"
    message="Você ainda não concluiu todas as tarefas, tem certeza que deseja finalizar a rotina?"
  />
</template>

<script lang="ts">
import { defineComponent } from "vue";
import TheModalConfirm from "../TheModalConfirm.vue";
import { NotificationEnum, useNotificationStore } from "@/stores/notification";
import { NotificationType } from "@/stores/notification";
import { RoutineService } from "@/services/routineService";

export default defineComponent({
  name: "ForceFinishRoutine",
  components: { TheModalConfirm },
  data() {
    return {
      notifcationStore: useNotificationStore(),
    };
  },
  methods: {
    showNotification(notification: NotificationType) {
      this.notifcationStore.createNotification(notification);
    },
    async forceFinish() {
      try {
        await RoutineService.finishRoutine();
        this.showNotification({
          title: "Rotina finalizada com sucesso!",
          time: 2000,
        });
        window.location.reload();
      } catch (error: any) {
        this.showNotification({
          title: error.response.data.message,
          time: 3000,
          type: NotificationEnum.error,
        });
      }
    },
  },
});
</script>