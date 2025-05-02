
import { GET_USER_REQUEST } from "../Auth/Action";
import { CREATE_CONTACT_FAILURE, CREATE_CONTACT_REQUEST, CREATE_CONTACT_SUCCESS, DELETE_CONTACT_FAILURE, DELETE_CONTACT_REQUEST, DELETE_CONTACT_SUCCESS, FETCH_CONTACTS_FAILURE, FETCH_CONTACTS_REQUEST, FETCH_CONTACTS_SUCCESS, GET_CONTACT_FAILURE, GET_CONTACT_REQUEST, GET_CONTACT_SUCCESS, SEARCH_CONTACT_FAILURE, SEARCH_CONTACT_REQUEST, SEARCH_CONTACT_SUCCESS, UPDATE_CONTACT_REQUEST, UPDATE_CONTACT_SUCCESS } from "./ActionType";

const initialState = {
  contact: [],
  error: null,
  loading: null,
  message: '',
  page: 0,
  totalPages: 0,
  searchContact: null,
  contactDetail: null
}

function contactReducer(state = initialState, action) {
  switch (action.type) {
    case CREATE_CONTACT_REQUEST:
    case DELETE_CONTACT_REQUEST:
    case GET_CONTACT_REQUEST:
    case UPDATE_CONTACT_REQUEST:
    case SEARCH_CONTACT_REQUEST:
      return { ...state, error: null, loading: true }

    case SEARCH_CONTACT_SUCCESS:
      return { ...state, error: null, loading: false, searchContact: action.contact }


    case GET_CONTACT_SUCCESS:
      return { ...state, loading: false, contactDetail: action.contact }

    case CREATE_CONTACT_SUCCESS:
      return { ...state, loading: false, message: 'Create Contact', contact: [...state.contact, action.payload] }

    case CREATE_CONTACT_FAILURE:
      return { ...state, loading: false, error: action.error }

    case FETCH_CONTACTS_REQUEST:
      return { ...state, loading: true, error: null };

    case FETCH_CONTACTS_SUCCESS:
      return {
        ...state,
        loading: false,
        contact: action.payload.data,
        page: action.payload.page,
        totalPages: action.payload.totalPages,
      };

    case FETCH_CONTACTS_FAILURE:
    case GET_CONTACT_FAILURE:
    case DELETE_CONTACT_FAILURE:
    case SEARCH_CONTACT_FAILURE:
      return { ...state, loading: false, error: action.error };

    case UPDATE_CONTACT_SUCCESS:
      const updated = state.contact.filter(c => c.contactId != action.payload.contactId)
      return { ...state, loading: false, contact: [...updated, action.payload] }

    case DELETE_CONTACT_SUCCESS:
      return { ...state, loading: false, message: 'Delete Contact', contact: state.contact.filter(c => c.contactId != action.contactId) }

    default:
      return state;
  }
}
export default contactReducer