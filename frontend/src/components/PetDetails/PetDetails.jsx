import './PetDetails.css'
import { PetDetailsGallery } from '../'
import { Link } from 'react-router-dom'

export function PetDetails({ pet }) {
    const CONTACT_INFO = {
        fullAddress: "Refugio Animal City, Ciudad XYZ",
        phone: "+1234567890",
        email: "contacto@refugioanimal.com",
    }

    function createRandomParagraph(stringArray, ammountOfLines) {
        if (ammountOfLines > stringArray.length) {
            throw new Error("La cantidad solicitada excede el número de elementos disponibles en el array.")
        }
      
        // Selecciona índices al azar sin repetición
        const randomIndexes = []
        while (randomIndexes.length < ammountOfLines) {
            const index = Math.floor(Math.random() * stringArray.length)
            if (!randomIndexes.includes(index)) {
                randomIndexes.push(index)
            }
        }

        // Crea un string concatenando los elementos seleccionados con ". "
        const oraciones = randomIndexes.map(index => stringArray[index])
        return oraciones.join(". ") + "."
    }

    return (
        <>
            <section id="PetDetailsGalleryContainer">
                <PetDetailsGallery images={ pet.photo } petName={ pet.name } />
            </section>

            <section id="PetDetailsCTAContainer">
                <button id="PetDetailsAdoptButton" className="PetDetailsCTA"><Link to={"/login"}>Solicitar Adopción</Link></button>
            </section>
            
            <section id="PetDetailsInfoContainer" className="PetInfoContainer">
                <h2 id="PetDetailsInfo" className="PetDetailsContainerLabel">Información de la mascota</h2>
                <div className="PetInfoList">
                    <p id="PetDetailsNameLabel" className="PetDetailsLabel"><strong>Nombre</strong></p>
                    <p id="PetDetailsName" className="PetDetailsText">{ pet.name }</p>

                    <p id="PetDetailsAgeLabel" className="PetDetailsLabel"><strong>Edad</strong></p>
                    <p id="PetDetailsAge" className="PetDetailsText">{ `${pet.ageInDays} días` }</p>

                    <p id="PetDetailsSpeciesLabel" className="PetDetailsLabel"><strong>Especie</strong></p>
                    <p id="PetDetailsSpecies" className="PetDetailsText">{ pet.species }</p>

                    <p id="PetDetailsBreedLabel" className="PetDetailsLabel"><strong>Raza</strong></p>
                    <p id="PetDetailsBreed" className="PetDetailsText">{ pet.breedName }</p>

                    <p id="PetDetailsSizeLabel" className="PetDetailsLabel"><strong>Género</strong></p>
                    <p id="PetDetailsSize" className="PetDetailsText">{ pet.gender }</p>
                </div>
            </section>

            <section id="PetDetailsHealthStatusContainer" className="PetInfoContainer">
                <h2 id="PetDetailsHealthStatus" className="PetDetailsContainerLabel">Salud de la mascota</h2>
                <div className="PetInfoList">
                    <p id="PetDetailsHealthStatusVaccinatedLabel" className="PetDetailsLabel"><strong>Vacunado</strong></p>
                    <p id="PetDetailsHealthStatusVaccinated" className="PetDetailsText">{ pet.healthStatus.vaccinated ? 'Sí' : 'No' }</p>

                    <p id="PetDetailsHealthStatusSterilizedLabel" className="PetDetailsLabel"><strong>Castrado</strong></p>
                    <p id="PetDetailsHealthStatusSterilized" className="PetDetailsText">{ pet.isCastrated ? 'Sí' : 'No' }</p>

                    <p id="PetDetailsHealthStatusDewormedLabel" className="PetDetailsLabel"><strong>Desparacitado</strong></p>
                    <p id="PetDetailsHealthStatusDewormed" className="PetDetailsText">{ pet.healthStatus.dewormed ? 'Sí' : 'No' }</p>

                    <p id="PetDetailsHealthStatusMedicalConditionsLabel" className="PetDetailsLabel"><strong>Condiciones médicas</strong></p>
                    <p id="PetDetailsHealthStatusMedicalConditions" className="PetDetailsText">{ pet.healthStatus }</p>
                </div>
            </section>

            <section id="PetDetailsExtraInfoContainer" className="PetInfoContainer">
                <h2 id="PetDetailsExtraInfo" className="PetDetailsContainerLabel">Información adicional de la mascota</h2>
                
                <p id="PetDetailsSpecialNeedsLabel" className="PetDetailsLabel"><strong>Comentarios</strong></p>
                <p id="PetDetailsSpecialNeeds" className="PetDetailsText"> { pet.comments } </p>
            </section>
            
            <section id="PetDetailsContactContainer" className="PetInfoContainer">
                <h2 id="PetDetailsContact" className="PetDetailsContainerLabel">Información de contacto</h2>
                <div className="PetInfoList">
                    <p id="PetDetailsLocationLabel" className="PetDetailsLabel"><strong>Ubicación</strong></p>
                    <p id="PetDetailsLocation" className="PetDetailsText"> { CONTACT_INFO.fullAddress } </p>

                    <p id="PetDetailsRefugeePhoneLabel" className="PetDetailsLabel"><strong>Teléfono</strong></p>
                    <p id="PetDetailsRefugeePhone" className="PetDetailsText"> { CONTACT_INFO.phone } </p>

                    <p id="PetDetailsRefugeeEmailLabel" className="PetDetailsLabel"><strong>Email</strong></p>
                    <p id="PetDetailsRefugeeEmail" className="PetDetailsText"><Link to={ `mailto:${CONTACT_INFO.email}` }> { CONTACT_INFO.email } </Link></p>
                </div>
            </section>
        </>
    )
}
