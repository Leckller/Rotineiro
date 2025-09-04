import { PriorityEnum, Routine, RoutineEntity } from "@/services/routineService";
import { TaskEntity } from "@/services/taskService";
import { defineStore } from "pinia";

export const useRoutineStore = defineStore("routine", {
  state: (): { routines: RoutineEntity[], selectedRoutine: RoutineEntity, infos: { actualRoutineId: number } } => (
    { routines: [], selectedRoutine: new Routine(0, "", [], PriorityEnum.LOW, ""), infos: { actualRoutineId: 0 } }
  ),
  actions: {
    selectRoutine(routine: RoutineEntity) {
      this.selectedRoutine = routine;
    },
    setRoutines(routines: RoutineEntity[]) {
      this.routines = routines;
    },
    setActualRoutine(id: number) {
      this.infos.actualRoutineId = id
    },
    editRoutine(routine: RoutineEntity) {
      this.routines = [...this.routines.filter(r => r.id != routine.id), routine]
      this.selectedRoutine = routine;
    },
    addRoutine(routine: RoutineEntity) {
      this.routines.push(routine)
    },
    addTaskToSelectedRoutine(task: TaskEntity) {
      this.selectedRoutine.tasks.push(task);
    },
    rmvTaskFromSelectedRoutine(taskId: number) {
      this.selectedRoutine.tasks = this.selectedRoutine.tasks.filter(t => t.id != taskId)
    }
  },
})