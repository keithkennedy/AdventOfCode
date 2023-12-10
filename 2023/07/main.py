from CamelPokerHand import CamelPokerHand

with open("input_files/input.txt", encoding="utf-8") as str_file:
    lines = str_file.read().splitlines()
    hands = []
    for l in lines:
        hand = l.split(" ")[0]
        bid = int(l.split(" ")[1])
        hands.append(CamelPokerHand(hand, bid))

    sorted_hands = sorted(hands)

    score = 0
    for i, h in enumerate(sorted_hands):
        score += h.bid * (i + 1)
    print(score)
