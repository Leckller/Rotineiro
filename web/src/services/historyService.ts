import api, { DefaultResponse } from "./api"

export type AmountOfUse = { id: number, name: string, uses: number }

export type CompleteHistoryResponse = DefaultResponse<{ most_used_routine: AmountOfUse, amount_of_routine_use: AmountOfUse, amount_of_task_use: AmountOfUse }>

export const HistoryService = {

    async completeHistory(start: Date, end: Date): Promise<DefaultResponse<CompleteHistoryResponse>> {
        const formatForSpring = (date: Date): string => date.toISOString().slice(0, 19); // corta milissegundos e Z

        const { data } = await api.get(`history/${formatForSpring(start)}/${formatForSpring(end)}`);
        return data as DefaultResponse<CompleteHistoryResponse>;
    }

}