import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import { config } from 'dotenv'

// https://vite.dev/config/
export default defineConfig({
  base: '/SegundasHuellas/',
  plugins: [react()],
  define: {
    'process.env': process.env
  }
})
