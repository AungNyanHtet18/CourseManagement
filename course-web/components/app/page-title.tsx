import { IconType } from "@/lib/type";
import IconComponent from "./icon-component";
import { Badge } from "../ui/badge";

export default function PageTitle({icon, title, subTitle} : {icon: IconType, title: string, subTitle?: string[]}){
    
    if(subTitle) {
         return (
            <header className="flex items-center gap-2">
                <div className="pt-1">
                    <IconComponent icon={icon} className="size-7" />
                </div>
                <div>
                    <div className="text-xl">{title}</div>
                    <div className="flex gap-2">
                        {subTitle.map((item, index) => 
                            <Badge key={index}>
                                {item}
                            </Badge>
                        )}
                    </div>
                </div>
            </header>
         )
    }
    

    return (
        <header className="flex items-center gap-2">
            <div>
            <IconComponent icon={icon} className="size-7" />
            </div>
            <div>
                <div className="text-xl">{title}</div>
            </div>
        </header>
     )
}