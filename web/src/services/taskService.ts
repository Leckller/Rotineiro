import api, { DefaultResponse } from "./api";

export class Task {
  constructor(id: number, name: string, estimate: number, completed: boolean, timer = 0) {
    this.id = id
    this.name = name;
    this.estimate = estimate;
    this.completed = completed;
    this.timer = timer;
  }

  public id: number;
  public name: string;
  public estimate: number;
  public completed: boolean;
  public timer: number;
}

export type TaskEntity = {
  id: number,
  name: string,
  estimate: number,
  timer: number,
  completed: boolean
}

export type CreateTaskRequest = {
  name: string,
  estimate: number
  routine_id?: number,
};

export type EditTaskRequest = { estimate: number, name: string }

type CreateTaskResponse = DefaultResponse<TaskEntity>

type GetAllTasksResponse = DefaultResponse<TaskEntity[]>

type GetAvailableTasksResponse = DefaultResponse<TaskEntity[]>

type DeleteTaskResponse = DefaultResponse<null>

type EditTaskResponse = DefaultResponse<TaskEntity>

type StartTaskResponse = DefaultResponse<TaskEntity>

type ToggleTaskResponse = DefaultResponse<TaskEntity>


export const TaskService = {

  async createTask(request: CreateTaskRequest): Promise<CreateTaskResponse> {
    const { data } = await api.post("task", request);
    return data as CreateTaskResponse;
  },
  async deleteTask(taskId: number): Promise<DeleteTaskResponse> {
    const { data } = await api.delete(`task/${taskId}`);
    return data as DeleteTaskResponse;
  },
  async editTask(taskId: number, request: EditTaskRequest): Promise<EditTaskResponse> {
    const { data } = await api.patch(`task/${taskId}`, request);
    return data as EditTaskResponse;
  },
  async toggleTask(taskId: number): Promise<ToggleTaskResponse> {
    const { data } = await api.patch(`task/toggleStatus/${taskId}`);
    return data as ToggleTaskResponse;
  },
  async getAllTasks(): Promise<GetAllTasksResponse> {
    const { data } = await api.get("task/all");
    return data as GetAllTasksResponse;
  },
  async startTimerTask(taskId: number): Promise<StartTaskResponse> {
    const { data } = await api.patch(`task/timer/start/${taskId}`);
    return data as StartTaskResponse;
  },
  async resetTimerTask(taskId: number): Promise<StartTaskResponse> {
    const { data } = await api.patch(`task/timer/reset/${taskId}`);
    return data as StartTaskResponse;
  },
  async pauseTimerTask(taskId: number, timer: number): Promise<StartTaskResponse> {
    const { data } = await api.patch(`task/timer/pause/${taskId}/${timer}`);
    return data as StartTaskResponse;
  },
  async getAvailableTasks(routineId: number): Promise<GetAvailableTasksResponse> {
    const { data } = await api.get(`task/${routineId}`);
    return data as GetAvailableTasksResponse;
  },

}