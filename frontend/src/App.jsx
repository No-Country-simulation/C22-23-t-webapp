import './App.css'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import { LandingPage, SearchPage, AboutPage, ContactPage, MissionPage, TipsPage, ProcessPage } from './pages/'

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
                <Route path="/mission" element={<MissionPage />} />
                <Route path="/contact" element={<ContactPage />} />
                <Route path="/tips" element={<TipsPage />} />
                <Route path="/process" element={<ProcessPage />} />
                <Route path="*" element={<h2>{ERROR_MESSAGE}</h2>} />
            </Routes>
        </BrowserRouter>
    )
}

export default App
