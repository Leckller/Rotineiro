<template>
  <div
    data-slot="progress"
    class="progress-root"
    v-bind="$attrs"
  >
    <div
      data-slot="progress-indicator"
      class="progress-indicator"
      :style="{ transform: `translateX(-${100 - (value || 0)}%)`, backgroundColor: color }"
    />
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "ProgressBar",
  props: {
    color: {
      type: String,
      default: "#3b82f6",
    },
    value: {
      type: Number,
      default: 0,
    },
  },
});
</script>

<style scoped>
.progress-root {
  background-color: rgba(0, 0, 0, 0.1);
  position: relative;
  height: 8px;
  width: 100%;
  overflow: hidden;
  border-radius: 9999px;
}

.progress-indicator {
  height: 100%;
  width: 100%;
  flex: 1;
  transition: transform 0.3s ease;
}
</style>
