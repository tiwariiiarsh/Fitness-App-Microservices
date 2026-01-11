import { createSlice } from '@reduxjs/toolkit'

const initialState = { 
  user: JSON.parse(localStorage.getItem("user")) || null,
  token: localStorage.getItem("token") || null,
  userId: localStorage.getItem("userId") || null,
}

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {

    // Login / Register ke time call hoga
    setCredentials: (state, action) => {
      const { user, token, userId } = action.payload

      state.user = user
      state.token = token
      state.userId = userId

      localStorage.setItem("user", JSON.stringify(user))
      localStorage.setItem("token", token)
      localStorage.setItem("userId", userId)
    },

    // Logout ke time call hoga
    logout: (state) => {
      state.user = null
      state.token = null
      state.userId = null

      localStorage.removeItem("user")
      localStorage.removeItem("token")
      localStorage.removeItem("userId")
    },
  },
})

export const { setCredentials, logout } = authSlice.actions
export default authSlice.reducer
