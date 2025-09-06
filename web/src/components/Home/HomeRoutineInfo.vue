<script lang="ts">
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { defineComponent } from 'vue';

export default defineComponent({
  name: "HomeRoutineInfo",
  components: { FontAwesomeIcon }
});

</script>

<template>

  <div class="routine-info">
    <div class="routine-applied">
      <p>Rotina aplicada:</p>
      <p>
        Workout Intenso
        <span>
          <FontAwesomeIcon icon="check" />
          Ativa
        </span>
      </p>
    </div>
    <button @click="() => $router.push('routine')">
      <FontAwesomeIcon icon="arrows-rotate" />
      <p>Trocar</p>
    </button>
  </div>

</template>