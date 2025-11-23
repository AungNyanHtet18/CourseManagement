import "server-only"

export async function request(path: string, init: RequestInit = {}) {
     const endpoint = `${process.env.REST_API}/${path}`

     const response = await fetch(endpoint, init)

     if(!response.ok) {
         
     }

}

