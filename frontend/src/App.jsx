import './App.css'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import { LandingPage, SearchPage, AboutPage } from './pages/'

function App() {
    const WIP_MESSAGE = "Página aún en construcción..."
    const ERROR_MESSAGE = "¡UPS! Esa página no existe..."

    return (
        <BrowserRouter basename="/SegundasHuellas/">
            <Routes>
                <Route path="/" element={<LandingPage />} />
                <Route path="/home" element={<SearchPage />} />
                <Route path="/pet" element={<h2>{WIP_MESSAGE}</h2>} />
                <Route path="/register" element={<h2>{WIP_MESSAGE}</h2>} />
                <Route path="/login" element={<h2>{WIP_MESSAGE}</h2>} />
                <Route path="/about" element={<AboutPage />} />
                <Route path="/mission" element={<h2>{WIP_MESSAGE}</h2>} />
                <Route path="/contact" element={<h2>{WIP_MESSAGE}</h2>} />
                <Route path="/tips" element={<h2>{WIP_MESSAGE}</h2>} />
                <Route path="/process" element={<h2>{WIP_MESSAGE}</h2>} />
                <Route path="*" element={<h2>{ERROR_MESSAGE}</h2>} />
            </Routes>
        </BrowserRouter>
    )
}

export default App
