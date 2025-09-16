import api, { DefaultResponse } from "./api"

export type AmountOfRoutineUse = { id: number, routine_name: string, uses: number }

export type AmountOfTaskUse = { id: number, task_name: string, uses: number }

export type CompleteHistoryResponse = DefaultResponse<{ most_used_routine: AmountOfRoutineUse, amount_of_routine_use: AmountOfRoutineUse, amount_of_task_use: AmountOfTaskUse }>

export const HistoryService = {

    async completeHistory(start: Date, end: Date): Promise<DefaultResponse<CompleteHistoryResponse>> {
        const { data } = await api.get(`history/${start}/${end}`);
        return data as DefaultResponse<CompleteHistoryResponse>;
    }

}