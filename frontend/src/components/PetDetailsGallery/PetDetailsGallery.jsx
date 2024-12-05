import './PetDetailsGallery.css'
import { BASENAME } from '../../config.js'
import { useState, useRef } from 'react'

export function PetDetailsGallery({ images, petName }) {
    if (images.length === 0) { return <p>No hay imágenes disponibles.</p> }

    const [selectedImageIndex, setSelectedImageIndex] = useState(0)
    const scrollRef = useRef(null)

    // Update the selected image based on scroll position (Mobile)
    const handleScroll = () => {
        const scrollLeft = scrollRef.current.scrollLeft
        const width = scrollRef.current.offsetWidth
        const index = Math.round(scrollLeft / width)
        setSelectedImageIndex(index)
    }

    return (
        <div id="PetDetailsGallery" className="pet-details-gallery">
            {/* Thumbnails (Desktop Only) */}
            <div className="thumbnails">
                {images.map((imageId, index) => (
                    <img
                        key={index}
                        src={`${BASENAME}/pet-${imageId}.jpeg`}
                        alt={`Foto ${index + 1} de la mascota ${petName}`}
                        className={`thumbnail ${index === selectedImageIndex ? 'active' : ''}`}
                        onClick={() => setSelectedImageIndex(index)}
                    />
                ))}
            </div>
            
            {/* Main Image */}
            <div className="main-image">
                <img
                    src={`${BASENAME}/pet-${images[selectedImageIndex]}.jpeg`}
                    alt={`Foto ${selectedImageIndex + 1} de la mascota ${petName}`}
                />
            </div>

            {/* Horizontal Scrolling for Mobile */}
            <div
                className="mobile-gallery"
                ref={scrollRef}
                onScroll={handleScroll}
            >
                {images.map((imageId, index) => (
                    <img
                        key={index}
                        src={`${BASENAME}/pet-${imageId}.jpeg`}
                        alt={`Foto ${index + 1} de la mascota ${petName}`}
                        className="mobile-image"
                    />
                ))}
            </div>

            {/* Dots for Navigation */}
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
