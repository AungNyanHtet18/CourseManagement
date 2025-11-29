import PageTitle from '@/components/app/page-title'
import { Badge } from '@/components/ui/badge'
import * as courseClient from '@/lib/client/course.client'

export default async function CourseDetails(props: PageProps<'/courses/[id]'>) {
    
    const {id} = await props.params
    const details = await courseClient.findById(id) // (server function) cause it is invoked by server component
    
    return (
       <section>
         <PageTitle icon='BookOpen' title='Courses Page' subTitle={[details.Level, details.deleted ? "Deleted" : "Active", details.createdAt]} />

         <div>
            <pre>{JSON.stringify(details, null, 2)}</pre>
         </div>
       </section>
     )
}