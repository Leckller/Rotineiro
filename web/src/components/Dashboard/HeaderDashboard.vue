<template>
  <div class="header-dashboard">
    <h2>Dashboard</h2>
    <button class="" @click="selectDate">
      <FontAwesomeIcon icon="history" />
      Selecionar Data
    </button>
  </div>
  <p>Acompanhe seu progresso e estatísticas</p>
</template>

<script lang="ts">
import { useModalStore } from "@/stores/modals";
import { defineComponent } from "vue";

export default defineComponent({
  name: "HeaderDashboard",
  data() {
    return {
      useModal: useModalStore(),
    };
  },
  methods: {
    selectDate() {
      this.useModal.openAndSetModal("selectDate");
    },
  },
});
</script>

<style scoped>
.header-dashboard {
  display: flex;
  width: 100%;
  max-width: 320px;
  justify-content: space-between;
  padding: 0 16px;
}
</style>