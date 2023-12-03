import re

def surroundingChars(lines, lineNumber, startIndex, endIndex):
    # get chars before and after indexes
    line = lines[lineNumber]
    chars = surroundCharsOnSameLine(line, startIndex, endIndex)

    if lineNumber > 0:
        aboveChars = [*surroundCharsOnAdjacentLine(lines[lineNumber-1], startIndex, endIndex)]        
        chars = chars + aboveChars
    if lineNumber < len(lines) - 1:
        belowChars = [*surroundCharsOnAdjacentLine(lines[lineNumber+1], startIndex, endIndex)]
        chars = chars + belowChars

    return chars

def surroundCharsOnAdjacentLine(adjacentLine, startIndex, endIndex):
    if startIndex > 0:
        startIndex = startIndex - 1;
    if endIndex < len(adjacentLine) - 1:
        endIndex = endIndex + 1;
    chars = adjacentLine[startIndex:endIndex]
    return chars

def surroundCharsOnSameLine(line, startIndex, endIndex):
    # gets chars before/after the number on the same line
    chars = []
    if startIndex > 0:
        chars.append(line[startIndex - 1])
    if endIndex < len(line):
        chars.append(line[endIndex])
    return chars

def containsSymbolOrPeriod(chars):
    for c in chars:
        if c != "." and not c.isalpha() and not c.isdigit():
            return True
    return False

def numbersAroundGear(gear, numbers):
    gearAdjacentNumbers = []
    for n in numbers:
        if isAdjacent(gear, n):
            gearAdjacentNumbers.append(int(n[1]))
    return gearAdjacentNumbers

def isAdjacent(gear, number):
    gearLine = gear[0]
    gearIndex = gear[1]

    lineNumber = number[0]
    startIndex = number[2]
    endIndex = number[3]

    if gearLine == lineNumber-1 or gearLine == lineNumber or gearLine == lineNumber + 1:
        r = range(startIndex-1, endIndex + 1)
        if gearIndex in r:
            return True
    
    return False

def main(filename):
    with open(filename, encoding="utf-8") as str_file:
        lines = str_file.read().splitlines()
        
        numberPattern = re.compile(r'\d+')
        numbers = []

        gearPattern = re.compile(r'\*')
        gears = []

        for index, l in enumerate(lines):
            numberMatches = numberPattern.finditer(l)
            for m in numberMatches:
                start_index, end_index = m.start(), m.end()
                number = m.group()
                numbers.append((index, number, start_index, end_index))
            gearMatches = gearPattern.finditer(l)
            for g in gearMatches:
                start_index = g.start()
                number = g.group()
                gears.append((index, start_index))

        validPartNumbers = []
        for r in numbers:
            number = r[1]
            lineNumber = r[0]
            startIndex = r[2]
            endIndex = r[3]
        
            chars = surroundingChars(lines, lineNumber, startIndex, endIndex)
            hasSymbolOrPeriod = containsSymbolOrPeriod(chars)

            if hasSymbolOrPeriod:
                validPartNumbers.append(int(number))

        # for each gear, we need to see if any numbers exist around it...
        gearRatios = []
        for g in gears:
            n = numbersAroundGear(g, numbers)
            if len(n) == 2:
                gearRatios.append(n[0] * n[1])

        print(sum(validPartNumbers))
        print(sum(gearRatios))

main("03/input.txt")