import Foundation

var currentDirectoryStack: [String] = []
var directorySizes: Dictionary<String, Int> = [:]

func run(command: String) {
    let splitCommand = command
        .replacingOccurrences(of: "$ ", with: "")
        .split(separator: " ")

    if splitCommand.first == "cd" {
        change(directory: String(splitCommand.last!)) // TODO: why do I need String() here?
    }
}

func change(directory: String) {
    if directory == ".." {
        // pop directory array
        currentDirectoryStack.popLast()
    } else if directory == "/" {
        currentDirectoryStack.append(directory)
    } else {
        currentDirectoryStack.append(directory + "/")
    }
    print("pwd: ", currentDirectoryStack)
    print(currentDirectoryStack.joined())
}

func record(listing: String) {
    print("Listing: ", listing)
    let listDetails = listing.split(separator: " ")

    if (listDetails.first != "dir") {

        let currentDirectoryFlat = currentDirectoryStack.joined()
        let fileSize =  Int(listDetails.first!)!

        if let size = directorySizes[currentDirectoryFlat] {
            directorySizes[currentDirectoryFlat] = size + fileSize
        } else {
            directorySizes[currentDirectoryFlat] = fileSize
        }
    }
}

func addDirectorySizes(listing: String) {
    let listDetails = listing.split(separator: " ")

    if (listDetails.first == "dir") {

        let additionalDirectoryFlat = currentDirectoryStack.joined() + listDetails.last!
        if let dirSize = directorySizes[additionalDirectoryFlat] {

            // need to do this for each parent dir (could reverse pop the DIR stack)
            /*
            copy curDirStack
            while count != 0:
                add to dirSizes[curDirStack.joined()]
                curDirStack.popLast()
            */

            var parentDirectoryStack = currentDirectoryStack
            while (parentDirectoryStack.count > 0) {
                if let parentDirectorySize = directorySizes[parentDirectoryStack.joined()] {
                    directorySizes[parentDirectoryStack.joined()] = parentDirectorySize + dirSize
                }
                parentDirectoryStack.popLast()
            }
        }
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

// Second enumeration to retrospectively add dir sizes to parent folders
currentDirectoryStack = []
terminalOutput.enumerateLines { (output, _) in
    if (output.first == "$") {
        run(command: output)
    } else {
        addDirectorySizes(listing: output)
    }
}

var directorySizeThreshold = 100000
var totalSizeOfSmallDirs = 0
for (path, size) in directorySizes {
    if size <= directorySizeThreshold {
        print("Adding: ", path , " ", size)
        totalSizeOfSmallDirs += size

        /*
        could loop again - check if any other dirs start with "path" 
        then add that to total
        */
        for (subPath, subSize) in directorySizes {
            /*
            if this doing all sub paths?! 
            */
            if (subPath != path &&
                subPath.starts(with:path)
                //subSize <= directorySizeThreshold
                ) 
                {
                    print("Adding (s): ", subPath , " ", subSize)
                    totalSizeOfSmallDirs += subSize
                }
        }
    }
}

print(directorySizes)
print(totalSizeOfSmallDirs)

// 12867536 (too hight)