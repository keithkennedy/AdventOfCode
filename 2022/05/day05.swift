import Foundation

func containerStacks(input: String) -> [[Character]]
{
    let stackChars: Set<Character> = ["[", "]", " "]
    var stacks: [[Character]] = [[],[],[]]
    input.enumerateLines { (line, _) in
        /*
         loop each line with [
         then loop each char
            when we hit an alpha char - add to array based on index of char in string / 3?
         */
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
        /*
         loop each line with [
         then loop each char
            when we hit an alpha char - add to array based on index of char in string / 3?
         */
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

let contentFromFile = try NSString(contentsOfFile: "input.txt", encoding: String.Encoding.utf8.rawValue)
let stacks = containerStacks(input: contentFromFile as String)
let instructions = containerInstructions(input: contentFromFile as String)

print(stacks)
print(instructions)


