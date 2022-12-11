package day3

import util.Input

class Rucksack {
    var compartments
    var contents

    Rucksack(contents) {
        this.contents = contents
        int mid = contents.length() / 2; //get the middle of the String
        this.compartments = [contents.substring(0, mid),contents.substring(mid)];
    }

    def getCommonTypes() {
        def lists = compartments.collect{ it.split("").toList() }
        return lists[0].intersect(lists[1]).unique()
    }
}

class Group {
    var ruckSacks

    Group(ruckSacks) {
        this.ruckSacks = ruckSacks
    }

    def getPasses() {
        return this.ruckSacks.collect{ it.contents.split("").toList() }.inject{
            ruckSack1, ruckSack2 -> ruckSack1.intersect(ruckSack2)
        }.unique().first()
    }

}

def priorityOf(type) {
    return (int)type - ((int)type < 97 ? 38 : 96)
}

def testPriorityOf() {
    assert priorityOf("a") == 1
    assert priorityOf("b") == 2
    assert priorityOf("z") == 26
    assert priorityOf("A") == 27
    assert priorityOf("B") == 28
    assert priorityOf("Z") == 52
}

def testRuckSack() {
    assert new Rucksack("vJrwpWtwJgWrhcsFMMfFFhFp").commonTypes == ["p"]
    assert new Rucksack("jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL").commonTypes == ["L"]
    assert new Rucksack("PmmdzqPrVvPwwTWBwg").commonTypes == ["P"]
    assert new Rucksack("wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn").commonTypes == ["v"]
    assert new Rucksack("ttgJtRGJQctTZtZT").commonTypes == ["t"]
    assert new Rucksack("CrZsJsPPZsGzwwsLwLmpwMDw").commonTypes == ["s"]
}

def calculatePriorities(input) {
    return input.readLines().collect{ new Rucksack(it).commonTypes.collect{ priorityOf(it) }.sum() }.sum()
}

def calculateGroupPriorities(input) {
    def groups = []
    def lines = input.readLines();

    // build groups
    while (!lines.isEmpty()) {
        groups.add(new Group(lines.take(3).collect{ new Rucksack(it)}))
        lines = lines.drop(3)
    }

    return groups.collect{ priorityOf(it.passes) }.sum()
}

static void main(String[] args) {
    testPriorityOf()
    testRuckSack()

    // Part 1
    assert calculatePriorities(Input.readFromFile("testinput.txt")) == 157
    assert calculatePriorities(Input.readFromAOC(3)) == 7831

    // Part 2
    assert calculateGroupPriorities(Input.readFromFile("testinput.txt")) == 70
    assert calculateGroupPriorities(Input.readFromAOC(3)) == 2683
}
