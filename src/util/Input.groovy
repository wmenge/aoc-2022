package util

class Input {

    static String readFromFile(filename) {
        File test = new File(filename)
        return test.getText("UTF-8")
    }
    static String readFromAOC(day) {
        def cachedInput = new File(".cache/input.txt")
        try {
            return cachedInput.getText("UTF-8")
        } catch(FileNotFoundException e) {
            def cookie = new File(System.getProperty("user.home") + "/advent-of-code-cookie.txt").getText("UTF-8").trim()
            def input = "https://adventofcode.com/2022/day/${day}/input".toURL().getText(
                    requestProperties: ['cookie': "session=${cookie}"])
            new File(".cache").mkdir()
            cachedInput.write(input)

            return input;
        }
    }
}