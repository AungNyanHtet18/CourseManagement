import { clsx, type ClassValue } from "clsx"
import { twMerge } from "tailwind-merge"
import { OptionItem } from "./type"

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

export const POST_CONFIG:RequestInit = {
   method: "POST",
   headers: {
     "Content-Type" : "application/json"
   }
} 

export const PUT_CONFIG:RequestInit = {
   method: "PUT",
   headers: {
     "Content-Type" : "application/json"
   }
} 

export const LEVELS_OPTIONS:OptionItem[] = [
      {key: "Basic", value: "Basic"},
      {key: "Intermediate", value: "Intermediate"},
      {key: "Advance", value: "Advance"},
      {key: "AllInOne", value: "ALL In One"}]

export const STATUS_OPTIONS: OptionItem[] = [
    {key: "false", value: "Active"},
    {key: "true", value: "Deleted"}
]

export const TYPE_OPTION: OptionItem[] = [
   {key: "Zoom", value: "Zoom"},
   {key: "Recorded", value: "Recorded"},
   {key: "Weekend", value: "Weekend"},
   {key: "Weekday", value: "Weekday"},
]

export const DAYS_OF_WEEK:OptionItem[] = [
    {key: "MONDAY",value: "Monday"},
    {key: "TUESDAY",value: "Tuesday"},
    {key: "WEDNESDAY",value: "Wednesday"},
    {key: "THURSDAY",value: "Thursday"},
    {key: "FRIDAY",value: "Friday"},
    {key: "SATURDAY",value: "Saturday"},
    {key: "SUNDAY",value: "Sunday"}
]
