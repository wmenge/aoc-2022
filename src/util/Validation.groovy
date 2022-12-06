package util

class Validation {

    static String validateOnAOC(day) {
        return "https://adventofcode.com/2022/day/${day}/input".toURL().getText(
                requestProperties: ['cookie': 'session=53616c7465645f5f0b5abf22a5ab102515f1edba6f1df35a58cf4c15dc47fd25d2a50dec2c4f39710cb29bcc784bcfa37b51d8eaa639000ba12bbbbf620ea583'])

    }
}
