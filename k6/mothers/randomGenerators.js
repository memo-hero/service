export function getRandomString(length) {
    let result = '';
    const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    const charactersLength = characters.length;
    let counter = 0;
    while (counter < length) {
        result += characters.charAt(Math.floor(Math.random() * charactersLength));
        counter += 1;
    }
    return result;
}

export function getRandomInt(min, max) {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

export function getRandomCategory() {
    switch (getRandomInt(0, 5)) {
        case 0:
            return "ARTS";
        case 1:
            return "COMPUTERS";
        case 2:
            return "HISTORY";
        case 3:
            return "LANGUAGES";
        default:
            return "SCIENCE";
    }
}

