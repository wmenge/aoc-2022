package day6

import util.Input

def markerPosition(input, length) {
    ((0..input.length()-length).collect{
        part = input.substring(it, it + length)
        if (part.split("").toUnique().size() == length) {
            return it + length
        }
    } - null).first()
}


static void main(String[] args) {

    // Part 1
    assert markerPosition("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 4) == 7
    assert markerPosition("bvwbjplbgvbhsrlpgdmjqwftvncz", 4) == 5
    assert markerPosition("nppdvjthqldpwncqszvftbrmjlhg", 4) == 6
    assert markerPosition("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 4) == 10
    assert markerPosition("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 4) == 11

    assert markerPosition(Input.readFromAOC(6), 4) == 1658

    // Part 2
    assert markerPosition("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 14) == 19
    assert markerPosition("bvwbjplbgvbhsrlpgdmjqwftvncz", 14) == 23
    assert markerPosition("nppdvjthqldpwncqszvftbrmjlhg", 14) == 23
    assert markerPosition("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 14) == 29
    assert markerPosition("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 14) == 26

    assert markerPosition(Input.readFromAOC(6), 14) == 2260
}
