import api, { DefaultResponse } from "./api"
import { TaskEntity } from "./taskService"

export enum PriorityEnum {
  LOW, MEDIUM, HIGH
}

export class Routine implements RoutineEntity {

  constructor(
    id: number,
    name: string,
    tasks: TaskEntity[],
    priority: PriorityEnum,
    description: string) {

    this.description = description;
    this.id = id;
    this.name = name;
    this.tasks = tasks;
    this.priority = priority;

  }

  public id: number;
  public name: string;
  public tasks: TaskEntity[];
  public priority: PriorityEnum;
  public description: string;
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

export type EditRoutineRequest = {
  name: string,
  priority: PriorityEnum,
  description: string
}

export type AssignTaskRequest = { tasks: number[] }

export type DeallocateTaskRequest = { tasks: number[] }

export type CreateRoutineResponse = DefaultResponse<RoutineEntity>

export type EditRoutineResponse = DefaultResponse<RoutineEntity>

export type getRoutineByIdResponse = DefaultResponse<RoutineEntity>

export type SetActualRoutineResponse = DefaultResponse<RoutineEntity>

export type GetAllRoutinesResponse = DefaultResponse<RoutineEntity[]>

export type AssignTaskResponse = DefaultResponse<RoutineEntity>

export type DeallocateTaskResponse = DefaultResponse<null>

export type DeleteRoutineResponse = DefaultResponse<null>

export type FinishRoutineResponse = DefaultResponse<null>

export const RoutineService = {

  async createRoutine(request: CreateRoutineRequest): Promise<CreateRoutineResponse> {
    const { data } = await api.post("routine", request);
    return data as CreateRoutineResponse;
  },
  async assingTasksToRoutine(request: AssignTaskRequest, routineID: number): Promise<AssignTaskResponse> {
    const { data } = await api.post(`routine/assign/${routineID}`, request);
    return data as AssignTaskResponse;
  },
  async deallocateTasksToRoutine(request: DeallocateTaskRequest, routineID: number): Promise<DeallocateTaskResponse> {
    const { data } = await api.post(`routine/deallocate/${routineID}`, request);
    return data as DeallocateTaskResponse;
  },
  async deleteRoutine(routineID: number): Promise<DeleteRoutineResponse> {
    const { data } = await api.delete(`routine/${routineID}`);
    return data as DeleteRoutineResponse;
  },
  async editRoutine(request: EditRoutineRequest, routineID: number): Promise<EditRoutineResponse> {
    const { data } = await api.patch(`routine/${routineID}`, request);
    return data as EditRoutineResponse;
  },
  async startRoutine(routineID: number): Promise<SetActualRoutineResponse> {
    const { data } = await api.patch(`routine/start/${routineID}`);
    return data as SetActualRoutineResponse;
  },
  async finishRoutine(): Promise<FinishRoutineResponse> {
    const { data } = await api.patch(`routine/finish}`);
    return data as FinishRoutineResponse;
  },
  async getAllRoutines(): Promise<GetAllRoutinesResponse> {
    const { data } = await api.get("routine/all");
    return data as GetAllRoutinesResponse;
  },
  async getRoutineById(routineId: number): Promise<getRoutineByIdResponse> {
    const { data } = await api.get(`routine/${routineId}`);
    return data as getRoutineByIdResponse;
  },

}