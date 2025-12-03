import type { Metadata } from "next";
import "./globals.css";
import AppMenu from "@/components/app/top-menu";
import { Toaster } from "sonner";

export const metadata: Metadata = {
  title: "Fetch Demo",
  description: "Learning About Rest Client via fetch",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body>
        <AppMenu/>

        <main className="px-16 py-4">
            {children}  
        </main>

        <Toaster position="bottom-right" duration={1 * 4000} /> {/*duration means how many second  error will be shown on screen */}
      </body>
    </html>
  );
}
