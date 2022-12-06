package day2

import util.Input

enum Shape {
    Rock,
    Paper,
    Scissors

    static def shapeMap =
            [ A: Shape.Rock,
              B: Shape.Paper,
              C: Shape.Scissors,
              X: Shape.Rock,
              Y: Shape.Paper,
              Z: Shape.Scissors ]

    static def comparator = new ShapeComparator()
}

class ShapeComparator implements Comparator {

    @Override
    int compare(Object o1, Object o2) {
        if (o1 == o2) return 0

        if((o1 == Shape.Rock && o2 == Shape.Paper) || (o1 == Shape.Paper && o2 == Shape.Scissors) || (o1 == Shape.Scissors && o2 == Shape.Rock))
            return 1

        return compare(o2, o1) * -1
    }
}

static int playGame(input) {
    input.readLines().collect{
        def line = it.split(" ")
        return [opponent: Shape.shapeMap[line[0]], you: Shape.shapeMap[line[1]]]
    }.collect{ playRound(it) }.sum()
}

static int playRound(game) {
    return (game.you.ordinal() + 1) + (Shape.comparator.compare(game.opponent, game.you) + 1) * 3
}

static int playGame2(input) {
    input.readLines().collect{
        def line = it.split(" ")
        return [opponent: Shape.shapeMap[line[0]], goal: line[1]]
    }.collect{ playRound2(it) }.sum()
}

static int playRound2(game) {
    def desiredOutcomes = [X: -1, Y: 0, Z: 1]
    def shape = Shape.values().find{Shape.comparator.compare(game.opponent, it) == desiredOutcomes[game.goal] }
    return playRound([opponent:game.opponent, you: shape])
}

static void testRound() {
    assert playRound([opponent:Shape.shapeMap["A"], you:Shape.shapeMap["Y"]]) == 8
    assert playRound([opponent:Shape.shapeMap["B"], you:Shape.shapeMap["X"]]) == 1
    assert playRound([opponent:Shape.shapeMap["C"], you:Shape.shapeMap["Z"]]) == 6
    assert playRound([opponent:Shape.shapeMap["A"], you:Shape.shapeMap["Z"]]) == 3
    assert playRound([opponent:Shape.shapeMap["C"], you:Shape.shapeMap["X"]]) == 7
}

static void testRound2() {
    assert playRound2([opponent:Shape.shapeMap["A"], goal:"Y"]) == 4
    assert playRound2([opponent:Shape.shapeMap["B"], goal:"X"]) == 1
    assert playRound2([opponent:Shape.shapeMap["C"], goal:"Z"]) == 7
}

static void main(String[] args) {
    testRound();
    testRound2()

    // Part 1
    assert playGame(Input.readFromFile("testinput.txt")) == 15
    assert playGame(Input.readFromAOC(2)) == 14297

    // Part 2
    assert playGame2(Input.readFromFile("testinput.txt")) == 12
    assert playGame2(Input.readFromAOC(2)) == 10498
}





