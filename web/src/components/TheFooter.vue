<template>
  <footer class="footer">
    <RouterLink :class="[{ selected: $route.path.includes('home') }, 'link']" to="/home">
      <FontAwesomeIcon icon="house" />
    </RouterLink>

    <RouterLink :class="[{ selected: $route.path.includes('routine') }, 'link']" to="/routine">
      <FontAwesomeIcon icon="list" />
    </RouterLink>

    <RouterLink :class="[{ selected: $route.path.includes('dashboard') }, 'link']" to="/dashboard">
      <FontAwesomeIcon icon="square-poll-vertical" />
    </RouterLink>
  </footer>
</template>

<script lang="ts">
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { defineComponent } from 'vue';
import { RouterLink } from 'vue-router';

export default defineComponent({
  name: "TheFooter",
  components: { FontAwesomeIcon, RouterLink }
})
</script>

<style scoped>
.footer {
  display: flex;
  flex-wrap: wrap;
  gap: 16px;
  border-top: solid 1px darkgray;
  padding: 16px;
  width: 100%;
  justify-content: space-around;
  box-sizing: border-box;
}

.link {
  color: black;
  text-decoration: none;
  font-size: x-large;
}

.selected {
  color: dodgerblue !important;
}

@media (min-width: 900px) {
  .footer {
    display: none;
  }
}
</style>
