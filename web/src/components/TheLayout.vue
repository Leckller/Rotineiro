<template>

  <TheHeader />

  <main class="main">

    <RoutineModal v-if="modalStore.open && getActiveModal() == 'createRoutine'" />
    <CreateTaskModal v-if="modalStore.open && getActiveModal() == 'createTask'" />

    <TheNotification />

    <slot />
  </main>

  <TheFooter />

</template>

<script lang="ts">
import { defineComponent } from 'vue';
import TheHeader from './TheHeader.vue';
import TheFooter from './TheFooter.vue';
import TheNotification from './Modal/TheNotification.vue';
import RoutineModal from './Modal/Routine/RoutineModal.vue';
import { useModalStore } from '@/stores/modals';
import CreateTaskModal from './Modal/Routine/CreateTaskModal.vue';


export default defineComponent({
  name: "TheLayout",
  data() {
    return {
      modalStore: useModalStore()
    }
  },
  components: {
    TheHeader,
    TheNotification,
    TheFooter,
    CreateTaskModal,
    RoutineModal
  },
  methods: {
    getActiveModal() {
      return this.modalStore.actualModalName
    }
  }
})

</script>

<style setup>
.main {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
  gap: 8px;
}
</style>