import Foundation

enum GameResult {
  case Win, Lose, Draw
}

enum GamePlay {
  case Rock, Paper, Scissors, Invalid
}

func GetPlayScore(play: GamePlay) -> Int {
    switch (play) {
        case GamePlay.Rock:
            return 1;
        case GamePlay.Paper:
            return 2;
        case GamePlay.Scissors:
            return 3;
        case GamePlay.Invalid:
            return 0;
    }
}

func GetResultScore(result: GameResult) -> Int {
    switch (result) {
        case GameResult.Win:
            return 6;
        case GameResult.Lose:
            return 0;
        case GameResult.Draw:
            return 3;
    }
}

func GetPlay(playCode: Character) -> GamePlay {
    switch (playCode) {
        case "A",
             "X":
            return GamePlay.Rock;
        case "B",
             "Y":
            return GamePlay.Paper;
        case "C",
             "Z":
            return GamePlay.Scissors;
        default:
            return GamePlay.Invalid;
    }
}

func GetPlayerResult(play: GamePlay, opponentPlay: GamePlay) -> GameResult
{
    if (play == opponentPlay) {
        return GameResult.Draw;
    }

    if (play == GamePlay.Rock &&
        opponentPlay == GamePlay.Scissors)
    {
        return GameResult.Win
    }
    
    if (play == GamePlay.Paper &&
        opponentPlay == GamePlay.Rock)
    {
        return GameResult.Win
    }

    if (play == GamePlay.Scissors &&
        opponentPlay == GamePlay.Paper)
    {
        return GameResult.Win
    }

    return GameResult.Lose;
}

var totalScore = 0

let contentFromFile = try NSString(contentsOfFile: "data.txt", encoding: String.Encoding.utf8.rawValue)
contentFromFile.enumerateLines { (line, _) in
    let opponentPlay = GetPlay(playCode: line.first!)
    let play = GetPlay(playCode: line.last!)

    let result = GetPlayerResult(play: play, opponentPlay: opponentPlay)
    let yourScore = GetPlayScore(play: play) + GetResultScore(result: result)

    totalScore += yourScore

    print(play , " (us) VS (op) ", opponentPlay , " :: We ", result, " SCORE: ", yourScore)
}

print("Final score: ", totalScore)