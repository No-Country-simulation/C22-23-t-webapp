import './PetDetails.css'
import { PetDetailsGallery } from '../'

export function PetDetails({ pet }) {
    return (
        <>
            <section id="PetDetailsHeader">
                <h2 id="PetDetailsName"> { pet.name } </h2>
                <p id="PetDetailsSpecies" className="PetDetailsText">
                    <strong>Especie: </strong>
                    { pet.species } 
                </p>
                <p id="PetDetailsBreed" className="PetDetailsText">
                    <strong>Raza: </strong>
                    { pet.breed }
                </p>
                <p id="PetDetailsSize" className="PetDetailsText">
                    <strong>Tamaño: </strong>
                    { pet.size }
                </p>
                <p id="PetDetailsAge" className="PetDetailsText">
                    <strong>Edad: </strong>
                    { pet.age }
                </p>
            </section>
        
            <section id="PetDetailsHealthStatusContainer">
                <h3 id="PetDetailsHealthStatusLabel" className="PetDetailsSubtitle">Estado de Salud</h3>
                <ul id="PetDetailsHealthStatusList">
                    <li id="PetDetailsHealthStatusVaccinated" className="PetDetailsHealthStatusListItem">
                        <strong>Vacunado: </strong>
                        { pet.healthStatus.vaccinated ? 'Sí' : 'No' }
                    </li>
                    <li id="PetDetailsHealthStatusSterilized" className="PetDetailsHealthStatusListItem">
                        <strong>Esterilizado: </strong>
                        { pet.healthStatus.sterilized ? 'Sí' : 'No' }
                    </li>
                    <li id="PetDetailsHealthStatusDewormed" className="PetDetailsHealthStatusListItem">
                        <strong>Desparacitado: </strong>
                        { pet.healthStatus.dewormed ? 'Sí' : 'No' }
                    </li>
                    <li id="PetDetailsHealthStatusMedicalConditions" className="PetDetailsHealthStatusListItem">
                        <strong>Condiciones médicas: </strong>
                        { pet.healthStatus.medicalConditions }
                    </li>
                </ul>
            </section>
        
            <section id="PetDetailsTemperamentContainer">
                <h3 id="PetDetailsTemperamentLabel" className="PetDetailsSubtitle">Temperamento</h3>
                <p id="PetDetailsTemperament" className="PetDetailsText"> { pet.temperament } </p>
            </section>
        
            <section id="PetDetailsCompatibilityContainer">
                <h3 id="PetDetailsCompatibilityLabel" className="PetDetailsSubtitle">Compatibilidad</h3>
                <p id="PetDetailsCompatibility" className="PetDetailsText"> { pet.compatibility.length > 0 ? pet.compatibility.join(', ') : 'No especificado' } </p>
            </section>
        
            <section id="PetDetailsSpecialNeedsContainer">
                <h3 id="PetDetailsSpecialNeedsLabel" className="PetDetailsSubtitle">Necesidades Especiales</h3>
                <p id="PetDetailsSpecialNeeds" className="PetDetailsText"> { pet.specialNeeds } </p>
            </section>
        
            <section id="PetDetailsLocationContainer">
                <h3 id="PetDetailsLocationLabel" className="PetDetailsSubtitle">Ubicación</h3>
                <p id="PetDetailsLocation" className="PetDetailsText"> { pet.location } </p>
            </section>
        
            <section id="PetDetailsRefugeeContainer">
                <h3 id="PetDetailsRefugeeLabel" className="PetDetailsSubtitle">Contacto del Refugio</h3>
                <p id="PetDetailsRefugeePhone" className="PetDetailsText">
                    <strong>Teléfono: </strong>
                    { pet.shelterContact.phone }
                </p>
                <p id="PetDetailsRefugeeEmail" className="PetDetailsText"> 
                    <strong>Email: </strong>
                    <a href={ `mailto:${pet.shelterContact.email}` }> { pet.shelterContact.email } </a>
                </p>
            </section>
        
            <section id="PetDetailsGalleryContainer">
                <h3 id="PetDetailsGalleryLabel" className="PetDetailsSubtitle">Imágenes</h3>
                <PetDetailsGallery images={ pet.images } petName={ pet.name } />
            </section>
        </>
    )
}