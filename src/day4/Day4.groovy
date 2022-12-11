package day4

import util.Input

class Pair {
    def ranges

    Pair(ranges) {
        this.ranges = ranges
    }

    def fullyContains() {
        return ranges[0].containsAll(ranges[1]) || ranges[1].containsAll(ranges[0])
    }

    def hasOverlap() {
        return ranges[0].intersect(ranges[1]).size() > 0
    }
}

def range(input) {
    def tokens = input.tokenize("-")
    return range = tokens[0].toInteger()..tokens[1].toInteger()
}

def findPairs(input) {
    def pairs = input.readLines().collect{ new Pair(it.tokenize(",").collect{ range(it) })}
    return pairs.findAll{ it.fullyContains() }
}

def findPairs2(input) {
    def pairs = input.readLines().collect{ new Pair(it.tokenize(",").collect{ range(it) })}
    return pairs.findAll{ it.hasOverlap() }
}

static void main(String[] args) {

    // Part 1
    assert findPairs(Input.readFromFile("testinput.txt")).size() == 2
    assert findPairs(Input.readFromAOC(5)).size() == 485

    // Part 2
    assert findPairs2(Input.readFromFile("testinput.txt")).size() == 4
    assert findPairs2(Input.readFromAOC(5)).size() == 857
}
