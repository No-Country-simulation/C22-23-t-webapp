import "./Register.css"
import { useState } from "react"
import { Link, useNavigate } from "react-router-dom"

export function Register() {
    const [formData, setFormData] = useState({
        firstName: "",
        lastName: "",
        email: "",
        confirmEmail: "",
        password: "",
        passwordConfirmation: "",
        userType: "adoptante",
        state: "",
        adoptFullName: "",
        adoptAge: "",
        adoptAddress: "",
        adoptCity:"",
        adoptProvince:"",
        adoptCountry:"",
        adoptPhone: "",
        fullName:"",
        profilePhoto:"",
        age:"",
        address:"",
        city:"",
        province:"",
        country:"",
        phoneNumber:"",
        rescuerFullName:"",
        rescuerProfilePhoto:"",
        rescuerAddress:"",
        rescuerCity:"",
        rescuerCountry:"", 
        rescuerPhone:"",
        status: "VERIFIED",
        street: "",
    })

    const [errors, setErrors] = useState({})
    const [loading, setLoading] = useState(false)
    const navigate = useNavigate()

    const validateEmail = (email) => {
        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
        return re.test(String(email).toLowerCase())
    }

    const validatePassword = (password) => {
        const regex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$/
        return regex.test(password)
    }

    const handleInputChange = (event) => {
        const { name, value } = event.target
        setFormData((prevState) => ({
            ...prevState,
            [name]: value,
        }))
    }

    const handleUserTypeChange = (event) => {
        const { value, checked } = event.target
        setFormData((prevState) => ({
            ...prevState,
            userType: checked ? value : "",
            refugeName: value !== "refugio" ? "" : prevState.refugeName,
            refugeAddress: value !== "refugio" ? "" : prevState.refugeAddress,
            refugeCity: value === "refugio" ? prevState.refugeCity : "",
            refugeCountry: value === "refugio" ? prevState.refugeCountry : "",
            refugePhone: value === "refugio" ? prevState.refugePhone : "",
            // Limpiar campos específicos de adoptante cuando no es seleccionado
            firstName: value === "adoptante" ? prevState.firstName : "",
            lastName: value === "adoptante" ? prevState.lastName : "",
            phoneNumber: value === "adoptante" ? prevState.phoneNumber: "",
            bio: value === "adoptante" ? prevState.bio: "",
            street: value === "adoptante" ? prevState.street: "",
            city: value === "adoptante" ? prevState.city : "",
            state: value === "adoptante" ? prevState.state : "",
            zip: value === "adoptante" ? prevState.zip : "",
            acountry: value === "adoptante" ? prevState.country : "",
            adoptFullName: value === "adoptante" ? prevState.fullName : "",
            status: value === "adoptante" ? prevState.status : "",

            adoptAgeage: value === "adoptante" ? prevState.age : "",
            adoptAddress: value === "adoptante" ? prevState.address : "",
            adoptProvince: value === "adoptante" ? prevState.province : "",
            // Limpiar campos específicos de rescatista cuando no es seleccionado
            rescuerFullName: value === "rescatista" ? prevState.rescuerFullName : "",
            rescuerAddress: value === "rescatista" ? prevState.rescuerAddress : "",
            rescuerCity: value === "rescatista" ? prevState.rescuerCity : "",
            rescuerCountry: value === "rescatista" ? prevState.rescuerCountry : "",
            rescuerPhone: value === "rescatista" ? prevState.rescuerPhone : "",
        }))
    }

    /*
        MARK: validateForm
    */
    
    const validateForm = () => {
        const newErrors = {}

        // Validación del correo electrónico
        if (!formData.email.trim()) {
            newErrors.email = "El correo electrónico es requerido"
        } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
            newErrors.email = "El correo electrónico no es válido"
        }

        if (formData.email !== formData.confirmEmail) {
            newErrors.confirmEmail = "Los correos electrónicos no coinciden"
        }

        // Validación de la contraseña
        if (!formData.password.trim()) {
            newErrors.password = "La contraseña es requerida"
        } else if (!validatePassword(formData.password)) {
            newErrors.password = "La contraseña debe tener al menos 8 caracteres, una letra mayúscula, una letra minúscula, un número y un carácter especial."
        }

        if (formData.password !== formData.passwordConfirmation) {
            newErrors.passwordConfirmation = "Las contraseñas no coinciden"
        }

        // Validación de tipo de usuario (refugio)
        if (formData.userType === "refugio") {
            if (!formData.refugeName.trim()) {
            newErrors.refugeName = "El nombre del refugio es requerido"
            }
            if (!formData.refugeAddress.trim()) {
            newErrors.refugeAddress = "La dirección del refugio es requerida"
            }
        }

        setErrors(newErrors)
        return Object.keys(newErrors).length === 0
    }

    /*
        MARK: handleSubmit
    */

    const handleSubmit = async (event) => {
        event.preventDefault()

        // Validación del formulario antes de enviarlo
        if (validateForm()) {
            setLoading(true)
            try {
                const submitData = {
                    firstName: formData.adoptFullName.split(" ")[0],
                    lastName: formData.adoptFullName.split(" ")[1] || "",
                    email: formData.email,
                    password: formData.password,
                    passwordConfirmation: formData.passwordConfirmation,
                }
                    
                const registerResponse = await fetch(import.meta.env.VITE_AUTH_ADOPTER_URL + "register", {
                    method: "POST", 
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify(submitData),
                })

                const registerData = await registerResponse.json()
                // localStorage.setItem("token", registerData.tokens.token)
                localStorage.setItem("userLogin", JSON.stringify(registerData))
                
                const submitMoreData = {
                    firstName: formData.firstName,
                    lastName: formData.lastName,
                    phoneNumber: formData.phoneNumber,
                    street: formData.street,
                    city: formData.city,
                    state: formData.state, 
                    country: formData.country,                       
                }

                const response = await fetch(import.meta.env.VITE_AUTH_ADOPTER_URL + registerData.userId, {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${registerData.tokens.token}`,
                    },
                    body: JSON.stringify(submitMoreData),
                })

                const data = await response
                console.log(data.status)

                // TO-DO: AVISAR AL USUARIO QUE SE REGISTRÓ EXITOSAMENTE
                // alert("Registro completado con éxito")
                navigate("/search")
            } catch (error) {
                console.error("Error en el registro o actualización", error)
                alert("Hubo un error. Intenta nuevamente.")
            } finally {
                setLoading(false)
            }
        }
    }

    /*
        MARK: RETURN
    */

    return (
        <main className="container-register">
            <div className="register-container">
                <form
                    id="inputs-colors"
                    onSubmit={handleSubmit}
                    className="register-form"
                >
                    <h2>Registro de nuevos usuarios</h2>
                    <div className="input-group">
                    <label htmlFor="RegisterFormEmail" className="RegisterFormLabel"><span>(*)</span> Correo Electrónico:</label>
                    <input
                        type="email"
                        name="email"
                        value={formData.email}
                        onChange={handleInputChange}
                        placeholder="hola@SegundasHuellas.com"
                    />
                    {errors.email && <span className="error">{errors.email}</span>}
                    </div>

                    <div className="input-group">
                    <label htmlFor="RegisterFormConfirmEmail" className="RegisterFormLabel"><span>(*)</span> Repetir Correo Electrónico:</label>
                    <input
                        type="email"
                        name="confirmEmail"
                        value={formData.confirmEmail}
                        onChange={handleInputChange}
                        placeholder="hola@SegundasHuellas.com"
                    />
                    {errors.confirmEmail && <span className="error">{errors.confirmEmail}</span>}
                    </div>

                    <div className="input-group">
                    <label htmlFor="RegisterFormPassword" className="RegisterFormLabel"><span>(*)</span> Contraseña:</label>
                    <input
                        type="password"
                        name="password"
                        value={formData.password}
                        onChange={handleInputChange}
                        placeholder="Al menos 8 caracteres, con una minúscula, una mayúscula y un caracter especial"
                    />
                    {errors.password && (
                        <span className="error">{errors.password}</span>
                    )}
                    </div>

                    <div className="input-group">
                    <label htmlFor="RegisterFormConfirmPassword" className="RegisterFormLabel"><span>(*)</span> Repetir Contraseña:</label>
                    <input
                        type="password"
                        name="passwordConfirmation"
                        value={formData.passwordConfirmation}
                        onChange={handleInputChange}
                        placeholder="Al menos 8 caracteres, con una minúscula, una mayúscula y un caracter especial"
                    />
                    {errors.passwordConfirmation && (
                        <span className="error">{errors.passwordConfirmation}</span>
                        )}
                    </div>

                    <div className="user-type-section">
                    <h3>Tipo de Usuario</h3>
                    <div className="user-type-checks">
                        {["adoptante", "rescatista", "refugio"].map((type) => (
                        <label key={type}>
                            <input
                            className="input-checks"
                            type="radio"
                            name="userType"
                            value={type}
                            checked={formData.userType === type}
                            onChange={handleUserTypeChange}
                            />
                            {type.charAt(0).toUpperCase() + type.slice(1)}
                        </label>
                        ))}
                    </div>
                    </div>

                    {/* 
                        MARK: REFUGIO
                    */}

                    {/* Si el tipo de usuario es "refugio" */}
                    {formData.userType === "refugio" && (
                    <>
                        <div className="input-group">
                        <input
                            type="text"
                            name="refugeName"
                            value={formData.refugeName}
                            onChange={handleInputChange}
                            placeholder="Nombre del refugio"
                            className="input-checks"
                        />
                        {errors.refugeName && (
                            <span className="error">{errors.refugeName}</span>
                        )}
                        </div>

                        <div className="input-group">
                        <input
                            type="text"
                            name="refugeAddress"
                            value={formData.refugeAddress}
                            onChange={handleInputChange}
                            placeholder="Dirección del refugio"
                            className="input-checks"
                        />
                        {errors.refugeAddress && (
                            <span className="error">{errors.refugeAddress}</span>
                        )}
                        </div>

                        <div className="input-group">
                        <input
                            type="text"
                            name="refugeCity"
                            value={formData.refugeCity}
                            onChange={handleInputChange}
                            placeholder="Ciudad donde vives"
                            className="input-checks"
                        />
                        {errors.refugeCity && (
                            <span className="error">{errors.refugeCity}</span>
                        )}
                        </div>

                        <div className="input-group">
                        <input
                            type="text"
                            name="refugeCountry"
                            value={formData.refugeCountry}
                            onChange={handleInputChange}
                            placeholder="País donde vives"
                            className="input-checks"
                        />
                        {errors.refugeCountry && (
                            <span className="error">{errors.refugeCountry}</span>
                        )}
                        </div>

                        <div className="input-group">
                        <input
                            type="tel"
                            name="refugePhone"
                            value={formData.refugePhone}
                            onChange={handleInputChange}
                            placeholder="Teléfono de contacto"
                            className="input-checks"
                        />
                        {errors.refugePhone && (
                            <span className="error">{errors.refugePhone}</span>
                        )}
                        </div>
                    </>
                    )}

                    {/* 
                        MARK: ADOPTANTE
                    */}

                    {/* Si el tipo de usuario es "adoptante" */}
                    {formData.userType === "adoptante" && (
                    <>
                        <div className="input-group">
                        <label htmlFor="RegisterFormFirstName" className="RegisterFormLabel">Nombre:</label>
                        <input
                            type="text"
                            name="firstName"
                            value={formData.firstName}
                            onChange={handleInputChange}
                            placeholder="Segundas"
                            className="optional-input-checks"
                        />
                        {errors.firstName && (
                            <span className="error">{errors.firstName}</span>
                        )}
                        </div>

                        <div className="input-group">
                        <label htmlFor="RegisterFormLastName" className="RegisterFormLabel">Apellido:</label>
                        <input
                            type="text"
                            name="lastName"
                            value={formData.lastName}
                            onChange={handleInputChange}
                            placeholder="Huellas"
                            className="optional-input-checks"
                        />
                        {errors.lastName && (
                            <span className="error">{errors.lastName}</span>
                        )}
                        </div>

                        {/* <div className="input-group">
                        <input
                            type="number"
                            name="adoptAge"
                            value={formData.adoptAge}
                            onChange={handleInputChange}
                            placeholder="Edad"
                            className="input-checks"
                        />
                        {errors.adoptAge && <span className="error">{errors.adoptAge}</span>}
                        </div> */}

                        <div className="input-group">
                        <label htmlFor="RegisterFormAddress" className="RegisterFormLabel">Dirección de Residencia:</label>
                        <input
                            type="text"
                            name="street"
                            value={formData.street}
                            onChange={handleInputChange}
                            placeholder="Remedios de Escalada 123"
                            className="optional-input-checks"
                        />
                        {errors.street && (
                            <span className="error">{errors.street}</span>
                        )}
                        </div>

                        <div className="input-group">
                        <label htmlFor="RegisterFormCity" className="RegisterFormLabel">Ciudad de Residencia:</label>
                        <input
                            type="text"
                            name="city"
                            value={formData.city}
                            onChange={handleInputChange}
                            placeholder="La Plata"
                            className="optional-input-checks"
                        />
                        {errors.city && <span className="error">{errors.city}</span>}
                        </div>

                        <div className="input-group">
                        <label htmlFor="RegisterFormProvince" className="RegisterFormLabel">Provincia/Estado de Residencia:</label>
                        <input
                            type="text"
                            name="state"
                            value={formData.state}
                            onChange={handleInputChange}
                            placeholder="Buenos Aires"
                            className="optional-input-checks"
                        />
                        {errors.state && (
                            <span className="error">{errors.state}</span>
                        )}
                        </div>

                        <div className="input-group">
                        <label htmlFor="RegisterFormCountry" className="RegisterFormLabel">País de Residencia:</label>
                        <input
                            type="text"
                            name="country"
                            value={formData.country}
                            onChange={handleInputChange}
                            placeholder="Argentina"
                            className="optional-input-checks"
                        />
                        {errors.country && (
                            <span className="error">{errors.country}</span>
                        )}
                        </div>

                        <div className="input-group">
                        <label htmlFor="RegisterFormPhone" className="RegisterFormLabel">Teléfono:</label>
                        <input
                            id="RegisterFormPhoneInput"
                            type="tel"
                            name="phoneNumber"
                            value={formData.phoneNumber}
                            onChange={handleInputChange}
                            placeholder="+54 9 11 1234-5678"
                            className="optional-input-checks"
                        />
                        {errors.phoneNumber && <span className="error">{errors.phoneNumber}</span>}
                        </div>
                    </>
                    )}

                    {/* 
                        MARK: RESCATISTA
                    */}

                    {/* Si el tipo de usuario es "rescatista" */}
                    {formData.userType === "rescatista" && (
                    <>
                        <div className="input-group">
                        <input
                            type="text"
                            name="rescuerFullName"
                            value={formData.rescuerFullName}
                            onChange={handleInputChange}
                            placeholder="Nombre completo"
                            className="input-checks"
                        />
                        {errors.rescuerFullName && (
                            <span className="error">{errors.rescuerFullName}</span>
                        )}
                        </div>

                        <div className="input-group">
                        <input
                            type="text"
                            name="rescuerAddress"
                            value={formData.rescuerAddress}
                            onChange={handleInputChange}
                            placeholder="Dirección donde vives"
                            className="input-checks"
                        />
                        {errors.rescuerAddress && (
                            <span className="error">{errors.rescuerAddress}</span>
                        )}
                        </div>

                        <div className="input-group">
                        <input
                            type="text"
                            name="rescuerCity"
                            value={formData.rescuerCity}
                            onChange={handleInputChange}
                            placeholder="Ciudad donde vives"
                            className="input-checks"
                        />
                        {errors.rescuerCity && (
                            <span className="error">{errors.rescuerCity}</span>
                        )}
                        </div>

                        <div className="input-group">
                        <input
                            type="text"
                            name="rescuerCountry"
                            value={formData.rescuerCountry}
                            onChange={handleInputChange}
                            placeholder="País donde vives"
                            className="input-checks"
                        />
                        {errors.rescuerCountry && (
                            <span className="error">{errors.rescuerCountry}</span>
                        )}
                        </div>

                        <div className="input-group">
                        <input
                            type="tel"
                            name="rescuerPhone"
                            value={formData.rescuerPhone}
                            onChange={handleInputChange}
                            placeholder="Teléfono de contacto"
                            className="input-checks"
                        />
                        {errors.rescuerPhone && (
                            <span className="error">{errors.rescuerPhone}</span>
                        )}
                        </div>
                    </>
                    )}
                    <button type="submit" className="register-button" disabled={loading}>
                    {loading ? "Registrando..." : "Únete"}
                    </button>

                    <hr className="line-register" />
                    <div className="link-login-container">
                    <p className="social-title">¿ Ya tienes una cuenta ?</p>
                    <Link className="links-form-register" to="/login">
                        Inicia Sesión
                    </Link>
                    </div>
                </form>
            </div>

            <figure className="image-container-pet">
            <img
                className="image-register"
                src="register-image.jpg"
                alt="Imagen de la mascota en Registro"
            />
            </figure>
        </main>
    )
}
