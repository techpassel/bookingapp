const getRatingRemark = (rating) => {
    let response = "";
    if (rating >= 4) {
        response = "Excellent"
    } else if (rating < 4 && rating >= 3) {
        response = "Good"
    } else if (rating < 3 && rating >= 2) {
        response = "OK"
    } else if (rating < 2 && rating >= 1) {
        response = "Unsatisfactory"
    } else {
        response = "Bad"
    }
    return response;
}

const formatCurrency = (amount) => {
    var formatter = new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'INR',
        maximumFractionDigits: 0
        // These options are needed to round to whole numbers if that's what you want.
        //minimumFractionDigits: 0, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
        //maximumFractionDigits: 0, // (causes 2500.99 to be printed as $2,501)
    });
    return formatter.format(amount);
}

const queryStringToObject = (str) => {
    let stringItems = str.substring(1).split("&");
    let result = {};
    stringItems.forEach(e => {
        let data = e.split("=");
        if (data[0] && data[0] != null)
            result = { ...result, [data[0]]: data[1] }
    })
    return result;
}

export {
    getRatingRemark,
    formatCurrency,
    queryStringToObject
}