def processRaces(data):
    times = []
    distances = []

    for l in data:
        k, vals = l.strip().split(":")
        ints = list(map(int,(filter(None, vals.strip().split(" ")))))
        if k.startswith("Time"):
            times = ints
        if k.startswith("Distance"):
            distances = ints

    _races = []
    for i, l in enumerate(times):
        _races.append({"time": l, "distance": distances[i]})
    return _races

def getRacesForFile(filename):
    with open(filename, encoding="utf-8") as str_file:
        lines = str_file.read().splitlines()
        _races = processRaces(lines)
        return _races

def distanceForRace(holdTime: int, time: int):
    if holdTime > time:
        raise Exception("invalid times provided")
    speedMilPerSec = holdTime
    timeRemaining = time - holdTime
    distance = timeRemaining * speedMilPerSec
    return distance

def possibleTimesForRace(time: int):
    times = []
    _t = 0
    while (_t <= time):
        d = distanceForRace(_t, time)
        times.append({"time": _t, "distance": d})
        _t += 1
    return times

def totalRacesThatBeatDistance(races:[], distance: int):
    total = 0
    for r in races:
        if r["distance"] > distance:
            total += 1
    return total

races = getRacesForFile("06/input.txt")
waysToWinProduct = 1
for r in races:
    w = totalRacesThatBeatDistance(possibleTimesForRace(r["time"]),r["distance"])
    waysToWinProduct *= w
print(waysToWinProduct)


print(totalRacesThatBeatDistance(
    possibleTimesForRace(61709066),
    643118413621041
))