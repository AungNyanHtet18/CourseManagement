import { IconType } from "@/lib/type";
import IconComponent from "./icon-component";

export default function PageTitle({icon, title} : {icon: IconType, title: string}){
     return (
        <header className="flex items-center gap-2">
            <IconComponent icon={icon} className="size-7" />
            <span className="text-xl">{title}</span>
        </header>
     )
}