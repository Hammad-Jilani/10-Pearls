import { CHANGE_USER_PASSWORD_FAILURE, CHANGE_USER_PASSWORD_REQUEST, CHANGE_USER_PASSWORD_SUCCESS, GET_USER_REQUEST, GET_USER_SUCCESS, LOGIN_FAILURE, LOGIN_REQUEST, LOGIN_SUCCESS, LOGOUT, REGISTER_FAILURE, REGISTER_REQUEST, REGISTER_SUCCESS } from "./Action";


const initialState = {
  user: null,
  loading: false,
  error: null,
  message: '',
  jwt: null
}

function authReducer(state = initialState, action) {
  switch (action.type) {
    case REGISTER_REQUEST:
    case LOGIN_REQUEST:
    case GET_USER_REQUEST:
    case CHANGE_USER_PASSWORD_REQUEST:
      return { ...state, loading: true, error: null }

    case CHANGE_USER_PASSWORD_SUCCESS:
      return { ...state, loading: false, error: null, message: action.payload }

    case REGISTER_SUCCESS:
    case LOGIN_SUCCESS:
      return { ...state, loading: false, jwt: action.payload.jwt }

    case GET_USER_SUCCESS:
      return { ...state, loading: false, error: null, user: action.payload }

    case LOGOUT:
      return initialState

    case REGISTER_FAILURE:
    case CHANGE_USER_PASSWORD_FAILURE:
    case LOGIN_FAILURE:
      return { ...state, loading: false, error: action.error }
    default:
      return state;
  }
}

export default authReducer