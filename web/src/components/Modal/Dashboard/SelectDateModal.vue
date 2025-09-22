<template>
  <TheModal title="Selecione uma data para exibição dos dados">
    <div class="form-group">
      <TheInput label="Inicio" :min="3" v-model="start" type="date" class="input-date" />
    </div>
    <div class="form-group">
      <TheInput label="Fim" :min="5" v-model="end" type="date" class="input-date" />
    </div>

    <div class="actions">
      <button class="btn btn-cancel" @click="handleCancelChanges">Cancelar</button>
      <button class="btn btn-confirm" @click="handleSaveChanges">Confirmar Alterações</button>
    </div>
  </TheModal>
</template>


<script lang="ts">
import { defineComponent } from "vue";
import TheModal from "../TheModal.vue";
import TheInput from "@/components/TheInput.vue";
import { useModalStore } from "@/stores/modals";
import { NotificationType, useNotificationStore } from "@/stores/notification";
import { useDateStore } from "@/stores/date";

export default defineComponent({
  name: "EditTaskModal",
  components: { TheModal, TheInput },
  data() {
    return {
      start: this.formatDate(new Date()),
      end: this.formatDate(new Date()),
      useDate: useDateStore(),
      modalStore: useModalStore(),
      notificationStore: useNotificationStore(),
    };
  },
  created() {
    this.start = this.formatDate(this.useDate.startDate);
    this.end = this.formatDate(this.useDate.endDate);
  },
  methods: {
    formatDate(date: Date) {
      if (!(date instanceof Date)) return "";
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, "0");
      const day = String(date.getDate()).padStart(2, "0");
      return `${year}-${month}-${day}`; // formato aceito pelo input date
    },
    showNotification(notification: NotificationType) {
      this.notificationStore.createNotification(notification);
    },
    handleCancelChanges() {
      this.modalStore.closeModal();
    },
    async handleSaveChanges() {
      this.useDate.setEndDate(new Date(this.end));
      this.useDate.setStartDate(new Date(this.start));
      this.showNotification({
        title: "Data alterada com sucesso!",
        time: 1500,
      });
      this.modalStore.closeModal();
    },
  },
});
</script>

<style scoped>
.form-group {
  display: flex;
  flex-direction: column;
  gap: 6px;
  margin-bottom: 12px;
}

.input-date {
  width: 100%;
  padding: 8px 10px;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 14px;
  outline: none;
  transition: border 0.2s;
}

.input-date:focus {
  border-color: #007bff;
}

.actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.btn {
  padding: 8px 14px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 14px;
  transition: background 0.2s;
}

/* Cancelar */
.btn-cancel {
  background: #f5f5f5;
  color: #333;
}

.btn-cancel:hover {
  background: #e0e0e0;
}

/* Confirmar */
.btn-confirm {
  background: #007bff;
  color: #fff;
}

.btn-confirm:hover {
  background: #0056b3;
}
</style>
