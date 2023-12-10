from CamelPokerHandType import CamelPokerHandType


class CamelPokerHand(object):
    hand: str
    hand_type: CamelPokerHandType
    bid: int

    def __init__(self, hand: str, bid: int) -> None:
        self.hand = hand
        self.hand_type = self.gethandtype(hand)
        wildcard = hand.count("J")

        for i in range(wildcard):
            self.hand_type = self.upgrade_hand_type_for_wildcard(self.hand_type)

        self.bid = bid

    @staticmethod
    def upgrade_hand_type_for_wildcard(hand_type: CamelPokerHandType):
        if hand_type == CamelPokerHandType.FiveOfAKind:
            return CamelPokerHandType.FiveOfAKind # no upgrade
        if hand_type == CamelPokerHandType.FourOfAKind:
            return CamelPokerHandType.FiveOfAKind
        if hand_type == CamelPokerHandType.FullHouse:
            return CamelPokerHandType.FourOfAKind
        if hand_type == CamelPokerHandType.ThreeOfAKind:
            return CamelPokerHandType.FourOfAKind
        if hand_type == CamelPokerHandType.TwoPair:
            return CamelPokerHandType.FullHouse
        if hand_type == CamelPokerHandType.OnePair:
            return CamelPokerHandType.ThreeOfAKind
        if hand_type == CamelPokerHandType.HighCard:
            return CamelPokerHandType.OnePair


    @staticmethod
    def value_for_card(card: str):
        if card == "A":
            return 14
        if card == "K":
            return 13
        if card == "Q":
            return 12
        if card == "J":
            return 1  # lowest in value compare (part 2)
        if card == "T":
            return 10
        # the rest of the values are ints
        return int(card)


    @staticmethod
    def compareValue(a: str, b: str) -> bool:
        return CamelPokerHand.value_for_card(a) < CamelPokerHand.value_for_card(b)


    def get_hand_type_with_wildcard(self, hand: str) -> CamelPokerHandType:
        jcount = hand.count("J")
        _handtype = self.gethandtype(hand.replace("J", ""))
        # if we have one pair and three wilds (then we have set)
        # if we have one pair and two wilds then 4 of a kind
        newCount = _handtype.value + jcount
        if newCount >= CamelPokerHandType.FullHouse.value and newCount < CamelPokerHandType.FiveOfAKind.value:
            newCount += 1
        return CamelPokerHandType(newCount)

    @staticmethod
    def gethandtype(hand: str) -> CamelPokerHandType:
        if len(hand) > 5 or len(hand) == 0:
            raise Exception("Invalid hand size")

        hasThree = ""
        hasTwo = ""

        for c in hand:
            if c == "J":
                continue

            count = hand.count(c)

            if count == 5:
                return CamelPokerHandType.FiveOfAKind
            if count == 4:
                return CamelPokerHandType.FourOfAKind
            if count == 3:
                hasThree = c
            if count == 2:
                if hasTwo != "" and hasTwo != c:
                    return CamelPokerHandType.TwoPair
                hasTwo = c
            if hasThree != "" and hasTwo != "":
                return CamelPokerHandType.FullHouse

        if hasThree != "" and hasTwo == "":
            return CamelPokerHandType.ThreeOfAKind

        if hasTwo != "":
            return CamelPokerHandType.OnePair

        return CamelPokerHandType.HighCard

    def __lt__(self, other):
        if self.hand_type == other.hand_type:
            # compare each char
            for i, s in enumerate(self.hand):
                if s != other.hand[i]:
                    if CamelPokerHand.compareValue(s, other.hand[i]):
                        return True
                    else:
                        return False
        return self.hand_type.value < other.hand_type.value