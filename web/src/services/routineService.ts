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
  description: string,
}

export type CreateRoutineRequest = {
  name: string,
  priority: PriorityEnum,
  description: string
  tasks?: number[]
}

export type CreateRoutineResponse = DefaultResponse<RoutineEntity>

export type getRoutineByIdResponse = DefaultResponse<RoutineEntity>

export type GetAllRoutinesResponse = DefaultResponse<RoutineEntity[]>


export const RoutineService = {

  async createRoutine(request: CreateRoutineRequest): Promise<CreateRoutineResponse> {
    const { data } = await api.post("routine", request);
    return data as CreateRoutineResponse;
  },
  async getAllRoutines(): Promise<GetAllRoutinesResponse> {
    const { data } = await api.get("routine/all");
    return data as GetAllRoutinesResponse;
  },
  async getRoutineById(routineId: number): Promise<getRoutineByIdResponse> {
    const { data } = await api.get(`routine/${routineId}`);
    return data as getRoutineByIdResponse;
  }

}