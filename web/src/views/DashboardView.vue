<template>
  <TheLayout>

    <HeaderDashboard />

    <div class="main-dashboard">
      <FrequencyStatus />

      <SmallCard icon="" text="" description="Taxa de Conclusão" />

      <SmallCard icon="" text="" description="Sequência atual" />

      <SmallCard icon="" text="" description="Rotinas Ativas" />

      <MostUsedRoutine />

      <FrequencyRoutine />

    </div>
  </TheLayout>
</template>

<script lang="ts">
import FrequencyRoutine from "@/components/Dashboard/FrequencyRoutine.vue";
import FrequencyStatus from "@/components/Dashboard/FrequencyStatus.vue";
import HeaderDashboard from "@/components/Dashboard/HeaderDashboard.vue";
import MostUsedRoutine from "@/components/Dashboard/MostUsedRoutine.vue";
import SmallCard from "@/components/Dashboard/SmallCard.vue";
import TheLayout from "@/components/TheLayout.vue";
import { HistoryService } from "@/services/historyService";
import { defineComponent } from "vue";

export default defineComponent({
  name: "DashboardView",
  components: {
    TheLayout,
    FrequencyStatus,
    SmallCard,
    MostUsedRoutine,
    HeaderDashboard,
    FrequencyRoutine,
  },
  methods: {
    async getCompleteHistory() {
      const startDate = new Date();
      startDate.setDate(startDate.getDate() - 1); // ontem
      const endDate = new Date();
      endDate.setDate(endDate.getDate() + 1); // amanhã
      // formatar como "YYYY-MM-DDTHH:mm:ss"
      const formatForSpring = (date: any) => date.toISOString().slice(0, 19); // corta milissegundos e Z
      console.log(
        await HistoryService.completeHistory(
          formatForSpring(startDate),
          formatForSpring(endDate)
        )
      );
    },
  },
});
</script>
