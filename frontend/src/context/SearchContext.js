import { createContext, useReducer } from "react"

const INITIAL_STATE = JSON.parse(localStorage.getItem('search_data')) || {
    city: undefined,
    dates: [],
    options: {
        adult: undefined,
        children: undefined,
        room: undefined
    }
}

export const SearchContext = createContext(INITIAL_STATE);

const SearchReducer = (state, action) => {
    switch (action.type) {
        case "NEW_SEARCH":
            localStorage.setItem('search_data', JSON.stringify(action.payload));
            return action.payload
        case "RESET_SEARCH":
            localStorage.removeItem('search_data');
            return INITIAL_STATE
        default:
            return state
    }
}

export const SearchContextProvider = ({ children }) => {
    const [state, dispatch] = useReducer(SearchReducer, INITIAL_STATE);

    return (<SearchContext.Provider value={{
        city: state.city,
        dates: state.dates,
        options: state.options,
        dispatch
    }}>
        {children}
    </SearchContext.Provider>);
}