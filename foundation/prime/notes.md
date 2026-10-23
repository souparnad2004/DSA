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

## Prime Factorization

Prime factorization means writing a number as a product of prime numbers.
For example:

`45 = 3 × 3 × 5`

### Approach 1: Brute Force

Try every possible divisor starting at 2. Whenever a divisor divides the
number, record it and divide it out. Keep trying that same divisor until it
no longer divides; then move to the next number.

For 45:

1. 2 does not divide 45.
2. 3 divides 45, so record 3 and divide: 45 becomes 15.
3. 3 still divides 15, so record 3 and divide: 15 becomes 5.
4. 4 does not divide 5.
5. 5 divides 5, so record 5 and divide: 5 becomes 1.

The prime factors are `3, 3, 5`.

Checking all possible divisors up to the original number takes O(n) time in
the worst case and O(1) extra space (not counting printed output).

### Approach 2: Check Only Up to the Square Root

Once a divisor has been divided out, keep checking divisors only while
`i <= n / i`. This condition is equivalent to `i * i <= n`, but avoids
overflow from multiplying large integers.

Why is it safe to stop there? If the remaining n had a factor larger than
its square root, its matching factor would have to be smaller than the square
root and would already have been found. So if the loop stops and n is still
greater than 1, that leftover n is prime and is the final factor.

### Approach 3: Skip Even Divisors

First divide out all factors of 2. After that, n is odd, so no even number
can divide it. Check only odd divisors: 3, 5, 7, and so on. For each divisor,
divide repeatedly before moving to the next one.

For 45:

1. There are no factors of 2.
2. 3 divides 45 twice, leaving 5.
3. The loop can stop because 5 is greater than `sqrt(5)`.
4. The leftover 5 is prime.

This is the approach used by `PrimeFactors`. It runs in O(sqrt(n)) time in
the worst case and uses O(1) extra space, not counting printed output.