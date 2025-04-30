import api from "@/config/api"
import { CREATE_CONTACT_FAILURE, CREATE_CONTACT_REQUEST, CREATE_CONTACT_SUCCESS } from "./ActionType"

export function createContact(userData) {
  return async function (dispatch) {
    dispatch({ type: CREATE_CONTACT_REQUEST })
    console.log('create contact ', userData);

    try {
      const { data } = await api.post('/api/contact', userData)
      dispatch({ type: CREATE_CONTACT_SUCCESS, payload: data })
    } catch (error) {
      console.log(error);
      dispatch({ type: CREATE_CONTACT_FAILURE, error: error.response.data.message })
    }
  }
}