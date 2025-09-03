<template>

  <TheHeader />

  <main class="main">

    <CreateRoutineModal v-if="modalStore.open && getActiveModal() == 'createRoutine'" />
    <CreateTaskModal v-if="modalStore.open && getActiveModal() == 'createTask'" />
    <EditTaskModal v-if="modalStore.open && getActiveModal() == 'editTask'" />
    <DeleteTaskModal v-if="modalStore.open && getActiveModal() == 'rmvTask'" />

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
import { useModalStore } from '@/stores/modals';
import EditTaskModal from './Modal/Task/EditTaskModal.vue';
import DeleteTaskModal from './Modal/Task/DeleteTaskModal.vue';
import CreateTaskModal from './Modal/Task/CreateTaskModal.vue';
import CreateRoutineModal from './Modal/Routine/CreateRoutineModal.vue';


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
    EditTaskModal,
    CreateTaskModal,
    DeleteTaskModal,
    CreateRoutineModal
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
  padding: 25px 0;
  width: 100%;
  height: 100%;
  overflow-y: scroll;
  gap: 8px;
}
</style>