
import { CREATE_CONTACT_FAILURE, CREATE_CONTACT_REQUEST, CREATE_CONTACT_SUCCESS, DELETE_CONTACT_REQUEST, GET_CONTACT_REQUEST } from "./ActionType";

const initialState = {
  contact: [],
  error: null,
  loading: null,
  message: ''
}

function contactReducer(state = initialState, action) {
  switch (action.type) {
    case CREATE_CONTACT_REQUEST:
    case DELETE_CONTACT_REQUEST:
    case GET_CONTACT_REQUEST:
      return { ...state, error: null, loading: true }

    case CREATE_CONTACT_SUCCESS:
      return { ...state, loading: false, message: 'Created', contact: [...state.contact, action.payload] }

    case CREATE_CONTACT_FAILURE:
      return { ...state, loading: false, error: action.error }

    default:
      return state;
  }
}
export default contactReducer