import { Control, FieldValues, Path } from "react-hook-form";
import { FormControl, FormField, FormItem, FormLabel, FormMessage } from "../ui/form";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "../ui/select";
import { OptionItem } from "@/lib/type";

type FormsInputProps<T extends FieldValues> = { 
    control: Control<T>
    path: Path<T> 
    options: OptionItem[]
    label?: string
    className?: string
    placeholder?: string
}

export default function FormsSelect<T extends FieldValues>({control, path, options,  label, className, placeholder} : FormsInputProps<T>) { 

    return (
        <FormField control={control} name={path} render={({field}) => 
            <FormItem className={className}>
                {label && <FormLabel>{label}</FormLabel>}

                <Select value={field.value} onValueChange={field.onChange}>
                  <FormControl>
                     <SelectTrigger className="w-full">
                        <SelectValue placeholder= {placeholder  || " Select One"} />
                     </SelectTrigger>
                  </FormControl>

                  <SelectContent>
                      {options.map((option, index) => 
                        <SelectItem  key={index} value={option.key}>
                            {option.value}
                        </SelectItem>
                        )}
                  </SelectContent>

                </Select>
                <FormMessage/>
            </FormItem>
        } />
    )
}
