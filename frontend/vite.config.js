// Contenido NUEVO (CORRECTO)
import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react' // <--- Esta es la línea corregida

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
})