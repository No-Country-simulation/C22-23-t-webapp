import './PetSearchForm.css'
import { BASENAME } from '../../config.js'
import { useState, useEffect, useRef } from 'react'
import { PetSearchFormSpecies, PetSearchFormBreed, PetSearchFormGender, PetSearchFormAge } from '../'

export function PetSearchForm({ onSearch }) {
    const [ isFilterMenuOpen, setIsFilterMenuOpen ] = useState(false)
    const [searchFilters, setSearchFilters] = useState({
        name: "",
        species: "",
        breed: "",
        gender: "",
        age: ""
    })

    const isFirstRender = useRef(true)
    const previousSearchFilters = useRef(searchFilters)

    const handleInputChange  = (event) => {
        const { name, value } = event.target

        setSearchFilters((previousFilters) => ({
            ...previousFilters,
            [name]: value,
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
                />

                {/* Filtro por Raza */}
                <PetSearchFormBreed
                    onInputChange={ handleInputChange }
                    inputValue={ searchFilters.species }
                />

                {/* Filtro por Sexo */}
                <PetSearchFormGender
                    onInputChange={ handleInputChange }
                    inputValue={ searchFilters.species }
                />

                {/* Filtro por Edad */}
                <PetSearchFormAge
                    onInputChange={ handleInputChange }
                    inputValue={ searchFilters.species }
                />
            </div>
        </form>
    )
}
