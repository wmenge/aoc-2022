package day13

import util.Input

def buildPairs(input) {
    def pairs = []
    def pair = [first:null,second:null]

    input.readLines().each {
        if (it == "") {
            pairs += pair
            pair = [first:null,second:null]
        }

        def packet = Eval.me(it)
        if (pair.first == null) {
            pair.first = packet
        } else if (pair.second == null) {
            pair.second = packet
        }
    }
    pairs += pair
    return pairs;
}

def buildList(input) {
    result = input.readLines().findAll{ it != ""}.collect{ Eval.me(it)}

    // add divider packets
    result += [[[2]]]
    result += [[[6]]]

    return result
}

def test() {
    def pairs = buildPairs(Input.readFromFile("testinput.txt"))

    assert hasCorrectOrder(pairs[0])
    assert hasCorrectOrder(pairs[1])
    assert !hasCorrectOrder(pairs[2])
    assert hasCorrectOrder(pairs[3])
    assert !hasCorrectOrder(pairs[4])
    assert hasCorrectOrder(pairs[5])
    assert !hasCorrectOrder(pairs[6])
    assert !hasCorrectOrder(pairs[7])
}

def hasCorrectOrder(pair) {
    return compare(pair.first, pair.second) > 0
}

def compare(List l1, List l2) {
    // old fashioned iterator from the 90 called
    for (def i = 0;i < l1.size();i++) {
        try {
            def order = compare(l1.get(i), l2.get(i))
            if (order != 0) return order
        } catch (IndexOutOfBoundsException e) {
            // right side ran out of items, incorrect order
            return -1
        }
    }
    // if left side ran out of items, correct order
    return l2.size() - l1.size()
}

def compare(int i1, List l2) {
    return compare([i1], l2)
}

def compare(List l1, int i2) {
    return compare(l1, [i2])
}

def compare(int i1, int i2) {
    return i2 - i1
}

def calculateDividerPositions(input) {
    def list = buildList(input)
    def sortedList = list.sort{a,b -> compare(a,b) }.reverse()

    def divider1 = sortedList.indexOf([[2]]) + 1
    def divider2 = sortedList.indexOf([[6]]) + 1

    return divider1 * divider2
}

static void main(String[] args) {
    test()

    // Part 1
    def pairs = buildPairs(Input.readFromFile("testinput.txt"))
    assert pairs.findAll{hasCorrectOrder(it) }.collect{ pairs.indexOf(it) + 1}.sum() == 13

    pairs = buildPairs(Input.readFromAOC(13))
    assert pairs.findAll{hasCorrectOrder(it) }.collect{ pairs.indexOf(it) + 1}.sum() == 6046

    // Part 2
    assert calculateDividerPositions(Input.readFromFile("testinput.txt")) == 140
    assert calculateDividerPositions(Input.readFromAOC(13)) == 21423


}