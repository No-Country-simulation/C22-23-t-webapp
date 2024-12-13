import './Adopt.css'
import { useEffect, useRef, useState } from 'react'
import { useParams, useNavigate } from 'react-router-dom'

export function Adopt() {
    const navigateTo = useNavigate()
    const FORM_REF = useRef(null)
    const petId = useParams().petId

    const [isOpen, setIsOpen] = useState(false)
    const [userLogin, setUserLogin] = useState(null)

    const checkUserLogin = () => {
        try {
            const userLoginData = localStorage.getItem("userLogin")

            if (userLoginData) {
                // Actualmente no estoy verificando que el JWT no haya expirado D:
                setUserLogin( JSON.parse(userLoginData) )
                return true
            } else {
                // "userLogin" no existe (null o undefined)
                setUserLogin(null) // Or handle it differently as per your logic
                return false
            }
        } catch (error) {
            console.error("Failed to parse userLogin data:", error)
            setUserLogin(null) // Fallback to null or a default value
            return false
        }
    }

    useEffect(() => {
        const isUserLoggedIn = checkUserLogin()

        if (!isUserLoggedIn) navigateTo("/login")
    }, [])

    const autoCompleteForm = async (userId) => {
        if (!userLogin) {
            console.error("No userLogin data available for autoCompleteForm.");
            return;
        }
        
        try {
            const response = await fetch(import.meta.env.VITE_AUTH_ADOPTER_URL + userLogin.userId, {
                method: "GET",
                headers: { "Authorization": `Bearer ${userLogin.tokens.token}` },
            })

            if (!response.ok) throw new Error(`Fetch failed: ${response.status} ${response.statusText}`)

            const data = await response.json()
            FORM_REF.current.children[2].children[1].value = data.userDetails.email
            FORM_REF.current.children[3].children[1].value = data.firstName
            FORM_REF.current.children[4].children[1].value = data.lastName
            FORM_REF.current.children[5].children[1].value = data.address.street
            FORM_REF.current.children[6].children[1].value = data.address.city
            FORM_REF.current.children[7].children[1].value = data.address.state
            FORM_REF.current.children[8].children[1].value = data.address.country
            FORM_REF.current.children[9].children[1].value = data.phoneNumber
        } catch (error) {
            console.error("Error al solicitar información del usuario:", error)
        }
    }

    useEffect(() => { if (userLogin) autoCompleteForm(userLogin.userId) }, [userLogin])

    const showModal = () => setIsOpen(true)
    const closeModal = () => {
        setIsOpen(false)
        navigateTo("/search")
    }

    const validateEmail = (email) => {
        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
        return re.test(String(email).toLowerCase())
    }

    const validatePhoneNumber = (phoneNumber) => {
        // Definición del regex:
        // 1. ^\+? - El número puede comenzar opcionalmente con un "+".
        // 2. [1-9][0-9]{0,2} - Después del "+" puede haber un código de país de 1 a 3 dígitos.
        // 3. \s? - Espacio opcional después del prefijo del código de país.
        // 4. 9? - El número puede tener un "9" opcional (móviles en Argentina).
        // 5. [0-9]{2,4} - Acepta de 2 a 4 dígitos como código de área/localidad.
        // 6. \s? - Espacio opcional entre el código de área y el número principal.
        // 7. [0-9]{6,8} - Acepta de 6 a 8 dígitos como cuerpo principal del número.
        // 8. (\-[0-9]{1,4})? - Puede tener un guion seguido de 1 a 4 dígitos adicionales (opcional).
        // 9. $ - Fin de la cadena.

        // Números válidos aceptados:
        // - "+54 9 123 456-7890" (con prefijo internacional, código de operador y separadores).
        // - "+5491234567890" (prefijo internacional y número concatenado).
        // - "1234567890" (número local sin prefijo internacional).
        // - "54111234567890" (prefijo internacional sin símbolo '+' y número concatenado).
        // - "123456789" (cualquier secuencia de al menos 9 dígitos).
        const phoneRegex = /^\+?[1-9][0-9]{0,2}\s?9?[0-9]{2,4}\s?[0-9]{6,8}(\-[0-9]{1,4})?$/
      
        return phoneRegex.test(phoneNumber)
    }

    const validateForm = (email, phoneNumber) => {
        if ( !validateEmail(email) ) return false

        if ( !validatePhoneNumber(phoneNumber) ) return false

        return true
    }

    const handleSubmit = (event) => {
        event.preventDefault()

        const EMAIL = FORM_REF.current.children[2].children[1].value
        const FIRST_NAME = FORM_REF.current.children[3].children[1].value
        const LAST_NAME = FORM_REF.current.children[4].children[1].value
        const ADDRESS = FORM_REF.current.children[5].children[1].value
        const CITY = FORM_REF.current.children[6].children[1].value
        const STATE = FORM_REF.current.children[7].children[1].value
        const COUNTRY = FORM_REF.current.children[8].children[1].value
        const PHONE = FORM_REF.current.children[9].children[1].value

        // if (validateForm(EMAIL, PHONE) === false) return

        const PAYLOAD = {
            petid: petId,
            email: EMAIL,
            firstName: FIRST_NAME,
            lastName: LAST_NAME,
            address: {
                street: ADDRESS,
                city: CITY,
                state: STATE,
                country: COUNTRY,
            },
            phoneNumber: PHONE,
        }

        console.log(PAYLOAD) // Acá va el fetch o lo que corresponda.

        showModal()
    }

    return (
        <main id="AdoptFormContainer">
            <form id="AdoptForm" className="AdoptForm" aria-labelledby="AdoptFormTitle" ref={FORM_REF} onSubmit={ handleSubmit }>
                <h2 id="AdoptFormTitle" className="AdoptFormTitle">Formulario de Solicitud de Adopción</h2>
                <p className="AdoptFormDescription">Por favor, complete la información necesaria para procesar su solicitud. Notar que los campos con <span>(*)</span> son obligatorios.</p>

                {/* <!-- Campo de email --> */}
                <div className="AdoptFormField">
                    <label htmlFor="AdoptFormEmail" className="AdoptFormLabel"><span>(*)</span> Correo Electrónico:</label>
                    <input 
                        type="email" 
                        id="AdoptFormEmail" 
                        name="email" 
                        className="AdoptFormInput" 
                        required 
                        aria-required="true" 
                        placeholder="ejemplo@correo.com" 
                    />
                </div>

                {/* <!-- Campo de nombre completo --> */}
                <div className="AdoptFormField">
                    <label htmlFor="AdoptFormFirstName" className="AdoptFormLabel"><span>(*)</span> Nombre:</label>
                    <input 
                        type="text" 
                        id="AdoptFormFirstName" 
                        name="firstName" 
                        className="AdoptFormInput" 
                        required 
                        aria-required="true" 
                        placeholder="Ingrese su nombre" 
                    />
                </div>

                {/* <!-- Campo de edad --> */}
                <div className="AdoptFormField">
                    <label htmlFor="AdoptFormLastName" className="AdoptFormLabel"><span>(*)</span> Apellido:</label>
                    <input 
                        type="text" 
                        id="AdoptFormLastName" 
                        name="lastName" 
                        className="AdoptFormInput" 
                        required 
                        aria-required="true" 
                        placeholder="Ingrese su apellido" 
                    />
                </div>

                {/* <!-- Campo de dirección --> */}
                <div className="AdoptFormField">
                    <label htmlFor="AdoptFormAddress" className="AdoptFormLabel"><span>(*)</span> Dirección de Residencia:</label>
                    <input 
                        type="text" 
                        id="AdoptFormAddress" 
                        name="address" 
                        className="AdoptFormInput" 
                        required 
                        aria-required="true" 
                        placeholder="Ingrese su dirección" 
                    />
                </div>

                {/* <!-- Campo de ciudad --> */}
                <div className="AdoptFormField">
                    <label htmlFor="AdoptFormCity" className="AdoptFormLabel"><span>(*)</span> Ciudad de Residencia:</label>
                    <input 
                        type="text" 
                        id="AdoptFormCity" 
                        name="city" 
                        className="AdoptFormInput" 
                        required 
                        aria-required="true" 
                        placeholder="Ingrese su ciudad" 
                    />
                </div>

                {/* <!-- Campo de provincia/estado --> */}
                <div className="AdoptFormField">
                    <label htmlFor="AdoptFormProvince" className="AdoptFormLabel"><span>(*)</span> Provincia/Estado de Residencia:</label>
                    <input 
                        type="text" 
                        id="AdoptFormProvince" 
                        name="province" 
                        className="AdoptFormInput" 
                        required 
                        aria-required="true" 
                        placeholder="Ingrese su provincia o estado" 
                    />
                </div>

                {/* <!-- Campo de país --> */}
                <div className="AdoptFormField">
                    <label htmlFor="AdoptFormCountry" className="AdoptFormLabel"><span>(*)</span> País de Residencia:</label>
                    <input 
                        type="text" 
                        id="AdoptFormCountry" 
                        name="country" 
                        className="AdoptFormInput" 
                        required 
                        aria-required="true" 
                        placeholder="Ingrese su país" 
                    />
                </div>

                {/* <!-- Campo de teléfono --> */}
                <div className="AdoptFormField">
                    <label htmlFor="AdoptFormPhone" className="AdoptFormLabel"><span>(*)</span> Teléfono:</label>
                    <input 
                        type="tel" 
                        id="AdoptFormPhone" 
                        name="phone" 
                        className="AdoptFormInput" 
                        required 
                        aria-required="true" 
                        placeholder="Ingrese su número de teléfono" 
                    />
                </div>

                {/* <!-- Botón de enviar --> */}
                <div className="AdoptFormActions">
                    <button 
                        type="submit" 
                        className="AdoptFormButton" 
                        aria-label="Enviar solicitud de adopción"
                    >
                    Enviar Solicitud
                    </button>
                </div>
            </form>
            <div
                id="AdoptFormModal"
                className={`AdoptFormModal ${isOpen ? 'active' : ''}`}
                role="dialog"
                aria-labelledby="AdoptFormModalTitle"
                aria-describedby="AdoptFormModalDescription"
            >
                <div className="AdoptFormModalContent">
                <h2 id="AdoptFormModalTitle" className="AdoptFormModalTitle">¡Solicitud enviada!</h2>
                <p id="AdoptFormModalDescription" className="AdoptFormModalDescription">Tu solicitud de adopción se envió exitosamente. Nos pondremos en contacto contigo pronto.</p>
                <button id="AdoptFormModalButton" className="AdoptFormModalButton" type="button" onClick={ closeModal }>Cerrar</button>
                </div>
            </div>
        </main>
    )
}
