<template>

  <TheHeader v-if="($route.path != '/' && $route.path != '/register')" />

  <main class="main">

    <!-- Routines -->
    <EditRoutineModal v-if="modalStore.open && getActiveModal() == 'editRoutine'" />
    <CreateRoutineModal v-if="modalStore.open && getActiveModal() == 'createRoutine'" />
    <SetRoutineModalConfirm v-if="modalStore.open && getActiveModal() == 'confirmSelectedRoutine'" />
    <ForceFinishRoutine v-if="modalStore.open && getActiveModal() == 'forceFinishRoutine'" />

    <!-- Tasks  -->
    <CreateTaskModal v-if="modalStore.open && getActiveModal() == 'createTask'" />
    <EditTaskModal v-if="modalStore.open && getActiveModal() == 'editTask'" />
    <DeleteTaskModal v-if="modalStore.open && getActiveModal() == 'rmvTask'" />

    <!-- Dashboard -->
    <SelectDateModal v-if="modalStore.open && getActiveModal() == 'selectDate'" />

    <TheNotification />

    <slot />
  </main>

  <TheFooter v-if="($route.path != '/' && $route.path != '/register')" />

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
import EditRoutineModal from './Modal/Routine/EditRoutineModal.vue';
import SetRoutineModalConfirm from './Modal/Routine/SetRoutineModalConfirm.vue';
import ForceFinishRoutine from './Modal/Routine/ForceFinishRoutine.vue';
import SelectDateModal from './Modal/Dashboard/SelectDateModal.vue';


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
    EditRoutineModal,
    DeleteTaskModal,
    CreateRoutineModal,
    ForceFinishRoutine,
    SetRoutineModalConfirm,
    SelectDateModal,
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