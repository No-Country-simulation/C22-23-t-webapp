import React, { useState } from "react";
import "./Login.css";
import { Link, useNavigate } from "react-router-dom";
import { FaGoogle, FaFacebook } from "react-icons/fa";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const validateEmail = (email) => {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(String(email).toLowerCase());
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setError("");

    if (!email || !password) {
      setError("Por favor, complete todos los campos");
      return;
    }

    if (!validateEmail(email)) {
      setError("Por favor, ingrese un email válido");
      return;
    }

    if (password.length < 6) {
      setError("La contraseña debe tener al menos 6 caracteres");
      return;
    }

    try {
      setLoading(true);

      const response = await loginUser({
        email,
        password,
      });

      if (response.success) {
        localStorage.setItem("token", response.token);
      } else {
        setError(response.message || "Error en el inicio de sesión");
      }
    } catch (err) {
      setError("Error de conexión. Intente nuevamente.");
    } finally {
      setLoading(false);
    }
  };

  const loginUser = async (credentials) => {
    return new Promise((resolve) => {
      setTimeout(() => {
        if (
          credentials.email === "email@email.com" &&
          credentials.password === "password"
        ) {
          resolve({
            success: true,
            token: "fake-jwt-token",
          });
          navigate("/");
          console.log("Credenciales aprobadas.");
        } else {
          resolve({
            success: false,
            message: "Credenciales inválidas",
          });
        }
      }, 1000);
    });
  };

  return (
    <div className="login-container">
      <div className="form-container">
        <form onSubmit={handleSubmit} className="login-form">
          <h2 className="form-title">Iniciar Sesión</h2>

          {error && <div className="error-message">{error}</div>}

          <div className="form-group">
            <input
              type="email"
              id="email"
              value={email}
              onChange={(event) => setEmail(event.target.value)}
              placeholder="Ingrese su email"
              required
            />
          </div>

          <div className="form-group">
            <input
              type="password"
              id="password"
              value={password}
              onChange={(event) => setPassword(event.target.value)}
              placeholder="Ingrese su contraseña"
              required
            />
          </div>

          <button type="submit" className="login-button" disabled={loading}>
            {loading ? "Iniciando sesión..." : "Iniciar Sesión"}
          </button>

          <div className="form-footer">
            ¿Olvidaste tu contraseña?{" "}
            <Link className="links-form" to="recovery">
              Recupérala aquí
            </Link>
          </div>
          <div className="button-social-container">
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
        <p className="form-register">
          ¿Quieres ser parte de ésta hermosa comunidad?
          <Link className="links-form-register" to="/register">
            Únete
          </Link>
        </p>
      </div>
      <figure className="image-container">
        <img className="image-login" src="login-image.jpg" alt="Imagen de la mascota en Login" />
      </figure>
    </div>
  );
};

export default Login;
