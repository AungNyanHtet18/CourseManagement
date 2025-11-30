'use client'

import { CourseForm, CourseSchema } from "@/lib/type"
import { zodResolver } from "@hookform/resolvers/zod"
import { useRouter, useSearchParams } from "next/navigation" //client router
import { useEffect } from "react"
import { useForm } from "react-hook-form"
import * as courseClient from "@/lib/client/course.client"
import { LEVELS_OPTIONS } from "@/lib/utils"
import FormsSelect from "@/components/forms/form-select"
import { Form } from "@/components/ui/form"
import PageTitle from "@/components/app/page-title"
import { Save } from "lucide-react"
import FormsInput from "@/components/forms/forms-input"
import FormsTextAreaInput from "@/components/forms/form-textarea"
import { Button } from "@/components/ui/button"
import { handle } from "@/lib/client-utils"

export default function CourseEditPage() {

   const router = useRouter() //client router

    const form = useForm({
       resolver: zodResolver(CourseSchema),
       defaultValues: {
          level: undefined,
          name: "",
          description: ""
       }
    })

    const searchParams = useSearchParams()
    const id = searchParams.get("id")

    useEffect(()=> {
      
      async function load() {
          if(id) {
             const result = await courseClient.findById(id) // server action => server function invoked by client component is called server action
             form.reset({
                level: result.Level,
                name: result.name,
                description: result.description
             })
          }
      }

      load()

    },[id, form])
    

    async function save(form: CourseForm) { 
       handle(async()=> {
         const result = await ( id ? courseClient.update(id, form) : courseClient.create(form))
         router.push(`/courses/${result.id}`)
       })
       
   }
   
   return (
       <section className="space-y-4">
         <PageTitle icon="Pencil" title={id ? "Edit Course" : "Add New Course"} />

         <Form {...form}>
            <form onSubmit={form.handleSubmit(save)}>
               <div className="grid grid-cols-1 md:grid-cols-4 gap-4 items-start">
                  <FormsSelect control={form.control} path="level" 
                     label="Course Level" options={LEVELS_OPTIONS}/>

                   <FormsInput control={form.control} path="name" label="Course Name" className="col-span-full md:col-span-2"/>
                   
                   <FormsTextAreaInput control={form.control} path="description" label="Description" className="col-span-3"/>
                   
                   <div className="col-span-full">
                     <Button type="submit">
                        <Save/> Save
                     </Button>
                   </div>
               </div>
            </form>
         </Form>
       </section>
     )
}