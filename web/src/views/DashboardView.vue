<template>
  <TheLayout> Dashboard </TheLayout>
</template>

<script lang="ts">
import TheLayout from "@/components/TheLayout.vue";
import { defineComponent } from "vue";

export default defineComponent({
  name: "DashboardView",
  components: {
    TheLayout,
  },
  methods: {
    // const startDate = new Date();
    // startDate.setDate(startDate.getDate() - 1); // ontem
    // const endDate = new Date();
    // endDate.setDate(endDate.getDate() + 1); // amanhã
    // // formatar como "YYYY-MM-DDTHH:mm:ss"
    // const formatForSpring = (date: any) => date.toISOString().slice(0, 19); // corta milissegundos e Z
    // console.log(
    //   await HistoryService.completeHistory(
    //     formatForSpring(startDate),
    //     formatForSpring(endDate)
    //   )
    // );
  },
});
</script>
