import api, { DefaultResponse } from "./api"
import { TaskEntity } from "./taskService"

export enum PriorityEnum {
  LOW, MEDIUM, HIGH
}

export type RoutineEntity = {
  id: number,
  name: string,
  tasks: TaskEntity[],
  priority: PriorityEnum
}

export type CreateRoutineRequest = {
  name: string,
  priority: PriorityEnum,
  tasks: number[]
}

export type CreateRoutineResponse = DefaultResponse<RoutineEntity>

export type GetAllRoutinesResponse = DefaultResponse<RoutineEntity[]>


export const RoutineService = {

  async createRoutine(request: CreateRoutineRequest): Promise<CreateRoutineResponse> {
    const { data } = await api.post("routine", request);
    return data as CreateRoutineResponse;
  },
  async getAllRoutines(): Promise<GetAllRoutinesResponse> {
    const { data } = await api.get("routine/all");
    return data as GetAllRoutinesResponse;
  }

}