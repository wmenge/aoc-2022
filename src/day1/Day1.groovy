package day1

import util.Input

static int getMostCalories(input) {
    return input.split("\n\n").collect{ it.readLines().collect{ it.toInteger() }.sum() }.max()
}

static int getMostCaloriesTopElves(input) {
    return input.split("\n\n").collect{ it.readLines().collect{ it.toInteger() }.sum() }.sort().reverse().take(3).sum()
}

static void main(String[] args) {
    // Part 1
    assert getMostCalories(Input.readFromFile("testinput.txt")) == 24000
    assert getMostCalories(Input.readFromAOC(1)) == 71300

    // Part 2
    assert getMostCaloriesTopElves(Input.readFromFile("testinput.txt")) == 45000
    assert getMostCaloriesTopElves(Input.readFromAOC(1)) == 45000
}





