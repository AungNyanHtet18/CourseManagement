import z from "zod";

export const ClassSchema = z.object({
     courseId: z.number(),
     startDate: z.string().nonempty("Please enter start date."),
     classType: z.string().nonempty("Please enter class type."),
     months: z.number,
     remark: z.string(),
})

const ScheduleSchema = z.object({
     day: z.string().nonempty("Please select schedule day.")
     
})