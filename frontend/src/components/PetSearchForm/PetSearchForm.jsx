import './PetSearchForm.css'
import { BASENAME } from '../../config.js'

export function PetSearchForm() {
    return (
        <form id="HeaderSearchContainer">
            <input id="HeaderSearchInput" type="text" placeholder="Estoy buscando..." />
            <button id="HeaderSearchSubmit" type="submit">
            <img id="HeaderSearchIcon" src={`${BASENAME}/search-logo.svg`} alt="Icono botón buscar" />
            </button>
        </form>
    )
}
