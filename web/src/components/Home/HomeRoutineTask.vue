<!-- eslint-disable vue/no-mutating-props -->
<template>
  <input type="checkbox" v-model="task.completed" @change="onToggleTask" />
  <div class="routine-task-info">
    <h4>{{ task.name }}</h4>
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
  <button v-if="!run" @click="startTimer(task)">
    <FontAwesomeIcon class="timer-btn" :class="{ running: run }" icon="play" />
  </button>
  <button v-if="run" @click="pauseTimer">
    <FontAwesomeIcon class="timer-btn" :class="{ running: run }" icon="pause" />
  </button>
  <button v-if="timer > 0 && !task.completed" @click="resetTimer">
    <FontAwesomeIcon icon="stop" />
  </button>
</template>

<script lang="ts">
import { Task, TaskService } from "@/services/taskService";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { defineComponent } from "vue";

export default defineComponent({
  name: "HomeRoutineTask",
  components: { FontAwesomeIcon },
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

        if (this.run) {
          // inicia o cronômetro
          this.intervalId = window.setInterval(() => {
            this.timer++;
          }, 1000);
        }
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
.routine-task-info {
  display: flex;
  flex-direction: column;
  width: 100%;
  justify-content: start;
  align-items: start;
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
