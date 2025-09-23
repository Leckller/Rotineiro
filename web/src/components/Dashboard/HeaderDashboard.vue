<template>
  <div class="header-dashboard">
    <h2>Dashboard</h2>
    <button class="selecionar-data" @click="selectDate">
      <FontAwesomeIcon icon="history" />
      Selecionar Data
    </button>
  </div>
  <p class="descricao-dashboard">Acompanhe seu progresso e estatísticas</p>
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

.descricao-dashboard {
  display: flex;
  justify-content: start;
  align-items: start;
  max-width: 320px;
  width: 100%;
  padding: 6px 16px;
  font-size: 14px;
}

.selecionar-data {
  padding: 2px 8px;
  border: solid 1px black;
  border-radius: 8px;
}
</style>