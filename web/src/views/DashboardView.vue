<template>
  <TheLayout>
    Dashboard
  </TheLayout>
</template>

<script lang="ts">
import TheLayout from '@/components/TheLayout.vue';
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'DashboardView',
  components: {
    TheLayout
  }
})
</script>
