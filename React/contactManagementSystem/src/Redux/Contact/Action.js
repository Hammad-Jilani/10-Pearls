import api, { API_BASE_URL } from "@/config/api"
import { CREATE_CONTACT_FAILURE, CREATE_CONTACT_REQUEST, CREATE_CONTACT_SUCCESS, DELETE_CONTACT_FAILURE, DELETE_CONTACT_REQUEST, DELETE_CONTACT_SUCCESS, FETCH_CONTACTS_FAILURE, FETCH_CONTACTS_REQUEST, FETCH_CONTACTS_SUCCESS, SEARCH_CONTACT_FAILURE, SEARCH_CONTACT_REQUEST, SEARCH_CONTACT_SUCCESS, UPDATE_CONTACT_FAILURE, UPDATE_CONTACT_REQUEST, UPDATE_CONTACT_SUCCESS } from "./ActionType"
import axios from "axios";

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

export const fetchContacts = (page = 0, size = 3) => async (dispatch) => {
  dispatch({ type: FETCH_CONTACTS_REQUEST });
  try {
    const response = await api.get(`/api/contact/contacts?page=${page}&size=${size}`)
    dispatch({
      type: FETCH_CONTACTS_SUCCESS,
      payload: {
        data: response.data.content,
        page: response.data.number,
        totalPages: response.data.totalPages,
      },
    });
  } catch (error) {
    console.log(error);

    dispatch({ type: FETCH_CONTACTS_FAILURE, error: error.message });
  }
};

export function updateContact({ contactId, dataUpdate }) {
  return async function (dispatch) {
    dispatch({ type: UPDATE_CONTACT_REQUEST })
    try {
      const { data } = await api.put(`/api/contact/update/${contactId}`, dataUpdate)
      dispatch({ type: UPDATE_CONTACT_SUCCESS, payload: data })
    } catch (error) {
      console.log('update error ', error);
      dispatch({ type: UPDATE_CONTACT_FAILURE, error: error.message })
    }
  }
}

export function deleteContact({ contactId }) {
  return async function (dispatch) {
    dispatch({ type: DELETE_CONTACT_REQUEST })
    try {
      const { data } = await api.delete(`/api/contact/${contactId}`)
      dispatch({ type: DELETE_CONTACT_SUCCESS, contactId: contactId, payload: data })
    } catch (error) {
      console.log('delete error ', error);
      dispatch({ type: DELETE_CONTACT_FAILURE, error: error.message })
    }
  }
}

export function searchContactList(keyword) {
  return async function (dispatch) {
    dispatch({ type: SEARCH_CONTACT_REQUEST })

    try {
      const { data } = await api.get('/api/contact/search?keyword=' + keyword)
      console.log("search contact ", data);
      dispatch({ type: SEARCH_CONTACT_SUCCESS, contact: data })
    } catch (error) {
      console.log('search ', error);
      dispatch({ type: SEARCH_CONTACT_FAILURE, error: error.message })
    }
  }
}