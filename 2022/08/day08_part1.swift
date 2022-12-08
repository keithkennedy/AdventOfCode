import Foundation

struct Tree {
    var size: Int
    var x: Int
    var y: Int
    var isEdge: Bool

    init(size: Int, x: Int, y: Int, isEdge: Bool) {
        self.size = size
        self.x = x
        self.y = y
        self.isEdge = isEdge
    }
}

var treeObjectGrid: [[Tree]] = []
var treeGrid: [[Int]] = []

var treeObjects: [Tree] = []

// Build int grid
let contentFromFile = try NSString(contentsOfFile: "input.txt", encoding: String.Encoding.utf8.rawValue)
contentFromFile.enumerateLines { (line, _) in
    var trees:[Int] = []
    for var e in line {
        trees.append(Int(String(e))!)
    }
    treeGrid.append(trees)
}

// Convert into tree grid
for x in 0...treeGrid.count-1
{
    var trees:[Tree] = []
    for y in 0...treeGrid[x].count-1 {
        let isEdge = x == 0 ||
            x == treeGrid.count-1 ||
            y == 0 ||
            y == treeGrid[x].count-1;

        trees.append(Tree(size: treeGrid[x][y], x: x, y: y, isEdge: isEdge))

        treeObjects.append(Tree(size: treeGrid[x][y], x: x, y: y, isEdge: isEdge))
    }
    treeObjectGrid.append(trees)
}
print(treeObjects)

// Very slow!
var totalVisibleTrees = 0
for var t in treeObjects {
    print("Checking: ", t)
    if t.isEdge {
        totalVisibleTrees += 1
        continue
    }

    // get all north trees
    var nTrees = treeObjects.filter { 
         $0.y < t.y && 
         $0.x == t.x &&
         $0.size >= t.size}
    let blockN = nTrees.count > 0

    var sTrees = treeObjects.filter { 
         $0.y > t.y && 
         $0.x == t.x &&
         $0.size >= t.size}
    let blockS = sTrees.count > 0

    var wTrees = treeObjects.filter { 
         $0.y == t.y && 
         $0.x < t.x &&
         $0.size >= t.size}
    let blockW = wTrees.count > 0

    var eTrees = treeObjects.filter { 
         $0.y == t.y && 
         $0.x > t.x &&
         $0.size >= t.size}
    let blockE = eTrees.count > 0

    if (blockN && blockS && blockE && blockW) {
        print("Blocked")
        continue
    }

    totalVisibleTrees += 1
}
print("TOTAL: ", totalVisibleTrees)
