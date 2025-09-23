<template>
  <article class="frequency-routines">
    <h3>Frequência das Rotinas</h3>
    <p class="empty" v-if="frequency.length <= 0">
      Você ainda não concluiu uma Rotina 😕
    </p>
    <Doughnut
      v-if="frequency.length > 0"
      :data="chartData"
      :options="chartOptions"
    />
  </article>
</template>

<script lang="ts">
import { defineComponent, computed, PropType } from "vue";
import { Chart as ChartJS, Title, Tooltip, Legend, ArcElement } from "chart.js";
import { Doughnut } from "vue-chartjs";
import { DayUseRoutine } from "@/services/historyService";

ChartJS.register(Title, Tooltip, Legend, ArcElement);

export default defineComponent({
  name: "FrequencyTasks",
  components: { Doughnut },
  props: {
    frequency: {
      type: Array as PropType<DayUseRoutine[]>,
      required: true,
    },
  },
  setup(props) {
    const chartData = computed(() => ({
      labels: props.frequency.map((f) => f.name), // nome da tarefa
      datasets: [
        {
          data: props.frequency.map((f) => f.uses), // quantidade
          backgroundColor: [
            "#42A5F5", // azul
            "#66BB6A", // verde
            "#FFA726", // laranja
            "#AB47BC", // roxo
          ],
          borderColor: "#fff",
          borderWidth: 2,
        },
      ],
    }));

    const chartOptions = {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: {
          position: "bottom",
        },
        tooltip: {
          callbacks: {
            label: function (context: any) {
              return `${context.label} (${context.raw})`;
            },
          },
        },
      },
    };

    return { chartData, chartOptions };
  },
});
</script>

<style scoped>
.empty {
  padding: 8px 0;
  white-space: nowrap;
}
.frequency-routines {
  width: 100%;
  max-width: 320px;
  max-height: 350px;
  border: solid 1px lightgray;
  border-radius: 8px;
  padding: 16px;
  text-align: start;
}
</style>
