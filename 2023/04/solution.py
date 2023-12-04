class Card:
    number: int
    winningNumbers: [int]
    numbers: [int]
    totalCopies: int

    def __init__(self, number, winningNumbers, numbers):
        self.number = number
        self.winningNumbers = winningNumbers
        self.numbers = numbers
        self.totalCopies = 1

    def matchingWinningNumbers(self):
        return list(set(self.winningNumbers) & set(self.numbers))
    
    def getCopyIds(self):
        copyNumbers = []
        for i in range(len(self.matchingWinningNumbers())):
            copyNumbers.append(self.number + i + 1)
        return copyNumbers

    def points(self):
        totalMatchingWinningNumbers = len(self.matchingWinningNumbers())
        if totalMatchingWinningNumbers == 0:
            return 0;
        return int(2 ** (totalMatchingWinningNumbers - 1))

def updateCopyCounts(card, cards, cardIds):
    for i in cardIds:
        c = cards[i - 1]
        c.totalCopies += card.totalCopies

def main(filename):
    cards = []
    cardPoints = []

    with open(filename, encoding="utf-8") as str_file:
        lines = str_file.read().splitlines()
        # process cards first
        for l in lines:
            l = l.replace("  ", " ") # cleans up spacing
            name, numbers = l.split(":")
            cardNumber = int(name.replace(" ", "").replace("Card", ""))
            w, nums = numbers.split("|")
            winningNumbers = list(map(int,w.strip().split(" ")))
            numbers = list(map(int,nums.strip().split(" ")))
            
            card = Card(cardNumber, winningNumbers, numbers)

            cards.append(card)
            cardPoints.append(card.points())

    # now we have all cards, we can set the copy values for part 2
    for c in cards:
        updateCopyCounts(c, cards, c.getCopyIds())
            
    print(sum(cardPoints))
    print(sum(map(lambda obj: obj.totalCopies, cards)))

main("04/input.txt")