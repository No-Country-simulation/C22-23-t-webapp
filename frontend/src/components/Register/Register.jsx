import React, { useState } from "react";
import "./Register.css";
import { Link, useNavigate } from "react-router-dom";

const Register = () => {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    userType: "",
    refugeName: "",
    refugeAddress: "",
    refugePhoto: "",
    refugeCity:"",
    refugeCountry:"",
    refugePhone:"",
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
      fullName: value === "adoptante" ? prevState.fullName : "",
      age: value === "adoptante" ? prevState.age : "",
      address: value === "adoptante" ? prevState.address : "",
      city: value === "adoptante" ? prevState.city : "",
      province: value === "adoptante" ? prevState.province : "",
      country: value === "adoptante" ? prevState.country : "",
      phone: value === "adoptante" ? prevState.phone : "",
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
    if (!formData.name.trim()) {
      newErrors.name = "El nombre es requerido";
    }
    if (!formData.email.trim()) {
      newErrors.email = "El correo electrónico es requerido";
    } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
      newErrors.email = "El correo electrónico no es válido";
    }
    if (!formData.password.trim()) {
      newErrors.password = "La contraseña es requerida";
    } else if (formData.password.length < 6) {
      newErrors.password = "La contraseña debe tener al menos 6 caracteres";
    }
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
    if (validateForm()) {
      setLoading(true);
      try {
        const submitData = {
          name: formData.name,
          email: formData.email,
          password: formData.password,
          userType: formData.userType,
          ...(formData.userType === "refugio" && {
            refugeName: formData.refugeName,
            refugeAddress: formData.refugeAddress,
            refugeCity: formData.refugeCity,
            refugeCountry: formData.refugeCountry,
            refugePhone: formData.refugePhone
          }),
        };

        console.log("Registro exitoso", submitData);
        navigate("/login");
        alert("Registro completado con éxito");
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
                  name="fullName"
                  value={formData.fullName}
                  onChange={handleInputChange}
                  placeholder="Nombre completo"
                  className="input-checks"
                />
                {errors.fullName && (
                  <span className="error">{errors.fullName}</span>
                )}
              </div>

              <div className="input-group">
                <input
                  type="number"
                  name="age"
                  value={formData.age}
                  onChange={handleInputChange}
                  placeholder="Edad"
                  className="input-checks"
                />
                {errors.age && <span className="error">{errors.age}</span>}
              </div>

              <div className="input-group">
                <input
                  type="text"
                  name="address"
                  value={formData.address}
                  onChange={handleInputChange}
                  placeholder="Dirección donde vives"
                  className="input-checks"
                />
                {errors.address && (
                  <span className="error">{errors.address}</span>
                )}
              </div>

              <div className="input-group">
                <input
                  type="text"
                  name="city"
                  value={formData.city}
                  onChange={handleInputChange}
                  placeholder="Ciudad donde vives"
                  className="input-checks"
                />
                {errors.city && <span className="error">{errors.city}</span>}
              </div>

              <div className="input-group">
                <input
                  type="text"
                  name="province"
                  value={formData.province}
                  onChange={handleInputChange}
                  placeholder="Provincia donde vives"
                  className="input-checks"
                />
                {errors.province && (
                  <span className="error">{errors.province}</span>
                )}
              </div>

              <div className="input-group">
                <input
                  type="text"
                  name="country"
                  value={formData.country}
                  onChange={handleInputChange}
                  placeholder="País donde vives"
                  className="input-checks"
                />
                {errors.country && (
                  <span className="error">{errors.country}</span>
                )}
              </div>

              <div className="input-group">
                <input
                  type="tel"
                  name="phone"
                  value={formData.phone}
                  onChange={handleInputChange}
                  placeholder="Teléfono de contacto"
                  className="input-checks"
                />
                {errors.phone && <span className="error">{errors.phone}</span>}
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
            {loading ? "Registrando..." : "Registrar"}
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
