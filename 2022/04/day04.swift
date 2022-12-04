import Foundation

func elfGroup(pair: String) -> [Elf] {
    var elfGroup: [Elf] = []
    
    let elfPair = pair.split(separator: ",")
    for elf in elfPair {
        let elfIndexes = elf.split(separator: "-")
                
        let firstSection = Int(elfIndexes[0])!
        let endSection = Int(elfIndexes[1])!
        
        let e = Elf(firstSection: firstSection, endSection: endSection)
        elfGroup.append(e)
    }
    
    return elfGroup
}

struct Elf {
    var assignedSections : Set<Int>
    
    init(firstSection: Int, endSection: Int) {
        self.assignedSections = []
        for index in firstSection...endSection {
            self.assignedSections.insert(index)
        }
    }
}

var totalFullyAssignedSectionPairs = 0
var totalOverlappedAssignedSectionPairs = 0

let contentFromFile = try NSString(contentsOfFile: "input.txt", encoding: String.Encoding.utf8.rawValue)
contentFromFile.enumerateLines { (line, _) in
    
    let elfGroup = elfGroup(pair: line)
    
    // Check if any section assignment is subset of other section assignment
    if (elfGroup[0].assignedSections.isSubset(of:elfGroup[1].assignedSections) ||
        elfGroup[1].assignedSections.isSubset(of:elfGroup[0].assignedSections))
    {
        totalFullyAssignedSectionPairs += 1
    }
    
    // Check if any section assignment overlaps with other section assignment
    if (elfGroup[0].assignedSections.isDisjoint(with:elfGroup[1].assignedSections))
    {
        totalOverlappedAssignedSectionPairs += 1
    }
}
print("totalFullyAssignedSectionPairs: ", totalFullyAssignedSectionPairs)
print("totalOverlappedAssignedSectionPairs: ", totalOverlappedAssignedSectionPairs)
