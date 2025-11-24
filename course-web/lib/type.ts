import * as lucideIcons from 'lucide-react'
import z from "zod";

export type IconType = keyof typeof lucideIcons

export const CourseSchema = z.object({
     name: z.string().nonempty("Please enter course name."),
     level: z.string().nonempty("Please select course level."),
     description: z.string().nonempty("Please enter course description.")
})

export type CourseForm = z.infer<typeof CourseSchema>

export type CourseSearch  = {
     level?: string
     deleted?: boolean
     keyword?: string
}

export type CourseListItem = {
     id: number
     name: string
     level: string
     description: string
     deleted: boolean
     createdAt: string
}


export type CourseDetails = CourseListItem & {
                            classes: ClassListItem[]
                            updatedAt: string
}

export type ClassListItem = {
     id: number
     courseId: number
     level: string
     courseName: string
     startDate: string
     classType: string
     months: number
     deleted: boolean
     createdAt: string
}

export class RestClientException{
     constructor(readonly message:string[]) {
        
     }
}

