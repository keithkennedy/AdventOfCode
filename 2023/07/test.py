import pytest
from CamelPokerHand import CamelPokerHand
from CamelPokerHandType import CamelPokerHandType


@pytest.mark.parametrize("hand,expected", [
    ("77777", CamelPokerHandType.FiveOfAKind),
    ("77677", CamelPokerHandType.FourOfAKind),
    ("2TT2T", CamelPokerHandType.FullHouse),
    ("3K2KK", CamelPokerHandType.ThreeOfAKind),
    ("32231", CamelPokerHandType.TwoPair),
    ("3KQ4Q", CamelPokerHandType.OnePair),
    ("5497Q", CamelPokerHandType.HighCard)
])
def test_hand(hand: str, expected: CamelPokerHandType):
    _c = CamelPokerHand(hand, 1)
    assert _c.hand_type == expected


@pytest.mark.parametrize("hand,expected", [
    ("33J33", CamelPokerHandType.FiveOfAKind),
    ("33J3J", CamelPokerHandType.FiveOfAKind),
    ("33J33", CamelPokerHandType.FiveOfAKind),
    ("33JQ3", CamelPokerHandType.FourOfAKind),
    ("32J31", CamelPokerHandType.ThreeOfAKind),
    ("1234J", CamelPokerHandType.OnePair)
])
def test_wildcard_hand(hand: str, expected: CamelPokerHandType):
    _c = CamelPokerHand(hand, 1)
    assert _c.hand_type == expected

