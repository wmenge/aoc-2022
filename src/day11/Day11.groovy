package day11

import util.Input

class Monkey {
    def id, items, operation, division, throwTrue, throwFalse, inspections

    Monkey(id) {
        this.id = id
        this.inspections = 0
    }

    def doStuff(monkeys) {
        this.items.clone().each { item ->
            this.inspections++;
            long level = Eval.me("old", item, this.operation)
            level = level.intdiv(3)
            this.items.removeElement(item)

            monkeys.get((level % this.division == 0) ? this.throwTrue : this.throwFalse).items += level
        }
    }

    def doWorrySomeStuff(monkeys, divisor) {
        this.items.clone().each { item ->
            this.inspections++;
            long level = Eval.me("old", item, this.operation)
            //level = level.intdiv(3)
            level = level % divisor
            this.items.removeElement(item)

            monkeys.get((level % this.division == 0) ? this.throwTrue : this.throwFalse).items += level
        }
    }
}

def buildMonkeys(input) {
    def monkey
    def monkeys = [:]

    input.readLines().each { line ->
        if (line ==~ /^Monkey \d*:/) {
            if (monkey) monkeys.put(monkey.id, monkey)
            def number = (line =~ /\d*/).grep().first().toInteger()
            monkey = new Monkey(number)
        }

        if (line ==~ /^  Starting items:.*/) {
            def numbers = (line =~ /\d*/).grep()
            monkey.items = numbers.collect{ Long.valueOf(it) }
        }

        if (line ==~ /^  Operation: new = .*/) {
            def operation = (line =~ /(?:  Operation: new = )(.*)/).grep().last().last()
            monkey.operation = operation
        }

        if (line ==~ /^  Test: divisible by \d*/) {
            def number = Long.valueOf((line =~ /\d*/).grep().first())
            monkey.division = number
        }

        if (line ==~ /^    If true: throw to monkey \d*/) {
            def number = (line =~ /\d*/).grep().first().toInteger()
            monkey.throwTrue = number
        }

        if (line ==~ /^    If false: throw to monkey \d*/) {
            def number = (line =~ /\d*/).grep().first().toInteger()
            monkey.throwFalse = number
        }
    }
    if (monkey) monkeys.put(monkey.id, monkey)
    return monkeys
}

def calculateMonkeyBusiness(monkeys) {
    20.times{
        monkeys.each{ id, monkey -> monkey.doStuff(monkeys) }
    }

    return monkeys.values().collect{ it.inspections }.sort().reverse().take(2).inject(1) { n1, n2 -> n1 * n2 }
}

def calculateWorrySomeMonkeyBusiness(monkeys) {

    println(monkeys.values().collect{ it.division })

    long divisor = monkeys.values().collect{ it.division }.inject(1) { n1, n2 -> n1 * n2 }

    println(divisor)

    10000.times{
        if (it % 100 == 0) println(it)
        monkeys.each{ id, monkey -> monkey.doWorrySomeStuff(monkeys, divisor) }
    }

    monkeys.values().each{
        println("Monkey ${it.id} inspected items ${it.inspections} times")
    }

    return monkeys.values().collect{ it.inspections }.sort().reverse().take(2).inject(1) { n1, n2 -> n1 * n2 }
}

static void main(String[] args) {

    // Part 1
    //assert calculateMonkeyBusiness(buildMonkeys(Input.readFromFile("testinput.txt"))) == 10605
    //assert calculateMonkeyBusiness(buildMonkeys(Input.readFromAOC(11))) == 102399

    // Part 2
    //println(calculateWorrySomeMonkeyBusiness(buildMonkeys(Input.readFromFile("testinput.txt"))))
    println(calculateWorrySomeMonkeyBusiness(buildMonkeys(Input.readFromAOC(11))))


}