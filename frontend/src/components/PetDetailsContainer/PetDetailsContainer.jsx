import './PetDetailsContainer.css'
import { PetDetails } from '../'
import { useEffect, useState } from 'react'

export function PetDetailsContainer() {
    const PETS = [
        {
            id: "1",
            name: "Buddy",
            age: "2 años",
            species: "Perro",
            breed: "Labrador",
            size: "Mediano",
            healthStatus: {
                vaccinated: true,
                sterilized: true,
                dewormed: true,
                medicalConditions: "Ninguna"
            },
            temperament: "Cariñoso y activo",
            compatibility: ["Niños", "Otros animales"],
            specialNeeds: "Necesita mucho ejercicio",
            location: "Refugio Animal City, Ciudad XYZ",
            shelterContact: {
                phone: "+1234567890",
                email: "contacto@refugioanimal.com"
            },
            images: [
                "1",
                "2",
                "3"
            ]
        },
        {
            id: "2",
            name: "Raza",
            age: "1 año",
            species: "Gato",
            breed: "Mestizo",
            size: "Pequeño",
            healthStatus: {
                vaccinated: true,
                sterilized: false,
                dewormed: true,
                medicalConditions: "Alergia a ciertos alimentos"
            },
            temperament: "Tranquilo y reservado",
            compatibility: ["Niños", "No otros animales"],
            specialNeeds: "Preferencia por espacios tranquilos",
            location: "Refugio Gatos Felices, Ciudad ABC",
            shelterContact: {
                phone: "+0987654321",
                email: "info@refugiogatosfelices.com"
            },
            images: [
                "4",
                "5",
                "6"
            ]
        },
        {
            id: "3",
            name: "Rocco",
            age: "5 años",
            species: "Perro",
            breed: "Pitbull",
            size: "Grande",
            healthStatus: {
                vaccinated: true,
                sterilized: true,
                dewormed: true,
                medicalConditions: "Condiciones de piel"
            },
            temperament: "Firme pero muy leal",
            compatibility: ["Niños mayores", "Otros perros"],
            specialNeeds: "Requiere medicación periódica",
            location: "Refugio Perros Guardianes, Ciudad XYZ",
            shelterContact: {
                phone: "+1122334455",
                email: "contact@refugioperrosguardianes.com"
            },
            images: [
                "7",
                "8",
                "5"
            ]
        },
        {
            id: "4",
            name: "Felicia",
            age: "2 años",
            species: "Gato",
            breed: "Siames",
            size: "Mediano",
            healthStatus: {
                vaccinated: true,
                sterilized: true,
                dewormed: false,
                medicalConditions: "Nada relevante"
            },
            temperament: "Juguetona y cariñosa",
            compatibility: ["Niños", "Otros gatos"],
            specialNeeds: "Espacios para escalar",
            location: "Refugio Gatos Felices, Ciudad ABC",
            shelterContact: {
                phone: "+2233445566",
                email: "info@refugiogatosfelices.com"
            },
            images: [
                "3",
                "1",
                "2"
            ]
        },
        {
            id: "5",
            name: "Rocky",
            age: "6 años",
            species: "Perro",
            breed: "Mestizo",
            size: "Grande",
            healthStatus: {
                vaccinated: true,
                sterilized: true,
                dewormed: true,
                medicalConditions: "Levele artritis"
            },
            temperament: "Protector y amigable",
            compatibility: ["Adultos", "Otros perros"],
            specialNeeds: "No apto para hogares con niños pequeños",
            location: "Refugio Perros y Amigos, Ciudad DEF",
            shelterContact: {
                phone: "+3344556677",
                email: "contact@refugioperrosyamigos.com"
            },
            images: [
                "4",
                "6",
                "8"
            ]
        }
    ]
    
    const [pet, setPet] = useState()
    const [loading, setLoading] = useState(true)
    const GREETING = "Espera mientras juntamos los detalles de esta mascota..."
    const ERROR_MESSAGE = "¡UPS! Parece que esta mascota no existe..."
    // const { petId } = useParams()

    const fakeFetch = async () => {
        return new Promise((resolve, reject) => {
            setTimeout(() => {
                resolve(PETS[0]) // Index 0 HARDCODEADO ya que es funcionalidad primitiva.
            }, 2000)
        })
    }

    useEffect(() => {
        fakeFetch()
            .then( response => setPet(response) )
            .catch( error => console.error("Error recibiendo datos:", error) )
            .finally( () => setLoading(false) )
    }, [])
    // }, [petId])

    return (
        <main id="PetDetailsContainer">
            {loading && <h2 id="PetDetailsLoadingGreet">{GREETING}</h2>}
            {!loading && pet && <PetDetails pet={pet} />}
            {!loading && !pet && <h2 id="PetDetailsErrorMessage">{ERROR_MESSAGE}</h2>}
        </main>
    )
}
