from enum import Enum

class Cube(Enum):
    RED = 1
    BLUE = 2
    GREEN = 3

class Game:
    id = 0;
    maxRed = 0
    maxBlue = 0
    maxGreen = 0

    def record(self, cubeType, value):
        if cubeType == Cube.RED:
            if self.maxRed < value:
                self.maxRed = value
        if cubeType == Cube.BLUE:
            if self.maxBlue < value:
                self.maxBlue = value
        if cubeType == Cube.GREEN:
            if self.maxGreen < value:
                self.maxGreen = value

def isPossible(game, redCubes, greenCubes, blueCubes):
    if game.maxRed <= redCubes and game.maxGreen <= greenCubes and game.maxBlue <= blueCubes:
        return game.id
    return 0

def power(game):
    return game.maxRed * game.maxGreen * game.maxBlue;

def solution(filename):
    games = []
    with open(filename, encoding="utf-8") as str_file:
        rounds = str_file.read().splitlines()
        for round in rounds:

            game = Game()

            x = round.split(":")
            gameInfo = x[0]
            gameNumber = int(gameInfo.replace("Game", "").strip())
            game.id = gameNumber;

            cubeInfo = x[1]
            bundles = cubeInfo.split(";")
            for c in bundles:
                colorInfo = c.split(",")
                for i in colorInfo:
                    _c = i.strip().split(" ")
                    
                    match _c[1]:
                        case "red":
                            game.record(Cube.RED, int(_c[0]))
                        case "blue":
                            game.record(Cube.BLUE, int(_c[0]))
                        case "green":
                            game.record(Cube.GREEN, int(_c[0]))
            
            games.append(game)

        r = 12;
        g = 13;
        b = 14;
        validGameIds = [*map(lambda x: isPossible(x, r, g, b), games)]
        sumOfValidGameIds = sum(validGameIds)

        sumOfPowers = sum([*map(power, games)])

        print(sumOfValidGameIds)
        print(sumOfPowers)
        
solution("02/input.txt")