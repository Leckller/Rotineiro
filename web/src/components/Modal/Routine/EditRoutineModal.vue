<template>
  <TheModal title="Editar Rotina">
    <form class="editar-rotina" @submit="editRoutine($event)">
      <div class="input">
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
        type="submit"
        :disabled="name.length < 3 && description.length < 5"
        class="confirm"
      >
        Salvar Alterações
      </button>
    </form>
  </TheModal>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import TheModal from "../TheModal.vue";
import TheInput from "@/components/TheInput.vue";
import TheSelect from "@/components/Forms/TheSelect.vue";
import { useModalStore } from "@/stores/modals";
import { useRoutineStore } from "@/stores/Routine";
import { PriorityEnum, RoutineService } from "@/services/routineService";

export default defineComponent({
  name: "EditRoutineModal",
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
  created() {
    this.name = this.routineStore.selectedRoutine.name;
    this.description = this.routineStore.selectedRoutine.description;
    let priority = 1;
    switch (this.routineStore.selectedRoutine.priority) {
      case PriorityEnum.MEDIUM:
        priority = 2;
        break;
      case PriorityEnum.HIGH:
        priority = 3;
        break;
      default:
        priority = 1;
        break;
    }
    this.selectedOption = priority;
  },
  methods: {
    async editRoutine(e: Event) {
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

        const routine = await RoutineService.editRoutine(
          { priority, name: this.name, description: this.description },
          +this.$route.params.id
        );
        this.routineStore.editRoutine(routine.response);

        this.modalStore.closeModal();
      } catch {}
    },
  },
});
</script>

<style scoped>
.editar-rotina {
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