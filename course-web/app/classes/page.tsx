'use client'

import PageTitle from "@/components/app/page-title";
import { ClassListItem, ClassSearch, PageResult } from "@/lib/type";
import * as classApi from "@/lib/client/classs-client";
import { useForm } from "react-hook-form";
import { useEffect, useState } from "react";
import { Form } from "@/components/ui/form";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import Loading from "@/components/app/app-loading";
import DeleteStatus from "@/components/app/status-component";
import FormsSelect from "@/components/forms/form-select";
import { LEVELS_OPTIONS, STATUS_OPTIONS } from "@/lib/utils";

export default function ClassManagement() {

     const [page, setPage] =useState<PageResult<ClassListItem> | undefined>(undefined)

      useEffect(()=> {
       search({page: 0})
        
      },[setPage])


     async function search(form: ClassSearch) {
         if(form.level == "-1") {
             delete form.deleted
         }

         if(form.type == "-1") {
             delete form.type
         }

         if(form.deleted == "-1") {
             delete form.deleted
         }

        const result = await classApi.search(form)
        setPage(result)
     }


     return (
        <section className="space-y-4">
            <PageTitle icon="CalendarCheck" title="Class Management"/>
            <SearchForm onSearch={search} />
            <SearchResult page={page} />
        </section>
      )
}

function SearchForm({onSearch} : {onSearch: (form: ClassSearch)=> void}) {

   const form = useForm<ClassSearch>({
       defaultValues: {
          level: "-1",
          type: "-1",
          deleted: "-1",
          keyword: "",
          page: 0
       }
   })

   return(
      <Form {...form} >
         <form onSubmit={form.handleSubmit(onSearch)}>
            <FormsSelect control={form.control} path="type" 
               options={[]} label="Class Type" />

            <FormsSelect control={form.control} path="level" 
               options={[{key: "-1", value: "Select All"}, ...LEVELS_OPTIONS]} 
               label="Level" />

            <FormsSelect control={form.control} path="deleted" 
               options={[{key: "-1", value: "Select All" }   ,...STATUS_OPTIONS]} label="Status"/>
         </form>
      </Form>
   )
}

function SearchResult({page} : {page: PageResult<ClassListItem> | undefined}) {

   if(!page) {
      return (
         <Loading data="Classes" />
      )
   }

   const {list, pageInfo } = page

   return (
        <Table>
         <TableHeader>
             <TableRow>
                  <TableHead>ID</TableHead>
                  <TableHead>Course Name</TableHead>
                  <TableHead>Level</TableHead>
                  <TableHead>Class Type</TableHead>
                  <TableHead>Start Date</TableHead>
                  <TableHead>Months</TableHead>
                  <TableHead>Status</TableHead>
                  <TableHead>Create At</TableHead>
                  <TableHead></TableHead>
             </TableRow>
         </TableHeader>

          <TableBody>
             {list.map((item, index) => 
              <TableRow key={index}>
                  <TableCell>{item.id}</TableCell>
                  <TableCell>{item.courseName}</TableCell>
                  <TableCell>{item.Level}</TableCell>
                  <TableCell>{item.classType}</TableCell>
                  <TableCell>{item.startDate}</TableCell>
                  <TableCell>{item.months}</TableCell>
                  <TableCell>
                     <DeleteStatus deleted={item.deleted} />
                  </TableCell>
                  <TableCell>{item.createdAt}</TableCell>
                  <TableCell></TableCell>                  
              </TableRow>
            )}
          </TableBody>
        </Table>
     )
}