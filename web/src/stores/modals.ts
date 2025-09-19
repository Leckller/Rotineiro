import { Task, TaskEntity } from "@/services/taskService";
import { defineStore } from "pinia";

export const useModalStore = defineStore("modals", {
    state: (): { actualModalName: string, open: boolean, infos: { setTask: number, editTask: TaskEntity, rmvTask: TaskEntity } } => (
        {
            actualModalName: "", open: false,
            infos: { setTask: 0, editTask: new Task(0, "", 0, false), rmvTask: new Task(0, "", 0, false) }
        }
    ),
    actions: {
        closeModal() {
            this.open = false
        },
        setRoutine(routineID: number) {
            this.infos.setTask = routineID
        },
        setEditTaskInfos(task: TaskEntity) {
            this.infos.editTask = task;
        },
        setRmvTaskInfos(task: TaskEntity) {
            this.infos.rmvTask = task;
        },
        openAndSetModal(name: string) {
            this.open = true
            this.actualModalName = name
        },
        toggleOpenModal() {
            this.open = !this.open
        },
        setActualModalName(name: string) {
            this.actualModalName = name
        }
    },
})