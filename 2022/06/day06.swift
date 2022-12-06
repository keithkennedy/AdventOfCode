import Foundation

func getSignals(fileContents: String) -> [String] {
    var signals:[String] = []
    fileContents.enumerateLines { (line, _) in
        signals.append(line)
    }
    return signals
}

func getFirstMarker(stream: String, markerCharacters: Int) -> Int {
    let streamCharacters = Array(stream) // using as we know we're only dealing with ASCII...

    for (index, _) in stream.enumerated() {
        
        if (index > markerCharacters-1) {
            var charsToCheck: Set<Character> = []

            for itrn in index-markerCharacters...index-1
            {
                charsToCheck.insert(streamCharacters[itrn])
            }
            
            // they're all unique as they're in a set
            if (charsToCheck.count == markerCharacters) { 
                return index
            }
        }
    }
    return -1;
}

let contentFromFile = try NSString(contentsOfFile: "input.txt", encoding: String.Encoding.utf8.rawValue)
let signals = getSignals(fileContents: (contentFromFile as String))
var output = ""
for signal in signals {
    output += String(getFirstMarker(stream: signal, markerCharacters: 14)) + " "
}
print(output)