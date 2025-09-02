<template>

  <header>
    <RouterLink to="/home">
      Home
    </RouterLink>

    <RouterLink to="/routine">
      Rotinas
    </RouterLink>

    <RouterLink to="/dashboard">
      Dashboard
    </RouterLink>


  </header>

</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { RouterLink } from 'vue-router';


export default defineComponent({
  name: "TheHeader",
  components: { RouterLink }
})

</script>