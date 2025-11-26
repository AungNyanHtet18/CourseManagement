'use client'

import { ClientError, CourseForm, CourseSchema } from "@/lib/type"
import { zodResolver } from "@hookform/resolvers/zod"
import { useRouter, useSearchParams } from "next/navigation" //client router
import { useEffect } from "react"
import { useForm } from "react-hook-form"
import * as courseClient from "@/lib/client/course.client"
import { LEVELS_OPTIONS } from "@/lib/utils"
import FormsSelect from "@/components/forms/form-select"
import { Form } from "@/components/ui/form"
import PageTitle from "@/components/app/page-title"
import { FormInput, Save } from "lucide-react"
import FormsInput from "@/components/forms/forms-input"
import FormsTextAreaInput from "@/components/forms/form-textarea"
import { Button } from "@/components/ui/button"

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
                description: result.descirption
             })
          }
      }

      load()

    },[id, form])
    

    async function save(form: CourseForm) { 
       try{
         const result = await ( id ? courseClient.update(id, form) : courseClient.create(form))
         router.push(`/courses/${result.id}`)
       }catch(e: any) {
          let messages:string[] = [e.message || "Unknown Error"]

          if(e.message) {
             const error = JSON.parse(e.message)
             if(error?.type == "Client Error") {
                const clientError = error as ClientError
                messages = clientError.message

             }
          }
       }
   }
   

     return (
       <section className="space-y-4">
         <PageTitle icon="Pencil" title={id ? "Edit Course" : "Add New Course"} />

         <Form {...form}>
            <form onSubmit={form.handleSubmit(save)}>
               <div className="grid grid-cols-4 gap-4 items-start">
                  <FormsSelect control={form.control} path="level" 
                     label="Course Level" options={LEVELS_OPTIONS}/>

                   <FormsInput control={form.control} path="name" label="Course Name" className="col-span-2"/>
                   
                    <div></div>
                   <FormsTextAreaInput control={form.control} path="description" label="Description" className="col-span-4"/>
                   
                   <div>
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