import Foundation

class Directory {
    let name: String
    var files: [File]
    var parent: Directory?
    var children: [Directory]

    init(name: String) {
        self.name = name
        self.parent = nil
        self.files = []
        self.children = []
    }

    init(name: String, parent: Directory) {
        self.name = name
        self.parent = parent
        self.files = []
        self.children = []
    }
}

struct File {
    let name: String
    let size: Int
    init(name: String, size: Int) {
        self.name = name
        self.size = size
    }
}

var rootDirectory: Directory = Directory(name: "/")
var currentDirectory: Directory = rootDirectory

func run(command: String) {
    print(command)
    let splitCommand = command
        .replacingOccurrences(of: "$ ", with: "")
        .split(separator: " ")

    // TODO: why do I need String() here?
    let dirName = String(splitCommand.last!)
    if splitCommand.first == "cd" {
        change(directory: dirName)
    }
}

//var currentDirectoryStack: [String] = []
func change(directory: String) {
    if directory == ".." {
        if let parent = currentDirectory.parent {
            currentDirectory = parent
        }
        // pop directory array
        //currentDirectoryStack.popLast()
    } else if directory == "/" {
        // ignore (we init with root)
        //currentDirectoryStack.append(directory)
    } else {
        print("new dir: ", directory)
        print(currentDirectory.name)
        
        var newDir = Directory(name: directory, parent: currentDirectory)
        // TODO: check if currentDirectory has a child with the name already!
        currentDirectory.children.append(newDir)
        currentDirectory = newDir
    }
    print("pwd: ", currentDirectory.name)
}

func record(listing: String) {
    print("Listing: ", listing)
    let listDetails = listing.split(separator: " ")

    if (listDetails.first != "dir") {

        // let currentDirectoryFlat = currentDirectoryStack.joined()
        // let fileSize =  Int(listDetails.first!)!

        // if let size = directorySizes[currentDirectoryFlat] {
        //     directorySizes[currentDirectoryFlat] = size + fileSize
        // } else {
        //     directorySizes[currentDirectoryFlat] = fileSize
        // }
    }
}

let terminalOutput = 
    try NSString(contentsOfFile: "input_demo.txt", 
                       encoding: String.Encoding.utf8.rawValue)
terminalOutput.enumerateLines { (output, _) in
    if (output.first == "$") {
        run(command: output)
    } else {
        record(listing: output)
    }
}

let x = 123