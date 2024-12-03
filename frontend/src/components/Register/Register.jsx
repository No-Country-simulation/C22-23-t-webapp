import React, { useState } from "react";
import "./Register.css";
import { FaGoogle, FaFacebook } from "react-icons/fa";
import { Link, useNavigate } from "react-router-dom";

const Register = () => {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    userType: "",
    refugeName: "",
    refugeAddress: "",
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
    <div className="container-register">
      <div className="register-container">
        <form onSubmit={handleSubmit} className="register-form">
          <h2>Registro de nuevos usuarios</h2>

          <div className="input-group">
            <input
              type="text"
              name="name"
              value={formData.name}
              onChange={handleInputChange}
              placeholder="Nombre Completo"
            />
            {errors.name && <span className="error">{errors.name}</span>}
          </div>

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
            {errors.password && <span className="error">{errors.password}</span>}
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

          {formData.userType === "refugio" && (
            <>
              <div className="input-group">
                <input
                  type="text"
                  name="refugeName"
                  value={formData.refugeName}
                  onChange={handleInputChange}
                  placeholder="Nombre del refugio"
                />
                {errors.refugeName && <span className="error">{errors.refugeName}</span>}
              </div>

              <div className="input-group">
                <input
                  type="text"
                  name="refugeAddress"
                  value={formData.refugeAddress}
                  onChange={handleInputChange}
                  placeholder="Dirección del refugio"
                />
                {errors.refugeAddress && <span className="error">{errors.refugeAddress}</span>}
              </div>
            </>
          )}

          <button type="submit" className="register-button" disabled={loading}>
            {loading ? "Registrando..." : "Registrar"}
          </button>

          <hr className="line-register" />
          <div className="button-social-container">
            <p className="social-title">También puedes crear tu cuenta con</p>
            <button className="button-style">
              <FaGoogle className="iconStyle" />
              Google
            </button>
            <button className="button-style-face">
              <FaFacebook className="iconStyle-face" />
              Facebook
            </button>
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
    </div>
  );
};

export default Register;

