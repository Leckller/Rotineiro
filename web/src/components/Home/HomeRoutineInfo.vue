<template>

  <div class="routine-info">
    <div class="routine-applied">
      <p>Rotina aplicada:</p>
      <p class="routine-applied-title">
        {{ title }}
        <span>
          <FontAwesomeIcon icon="check" />
          Ativa
        </span>
      </p>
    </div>
    <div class="routine-info-btns">
      <button class="routine-info-btn" @click="toggleFullscreen">
        <FontAwesomeIcon icon="expand" />
      </button>
      <button class="routine-info-btn" @click="() => $router.push('routine')">
        <FontAwesomeIcon icon="arrows-rotate" />
        <p>Trocar</p>
      </button>
    </div>
  </div>

</template>

<script lang="ts">

import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { defineComponent } from 'vue';

export default defineComponent({
  name: "HomeRoutineInfo",
  components: { FontAwesomeIcon },
  props: {
    title: { required: true, type: String, default: "" }
  },
  methods: {
    toggleFullscreen() {
      if (!document.fullscreenElement) {
        document.documentElement.requestFullscreen().catch((err) => {
          console.error(`Erro ao tentar entrar em tela cheia: ${err.message}`);
        });
      } else {
        document.exitFullscreen();
      }
    }
  }
});

</script>

<style scoped>
.routine-info {
  display: flex;
  justify-content: space-between;
  max-width: 300px;
  width: 100%;
  align-items: center;
  font-size: small;
}

.routine-applied {
  display: flex;
  align-items: start;
  flex-direction: column;
  justify-content: end;
}

.routine-info-btns {
  display: flex;
  gap: 8px;
}

.routine-applied-title {
  color: forestgreen;
  display: flex;
  align-items: center;
  gap: 8px;
}

.routine-applied-title span {
  padding: 4px;
  border-radius: 8px;
  background-color: rgb(216, 255, 216);
  border: solid 1px rgba(0, 128, 0, 0.453);
}

.routine-info-btn {
  display: flex;
  gap: 4px;
  padding: 4px;
  border-radius: 8px;
  border: solid 1px rgb(190, 190, 190);
  align-items: center;
}
</style>