import PageTitle from '@/components/app/page-title'
import DeleteStatus from '@/components/app/status-component'
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table'
import * as courseClient from '@/lib/client/course.client'
import { ArrowRight } from 'lucide-react'
import Link from 'next/link'

export default async function CourseDetails(props: PageProps<'/courses/[id]'>) {
    
    const {id} = await props.params
    const details = await courseClient.findById(id) // (server function) cause it is invoked by server component
    
    return (
       <section className='space-y-6'>
         <PageTitle icon='BookOpen' title='Courses Page'
          subTitle={[details.Level, details.deleted ? "Deleted" : "Active", `Create at : ${details.createdAt}`]} 
          description={details.description} 
          editUrl={`/courses/edit?id=${id}`}/>

         <div>
            <h4 className='text-xl'>Classes</h4>
            <Table>
                <TableHeader>
                    <TableRow>
                       <TableHead>ID</TableHead>
                       <TableHead>Class Type</TableHead>
                       <TableHead>Start Date</TableHead>
                       <TableHead>Months</TableHead>
                       <TableHead>Deleted</TableHead>
                    </TableRow>
                </TableHeader>

                <TableBody>
                  {details.classes.map((item,index) => 
                    <TableRow key={index}>
                        <TableCell>{item.id}</TableCell>
                        <TableCell>{item.classType}</TableCell>
                        <TableCell>{item.startDate}</TableCell>
                        <TableCell>{item.months}</TableCell>
                        <TableCell><DeleteStatus deleted={item.deleted} /></TableCell>
                        <TableCell>
                           <Link href={`/classes/${item.id}`}>
                              <ArrowRight className='size-4' />
                           </Link>
                        </TableCell>
                    </TableRow>
                  )}
                </TableBody>
            </Table>
         </div>
       </section>
     )
}