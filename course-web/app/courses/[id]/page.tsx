import * as courseClient from '@/lib/client/course.client'

export default async function CourseDetails(props: PageProps<'/courses/[id]'>) {
    
    const {id} = await props.params
    const details = await courseClient.findById(id) // (server function) cause it is invoked by server component
    
    return (
        <></>
     )
}