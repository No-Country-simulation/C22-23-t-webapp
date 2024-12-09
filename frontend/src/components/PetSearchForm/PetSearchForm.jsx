import './PetSearchForm.css'
import { BASENAME } from '../../config.js'
import { useState } from 'react'

export function PetSearchForm() {
    const [ isFilterMenuOpen, setIsFilterMenuOpen ] = useState(false)

    const handleFilterMenuClick = () => { setIsFilterMenuOpen(!isFilterMenuOpen) }

    return (
        <form id="PetSearchForm">
            <div id="PetSearchBarContainer">
                <button id="PetSearchFilterButton" type="button" onClick={ handleFilterMenuClick }>
                    <img id="PetSearchFilterButtonIcon" src={`${BASENAME}/sh-icon-${isFilterMenuOpen ? 'close' : 'filter-list'}.svg`} alt={`Icono ${isFilterMenuOpen ? 'cerrar' : 'abrir'} menú de filtros`} />
                </button>
                <div id="PetSearchByName">
                    <input id="PetSearchByNameInput" type="text" placeholder="Buscar por nombre..." />
                    <button id="PetSearchByNameSubmit" type="submit">
                        <img id="PetSearchByNameIcon" src={`${BASENAME}/search-logo.svg`} alt="Icono botón buscar por nombre" />
                    </button>
                </div>
            </div>
            <div id="PetSearchFilterMenuContainer" className={ isFilterMenuOpen ? 'open' : '' }>
                <h3 id="PetSearchFilterMenuLabel">Filtrar por:</h3>
                <select name="" id="PetSearchFilterSpecies" className="PetSearchFilter">
                    <option value="" className="PetSearchFilterOption" disabled selected>Especie</option>
                    <option value="" className="PetSearchFilterOption">Perro</option>
                    <option value="" className="PetSearchFilterOption">Gato</option>
                    <option value="" className="PetSearchFilterOption">Otros</option>
                </select>
                <select name="" id="PetSearchFilterBreed" className="PetSearchFilter">
                    <option value="" className="PetSearchFilterOption" disabled selected>Raza</option>
                    <option value="" className="PetSearchFilterOption">Raza1</option>
                </select>
                <select name="" id="PetSearchFilterSex" className="PetSearchFilter">
                    <option value="" className="PetSearchFilterOption" disabled selected>Sexo</option>
                    <option value="" className="PetSearchFilterOption">Hembra</option>
                    <option value="" className="PetSearchFilterOption">Macho</option>
                    <option value="" className="PetSearchFilterOption">No definido</option>
                </select>
                <select name="" id="PetSearchFilterAge" className="PetSearchFilter">
                    <option value="" className="PetSearchFilterOption" disabled selected>Edad</option>
                    <option value="" className="PetSearchFilterOption">Menos de 1 año</option>
                    <option value="" className="PetSearchFilterOption">1 a 5 años</option>
                    <option value="" className="PetSearchFilterOption">5 a 10 años</option>
                    <option value="" className="PetSearchFilterOption">Más de 10 años</option>
                </select>
            </div>
        </form>
    )
}
