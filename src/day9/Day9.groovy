package day9

import util.Input

enum Direction {
    R(RightMotion), U(UpMotion), L(LeftMotion), D(DownMotion)

    def implementation

    Direction(directionClass) {
        this.implementation = directionClass.newInstance()
    }
}

class Motion {

    def perform(state) {
        state.nodes.inject(state.nodes[0]) { node1, node2 ->
            if (node1 !== node2) {
                this.updateTail(node1, node2)
            }
            return node2;
        }
        state.map += state.nodes.last().clone()
    }

    def updateTail(head, tail) {
        def oldTail = tail.clone()

        if (head.x - tail.x > 1 || (head.x - tail.x > 0 && Math.abs(head.y - tail.y) > 1)) {
            tail.x++
        } else if (head.x - tail.x < -1 || (head.x - tail.x < 0 && Math.abs(head.y - tail.y) > 1)) {
            tail.x--
        }

        if (head.y - tail.y > 1 || (head.y - oldTail.y > 0 && Math.abs(head.x - oldTail.x) > 1)) {
            tail.y++
        } else if (head.y - tail.y < -1 || (head.y - oldTail.y < 0 && Math.abs(head.x - oldTail.x) > 1)) {
            tail.y--
        }
    }
}

class UpMotion extends Motion {
    def perform(state) {
        state.nodes[0].y++
        super.perform(state)
    }
}

class RightMotion extends Motion {
    def perform(state) {
        state.nodes[0].x++
        super.perform(state)
    }
}

class LeftMotion extends Motion {
    def perform(state) {
        state.nodes[0].x--
        super.perform(state)
    }
}

class DownMotion extends Motion {
    def perform(state) {
        state.nodes[0].y--
        super.perform(state)
    }
}

def printState(state) {
    println(state)
    def range = -25..25

    lines = []

    range.each { y ->
        {
            def line = ""
            range.each { x ->
                def result = ""
                state.nodes.eachWithIndex { it, index ->
                    if (it.x == x && it.y == y) {
                        result = index
                    }
                }

                if (result == "") {
                    if (x == 0 && y == 0) {
                        result = "s"
                    } else {
                        result = "."
                    }
                }

                line += result
            }
            lines += line
        }
    }
    lines.reverse().each{ println(it) }
    println("")

}

def printPath(state) {
    println(state)
    def range = -25..25

    lines = []

    range.each { y ->
        {
            def line = ""
            range.each { x ->
                def coord = [x:x,y:y]
                if (state.map.contains(coord)) {
                    line += "#"
                } else {
                    line += "."
                }
            }
            lines += line
        }
    }
    lines.reverse().each{ println(it) }
    println("")

}

def simulate(input, nodeCount) {
    def nodes = []
    nodeCount.times{ nodes+= [x:0,y:0] }
    def state = [nodes:nodes,map:[[x:0,y:0]] as Set]

    input.readLines().each{
        tokens = it.tokenize(" ")
        def motion = Direction.valueOf(tokens[0]).implementation
        def steps = tokens[1].toInteger()
        steps.times { motion.perform(state) }
        //printState(state)
    }
    //printPath(state)
    return state.map.size()
}

static void main(String[] args) {
    // Part 1
    assert simulate(Input.readFromFile("testinput.txt"), 2) == 13
    assert simulate(Input.readFromAOC(9), 2) == 6339

    // Part 2
    assert simulate(Input.readFromFile("testinput2.txt"), 10) == 36
    assert simulate(Input.readFromAOC(9), 10) == 2541
}