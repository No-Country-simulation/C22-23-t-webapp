import './App.css'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import { LandingPage } from './pages/LandingPage.jsx'

function App() {
    return (
        <BrowserRouter basename="/SegundasHuellas/">
            <Routes>
                <Route path="/" element={<LandingPage />} />
            </Routes>
        </BrowserRouter>
    )
}

export default App
