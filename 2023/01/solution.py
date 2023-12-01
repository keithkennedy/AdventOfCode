def solution(filename):
    with open(filename, encoding="utf-8") as f:
        calibrationValues = []
        for line in f:
            convertedLine = convertLineTextNumbersToInt(line)
            linenumbers = []
            for char in convertedLine:
                if char.isdigit():
                    linenumbers.append(char)
            calibrationValues.append(getLineNumber(linenumbers))
        
        print("OUTPUT")
        print(sum(calibrationValues))

    f.closed

def convertLineTextNumbersToInt(lineText):
    lineText = lineText.replace("one", "o1ne")
    lineText = lineText.replace("two", "t2wo")
    lineText = lineText.replace("three", "t3hree")
    lineText = lineText.replace("four", "four4")
    lineText = lineText.replace("five", "fi5ve")
    lineText = lineText.replace("six", "six6")
    lineText = lineText.replace("seven", "seve7n")
    lineText = lineText.replace("eight", "eigh8t")
    lineText = lineText.replace("nine", "nin9e")
    lineText = lineText.replace("zero", "zer0o")
    return lineText;

def getLineNumber(numbers):
    if len(numbers) == 0:
        return -1
    if len(numbers) == 1:
        return int(numbers[0] + numbers[0])
    return int(str(numbers[0]) + str(numbers[len(numbers)-1]))

solution("input.txt");
