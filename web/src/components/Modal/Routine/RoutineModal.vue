<template>

  <TheModal title="Criar Rotina">

    <form @submit="createRotuine($event)">

      <TheInput label="Nome da Rotina" :min="3" v-model="name" />
      <TheInput label="Descrição" :min="5" v-model="description" />

      <TheSelect v-model="selectedOption"
        :options="[{ name: 'Baixa', value: 1 }, { name: 'Média', value: 2 }, { name: 'Alta', value: 2 }]"
        label="Escolha uma prioridade:" />

      <button type="submit" class="confirm">
        Criar Rotina
      </button>

    </form>

  </TheModal>


</template>

<script lang="ts">
import { defineComponent } from 'vue';
import TheModal from '../TheModal.vue';
import TheInput from '@/components/TheInput.vue';
import TheSelect from '@/components/Forms/TheSelect.vue';
import { PriorityEnum, RoutineService } from '@/services/routineService';
import { useModalStore } from '@/stores/modals';

export default defineComponent({
  name: "RoutineModal",
  data() {
    return {
      name: "",
      selectedOption: 1,
      description: "",
      modalStore: useModalStore()
    }
  },
  components: { TheModal, TheInput, TheSelect },
  methods: {
    async createRotuine(e: Event) {
      e.preventDefault();

      try {
        let priority = PriorityEnum.LOW
        switch (this.selectedOption) {
          case 2:
            priority = PriorityEnum.MEDIUM
            break;
          case 3:
            priority = PriorityEnum.HIGH
            break;
          default:
            priority = PriorityEnum.LOW
            break;
        }

        await RoutineService.createRoutine({ priority, name: this.name, description: this.description });

        this.modalStore.closeModal()

      } catch {

      }

    }
  }
})

</script>
