<template>

  <TheHeader />

  <main>
    <slot />
  </main>

  <TheFooter />

</template>

<script lang="ts">
import { defineComponent } from 'vue';
import TheHeader from './TheHeader.vue';
import TheFooter from './TheFooter.vue';


export default defineComponent({
  name: "TheLayout",
  components: {
    TheHeader,
    TheFooter
  }
})

</script>