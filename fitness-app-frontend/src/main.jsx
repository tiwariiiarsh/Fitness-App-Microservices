// index.js
import React from 'react'
import ReactDOM from 'react-dom/client'
import { Provider } from 'react-redux'
import { store } from './store/store'
import App from './App.jsx'
import { AuthProvider } from 'react-oauth2-code-pkce'
import { authConfig } from './authconfig.js'

const root = ReactDOM.createRoot(
  document.getElementById('root')
)


root.render(
  // <React.StrictMode>
  <AuthProvider authConfig={authConfig}
                loadingComponenet={<div>Loading...</div>}>
      <Provider store={store}>
        <App />
      </Provider>
  </AuthProvider>
  // </React.StrictMode>.
)
