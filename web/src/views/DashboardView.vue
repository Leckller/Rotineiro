<template>
  <TheLayout>
    <HeaderDashboard />

    <div class="main-dashboard">

      <FrequencyStatus />

      <div class="small-cards">
        <SmallCard
          icon="bullseye"
          :value="history.history_task_count.completed"
          text="Tarefas Concluídas"
        />

        <SmallCard
          icon="bullseye"
          :value="`${getPercent(
            history.history_task_count.total,
            history.history_task_count.completed
          )}%`"
          text="Taxa de Conclusão"
          color="#008000"
        />

        <SmallCard
          icon="bullseye"
          :value="history.sequency.actual_sequency"
          text="Sequência atual"
          color="#F54927"
        />

        <SmallCard
          icon="bullseye"
          :value="history.sequency.best_sequency"
          text="Melhor Sequência"
          color="#6A00A1"
        />
      </div>

      <MostUsedRoutine
        :routine="history.most_used_routine.name"
        :quantity="history.most_used_routine.uses"
      />

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
import {
  CompleteHistoryResponse,
  HistoryService,
} from "@/services/historyService";
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
  data() {
    return {
      history: {
        most_used_routine: { id: 0, name: "", uses: 0 },
        amount_of_routine_use: { id: 0, name: "", uses: 0 },
        amount_of_task_use: { id: 0, name: "", uses: 0 },
        sequency: { actual_sequency: 0, best_sequency: 0 },
        history_task_count: { total: 0, completed: 0 },
      } as CompleteHistoryResponse,
    };
  },
  mounted() {
    this.getCompleteHistory();
  },
  methods: {
    getPercent(total: number, value: number) {
      return total === 0 ? 0 : Math.round((value / total) * 100);
    },
    async getCompleteHistory() {
      const startDate = new Date();
      startDate.setDate(startDate.getDate() - 1); // ontem
      const endDate = new Date();
      endDate.setDate(endDate.getDate() + 1); // amanhã
      const response = (
        await HistoryService.completeHistory(startDate, endDate)
      ).response;
      this.history = response;
      console.log(this.history);
    },
  },
});
</script>

<style scoped>
.main-dashboard {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  padding: 16px;
  gap: 16px;
}

.small-cards {
  display: flex;
  width: 100%;
  flex-wrap: wrap;
  align-items: center;
  justify-content: center;
  gap: 8px;
}
</style>
