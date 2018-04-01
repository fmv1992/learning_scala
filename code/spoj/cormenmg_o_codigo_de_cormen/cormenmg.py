# Section: sequence of integers between 1 and 26.
# Subsections sums must add up to 42.
# A message is a sequence of words for which there no overlap between them.
# The size of a message equals the number of words which it contains.

# Input:
# First line:   S with 0 < S <= 16: the number of sections to be analized.
# S Groups of two lines: | Integer N: number of letters in this section.
#                        | Sequence of letters of the section.

import sys


SUBSUM = 42


def get_sections():
    """Return a list of integers list."""
    # Discard first line.
    sys.stdin.readline()
    lines = map(lambda x: x.strip(), sys.stdin.readlines())
    # Discard every "header" from lines.
    filtered_lines = filter(lambda x: x[0] % 2 == 1, enumerate(lines))
    integer_list = map(
        lambda x: [int(y) for y in x[1].split(' ')], filtered_lines)
    return integer_list


def scan_list(array):
    indexes_list = list()
    lenarray = len(array)
    for i in range(lenarray):
        j = i
        subsum = array[j]
        while subsum < SUBSUM and j < lenarray - 1:
            j = j + 1
            subsum = subsum + array[j]
        if subsum == SUBSUM:
            indexes_list.append((i, j))
    return indexes_list


def n_overlaps(indexes_list):
    overlaps = 0
    for a, b in zip(indexes_list, indexes_list[1:]):
        if a[1] >= b[0]:
            overlaps = overlaps + 1
    return overlaps


def main():
    sections = get_sections()
    for sec in sections:
        idx_list = scan_list(sec)
        res = len(idx_list) - n_overlaps(idx_list)
        print(res)


if __name__ == '__main__':
    main()
