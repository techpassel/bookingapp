import { useEffect, useRef } from "react";

export const useOnOutsideClick = handleOutsideClick => {
    //Here note that 'handleOutsideClick' is a function and not a normal variable.
    //In JS we can pass functions also are parameter into another function.

    const innerBorderRef = useRef();

    const onClick = event => {
        if (
            innerBorderRef.current &&
            !innerBorderRef.current.contains(event.target)
        ) {
            handleOutsideClick();
        }
    };

    useMountEffect(() => {
        document.addEventListener("click", onClick, true);
        return () => {
            document.removeEventListener("click", onClick, true);
        };
    });

    return { innerBorderRef };
};

const useMountEffect = e => useEffect(e, []);