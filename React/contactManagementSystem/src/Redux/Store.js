import { thunk } from "redux-thunk";
import authReducer from "./Auth/Reducer";

import { combineReducers, legacy_createStore, applyMiddleware } from "redux"
import { LOGOUT } from "./Auth/Action";
import contactReducer from "./Contact/Reducer";

const appReducer = combineReducers({
  auth: authReducer,
  contact: contactReducer
})

const rootReducer = (state, action) => {
  if (action.type == LOGOUT) {
    state = undefined
  }
  return appReducer(state, action)
}

export const store = legacy_createStore(rootReducer, applyMiddleware(thunk))