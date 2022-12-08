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

        treeObjects.append(Tree(size: treeGrid[x][y], x: x, y: y, isEdge: isEdge))
    }
}

func nScore(tree: Tree) -> Int {
    var nTrees = 0
    var n = tree.x - 1
    // traverse "up"
    while (n >= 0) {
        // get tree at index
        let nTree = treeObjects.first(where: {
            $0.y == tree.y && 
            $0.x == n })!
        nTrees += 1
        if nTree.size >= tree.size {
            // end loop - blocked
            n = -1
        } else {
            n -= 1
        }
    }
    return nTrees
}

func sScore(tree: Tree) -> Int {
    var sTrees = 0
    var s = tree.x + 1
    // traverse "up"
    while (s != -1 && s <= treeObjects.count) {
        // get tree at index
        if let sTree = treeObjects.first(where: {
            $0.y == tree.y && 
            $0.x == s })
        {
            sTrees += 1
            if sTree.size >= tree.size {
                // end loop - blocked
                s = -1
            } else {
                s += 1
            }
        } else {
            s = -1
        }
    }
    return sTrees
}

func eScore(tree: Tree) -> Int {
    var eTrees = 0
    var e = tree.y + 1
    // traverse "up"
    while (e != -1 && e <= treeObjects.count) {
        // get tree at index
        if let eTree = treeObjects.first(where: {
            $0.y == e && 
            $0.x == tree.x })
        {
            eTrees += 1
            if eTree.size >= tree.size {
                // end loop - blocked
                e = -1
            } else {
                e += 1
            }
        } else {
            e = -1
        }
    }
    return eTrees
}

func wScore(tree: Tree) -> Int {
    var wTrees = 0
    var w = tree.y - 1
    // traverse "west"
    while (w >= 0) {
        // get tree at index
        if let wTree = treeObjects.first(where: {
            $0.y == w && 
            $0.x == tree.x })
        {
            wTrees += 1
            if wTree.size >= tree.size {
                // end loop - blocked
                w = -1
            } else {
                w -= 1
            }
        } else {
            w = -1
        }
    }
    return wTrees
}

var largestViewScore = 0
for var t in treeObjects {

    let viewScore = 
        nScore(tree: t) *
        sScore(tree: t) *
        wScore(tree: t) *
        eScore(tree: t)

    if viewScore > largestViewScore {
        largestViewScore = viewScore
    }
}

print("Best view score: ", largestViewScore)