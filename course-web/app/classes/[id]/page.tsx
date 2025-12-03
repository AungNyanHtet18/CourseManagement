import PageTitle from '@/components/app/page-title'
import * as classClient from '@/lib/client/classs-client'

export default async function ClassDetails(props: PageProps<'/classes/[id]'>) {

      const {id} = await props.params
      const details = await classClient.findById(id)

      console.log(details);

     return (
         <section>
             <PageTitle icon='CalendarCheck' title={`${details.courseName} ${details.classType}`} 
                        description={`${details.startDate} intake`} />

         </section>
     )
}