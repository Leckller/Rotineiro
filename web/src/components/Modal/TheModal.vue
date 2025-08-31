<template>
  <div class="overlay" @click.self="modalStore.closeModal()">
    <div class="modal" role="dialog" aria-modal="true">
      <div class="modal-header">
        <h2>{{ title }}</h2>
        <button @click="modalStore.closeModal()" aria-label="Fechar">
          <FontAwesomeIcon icon="xmark" />
        </button>
      </div>
      <div class="modal-content">
        <slot />
      </div>
    </div>
  </div>
</template>

<script>
import { useModalStore } from "@/stores/modals";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

export default {
  name: "TheModal",
  data() {
    return {
      modalStore: useModalStore()
    }
  },
  props: {
    title: {
      type: String,
      required: true,
    },
  },
  components: {
    FontAwesomeIcon,
  },
  mounted() {
    document.addEventListener('keydown', (e) => {
      if(e.key == "Escape") {
        this.modalStore.closeModal()
      }
    });
  },
  unmounted() {
    document.removeEventListener('keydown');
  }
};
</script>

<style scoped>
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

.modal {
  background-color: white;
  display: flex;
  flex-direction: column;
  position: relative;
  padding: 8px 16px;
  border-radius: 16px;
  max-width: 500px;
  height: 90%;
  width: 100%;
}

@media (max-width: 500px) {
  .modal {
    border-radius: 0px;
    height: 100%;
  }
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
}

.modal-content {
  padding-top: 12px;
  display: flex;
  overflow: hidden;
  border: none;
  height: 100%;
  justify-content: space-between;
  flex-direction: column;
}
</style>
