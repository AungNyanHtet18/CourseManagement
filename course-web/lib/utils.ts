import { clsx, type ClassValue } from "clsx"
import { twMerge } from "tailwind-merge"
import { object } from "zod"

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs))
}

export function queryString(form : {[key:string] : any}){
   const searchParam = new URLSearchParams

   Object.keys(form).forEach(key => { 
     searchParam.append(key, form[key] as string)
   })

   return searchParam.toString()
}
