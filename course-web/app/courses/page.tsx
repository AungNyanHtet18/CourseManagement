import CourseSearchForm from "@/components/app/course-search-form";
import PageTitle from "@/components/app/page-title";

import * as courseClient from "@/lib/client/course.client"
import { CourseListItem } from "@/lib/type";


export default async function CourseManagement(props : PageProps<'/courses'>) {
  
    const searchParams = await props.searchParams
    const result = await courseClient.search(searchParams)

    return (
        <section className="space-y-4">

            <PageTitle icon="BookOpen" title="Course Management"/>

            <CourseSearchForm />

            <SearchResult list={result} />

        </section>
     )
}

function SearchResult({list} : {list : CourseListItem[]}) {
     
    return (
        <></>
    )
}