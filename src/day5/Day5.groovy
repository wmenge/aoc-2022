package day5

import util.Input

def constructStacks(input) {
    def stacks = input.readLines().findAll{ line -> line.contains("[")}.reverse().collect{ line ->
        def size = ((line.size() + 1) / 4) - 1
        def myList = (0..size).collect{ index ->
            line.substring((index.intValue() * 4) + 1, (index.intValue() * 4) + 2)
        }
        return myList;
    }
    return stacks.transpose().collect{ it.findAll{ it != " " }.reverse() }
}

class Command {
    int quantity
    int source
    int target

    Command(int quantity, int source, int target) {
        this.quantity = quantity
        this.source = source - 1
        this.target = target - 1
    }

    def apply(stacks) {
        quantity.times {
            stacks[target].push(stacks[source].pop())
        }
    }

    // model 9001 moves multiple crates in one go and preserves the order of those crates
    def apply9001(stacks) {
        stacks[target].addAll(0, stacks[source].take(quantity))
        stacks[source] = stacks[source].drop(quantity)
    }
}
def constructCommands(input) {
    input.readLines().findAll{ it.contains("move")}.collect{(it =~ /\d+/).findAll() }.collect{ new Command(it[0].toInteger(),it[1].toInteger(),it[2].toInteger()) }
}

def rearrange(String input) {
    def stacks = constructStacks(input)
    def commands = constructCommands(input)
    commands.each{ it.apply(stacks) }
    return stacks.collect{ it.pop() }.join()
}

def rearrange9001(String input) {
    def stacks = constructStacks(input)
    def commands = constructCommands(input)
    commands.each{ it.apply9001(stacks) }
    return stacks.collect{ it.pop() }.join()
}

static void main(String[] args) {

    // Part 1
    assert rearrange(Input.readFromFile("testinput.txt")) == "CMZ"
    assert rearrange(Input.readFromAOC(5)) == "QMBMJDFTD"

    // Part 2
    assert rearrange9001(Input.readFromFile("testinput.txt")) == "MCD"
    assert rearrange9001(Input.readFromAOC(5)) == "NBTVTJNFJ"
}
