<template>
  <article>
    <p>Frequência das Rotinas</p>

    <div>chart</div>

    <div>
      <div>
        <span />
        <p>Nome da Rotina</p>
        <small>x vezes entre start e end</small>
      </div>
    </div>
  </article>
</template>

<script>
import { defineComponent } from "vue";

export default defineComponent({
  name: "FrequencyRoutine",
});
</script>