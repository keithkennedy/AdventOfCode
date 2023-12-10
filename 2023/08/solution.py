import math

with open("input_files/input.txt", encoding="utf-8") as str_file:
    lines = str_file.read().splitlines()

    ndic = {}

    for i, l in enumerate(lines):
        if i == 0:
            instructions = l
        elif l != "":
            name, lr = l.split(" = ")
            l, r = lr.replace("(", "").replace(")", "").split(", ")
            ndic[name] = (l, r)

    current_node = "AAA"
    end = "ZZZ"

    steps = 0
    while current_node != end:
        for i in instructions:
            n = ndic[current_node]
            steps += 1
            if i == "L":
                current_node = n[0]
            else:
                current_node = n[1]

    print(steps)

    # part 2
    steps_2 = 0
    current_nodes = [key for key in ndic.keys() if key.endswith("A")]
    end_node_char = "Z"
    end_nodes = []
    number_of_paths = len(current_nodes)
    loop = True

    nums = []

    # find path from A/Z for each node...
    for idx, c in enumerate(current_nodes):
        node_next = True
        node_steps = 0
        while node_next:
            for i in instructions:
                n = ndic[c]
                if i == "L":
                    c = n[0]
                else:
                    c = n[1]

                node_steps += 1

                if c.endswith("Z"):
                    nums.append(node_steps)
                    node_next = False

    def find_lcd(numbers):
        def lcm(x, y):
            return x * y // math.gcd(x, y)
        lcd = 1
        for num in numbers:
            lcd = lcm(lcd, num)
        return lcd

    # then do LCD of the 6 nums
    print(find_lcd(nums))