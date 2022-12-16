package day10

import util.Input

enum InstructionSet {
    noop(Noop), addx(AddX)

    def implementation

    InstructionSet(implementationClass) {
        this.implementation = implementationClass.newInstance()
    }
}

class Clock {

    def cpu, crt, afterTickClosure

    Clock(cpu, crt, afterTickClosure) {
        this.cpu = cpu
        this.crt = crt
        this.afterTickClosure = afterTickClosure
    }

    def tick() {
        this.afterTickClosure()
        cpu.cycles++
        if ((cpu.cycles + 20) % 40 == 0) {
            //println([cpu, cpu.cycles * cpu.x])
            cpu.signalStrengths += cpu.cycles * cpu.x
        }

    }
}

class Instruction {
    def cycles = 1

    def execute(cpu, clock, parameter) {
        cycles.times{clock.tick() }
    }
}

class Noop extends Instruction {

    def execute(cpu, clock, parameter) {
        // I do nothing
        super.execute(cpu,clock, parameter)
    }
}

class AddX extends Instruction {

    AddX() {
        this.cycles = 2
    }

    def execute(cpu, clock, parameter) {
        //println("Add ${parameter}")
        super.execute(cpu, clock, parameter)
        cpu.x += parameter
    }
}

def printCrt(crt) {
    (0..5).each{ y ->
        println(crt[(40*y)..(40*(y+1)-1)].join(""))
    }
    println("")
}

def simulate(input) {
    def cpu = [x:1,cycles:0,signalStrengths:[]]
    def crt = ["."]*(40*6)

    def draw = {
        if (Math.abs(cpu.x - (cpu.cycles % 40)) < 2) {
            crt[cpu.cycles] = "#"
        } else {
            crt[cpu.cycles] = "."
        }

        //println("cycle: ${cpu.cycles}")
        //println((0..39).collect{ (Math.abs(it - cpu.x) < 2) ? "X" : "."}.join(""))
        //printCrt(crt)
    }

    def clock = new Clock(cpu, crt, draw)

    def instructions = input.readLines().collect{
        tokens = it.tokenize(" ")
        def instruction = InstructionSet.valueOf(tokens[0]).implementation
        def parameter = tokens[1]?.toInteger() ?: 0
        instruction.execute(cpu, clock, parameter)
    }
    printCrt(crt)
    return cpu.signalStrengths.sum()
}

static void main(String[] args) {

    // Part 1 & 2
    assert simulate( Input.readFromFile("testinput.txt")) == 13140
    assert simulate( Input.readFromAOC(10)) == 11960
}





