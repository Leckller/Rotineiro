import api, { DefaultResponse } from "./api";

export class Task {
  constructor(id: number, name: string, estimate: number, completed: boolean) {
    this.id = id
    this.name = name;
    this.estimate = estimate;
    this.completed = completed;
  }

  public id: number;
  public name: string;
  public estimate: number;
  public completed: boolean;
}

export type TaskEntity = {
  id: number,
  name: string,
  estimate: number,
  completed: boolean
}

export type CreateTaskRequest = {
  name: string,
  estimate: number
  routine_id?: number,
};

export type AssignTaskRequest = { routine_id: number, tasks: number[] }

export type EditTaskRequest = { estimate: number, name: string }

type CreateTaskResponse = DefaultResponse<TaskEntity>

type GetAllTasksResponse = DefaultResponse<TaskEntity[]>

type GetAvailableTasksResponse = DefaultResponse<TaskEntity[]>

type AssignTaskResponse = DefaultResponse<number>

type DeleteTaskResponse = DefaultResponse<null>

type EditTaskResponse = DefaultResponse<TaskEntity>

type StartTaskResponse = DefaultResponse<TaskEntity>

type ToggleTaskResponse = DefaultResponse<TaskEntity>



export const TaskService = {

  async createTask(request: CreateTaskRequest): Promise<CreateTaskResponse> {
    const { data } = await api.post("task", request);
    return data as CreateTaskResponse;
  },
  async getAllTasks(): Promise<GetAllTasksResponse> {
    const { data } = await api.get("task/all");
    return data as GetAllTasksResponse;
  },
  async getAvailableTasks(routineId: number): Promise<GetAvailableTasksResponse> {
    const { data } = await api.get(`task/${routineId}`);
    return data as GetAvailableTasksResponse;
  },
  async assingTasksToRoutine(request: AssignTaskRequest): Promise<AssignTaskResponse> {
    const { data } = await api.post("task/assing", request);
    return data as AssignTaskResponse;
  },
  async editTask(taskId: number, request: EditTaskRequest): Promise<EditTaskResponse> {
    const { data } = await api.patch(`task/${taskId}`, request);
    return data as EditTaskResponse;
  },
  async deleteTask(taskId: number): Promise<DeleteTaskResponse> {
    const { data } = await api.delete(`task/${taskId}`);
    return data as DeleteTaskResponse;
  },
  async startTask(taskId: number): Promise<StartTaskResponse> {
    const { data } = await api.patch(`task/start/${taskId}`);
    return data as StartTaskResponse;
  },
  async toggleTask(taskId: number): Promise<ToggleTaskResponse> {
    const { data } = await api.patch(`task/complete/${taskId}`);
    return data as ToggleTaskResponse;
  }

}