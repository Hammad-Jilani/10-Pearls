import api, { API_BASE_URL } from "@/config/api"
import { CHANGE_USER_PASSWORD_FAILURE, CHANGE_USER_PASSWORD_REQUEST, CHANGE_USER_PASSWORD_SUCCESS, GET_USER_FAILURE, GET_USER_REQUEST, GET_USER_SUCCESS, LOGIN_FAILURE, LOGIN_REQUEST, LOGIN_SUCCESS, LOGOUT, REGISTER_FAILURE, REGISTER_REQUEST, REGISTER_SUCCESS } from "./Action"
import axios from "axios"
import { toast } from "sonner"

export function register(userData) {
  return async function (dispatch) {
    dispatch({ type: REGISTER_REQUEST })
    try {
      const { data } = await axios.post(`${API_BASE_URL}/auth/signup`, userData)
      if (data.jwt) {
        localStorage.setItem('jwt', data.jwt)
        console.log('register redux success dispatch', data);

        dispatch({ type: REGISTER_SUCCESS, payload: data })
      }
    } catch (error) {
      console.log(error.response.data.detail);

      dispatch({ type: REGISTER_FAILURE, error: error.response.data.detail })
    }
  }
}

export function changePassword(changeRequest) {
  return async function (dispatch) {
    dispatch({ type: CHANGE_USER_PASSWORD_REQUEST })
    try {
      const { data } = await api.post('/api/user/change', changeRequest)
      console.log(data);
      toast.success(data.message)
      dispatch({ type: CHANGE_USER_PASSWORD_SUCCESS, payload: data })
    } catch (error) {
      console.log('change password ', error);
      toast.error('Incorrect Credentials! Failed to change password')
      dispatch({ type: CHANGE_USER_PASSWORD_FAILURE, error: error.message })
    }
  }
}

export const getUser = () => async (dispatch) => {
  dispatch({ type: GET_USER_REQUEST })
  try {
    const { data } = await axios.get(`${API_BASE_URL}/api/user/profile`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('jwt')}`
      }
    })

    dispatch({ type: GET_USER_SUCCESS, payload: data })

    console.log('user success ', data);

  } catch (error) {
    dispatch({ type: GET_USER_FAILURE, error: error.message })
  }
}

export function login(userData) {
  return async function (dispatch) {
    dispatch({ type: LOGIN_REQUEST })
    try {
      const { data } = await axios.post(`${API_BASE_URL}/auth/signin`, userData)

      localStorage.setItem('jwt', data.jwt)
      console.log('login redux success dispatch', data);
      dispatch({ type: LOGIN_SUCCESS, payload: data })

    } catch (error) {
      console.log(error);
      dispatch({ type: LOGIN_FAILURE, error: error.response.data.message })
    }
  }
}

export const logout = () => async (dispatch) => {
  dispatch({ type: LOGOUT })
  localStorage.clear()
}