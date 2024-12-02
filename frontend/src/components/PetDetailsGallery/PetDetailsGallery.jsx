import './PetDetailsGallery.css'

export function PetDetailsGallery ({ images, petName }) {
    if (images.length === 0) { return <p>No hay imágenes disponibles.</p> }

    return (
        <div id="PetDetailsGallery" className="image-gallery">
            {
                images.map((imageId, index) => (
                    <img
                        key={ index }
                        src={ `./pet-${imageId}.jpeg` }
                        alt={ `Foto ${index + 1} de la mascota ${petName}` }
                        className="PetDetailsGalleryPhoto"
                    />
                ))
            }
        </div>
    )
}