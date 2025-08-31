<template>

  <TheHeader />

  <main class="main">
    <TheNotification />
    <slot />
  </main>

  <TheFooter />

</template>

<script lang="ts">
import { defineComponent } from 'vue';
import TheHeader from './TheHeader.vue';
import TheFooter from './TheFooter.vue';
import TheNotification from './Modal/TheNotification.vue';


export default defineComponent({
  name: "TheLayout",
  components: {
    TheHeader,
    TheNotification,
    TheFooter
  }
})

</script>

<style setup>

.main {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

</style>