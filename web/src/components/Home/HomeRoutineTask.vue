<!-- eslint-disable vue/no-mutating-props -->
<template>
  <article class="task-card" :class="task.completed ? 'task-completed' : ''">
    <input
      :style="{ cursor: 'pointer' }"
      type="checkbox"
      v-model="task.completed"
      @change="onToggleTask"
    />
    <div class="routine-task-info">
      <h4 :style="{ textDecoration: task.completed ? 'line-through' : '' }">
        {{ task.name }}
      </h4>
      <p class="timer">
        <small>{{ task.estimate }} min</small>
        <FontAwesomeIcon v-if="timer > 0" icon="circle" class="circle" />
        <small v-if="timer > 0" class="timer-text" :class="{ tick: run }">
          {{ formattedTimer }}
          <span v-if="run">
            <FontAwesomeIcon icon="circle" class="circle" />
          </span>
        </small>
      </p>
    </div>
    <TheButton
      variant="tertiary"
      v-if="!run"
      :disabled="task.completed"
      @click="startTimer(task)"
    >
      <FontAwesomeIcon
        class="timer-btn"
        :class="{ running: run }"
        icon="play"
      />
    </TheButton>
    <TheButton variant="tertiary" v-if="run" @click="pauseTimer">
      <FontAwesomeIcon
        class="timer-btn"
        :class="{ running: run }"
        icon="pause"
      />
    </TheButton>
    <TheButton
      variant="tertiary"
      :disabled="task.completed"
      v-if="timer > 0"
      @click="resetTimer"
    >
      <FontAwesomeIcon :icon="task.completed ? 'check' : 'stop'" />
    </TheButton>
  </article>
</template>

<script lang="ts">
import { Task, TaskService } from "@/services/taskService";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { defineComponent } from "vue";
import TheButton from "../Forms/TheButton.vue";

export default defineComponent({
  name: "HomeRoutineTask",
  components: { FontAwesomeIcon, TheButton },
  props: {
    task: { required: true, type: Object as () => Task },
  },
  data() {
    return {
      run: false,
      timer: this.task.timer,
      intervalId: undefined as number | undefined,
    };
  },
  computed: {
    formattedTimer(): string {
      const minutes = Math.floor(this.timer / 60)
        .toString()
        .padStart(2, "0");
      const seconds = (this.timer % 60).toString().padStart(2, "0");
      return `${minutes}:${seconds}`;
    },
  },
  methods: {
    async onToggleTask() {
      try {
        await TaskService.toggleTask(this.task.id);
        this.pauseTimer();
      } catch (error) {
        console.error(error);
      }
    },
    async resetTimer() {
      try {
        if (this.timer <= 0) return;
        await TaskService.resetTimerTask(this.task.id);
        this.timer = 0;
        this.run = false;
        if (this.intervalId) {
          clearInterval(this.intervalId);
          this.intervalId = undefined;
        }
      } catch (error) {
        console.log(error);
      }
    },
    async startTimer(task: Task) {
      try {
        if (this.timer <= 0) {
          await TaskService.startTimerTask(task.id);
        }
        this.run = true;

        // limpa qualquer intervalo anterior antes de criar um novo
        if (this.intervalId) {
          clearInterval(this.intervalId);
          this.intervalId = undefined;
        }

        this.intervalId = window.setInterval(() => {
          this.timer++;
        }, 1000);
      } catch (error) {
        console.error(error);
      }
    },
    async pauseTimer() {
      try {
        this.run = false;
        await TaskService.pauseTimerTask(this.task.id, this.timer);
        // pausa o cronômetro
        if (this.intervalId) {
          clearInterval(this.intervalId);
          this.intervalId = undefined;
        }
      } catch (error) {
        console.error(error);
      }
    },
  },
  beforeUnmount() {
    if (this.intervalId) {
      clearInterval(this.intervalId);
      this.intervalId = undefined;
    }
  },
});
</script>

<style scoped>
.task-card {
  display: flex;
  gap: 16px;
  width: 100%;
  align-items: center;
  justify-content: space-between;
  border: solid 1px rgba(128, 128, 128, 0.421);
  padding: 16px;
  border-radius: 16px;
}

.routine-task-info {
  display: flex;
  flex-direction: column;
  width: 100%;
  justify-content: start;
  align-items: start;
}

.task-completed {
  background-color: var(--light-green);
}

/* Animação do número do timer */
.timer-text {
  font-weight: bold;
  display: flex;
  align-items: center;
  gap: 4px;
  transition: transform 0.2s ease;
  color: #958acd;
}

.timer-text.tick {
  animation: tickPulse 2s infinite;
}

.timer {
  display: flex;
  gap: 4px;
  align-items: center;
}

.circle {
  display: flex;
  align-items: center;
  font-size: 6px;
}

@keyframes tickPulse {
  0% {
    transform: scale(1);
    color: #958acd;
  }

  50% {
    transform: scale(1.1);
    color: #482ecc;
  }

  100% {
    transform: scale(1);
    color: #958acd;
  }
}
</style>
