import Foundation

enum CrateMover {
    case CrateMover9000,CrateMover9001
}

func containerStacks(input: String) -> [[Character]]
{
    let stackChars: Set<Character> = ["[", "]", " "]
    // hard-coded init...
    var stacks: [[Character]] = [[],[],[],[],[],[],[],[],[]]
    input.enumerateLines { (line, _) in
       
        if line.contains("[") {
            // Containers section
            var replaced = line.replacingOccurrences(of: "    ", with: "-")
            replaced.removeAll(where: { stackChars.contains($0) })
            
            for (index, element) in replaced.enumerated() {
                if (element != "-") {
                    stacks[index].append(element)
                }
            }
        }
    }
    return stacks;
}

struct ContainerInstruction {
    var move: Int
    var from: Int
    var to: Int
    
    init(move: Int, from: Int, to: Int) {
        self.move = move
        self.from = from
        self.to = to
    }
}

func containerInstructions(input: String) -> [ContainerInstruction]
{
    var instructions: [ContainerInstruction] = []
    input.enumerateLines { (line, _) in
        if line.contains("move") {
            let replaced = line.replacingOccurrences(of: "move ", with: "")
                .replacingOccurrences(of: " from ", with: ",")
                .replacingOccurrences(of: " to ", with: ",")
            // Instructions
            let i = replaced.split(separator: ",")
            let move = Int(i[0])!
            let from = Int(i[1])!
            let to = Int(i[2])!
            instructions.append(ContainerInstruction(move: move, from: from, to: to))
        }
    }
    return instructions;
}

func moveContainers(containers: [[Character]],
                    instructions: [ContainerInstruction],
                    crateMover: CrateMover) -> [[Character]]
{
    var rearrangedContainers = containers
    for instruction in instructions {
        print(instruction)
        var moveSlice = rearrangedContainers[instruction.from-1][..<instruction.move]
        
        if (crateMover == CrateMover.CrateMover9001) {
            moveSlice.reverse()
        }
        
        rearrangedContainers[instruction.from-1].removeFirst(instruction.move)
        rearrangedContainers[instruction.to-1].insert(contentsOf: moveSlice, at: 0)
        
        print(rearrangedContainers)
    }
    return rearrangedContainers
}

let contentFromFile = try NSString(contentsOfFile: "input.txt", encoding: String.Encoding.utf8.rawValue)
let stacks = containerStacks(input: contentFromFile as String)
let instructions = containerInstructions(input: contentFromFile as String)

let crateMover = CrateMover.CrateMover9000
let rearrangedContainers = moveContainers(containers: stacks, instructions: instructions, crateMover: crateMover)

print("Top containers: ")
for rearrangedContainer in rearrangedContainers {
    if (rearrangedContainer.count == 0) {
        print("None")
    } else {
        print(rearrangedContainer.first!)
    }
}
