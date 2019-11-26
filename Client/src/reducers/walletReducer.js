import { GET_WALLETS, GET_WALLET } from "../actions/types";

const initialState = {
  wallets: [],
  wallet: {}
};

export default function(state = initialState, action) {
  switch (action.type) {
    case GET_WALLETS:
      return {
        ...state,
        wallets: action.payload
      };

      case GET_WALLET:
        return {
          ...state,
          wallet: action.payload
        };

    default:
      return state;
  }
}