'use client'

import PageTitle from "@/components/app/page-title"
import FormsSelect from "@/components/forms/form-select"
import FormsTextAreaInput from "@/components/forms/form-textarea"
import FormsInput from "@/components/forms/forms-input"
import { Form } from "@/components/ui/form"
import { ClassesSchema,  ClassForm,  CourseListItem } from "@/lib/type"
import { DAYS_OF_WEEK, TYPE_OPTION } from "@/lib/utils"
import { zodResolver } from "@hookform/resolvers/zod"
import { useEffect, useState } from "react"
import { useFieldArray, useForm } from "react-hook-form"
import { Button } from "@/components/ui/button"
import { Plus, Save, Trash } from "lucide-react"

import * as courseClient from '@/lib/client/course.client'
import * as classClient from '@/lib/client/classs-client'
import { useRouter, useSearchParams } from "next/navigation"

export default function ClassEditPage() {

    const router = useRouter()
    const [courses, setCourses] = useState<CourseListItem[]>([])

    const form = useForm({
        resolver: zodResolver(ClassesSchema),
        defaultValues: {
           courseId: "",
           classType: "",
           months: "",
           startDate: "",
           remark: "",
           schedules: [
                {day: "", start: "", end: ""}]
        }
    })

    const {fields, append, remove} = useFieldArray({  //Scehudles Form Object Array
        control: form.control, 
        name: 'schedules'
    })

    useEffect(()=> {
        async function load() {
             const result =  await courseClient.search({deleted: "false"})
             setCourses(result)
        }

        load()

    }, [setCourses])

    const params = useSearchParams()
    const id = params.get("id")

    useEffect(() => {
    
        async function load() {
             if(id) {
                 const details = await classClient.findById(id)
                 form.setValue("courseId", String(details.courseId))
                 form.setValue("classType", details.classType)
                 form.setValue("months",  String(details.months))
                 form.setValue("startDate", details.startDate)
                 form.setValue("remark", details.remark)

                 remove(0)  // removing firstly scheule inputs box before inserting schedules for edit
                
                 form.setValue('schedules',details.schedules)
             }
        }

        load()

    }, [id, form])


    async function save(form: ClassForm) {
        const result =  id ? await classClient.update(id, form)  :  await classClient.create(form)
        router.push(`/classes/${result.id}`)
    }

    function addSchedule() {
         append({day: "",start: "",end: ""})
    }

    function removeSchedule(index: number) {
            
        remove(index)

         const schedules = form.watch('schedules')

        if(schedules.length == 0) {
             addSchedule()
        }

    }

    return (
        <section className="space-y-4">
            <PageTitle icon="Pencil" title={`${id ? 'Edit': 'Create'} Course`} />

            <Form {...form}>
                <form onSubmit={form.handleSubmit(save)} className="grid grid-cols-4 gap-4">
                    <FormsSelect control={form.control} path="courseId" label="Course" options={courses.map(a => ({key: String(a.id), value: a.name}))}  /> {/*() when returning an object literal directly inside an arrow function. Without (), JavaScript thinks {} is a function block, not an object */}
                    <FormsSelect control={form.control} path="classType" label="Class Type" options={[...TYPE_OPTION]}  />
                    <FormsInput control={form.control} path="startDate" type="date" label="Start Date" className="col-start-1" /> {/* start from new column*/}
                    <FormsInput control={form.control} path="months" type="number" label="Duration in Month"  />
                    <FormsTextAreaInput control={form.control}  path="remark" label="Remark" className="col-span-3" />
               
                     <div className="col-span-3">
                        <h3 className="text-lg mb-4">Schedules</h3>

                        <div className="space-y-2">
                           {fields.map((field,index) => 
                             <div key={field.id} className="flex items-start gap-4">
                                <FormsSelect control={form.control}  path={`schedules.${index}.day`}  
                                    label={index == 0 ? "Day" : undefined } options={[...DAYS_OF_WEEK]} className="w-[200px]" />
                                
                                <FormsInput control={form.control}  path={`schedules.${index}.start`} 
                                    label= {index == 0 ? "Start Time" : undefined} type="time" className="w-[200px]" />

                                  <FormsInput control={form.control}  path={`schedules.${index}.end`} 
                                    label= {index == 0 ? "End Time" : undefined} type="time" className="w-[200px]" />
                                 
                                 <div className={index == 0 ? 'pt-[1.3rem]': ''}>
                                  <Button type="button" onClick={()=> removeSchedule(index)} disabled={!form.formState.isValid}  >
                                    <Trash/>
                                  </Button>
                                </div>
                             </div>
                           )} 
                        </div>
                     </div>
                    

                     <div className=" col-span-2 space-x-6">
                        <Button type="button" onClick={addSchedule} disabled={!form.formState.isValid}> {/*formState.isValid = false is in initial state so !formState.isValid  get true  */}
                            <Plus/> Add Schedule
                        </Button>

                        <Button type="submit" >
                            <Save/> Save Course
                        </Button>
                     </div>
                </form>
            </Form>
        </section>
    )
}
