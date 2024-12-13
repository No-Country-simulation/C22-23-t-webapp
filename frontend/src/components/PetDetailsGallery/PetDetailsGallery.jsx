import './PetDetailsGallery.css'
import { BASENAME } from '../../config.js'
import { useState, useRef } from 'react'

export function PetDetailsGallery({ images, petName }) {
    if (images.length === 0) { return <p>No hay imágenes disponibles.</p> }

    const [selectedImageIndex, setSelectedImageIndex] = useState(0)
    const scrollRef = useRef(null)

    // Actualizar la imagen seleccionada acorde a la posición del scroll (Mobile)
    const handleScroll = () => {
        const scrollLeft = scrollRef.current.scrollLeft
        const width = scrollRef.current.offsetWidth
        const index = Math.round(scrollLeft / width)
        setSelectedImageIndex(index)
    }

    return (
        <div id="PetDetailsGallery" className="pet-details-gallery">
            {/* Miniaturas (Desktop) */}
            <div className="thumbnails">
                {images.map((image, index) => (
                    <img
                        key={index}
                        src={image.thumbnailUrl}
                        alt={`Foto ${index + 1} de la mascota ${petName}`}
                        className={`thumbnail ${index === selectedImageIndex ? 'active' : ''}`}
                        onClick={() => setSelectedImageIndex(index)}
                    />
                ))}
            </div>
            
            {/* Imagen principal */}
            <div className="main-image">
                <img
                    src={ images[selectedImageIndex].url }
                    alt={`Foto ${selectedImageIndex + 1} de la mascota ${petName}`}
                />
            </div>

            {/* Scroll horizontal para mobile */}
            <div
                className="mobile-gallery"
                ref={scrollRef}
                onScroll={handleScroll}
            >
                {images.map((image, index) => (
                    <img
                        key={index}
                        src={ image.thumbnailUrl }
                        alt={`Foto ${index + 1} de la mascota ${petName}`}
                        className="mobile-image"
                    />
                ))}
            </div>

            {/* Puntos para navegación */}
            <div className="dots">
                {images.map((_, index) => (
                    <button
                        key={index}
                        className={`dot ${index === selectedImageIndex ? 'active' : ''}`}
                        onClick={() => {
                            setSelectedImageIndex(index)
                            if (scrollRef.current) {
                                scrollRef.current.scrollTo({
                                    left: index * scrollRef.current.offsetWidth,
                                    behavior: 'smooth',
                                })
                            }
                        }}
                    />
                ))}
            </div>
        </div>
    )
}
