def sequence_differences(nums) -> []:
    diffs = []
    for i, n in enumerate(nums):
        if i == 0:
            continue
        diffs.append(n - nums[i - 1])
    return diffs


def forward_sequences(nums) -> []:
    _n = nums
    seqs = []
    while sum(_n) != 0:
        seqs.append(_n)
        s = sequence_differences(_n)
        _n = s
    return seqs


def prev_number_in_sequence(nums) -> int:
    _s = forward_sequences(nums)
    last_prev = 0
    for i, s in enumerate(reversed(_s)):
        last_prev = s[0] - last_prev
    return last_prev


def next_number_in_sequence(nums) -> int:
    _s = forward_sequences(nums)
    total = 0
    for seq in _s:
        total += seq[len(seq) - 1]
    return total


with open("input_files/input.txt", encoding="utf-8") as str_file:
    lines = str_file.read().splitlines()
    total_next = 0
    total_prev = 0
    for l in lines:
        nums = list(map(int, l.strip().split(" ")))
        total_next += next_number_in_sequence(nums)
        total_prev += prev_number_in_sequence(nums)
    print(total_next)
    print(total_prev)

