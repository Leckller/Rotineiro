<template>

  <footer class="footer">
    <RouterLink class="link" to="/home">
      <FontAwesomeIcon icon="house" />
    </RouterLink>

    <RouterLink class="link" to="/routine">
      <FontAwesomeIcon icon="list" />
    </RouterLink>

    <RouterLink class="link" to="/dashboard">
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
  gap: 16px;
  width: 100%;
  justify-content: space-around;
}

@media (min-width: 900px) {
  .footer {
    position: absolute;
    z-index: -10;
    width: 0;
    visibility: hidden;
  }
}

.link {
  color: black;
  text-decoration: none;
  font-size: x-large;
}
</style>