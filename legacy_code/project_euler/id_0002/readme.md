# Problem 2: Even Fibonacci numbers

## Runtime

* `even_fibonacci_numbers.scala`:

        python3 -m timeit -n 1 -r 10 -s 'import os' 'os.system("scala ./even_fibonacci_numbers.scala")'
        4613732
        4613732
        4613732
        4613732
        4613732
        4613732
        4613732
        4613732
        4613732
        4613732
        1 loops, best of 10: 1.7 sec per loop

* `even_fibonacci_numbers_functional.scala`:

        python3 -m timeit -n 1 -r 10 -s 'import os' 'os.system("scala ./even_fibonacci_numbers_functional.scala")'
        4613732
        4613732
        4613732
        4613732
        4613732
        4613732
        4613732
        4613732
        4613732
        4613732
        1 loops, best of 10: 1.14 sec per loop

To be more accurate one should also count the number of calls to the `Fibb` function.

## Improvements

* Cache calls to `Fibb` function.
