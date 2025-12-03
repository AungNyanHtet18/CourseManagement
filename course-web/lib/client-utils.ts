import { toast } from "sonner";
import { ClientError } from "./type";

export async function handle(func: ()=> Promise<void>) {
    try{
        await func() 
    }catch(e: any) {
        let messages:string[] = [e.message || "Unknown Error"]

        if(e.message) {
            const error = JSON.parse(e.message) //JSON.parse() parse the json string to object
            console.log( `e.message :  ${e.message}`);

            if(error?.type == "Client Error") {
            const clientError = error as ClientError
            messages = clientError.messages  
                  
            }
        }
        
        toast("Error",{
            description: messages
        })
    }

}