import './PetSearchForm.css'
import { BASENAME } from '../../config.js'
import { useState, useEffect, useRef } from 'react'
import { PetSearchFormSpecies, PetSearchFormBreed, PetSearchFormGender, PetSearchFormAge } from '../'

export function PetSearchForm({ onSearch }) {
    const [ isFilterMenuOpen, setIsFilterMenuOpen ] = useState(false)
    const [ speciesValue, setSpeciesValue ] = useState("")
    const [ searchFilters, setSearchFilters ] = useState({
        name: "",
        species: "",
        breed: "",
        gender: "",
        age: "",        // Valor codificado que simplifica PetSearchFormAge
        minAge: "",
        maxAge: "",
    })

    const isFirstRender = useRef(true)
    const previousSearchFilters = useRef(searchFilters)

    const decodeAgeValue = (age) => {
        switch (age) {
            case "0-365": return ({ minAge: "", maxAge: "365"})

            case "365-1825": return ({ minAge: "365", maxAge: "1825"})

            case "1825-3650": return ({ minAge: "1825", maxAge: "3650"})

            case "3650-inf": return ({ minAge: "3650", maxAge: ""})
        
            default: return ({ minAge: "", maxAge: ""})
        }
    }

    const handleInputChange  = (event) => {
        const { name, value } = event.target

        setSearchFilters((previousFilters) => ({
            ...previousFilters,
            [name]: value,
            ...(name === "species" && { breed: "" }), // Resetea el filtro breed si species cambia
            ...(name === "age" && value !== "" && decodeAgeValue(value)),
        }))
    }
    
    const handleNameSearchSubmit  = (event) => {
        event.preventDefault()
        onSearch(searchFilters)
    }

    const handleFilterMenuClick = () => { setIsFilterMenuOpen(!isFilterMenuOpen) }

    useEffect(() => {
        if (isFirstRender.current) {
            isFirstRender.current = false
            return
        }
        
        // Comprobar si los filtros realmente cambiaron antes de llamar a onSearch
        if (JSON.stringify(previousSearchFilters.current) !== JSON.stringify(searchFilters)) {
            previousSearchFilters.current = searchFilters
            onSearch(searchFilters)
        }
    }, [searchFilters, onSearch])

    return (
        <form id="PetSearchForm" onSubmit={ handleNameSearchSubmit }>
            <div id="PetSearchBarContainer">
                <button id="PetSearchFilterButton" type="button" onClick={ handleFilterMenuClick }>
                    <img
                        id="PetSearchFilterButtonIcon"
                        src={`${BASENAME}/sh-icon-${isFilterMenuOpen ? 'close' : 'filter-list'}.svg`}
                        alt={`Icono ${isFilterMenuOpen ? 'cerrar' : 'abrir'} menú de filtros`}
                    />
                </button>
                <div id="PetSearchByName">
                    <input id="PetSearchByNameInput" type="text" placeholder="Buscar por nombre..." name="name" value={ searchFilters.name } onChange={ handleInputChange }/>
                    <button id="PetSearchByNameSubmit" type="submit">
                        <img
                            id="PetSearchByNameIcon"
                            src={`${BASENAME}/search-logo.svg`}
                            alt="Icono botón buscar por nombre"
                        />
                    </button>
                </div>
            </div>
            <div id="PetSearchFilterMenuContainer" className={ isFilterMenuOpen ? 'open' : '' }>
                <h3 id="PetSearchFilterMenuLabel">Filtrar por:</h3>

                {/* Filtro por Especie */}
                <PetSearchFormSpecies
                    onInputChange={ handleInputChange }
                    inputValue={ searchFilters.species }
                    speciesValue={ setSpeciesValue } // Nótese que es el setter del state.
                />

                {/* Filtro por Raza */}
                <PetSearchFormBreed
                    onInputChange={ handleInputChange }
                    inputValue={ searchFilters.breed }
                    speciesValue={ speciesValue } // Nótese que es el state per se.
                />

                {/* Filtro por Sexo */}
                <PetSearchFormGender
                    onInputChange={ handleInputChange }
                    inputValue={ searchFilters.gender }
                />

                {/* Filtro por Edad */}
                <PetSearchFormAge
                    onInputChange={ handleInputChange }
                    inputValue={ searchFilters.age }
                />
            </div>
        </form>
    )
}
