import "./Login.css"
import { useState } from "react"
import { Link, useNavigate } from "react-router-dom"

export function Login() {
    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")
    const [error, setError] = useState("")
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

    const handleSubmit = async (event) => {
        event.preventDefault()
        setError("")

        if (!email || !password) {
            setError("Por favor, complete todos los campos")
            return
        }

        if (!validateEmail(email)) {
            setError("Por favor, ingrese un email válido")
            return
        }

        if (!validatePassword(password)) {
            setError("La contraseña debe tener al menos 8 caracteres, incluyendo una letra mayúscula, una minúscula, un número y un carácter especial.")
            return
        }

        try {
            setLoading(true)

            const response = await loginUser({
                email,
                password,
            })

            if (response.success) {
                // localStorage.setItem("token", response.token)
                // localStorage.setItem("refreshToken", response.refreshToken)
                // localStorage.setItem("expiresAt", response.expiresAt)

                navigate("/")
                console.log("Credenciales aprobadas.")
            } else {
                setError(response.message || "Error en el inicio de sesión")
            }
        } catch (err) {
            setError("Error de conexión. Intente nuevamente.")
        } finally {
            setLoading(false)
        }
    }

    const loginUser = async (credentials) => {
        try {
            const response = await fetch(import.meta.env.VITE_AUTH_LOGIN_URL , {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(credentials),
            })

            const data = await response.json()
            localStorage.setItem("userLogin", JSON.stringify(data))

            if (response.ok) {
                return {
                    success: true,
                    token: data.token,
                    refreshToken: data.refreshToken,
                    expiresAt: data.expiresAt,
                }
            } else {
                return {
                success: false,
                message: data.message || "Credenciales inválidas",
                }
            }
        } catch (error) {
            return {
                success: false,
                message: "Error de conexión. Intente nuevamente.",
            }
        }
    }

    return (
        <main className="login-container">
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
                        <Link className="links-form" to="/recovery">
                        Recupérala aquí
                        </Link>
                    </div>
                </form>
                <p className="form-register">
                    ¿Quieres ser parte de ésta hermosa comunidad?
                    <Link className="links-form-register" to="/register">Únete</Link>
                </p>
            </div>
            <figure className="image-container">
                <img className="image-login" src="login-image.jpg" alt="Imagen de la mascota en Login" />
            </figure>
        </main>
    )
}
