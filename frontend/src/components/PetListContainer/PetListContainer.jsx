import './PetListContainer.css'
import { PetList } from '../'
import { useEffect, useState } from 'react'
// import { useParams } from 'react-router-dom'

export function PetListContainer() {
    const PETS = [
        {
            image: "1",
            name: "Buddy",
            age: "2 años",
            location: "Lugar donde se encuentra"
        },
        {
            image: "2",
            name: "Raza",
            age: "1 año",
            location: "Lugar donde se encuentra"
        },
        {
            image: "3",
            name: "Raza",
            age: "5 años",
            location: "Lugar donde se encuentra"
        },
        {
            image: "4",
            name: "Raza",
            age: "2 años",
            location: "Lugar donde se encuentra"
        },
        {
            image: "5",
            name: "Raza",
            age: "6 años",
            location: "Lugar donde se encuentra"
        },
        {
            image: "6",
            name: "Raza",
            age: "9 meses",
            location: "Lugar donde se encuentra"
        },
        {
            image: "7",
            name: "Raza",
            age: "6 años",
            location: "Lugar donde se encuentra"
        },
        {
            image: "8",
            name: "Raza",
            age: "2 años",
            location: "Lugar donde se encuentra"
        }
    ]      

    const [pets, setPets] = useState()
    const [loading, setLoading] = useState(true)
    const GREETING = "Espera mientras preparamos el listado de mascotas..."
    const ERROR_MESSAGE = "¡UPS! Parece que no hay mascotas para adoptar ahora..."
    // const { categoryId } = useParams()

    const fakeFetch = async () => {
        return new Promise((resolve, reject) => {
            setTimeout(() => {
                resolve(PETS) 
            }, 2000)
        })
    }

    useEffect(() => {
        fakeFetch()
            .then( response => setPets(response) )
            .catch( error => console.error("Error recibiendo datos:", error) )
            .finally( () => setLoading(false) )
    }, [])
    // }, [categoryId])

    return (
        <main id="PetListContainer">
            {loading && <h2 id="SearchLoadingGreet">{GREETING}</h2>}
            {!loading && pets && <PetList pets={pets} />}
            {!loading && !pets && <h2 id="SearchErrorMessage">{ERROR_MESSAGE}</h2>}
        </main>
    )
}
