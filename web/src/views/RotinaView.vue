<template>
  <TheLayout>
    <button>
      Criar Rotina
    </button>
  </TheLayout>
</template>

<script lang="ts">
import TheLayout from '@/components/TheLayout.vue';
import { useModalStore } from '@/stores/modals';
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'RotinaView',
  data() {
    return {
      modalStore: useModalStore()
    }
  },
  components: {
    TheLayout
  },
  methods: {
    CreateRoutine() {
      this.modalStore.openAndSetModal("createRoutine");
    }
  }
})
</script>
