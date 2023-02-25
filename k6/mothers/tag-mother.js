import { getRandomString, getRandomInt } from './randomGenerators.js'

export function getRandomTags() {
    const totalTags = getRandomInt(0, 5)
    var tags = []
    for (let index = 0; index < totalTags; index++) {
        tags.push(getRandomString(getRandomInt(3, 7)))
    }

    return tags;
}