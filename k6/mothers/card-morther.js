import { getRandomCategory, getRandomString } from './randomGenerators.js'
import { getRandomTags } from './tag-mother.js'

export function getRandomCard() {
    return {
        front: getRandomString(20),
        back: getRandomString(20),
        category: getRandomCategory(),
        tags: getRandomTags()
    }
}