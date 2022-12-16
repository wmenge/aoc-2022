package day8

import util.Input

class Map {
    def contents

    def Map(contents) {
        this.contents = contents.readLines()
    }

    def getDimensions() {
        return [x:this.contents.first().length(),y:this.contents.size()]
    }

    def getValue(x, y) {
        return this.contents.get(y).substring(x, x+1)
    }

    def isVisible(x,y) {
        def value = this.getValue(x,y)

        // horizontal lines of sight
        def hline = this.contents.get(y)
        def line1 = !(hline.substring(0, x).split("").toList().any{it >= value})
        def line2 = !(hline.substring(x + 1).split("").toList().any{it >= value})

        def vline = this.contents.collect{ it.substring(x, x+1) }.join("")
        def line3 = !(vline.substring(0, y).split("").toList().any{it >= value})
        def line4 = !(vline.substring(y + 1).split("").toList().any{it >= value})

        return line1 || line2 || line3 || line4
    }

    def lineOfSight(trees, height) {
        if (!trees) return 0;

        def result = 0

        for (tree in trees) {
            result++
            if (tree >= height) {
                return result;
            }
        }

        return result
    }

    def scenicScore(x, y) {
        def value = this.getValue(x,y)

        def hline = this.contents.get(y)
        def line1 = hline.substring(0, x).split("").toList().findAll{ it }.reverse()
        def line2 = hline.substring(x + 1).split("").findAll{ it }.toList()

        def vline = this.contents.collect{ it.substring(x, x+1) }.join("")
        def line3 = vline.substring(0, y).split("").toList().findAll{ it }.reverse()
        def line4 = vline.substring(y + 1).split("").toList().findAll{ it }

        //println("${line1}, ${line2}, ${line3}, ${line4}")
        //println("${this.lineOfSight(line1, value)}, ${this.lineOfSight(line2, value)}, ${this.lineOfSight(line3, value)}, ${this.lineOfSight(line4, value)}")
        return this.lineOfSight(line1, value) * this.lineOfSight(line2, value) * this.lineOfSight(line3, value) * this.lineOfSight(line4, value)
    }
}

def countVisibleTrees(input) {
    def map = new Map(input)
    def result = 0

    for (def i = 0;i < map.dimensions.x;i++) {
        for (def j = 0;j < map.dimensions.y;j++) {
            if (map.isVisible(j,i)) result++
        }
    }
    return result;
}

def testIsVisible(input) {
    def map = new Map(input)

    assert map.getValue(0,0) == "3"
    assert map.getValue(1,0) == "0"
    assert map.getValue(0,1) == "2"
    assert map.getValue(4,4) == "0"

    assert map.isVisible(1,1)
    assert map.isVisible(2,1)
    assert !map.isVisible(3,1)
    assert map.isVisible(1,2)
    assert !map.isVisible(2,2)
    assert map.isVisible(3,2)
    assert !map.isVisible(1,3)
    assert map.isVisible(2,3)
    assert !map.isVisible(3,3)
}

def testScenicScore(input) {
    def map = new Map(input)

    assert map.scenicScore(0,0) == 0
    assert map.scenicScore(2,1) == 4
    assert map.scenicScore(2,3) == 8
}

def bestScenicScore(input) {
    def map = new Map(input)
    def scores = []

    for (def i = 0;i < map.dimensions.x;i++) {
        for (def j = 0;j < map.dimensions.y;j++) {
            //println("${j}, ${i}: ${map.getValue(j,i)} => ${map.scenicScore(j,i)}")
            scores += map.scenicScore(j, i)
        }
    }
    return scores.max();
}

static void main(String[] args) {

    // Part 1
    testIsVisible(Input.readFromFile("testinput.txt"))

    assert countVisibleTrees(Input.readFromFile("testinput.txt")) == 21
    assert countVisibleTrees(Input.readFromAOC(8)) == 1840

    // Part 2
    testScenicScore(Input.readFromFile("testinput.txt"))

    assert bestScenicScore(Input.readFromFile("testinput.txt")) == 8
    assert bestScenicScore(Input.readFromAOC(8)) == 405769
}
