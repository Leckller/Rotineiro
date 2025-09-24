<template>
  <article class="card-task">
    <div class="icon-content">
      <FontAwesomeIcon icon="book" />
    </div>

    <div class="info-content">
      <p>{{ task.name }}</p>
      <p>Descrição</p>
      <p>{{ task.estimate }}m</p>
    </div>

    <div class="btn-content">
      <TheButton variant="tertiary" @click="handleEditTask(task)">
        <FontAwesomeIcon icon="pencil" />
      </TheButton>
      <TheButton variant="tertiary" @click="handleRmvTask(task)">
        <FontAwesomeIcon icon="trash" />
      </TheButton>
    </div>
  </article>
</template>

<script lang="ts">
import { Task, TaskEntity } from "@/services/taskService";
import { useModalStore } from "@/stores/modals";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { defineComponent } from "vue";
import TheButton from "../Forms/TheButton.vue";

export default defineComponent({
  name: "TaskCard",
  components: {
    FontAwesomeIcon,
    TheButton,
  },
  data() {
    return {
      modalStore: useModalStore(),
    };
  },
  props: {
    task: {
      type: Task,
      required: true,
    },
  },
  methods: {
    handleEditTask(task: TaskEntity) {
      this.modalStore.setEditTaskInfos(task);
      this.modalStore.openAndSetModal("editTask");
    },
    handleRmvTask(task: TaskEntity) {
      this.modalStore.setRmvTaskInfos(task);
      this.modalStore.openAndSetModal("rmvTask");
    },
  },
});
</script>

<style scoped>
.card-task {
  width: 100%;
  display: flex;
  flex-direction: row;
  gap: 8px;
  max-width: 320px;
  border: solid black 1px;
  padding: 16px;
  border-radius: 8px;
  justify-content: space-between;
  align-items: start;
}

.info-content p:first-child {
  font-size: var(--medium);
  font-weight: 600;
}

.info-content {
  display: flex;
  flex-direction: column;
  align-items: start;
  flex-grow: 1;
  padding: 0 8px;
  gap: 4px;
}

.sub-info {
  display: flex;
  margin: 4px 0;
  gap: 16px;
  align-items: center;
  justify-content: center;
}

.sub-info p:first-child {
  padding: 4px 8px;
  border-radius: 8px;
  background-color: aqua;
}

.icon-content {
  display: flex;
  background-color: var(--white-gray);
  border-radius: 666px;
  align-items: center;
  font-size: var(--large);
  justify-content: center;
  height: 40px;
  width: 40px;
  padding: 8px;
}

.btn-content {
  display: flex;
  gap: 12px;
}

.btn-content button:first-child {
  color: var(--primary-text-color);
}

.btn-content button:last-child {
  color: var(--medium-red);
}
</style>