import Foundation

let priorityOrder = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
func priority(item: Character) -> Int
{
    if let i = priorityOrder.firstIndex(of: item) {
        let index: Int = priorityOrder.distance(from: priorityOrder.startIndex, to: i)
        return index + 1
    }
    return -1
}

struct Backpack {
    var items: String
    var firstCompartment: String
    var secondCompartment: String

    init(items: String) {
        self.items = items;

        // split items into compartments
        firstCompartment = String(items.prefix(items.count/2))
        secondCompartment = String(items.suffix(items.count/2))
    }

    func priorityOfSharedComponentItems() -> Int {
        for char in firstCompartment {
            if (secondCompartment.contains(char)) {
                return priority(item: char)
            }
        }  
        return -1;
    }
}

// Create groups of backpacks for elf groups
var groups: [[Backpack]] = []
var group: [Backpack] = []

var totalScore = 0

let contentFromFile = try NSString(contentsOfFile: "data.txt", encoding: String.Encoding.utf8.rawValue)
contentFromFile.enumerateLines { (line, _) in
    let backpack = Backpack(items: line)
    group.append(backpack)

    let score = backpack.priorityOfSharedComponentItems()
    if score > 0 {
        totalScore += score
    }

    // Set new group every 3 backpacks
    if group.count == 3 {
        groups.append(group)
        group = []
    }
}

var badgeGroupScore = 0
for elfGroup in groups {
    for char in elfGroup[0].items {
        if (elfGroup[1].items.contains(char) &&
            elfGroup[2].items.contains(char)) 
        {
            badgeGroupScore += priority(item: char)
            break
        }
    }
}

print("Total: ", totalScore)
print("Total badge score: ", badgeGroupScore)
