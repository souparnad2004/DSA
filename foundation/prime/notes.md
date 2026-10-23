# Prime Number

## Definition
A prime number is a natural number greater than 1
that has exactly two positive divisors: 1 and itself.

## Approach 1: Brute Force

### Intuition
Check every possible divisor from 2 to n - 1.

### Algorithm
1. If n <= 1, return false.
2. Iterate from 2 to n - 1.
3. If n is divisible by any number, return false.
4. Otherwise, return true.

### Complexity
- Time: O(n)
- Space: O(1)

## Approach 2: Square Root Optimization

### Intuition

Consider n = 36.

Factor pairs:

1 × 36

2 × 18

3 × 12

4 × 9

6 × 6

Notice that every factor pair contains at least
one factor <= sqrt(36).

### Why does this work?

Suppose both factors were greater than sqrt(n).

Then:

a > sqrt(n)
b > sqrt(n)

Therefore:

a × b > n

But a × b = n, which is a contradiction.

Hence, every composite number has at least
one factor <= sqrt(n).

### Algorithm
1. If n <= 1, return false.
2. Check divisors from 2 to sqrt(n).
3. If any divisor divides n, return false.
4. Otherwise, return true.

### Complexity
- Time: O(sqrt(n))
- Space: O(1)

## Approach 3: Skip Even Numbers

### Intuition
Every even number greater than 2 is composite.

Therefore:
1. Handle n <= 1.
2. Handle n == 2.
3. Reject even numbers.
4. Check only odd divisors up to sqrt(n).

### Complexity
- Time: O(sqrt(n))
- Space: O(1)