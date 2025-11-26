import "server-only"
import { RestClientException } from "./type"

export async function request(path: string, init: RequestInit = {}) {
     const endpoint = `${process.env.REST_API}/${path}`

     const response = await fetch(endpoint, init)

     if(!response.ok) {
          const message = await response.json() as string[]
          throw new RestClientException(message)
     }

     return response
}