package util

class Input {

    static String readFromFile(filename) {
        File test = new File("testinput.txt")
        return test.getText("UTF-8")
    }

    static String readFromAOC(day) {
       return "https://adventofcode.com/2022/day/${day}/input".toURL().getText(
                requestProperties: ['cookie': 'session=<some cookie>'])

    }
}
