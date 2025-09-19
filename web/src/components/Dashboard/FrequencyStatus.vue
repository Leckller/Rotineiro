<template>
  <article>
    <FontAwesomeIcon icon="rocket" />
    <p>{{ text }}</p>
    <small>{{ description }}</small>
  </article>
</template>

<script>
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { defineComponent } from "vue";

export default defineComponent({
  name: "FrequencyStatus",
  components: {
    FontAwesomeIcon
  },
  data() {
    return {
      text: "Frequência de Uso",
      description: "x vezes entre start e end",
    };
  },
});
</script>