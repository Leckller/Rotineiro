import api, { DefaultResponse } from "./api"

export type AmountOfUse = { id: number, name: string, uses: number, created_at: Date }

export type Sequency = { actual_sequency: number, best_sequency: number }

export type HistoryTaskCount = { total: number, completed: number }

export type CompleteHistoryResponse = {
    most_used_routine: AmountOfUse, amount_of_routine_use: AmountOfUse, amount_of_task_use: AmountOfUse,
    sequency: Sequency, history_task_count: HistoryTaskCount
}

export const HistoryService = {

    async completeHistory(start: Date, end: Date): Promise<DefaultResponse<CompleteHistoryResponse>> {
        const formatForSpring = (date: Date): string => date.toISOString().slice(0, 19); // corta milissegundos e Z

        const { data } = await api.get(`history/${formatForSpring(start)}/${formatForSpring(end)}`);
        return data as DefaultResponse<CompleteHistoryResponse>;
    }

}