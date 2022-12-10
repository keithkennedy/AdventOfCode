import Foundation

var register:Int = 1
var cycles:Int = 0
let checkpoints:[Int] = [20, 60, 100, 140, 180, 220]
var checkpointTotal = 0

let terminalOutput = try NSString(contentsOfFile: "input.txt", 
                                        encoding: String.Encoding.utf8.rawValue)
terminalOutput.enumerateLines { (output, _) in
    let commandSplit = output.split(separator: " ")
    let command = String(commandSplit[0])
    if command == "noop" {
        noop()
    } else if command == "addx" {
        let x = Int(commandSplit[1])!
        addX(x: x)
    }
}
print(checkpointTotal)

func addCycle() {
    cycles += 1

    let c = cycles
    let signalStrength = c * register
    if checkpoints.contains(c) {
        print("Register value: ", register , " (c) ", c)
        print("Signal str: ", signalStrength)
        checkpointTotal += signalStrength
    }
}

func noop() {
    addCycle()
}

func addX(x:Int) {
    addCycle()
    addCycle()
    register += x
}
