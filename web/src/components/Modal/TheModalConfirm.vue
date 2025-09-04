<template>
  <div class="overlay" @click.self="close">
    <div class="modal" role="dialog" aria-modal="true">
      <div class="modal-header">
        <h2>{{ title }}</h2>
        <button @click="close" aria-label="Fechar" class="fechar">
          <FontAwesomeIcon icon="xmark" />
        </button>
      </div>

      <div class="modal-content">
        <p v-if="message">{{ message }}</p>
      </div>

      <div class="modal-actions">
        <button class="btn cancel" @click="close">Cancelar</button>
        <button class="btn confirm" @click="confirm">Confirmar</button>
      </div>
    </div>
  </div>
</template>

<script>
import { useModalStore } from "@/stores/modals";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

export default {
  name: "ConfirmModal",
  data() {
    return {
      modalStore: useModalStore()
    }
  },
  props: {
    title: { type: String, required: true },
    message: { type: String, default: "" },
  },
  emits: ["confirm"],
  methods: {
    close() {
      this.modalStore.closeModal();
    },
    confirm() {
      this.$emit("confirm");
      this.close();
    },
  },
  components: {
    FontAwesomeIcon,
  },
};
</script>

<style scoped>
/* Overlay */
.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1001;
}

/* Modal */
.modal {
  background-color: white;
  display: flex;
  flex-direction: column;
  position: relative;
  padding: 16px;
  border-radius: 16px;
  max-width: 400px;
  width: 100%;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.modal-header button {
  background-color: transparent;
  border: none;
  outline: none;
  font-size: x-large;
  cursor: pointer;
}

/* Conteúdo */
.modal-content {
  margin: 16px 0;
}

/* Botões */
.modal-actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}

.btn {
  padding: 8px 16px;
  border-radius: 8px;
  border: none;
  cursor: pointer;
  font-size: medium;
}

.btn.cancel {
  background: #eee;
}

.btn.confirm {
  background: #4caf50;
  color: white;
}
</style>
