import { RoutineEntity } from "@/services/routineService";
import { defineStore } from "pinia";

export const useRoutineStore = defineStore("routine", {
  state: (): { routines: RoutineEntity[], selectedRoutine: RoutineEntity } => ({ routines: [], selectedRoutine: {} as RoutineEntity }),
  actions: {
    selectRoutine(routine: RoutineEntity) {
      this.selectedRoutine = routine;
    },
    setRoutines(routines: RoutineEntity[]) {
      this.routines = routines;
    }
  },
})