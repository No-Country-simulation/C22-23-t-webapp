import './PetListContainer.css'
import { PetList } from '../'
import { useEffect, useState } from 'react'

export function PetListContainer() {
    const [pets, setPets] = useState()
    const [loading, setLoading] = useState(true)
    const GREETING = "Espera mientras preparamos el listado de mascotas..."
    const ERROR_MESSAGE = "¡UPS! Parece que no hay mascotas para adoptar ahora..."

    const fetchPets = async () => {
        try {
            const petsResponse = await fetch("http://localhost:8080/api/pets/search")

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
            {loading && <h2 id="SearchLoadingGreet">{GREETING}</h2>}
            {!loading && pets && <PetList pets={pets} />}
            {!loading && !pets && <h2 id="SearchErrorMessage">{ERROR_MESSAGE}</h2>}
        </main>
    )
}
