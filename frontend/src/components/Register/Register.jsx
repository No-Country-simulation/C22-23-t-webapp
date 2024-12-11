import React, { useState } from "react";
import "./Register.css";
import { Link, useNavigate } from "react-router-dom";

const Register = () => {
  const [formData, setFormData] = useState({
    name: "",
    firstName: "",
    lastName: "",
    email: "",
    confirmEmail: "",
    password: "",
    passwordConfirmation: "",
    userType: "adoptante",
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
    phone:"",
    rescuerFullName:"",
    rescuerProfilePhoto:"",
    rescuerAddress:"",
    rescuerCity:"",
    rescuerCountry:"", 
    rescuerPhone:"",

  });
  const [errors, setErrors] = useState({});
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const validateEmail = (email) => {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(String(email).toLowerCase());
  };

  const validatePassword = (password) => {
    const regex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$/;
    return regex.test(password);
  };

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleUserTypeChange = (event) => {
    const { value, checked } = event.target;
    setFormData((prevState) => ({
      ...prevState,
      userType: checked ? value : "",
      refugeName: value !== "refugio" ? "" : prevState.refugeName,
      refugeAddress: value !== "refugio" ? "" : prevState.refugeAddress,
      refugeCity: value === "refugio" ? prevState.refugeCity : "",
      refugeCountry: value === "refugio" ? prevState.refugeCountry : "",
      refugePhone: value === "refugio" ? prevState.refugePhone : "",
      // Limpiar campos específicos de adoptante cuando no es seleccionado
      adoptFullName: value === "adoptante" ? prevState.fullName : "",
      adoptAgeage: value === "adoptante" ? prevState.age : "",
      adoptAddress: value === "adoptante" ? prevState.address : "",
      adoptCity: value === "adoptante" ? prevState.city : "",
      adoptProvince: value === "adoptante" ? prevState.province : "",
      adoptCountry: value === "adoptante" ? prevState.country : "",
      adoptPhone: value === "adoptante" ? prevState.phone : "",
      // Limpiar campos específicos de rescatista cuando no es seleccionado
      rescuerFullName: value === "rescatista" ? prevState.rescuerFullName : "",
      rescuerAddress: value === "rescatista" ? prevState.rescuerAddress : "",
      rescuerCity: value === "rescatista" ? prevState.rescuerCity : "",
      rescuerCountry: value === "rescatista" ? prevState.rescuerCountry : "",
      rescuerPhone: value === "rescatista" ? prevState.rescuerPhone : "",
    }));
  };

  const validateForm = () => {
    const newErrors = {};
  
    // Validación del correo electrónico
    if (!formData.email.trim()) {
      newErrors.email = "El correo electrónico es requerido";
    } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
      newErrors.email = "El correo electrónico no es válido";
    }
  
    if (formData.email !== formData.confirmEmail) {
      newErrors.confirmEmail = "Los correos electrónicos no coinciden";
    }
  
    // Validación de la contraseña
    if (!formData.password.trim()) {
      newErrors.password = "La contraseña es requerida";
    } else if (!validatePassword(formData.password)) {
      newErrors.password = "La contraseña debe tener al menos 8 caracteres, una letra mayúscula, una letra minúscula, un número y un carácter especial.";
    }
  
    if (formData.password !== formData.passwordConfirmation) {
      newErrors.passwordConfirmation = "Las contraseñas no coinciden";
    }
  
    // Validación de tipo de usuario (refugio)
    if (formData.userType === "refugio") {
      if (!formData.refugeName.trim()) {
        newErrors.refugeName = "El nombre del refugio es requerido";
      }
      if (!formData.refugeAddress.trim()) {
        newErrors.refugeAddress = "La dirección del refugio es requerida";
      }
    }
  
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };
   
  
  const handleSubmit = async (event) => {
    event.preventDefault();

    // Validación del formulario antes de enviarlo
    if (validateForm()) {
        setLoading(true);
        try {
            const submitData = {
                email: formData.email,
                password: formData.password,
                passwordConfirmation: formData.passwordConfirmation,
                userType: formData.userType,
                // Solo enviamos los datos del adoptante si es seleccionado
                ...(formData.userType === "adoptante" && {
                    firstName: formData.adoptFullName.split(" ")[0], // Asumiendo que el nombre está completo
                    lastName: formData.adoptFullName.split(" ")[1] || "",
                    phoneNumber: formData.adoptPhone,
                    street: formData.adoptAddress,
                    city: formData.adoptCity,
                    state: formData.adoptProvince,
                    country: formData.adoptCountry,
                    bio: "N/A", // Puedes poner un valor por defecto o pedirlo como otro campo si lo necesitas
                }),
            };

            // Realizar la solicitud al backend (POST para registro de usuario)
            const response = await fetch("http://localhost:5173/auth/adopters/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Accept": "application/json", // Especificamos que esperamos una respuesta JSON
                },
                body: JSON.stringify(submitData),
            });

            const data = await response.json();

            // Si la respuesta es exitosa, manejar la respuesta
            if (response.ok) {
                console.log("Registro exitoso", data);

                // Guardar el token en el localStorage
                localStorage.setItem("token", data.tokens.token);
                localStorage.setItem("refreshToken", data.tokens.refreshToken);
                localStorage.setItem("expiresAt", data.tokens.expiresAt);

                // Mostrar un mensaje de éxito y redirigir a login
                alert("Registro completado con éxito");
                navigate("/login");

            } else {
                console.error("Error en el registro:", data.message);
                alert("Hubo un error en el registro. Intenta nuevamente.");
            }
        } catch (error) {
            console.error("Error en el registro", error);
            alert("Hubo un error en el registro. Intenta nuevamente.");
        } finally {
            setLoading(false);
        }
    }
};

  

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
            <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleInputChange}
              placeholder="Correo Electrónico"
            />
            {errors.email && <span className="error">{errors.email}</span>}
          </div>

          <div className="input-group">
            <input
              type="email"
              name="confirmEmail"
              value={formData.confirmEmail}
              onChange={handleInputChange}
              placeholder="Confirmar Correo Electrónico"
            />
            {errors.confirmEmail && <span className="error">{errors.confirmEmail}</span>}
          </div>

          <div className="input-group">
            <input
              type="password"
              name="password"
              value={formData.password}
              onChange={handleInputChange}
              placeholder="Contraseña"
            />
            {errors.password && (
              <span className="error">{errors.password}</span>
            )}
          </div>

          <div className="input-group">
            <input
              type="password"
              name="passwordConfirmation"
              value={formData.passwordConfirmation}
              onChange={handleInputChange}
              placeholder="Confirmar Contraseña"
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

          {/* Si el tipo de usuario es "adoptante" */}
          {formData.userType === "adoptante" && (
            <>
              <div className="input-group">
                <input
                  type="text"
                  name="adoptFullName"
                  value={formData.adoptFullName}
                  onChange={handleInputChange}
                  placeholder="Nombre completo"
                  className="input-checks"
                />
                {errors.adoptFullName && (
                  <span className="error">{errors.adoptFullName}</span>
                )}
              </div>

              <div className="input-group">
                <input
                  type="number"
                  name="adoptAge"
                  value={formData.adoptAge}
                  onChange={handleInputChange}
                  placeholder="Edad"
                  className="input-checks"
                />
                {errors.adoptAge && <span className="error">{errors.adoptAge}</span>}
              </div>

              <div className="input-group">
                <input
                  type="text"
                  name="adoptAddress"
                  value={formData.adoptAddress}
                  onChange={handleInputChange}
                  placeholder="Dirección donde vives"
                  className="input-checks"
                />
                {errors.adoptAddress && (
                  <span className="error">{errors.adoptAddress}</span>
                )}
              </div>

              <div className="input-group">
                <input
                  type="text"
                  name="adoptCity"
                  value={formData.adoptCity}
                  onChange={handleInputChange}
                  placeholder="Ciudad donde vives"
                  className="input-checks"
                />
                {errors.adoptCity && <span className="error">{errors.adoptCity}</span>}
              </div>

              <div className="input-group">
                <input
                  type="text"
                  name="adoptProvince"
                  value={formData.adoptProvince}
                  onChange={handleInputChange}
                  placeholder="Provincia donde vives"
                  className="input-checks"
                />
                {errors.adoptProvince && (
                  <span className="error">{errors.adoptProvince}</span>
                )}
              </div>

              <div className="input-group">
                <input
                  type="text"
                  name="adoptCountry"
                  value={formData.adoptCountry}
                  onChange={handleInputChange}
                  placeholder="País donde vives"
                  className="input-checks"
                />
                {errors.adoptCountry && (
                  <span className="error">{errors.adoptCountry}</span>
                )}
              </div>

              <div className="input-group">
                <input
                  type="tel"
                  name="adoptPhone"
                  value={formData.adoptPhone}
                  onChange={handleInputChange}
                  placeholder="Teléfono de contacto"
                  className="input-checks"
                />
                {errors.adoptPhone && <span className="error">{errors.adoptPhone}</span>}
              </div>
            </>
          )}

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
  );
};

export default Register;
