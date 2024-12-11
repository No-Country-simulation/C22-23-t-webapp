import './PetListContainer.css'
import { PetList } from '../'
import { useEffect, useState } from 'react'
import { PetSearchForm } from '../PetSearchForm/PetSearchForm'

export function PetListContainer() {
    const [pets, setPets] = useState()
    const [loading, setLoading] = useState(true)
    const GREETING = "Espera mientras preparamos el listado de mascotas..."
    const ERROR_MESSAGE = "¡UPS! Parece que no hay mascotas para adoptar ahora..."

    const handleSearchSubmit = (searchFilters) => { fetchPets(searchFilters) }

    const fetchPets = async (searchParams = {}) => {
        try {
            let url = "http://localhost:8080/api/pets/search"
            
            if (Object.keys(searchParams).length > 0) {
                const filteredParams = []
                
                for (const key in searchParams) {
                    if (searchParams.hasOwnProperty(key)) {
                        const value = searchParams[key]
                        
                        // Solo parámetros con valor no vacío que además no sean "age"
                        if (key !== "age" && value !== "" && value !== null && value !== undefined) {
                            filteredParams.push(`${encodeURIComponent(key)}=${encodeURIComponent(value)}`)
                        }
                    }
                }
                
                if (filteredParams.length > 0) { url += `?${filteredParams.join("&")}` }
            }

            const petsResponse = await fetch(url)

            const petsData = await petsResponse.json()

            setPets(petsData.content)
            setLoading(false)
        } catch (error) {
            console.error(error)
        }
    }

    useEffect(() => { fetchPets() }, [])

    return (
        <main id="PetListContainer">
            <PetSearchForm onSearch={ handleSearchSubmit } />
            {loading && <h2 id="SearchLoadingGreet">{GREETING}</h2>}
            {!loading && pets && <PetList pets={pets} />}
            {!loading && !pets && <h2 id="SearchErrorMessage">{ERROR_MESSAGE}</h2>}
        </main>
    )
}
