<template>
  <TheModal title="Criar Rotina">
    <form class="criar-rotina" @submit="createRotuine($event)">
      <div class="inputs">
        <TheInput label="Nome da Rotina" :min="3" v-model="name" />
        <TheInput label="Descrição" :min="5" v-model="description" />

        <TheSelect
          v-model="selectedOption"
          :options="[
            { name: 'Baixa', value: 1 },
            { name: 'Média', value: 2 },
            { name: 'Alta', value: 2 },
          ]"
          label="Escolha uma prioridade:"
        />
      </div>
      <button
        :disabled="name.length < 3 && description.length < 5"
        type="submit"
        class="confirm"
      >
        Criar Rotina
      </button>
    </form>
  </TheModal>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import TheModal from "../TheModal.vue";
import TheInput from "@/components/TheInput.vue";
import TheSelect from "@/components/Forms/TheSelect.vue";
import { PriorityEnum, RoutineService } from "@/services/routineService";
import { useModalStore } from "@/stores/modals";
import { useRoutineStore } from "@/stores/Routine";

export default defineComponent({
  name: "RoutineModal",
  data() {
    return {
      name: "",
      selectedOption: 1,
      description: "",
      modalStore: useModalStore(),
      routineStore: useRoutineStore(),
    };
  },
  components: { TheModal, TheInput, TheSelect },
  methods: {
    async createRotuine(e: Event) {
      e.preventDefault();

      try {
        let priority = PriorityEnum.LOW;
        switch (this.selectedOption) {
          case 2:
            priority = PriorityEnum.MEDIUM;
            break;
          case 3:
            priority = PriorityEnum.HIGH;
            break;
          default:
            priority = PriorityEnum.LOW;
            break;
        }

        const routine = await RoutineService.createRoutine({
          priority,
          name: this.name,
          description: this.description,
        });
        this.routineStore.addRoutine(routine.response);

        this.modalStore.closeModal();
      } catch {}
    },
  },
});
</script>

<style scoped>
.criar-rotina {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  height: 100%;
}

.inputs {
  display: flex;
  flex-direction: column;
  gap: 16px;
}
.confirm {
  width: 100%;
  background-color: #1a65fe;
  padding: 8px;
  color: white;
  border-radius: 8px;
}
.confirm:disabled {
  cursor: not-allowed;
  background-color: #8bb0fe;
}
</style>