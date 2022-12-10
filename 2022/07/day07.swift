import Foundation

var totalSizeOfSmallDirectories = 0
var closestDirSize = Int.max

class Directory {
    var name: String
    var parent: Directory?
    var children: [Directory]
    var files: [File]

    init(name: String) {
        self.name = name
        self.parent = nil
        self.children = []
        self.files = []
    }

    func size() -> Int {
        var size = 0
        for var f in files {
            size += f.size
        }

        for var c in children {
            size += c.size()
        }

        return size
    }

    func addSubdirectory(directory: Directory) {
        directory.parent = self
        self.children.append(directory)
    }

    func listChildren(sep: String) {
        let size = size()
        if size <= 100000 {
            totalSizeOfSmallDirectories += size
        }

        if (size >= requiredFilespace &&
            size < closestDirSize) {
            closestDirSize = size
        }

        print(sep, self.name, "(",size,")")

        listFiles(sep: sep + " ")

        if children.count == 0 {
            return
        }

        for var c in children {
            c.listChildren(sep: sep + " ")
        }
    }

    func listFiles(sep: String) {
        for var f in files {
            print(sep, f.name , "(file ", f.size, ")")
        }
    }
}

class File {
    var name: String
    var size: Int
    init(name: String, size: Int) {
        self.name = name
        self.size = size
    }
}

let rootDirectory = Directory(name: "/")
var currentDirectory = rootDirectory

// Input
let terminalOutput = try NSString(contentsOfFile: "input.txt", 
                                        encoding: String.Encoding.utf8.rawValue)
terminalOutput.enumerateLines { (output, _) in
    if (output.first == "$") {
        run(command: output)
    } else if (output.starts(with: "dir")) {
        let outputSplit = output.split(separator: " ")
        recordDir(directoryName: String(outputSplit.last!))
    } else {
        let outputSplit = output.split(separator: " ")
        let size = Int(outputSplit[0])!
        let name = String(outputSplit[1])

        recordFile(name: name, size: size)
    }
}

func run(command: String) {
    let commandSplit = command.split(separator: " ")
    let operation = String(commandSplit[1])
    if operation == "cd" {
        change(directoryName: String(commandSplit[2]))
    }
}

func change(directoryName: String) {
    if (directoryName == "..") {
        // Go up
        if let parent = currentDirectory.parent {
            currentDirectory = parent
        }
    } else {
        // ignore root
        if (directoryName != "/") {
            if let dir = currentDirectory.children.first(where: { $0.name == directoryName}) {
                print("cd (", dir.name ,")")
                currentDirectory = dir
            }
        }
    }
}

func recordDir(directoryName: String) {
    print("Add dir: ", directoryName)
    currentDirectory.addSubdirectory(directory: Directory(name: directoryName))
}

func recordFile(name: String, size: Int) {
    print("Adding file to: ", currentDirectory.name, " fileName ", name, " (", currentDirectory.files.count, ")")
    currentDirectory.files.append(File(name: name, size: size))
}

// Solution
let totalFilespace = 70000000
let filespaceRequired = 30000000
let rootSize = rootDirectory.size()
let remainingFilespace = totalFilespace - rootSize
let requiredFilespace = filespaceRequired - remainingFilespace

rootDirectory.listChildren(sep: "")

print("P1: ", totalSizeOfSmallDirectories)
print("P2: ", closestDirSize)