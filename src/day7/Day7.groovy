package day7

import util.Input

class Parser {

    def parse(input) {
        def context = new Context()
        input.readLines().each{ buildState(it)?.process(context) }
        return context
    }

    def buildState(line) {
        if (line.startsWith("\$ cd ")) {
            return new ChangeDirectoryCommand(line.replace("\$ cd ", ""))
        }

        if (line ==~ /^[0-9].*/) {
            def tokens = line.tokenize(" ")
            return new FileEntry(tokens[1], tokens[0].toInteger())
        }
    }
}

class Context {
    def directories = []
    private root
    def currentDir

    Context() {
        root = new DirectoryEntry("/", null)
        currentDir = root
        directories += root
    }
}

interface State {
    def process(context)
}

class ChangeDirectoryCommand implements State {

    def parameter

    ChangeDirectoryCommand(parameter) {
        this.parameter = parameter
    }

    @Override
    def process(context) {

        // really another state
        if (parameter == "..") {
            context.currentDir = context.currentDir.parent
        // also another state really
        } else if (parameter == "/") {
            context.currentDir = context.directories.find{ it.name == parameter }
        } else {
             def directory = context.directories.find{ it.name == parameter && it.parent === context.currentDir }

            if (!directory) {
                directory = new DirectoryEntry(parameter, context.currentDir)
                context.directories += directory
            }

            context.currentDir = directory
        }
    }
}

class DirectoryEntry {

    def parent
    def name
    int size

    DirectoryEntry(name, parent) {
        this.name = name
        this.parent = parent
    }

    def calculateTotalSize(context) {
        // nice old fashioned recursion, ahh!
        def result = size + (context.directories.findAll{ it.parent === this }.collect{ it.calculateTotalSize(context) }.sum() ?: 0)
        return result
    }
}

class FileEntry implements State {

    def name
    int size

    FileEntry(name, int size) {
        this.name = name
        this.size = size
    }

    @Override
    def process(context) {
        context.currentDir.size += this.size
    }
}

def calculateTotalSize(input) {
    def parser = new Parser()
    def context = parser.parse(input)
    return context.directories.collect{ it.calculateTotalSize(context) }.findAll{ it <= 100000 }.sum()
}

def selectDeletionCanditate(input) {
    def parser = new Parser()
    def context = parser.parse(input)

    // determine space to reclaimed
    final totalDiskSize = 70000000
    final updateSize = 30000000
    final spaceUsed = context.directories.find{ it.name == "/" && it.parent == null }.calculateTotalSize(context)
    final toBeReclaimed = updateSize - totalDiskSize + spaceUsed

    // Get smallest dir that satisfies required space
    return context.directories.collect{ it.calculateTotalSize(context) }.findAll{ it > toBeReclaimed }.sort().first()
}

static void main(String[] args) {

    // Part 1
    assert calculateTotalSize(Input.readFromFile("testinput.txt")) == 95437
    assert calculateTotalSize(Input.readFromAOC(7)) == 1915606

    // Part 2
    assert selectDeletionCanditate(Input.readFromFile("testinput.txt")) == 24933642
    assert selectDeletionCanditate(Input.readFromAOC(7)) == 5025657
}
