dataFile = open("data.txt", "r")

elves = [];

myline = dataFile.readline().replace('\n','<END>')

elf = []

while myline:
    myline = dataFile.readline().replace('\n','<END>')
    
    if len(myline) == 5:
        elves.append(elf)
        elf = []
    elif len(myline) > 0:
        elf.append(int(myline.replace('<END>','')))

dataFile.close() 

maxCals = 0
elvesCals = 0

topThreeMaxCals = []
for e in elves:
    elvesCals = 0
    for cal in e:
        elvesCals += cal

    if len(topThreeMaxCals) < 3:
        topThreeMaxCals.append(elvesCals)
    elif min(topThreeMaxCals) < elvesCals:
        topThreeMaxCals.remove(min(topThreeMaxCals))
        topThreeMaxCals.append(elvesCals)

print("ANSWER")
print(sum(topThreeMaxCals))
