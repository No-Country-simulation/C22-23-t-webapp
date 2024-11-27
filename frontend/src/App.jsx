import './App.css'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import { LandingPage } from './pages/LandingPage.jsx'
import CardsPage from './components/cards/CardsPage'

function App() {
    const WIP_MESSAGE = "Página aún en construcción..."
    const ERROR_MESSAGE = "¡UPS! Esa página no existe..."

    return (
        <BrowserRouter basename="/SegundasHuellas/">
            <Routes>
                <Route path="/" element={<LandingPage />} />
                <Route path="/home" element={<CardsPage />} />
                <Route path="*" element={<h2>{ERROR_MESSAGE}</h2>} />
            </Routes>
        </BrowserRouter>
    )
}

export default App
