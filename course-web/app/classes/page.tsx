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
import { LEVELS_OPTIONS, STATUS_OPTIONS, TYPE_OPTION } from "@/lib/utils";
import FormsInput from "@/components/forms/forms-input";
import { Button } from "@/components/ui/button";
import {ArrowRight, Plus, Search } from "lucide-react";
import Link from "next/link";

export default function ClassManagement() {

     const [page, setPage] =useState<PageResult<ClassListItem> | undefined>(undefined)

      useEffect(()=> {
       search({page: 0})
        
      },[setPage])


     async function search(form: ClassSearch) {
         if(form.Level == "-1") {
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
          Level: "-1",
          type: "-1",
          deleted: "-1",
          keyword: "",
          page: 0
       }
   })

   return(
      <Form {...form} >
         <form onSubmit={form.handleSubmit(onSearch)} className="flex gap-4 items-end">
            <FormsSelect control={form.control} path="type" 
               options={[{key: "-1",value: "Select All"}, ...TYPE_OPTION]} 
               label="Class Type" className="w-[180px]" />

            <FormsSelect control={form.control} path="Level"
               options={[{key: "-1", value: "Select All"}, ...LEVELS_OPTIONS]} 
               label="Level" className="w-[180px]" />

            <FormsSelect control={form.control} path="deleted"  
               options={[{key: "-1", value: "Select All" }   ,...STATUS_OPTIONS]} 
               label="Status" className="w-[180px]"/>
            
            <FormsInput control={form.control} path="keyword" 
               label="Keyword" placeholder="Search Keyword" className="w-[250px]" />
         
           <div className="space-x-2">
              <Button type="submit">
                  <Search/> Search
              </Button>

              <Button type="button" asChild>
                 <Link href={"/classes/edit"}>
                    <Plus/> Add New
                 </Link>
              </Button>
           </div>

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
                  <TableCell>
                     <Link href={`/classes/${item.id}`}>
                         <ArrowRight className="size-4"/> 
                     </Link>   
                  </TableCell>                  
              </TableRow>
            )}
          </TableBody>
        </Table>
     )
}