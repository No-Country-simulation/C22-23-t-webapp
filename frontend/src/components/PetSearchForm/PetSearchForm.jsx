import './PetSearchForm.css'
import { BASENAME } from '../../config.js'
import { useState, useEffect, useRef } from 'react'

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
                <select
                    name="species"
                    id="PetSearchFilterSpecies"
                    className="PetSearchFilter"
                    onChange={ handleInputChange }
                    value={ searchFilters.species }
                >
                    <option value="" className="PetSearchFilterOption" disabled>Especie</option>
                    <option value="DOG" className="PetSearchFilterOption">Perro</option>
                    <option value="CAT" className="PetSearchFilterOption">Gato</option>
                    <option value="OTHER" className="PetSearchFilterOption">Otros</option>
                </select>

                {/* Filtro por Raza */}
                <select
                    name="breed"
                    id="PetSearchFilterBreed"
                    className="PetSearchFilter"
                    onChange={ handleInputChange }
                    value={ searchFilters.breed }
                >
                    <option value="" className="PetSearchFilterOption" disabled>Raza</option>
                    <option value="" className="PetSearchFilterOption">Raza1</option>
                </select>

                {/* Filtro por Sexo */}
                <select
                    name="gender"
                    id="PetSearchFilterSex"
                    className="PetSearchFilter"
                    onChange={ handleInputChange }
                    value={ searchFilters.gender }
                >
                    <option value="" className="PetSearchFilterOption" disabled>Sexo</option>
                    <option value="" className="PetSearchFilterOption">Hembra</option>
                    <option value="" className="PetSearchFilterOption">Macho</option>
                    <option value="" className="PetSearchFilterOption">No definido</option>
                </select>

                {/* Filtro por Edad */}
                <select
                    name="age"
                    id="PetSearchFilterAge"
                    className="PetSearchFilter"
                    onChange={ handleInputChange }
                    value={ searchFilters.age }
                >
                    <option value="" className="PetSearchFilterOption" disabled>Edad</option>
                    <option value="" className="PetSearchFilterOption">Menos de 1 año</option>
                    <option value="" className="PetSearchFilterOption">1 a 5 años</option>
                    <option value="" className="PetSearchFilterOption">5 a 10 años</option>
                    <option value="" className="PetSearchFilterOption">Más de 10 años</option>
                </select>
            </div>
        </form>
    )
}
