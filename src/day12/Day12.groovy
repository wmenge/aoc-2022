package day12

import util.Input

class Day12Map {
    def contents
    def width

    Day12Map(contents, width) {
        this.contents = contents
        this.width = width
    }

    def getValue(coords) {
        return this.contents[coords.y * width + coords.x]
    }

    def setValue(coords, value) {
        this.contents[coords.y * width + coords.x] = value
    }

    def getAllForValue(value) {
        return contents.findIndexValues{ it == value }.collect{[x:it % this.width, y:it.intdiv(this.width)]}
    }

    def print() {
        this.contents.size().intdiv(this.width).times { i ->
            def line = this.contents
                    .subList(i * this.width, (i + 1) * width)
                    .collect { it == Long.MAX_VALUE ? " " : it }
                    .collect { String.format("%3s", it) }
                    .join("|")
            println(line)
        }
        println("")
    }

    def neighbors(coords) {
        def neighbors = []

        ((coords.y - 1)..(coords.y + 1)).each { i ->
            def candidate = [x: coords.x, y: i]
            if (this.exists(candidate) && coords != candidate && (this.getValue(coords) - this.getValue(candidate)) < 2) {
                neighbors += candidate
            }
        }

        ((coords.x - 1)..(coords.x + 1)).each { j ->
            def candidate = [x: j, y: coords.y]
            if (this.exists(candidate) && coords != candidate && (this.getValue(coords) - this.getValue(candidate)) < 2) {
                neighbors += candidate
            }
        }

        return neighbors
    }

    def exists(coords) {
        return coords.x >= 0 && coords.x < this.width && coords.y >= 0 && coords.y < this.contents.size().intdiv(this.width)
    }
}

def setupContext(input) {
    def levels = []
    def distances = []
    def start, destination
    def width = input.readLines().first().length()

    // Build maps and extract start, destination
    input.readLines().eachWithIndex { line, i ->
        line.split("").eachWithIndex { node, j ->
            if (node == "S") start = [x: j, y: i]
            if (node == "E") destination = [x: j, y: i]
            levels += (int) ((node == "S") ? "a" : (node == "E") ? "z" : node) - 97;
            distances += Long.MAX_VALUE
        }
    }

    def levelsMap = new Day12Map(levels, width)
    def distancesMap = new Day12Map(distances, width)

    return [start:start, destination:destination,levelsMap:levelsMap,distancesMap:distancesMap]
}

// Go Dijkstra!
def calculateShortestPaths(context) {
    context.distancesMap.setValue(context.destination, 0)
    def unsettledNodes = [context.destination].toSet()
    def settledNodes = [].toSet()

    while (!unsettledNodes.isEmpty()) {
        def currentNode = unsettledNodes.sort{a,b -> context.distancesMap.getValue(a) <=> context.distancesMap.getValue(b)}.first()
        def nodesToConsider = (context.levelsMap.neighbors(currentNode) - settledNodes)

        nodesToConsider.each {
            context.distancesMap.setValue(it, Math.min(context.distancesMap.getValue(it), context.distancesMap.getValue(currentNode) + 1))
            unsettledNodes += it
        }

        unsettledNodes.remove(currentNode)
        settledNodes += currentNode
    }

    return context.distancesMap
}


def getShortesPath(input) {
    def context = setupContext(input)
    calculateShortestPaths(context)
    return context.distancesMap.getValue(context.start)
}

def getShortesPathToLevelA(input) {
    def context = setupContext(input)
    calculateShortestPaths(context)
    return context.levelsMap.getAllForValue(0).collect{ context.distancesMap.getValue(it) }.sort().first()
}

static void main(String[] args) {

    // Part 1
    assert getShortesPath(Input.readFromFile("testinput.txt")) == 31
    assert getShortesPath(Input.readFromAOC(12)) == 484

    // Part 1
    assert getShortesPathToLevelA(Input.readFromFile("testinput.txt")) == 29
    assert getShortesPathToLevelA(Input.readFromAOC(12)) == 478

}