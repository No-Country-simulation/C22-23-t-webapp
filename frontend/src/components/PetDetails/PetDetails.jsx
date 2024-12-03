import './PetDetails.css'
import { PetDetailsGallery } from '../'
import { Link } from 'react-router-dom'

export function PetDetails({ pet }) {
    return (
        <>
            <section id="PetDetailsGalleryContainer">
                <PetDetailsGallery images={ pet.images } petName={ pet.name } />
            </section>

            <section id="PetDetailsCTAContainer">
                <button id="PetDetailsAdoptButton" className="PetDetailsCTA"><Link to={"/login"}>Solicitar Adopción</Link></button>
            </section>
            
            <section id="PetDetailsInfoContainer" className="PetInfoContainer">
                <h2 id="PetDetailsInfo" className="PetDetailsContainerLabel">Información de la mascota</h2>
                
                <p id="PetDetailsNameLabel" className="PetDetailsLabel"><strong>Nombre</strong></p>
                <p id="PetDetailsName" className="PetDetailsText">{ pet.name }</p>

                <p id="PetDetailsAgeLabel" className="PetDetailsLabel"><strong>Edad</strong></p>
                <p id="PetDetailsAge" className="PetDetailsText">{ pet.age }</p>

                <p id="PetDetailsSpeciesLabel" className="PetDetailsLabel"><strong>Especie</strong></p>
                <p id="PetDetailsSpecies" className="PetDetailsText">{ pet.species }</p>

                <p id="PetDetailsBreedLabel" className="PetDetailsLabel"><strong>Raza</strong></p>
                <p id="PetDetailsBreed" className="PetDetailsText">{ pet.breed }</p>

                <p id="PetDetailsSizeLabel" className="PetDetailsLabel"><strong>Tamaño</strong></p>
                <p id="PetDetailsSize" className="PetDetailsText">{ pet.size }</p>
            </section>

            <section id="PetDetailsHealthStatusContainer" className="PetInfoContainer">
                <h2 id="PetDetailsHealthStatus" className="PetDetailsContainerLabel">Salud de la mascota</h2>

                <p id="PetDetailsHealthStatusVaccinatedLabel" className="PetDetailsLabel"><strong>Vacunado</strong></p>
                <p id="PetDetailsHealthStatusVaccinated" className="PetDetailsText">{ pet.healthStatus.vaccinated ? 'Sí' : 'No' }</p>

                <p id="PetDetailsHealthStatusSterilizedLabel" className="PetDetailsLabel"><strong>Esterilizado</strong></p>
                <p id="PetDetailsHealthStatusSterilized" className="PetDetailsText">{ pet.healthStatus.sterilized ? 'Sí' : 'No' }</p>

                <p id="PetDetailsHealthStatusDewormedLabel" className="PetDetailsLabel"><strong>Desparacitado</strong></p>
                <p id="PetDetailsHealthStatusDewormed" className="PetDetailsText">{ pet.healthStatus.dewormed ? 'Sí' : 'No' }</p>

                <p id="PetDetailsHealthStatusMedicalConditionsLabel" className="PetDetailsLabel"><strong>Condiciones médicas</strong></p>
                <p id="PetDetailsHealthStatusMedicalConditions" className="PetDetailsText">{ pet.healthStatus.medicalConditions }</p>
            </section>

            <section id="PetDetailsExtraInfoContainer" className="PetInfoContainer">
                <h2 id="PetDetailsExtraInfo" className="PetDetailsContainerLabel">Información adicional de la mascota</h2>
                
                <p id="PetDetailsTemperamentLabel" className="PetDetailsLabel"><strong>Temperamento</strong></p>
                <p id="PetDetailsTemperament" className="PetDetailsText"> { pet.temperament } </p>
            
                <p id="PetDetailsCompatibilityLabel" className="PetDetailsLabel"><strong>Compatibilidad</strong></p>
                <p id="PetDetailsCompatibility" className="PetDetailsText"> { pet.compatibility.length > 0 ? pet.compatibility.join(', ') : 'No especificado' } </p>
            
                <p id="PetDetailsSpecialNeedsLabel" className="PetDetailsLabel"><strong>Necesidades Especiales</strong></p>
                <p id="PetDetailsSpecialNeeds" className="PetDetailsText"> { pet.specialNeeds } </p>
            </section>
            
            <section id="PetDetailsContactContainer" className="PetInfoContainer">
                <h2 id="PetDetailsContact" className="PetDetailsContainerLabel">Información de contacto</h2>

                <p id="PetDetailsLocationLabel" className="PetDetailsLabel"><strong>Ubicación</strong></p>
                <p id="PetDetailsLocation" className="PetDetailsText"> { pet.location } </p>

                <p id="PetDetailsRefugeePhoneLabel" className="PetDetailsLabel"><strong>Teléfono</strong></p>
                <p id="PetDetailsRefugeePhone" className="PetDetailsText"> { pet.shelterContact.phone } </p>

                <p id="PetDetailsRefugeeEmailLabel" className="PetDetailsLabel"><strong>Email</strong></p>
                <p id="PetDetailsRefugeeEmail" className="PetDetailsText"><a href={ `mailto:${pet.shelterContact.email}` }> { pet.shelterContact.email } </a></p>
            </section>
        </>
    )
}
