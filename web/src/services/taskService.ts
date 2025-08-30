import api, { DefaultResponse } from "./api";

export type TaskEntity = {
  id: number,
  name: string,
  estimate: number
}

export type CreateTaskRequest = {
  name: string,
  estimate: number
  routine_id?: number,
};

type CreateTaskResponse = DefaultResponse<TaskEntity>

type GetAllTasksResponse = DefaultResponse<TaskEntity[]>

export const TaskService = {

  async createTask(request: CreateTaskRequest): Promise<CreateTaskResponse> {
    const { data } = await api.post("task", request);
    return data as CreateTaskResponse;
  },
  async getAllTasks(): Promise<GetAllTasksResponse> {
    const { data } = await api.get("task/all");
    return data as GetAllTasksResponse;
  }

}