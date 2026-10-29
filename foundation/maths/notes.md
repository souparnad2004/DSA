# Extended Euclidean Algorithm — Quick Revision Notes

## What it finds

For integers `a` and `b`, the Extended Euclidean Algorithm finds:

- `g = gcd(a, b)`
- integers `x` and `y` such that:

  `a*x + b*y = g`

This identity is called **Bézout's identity**. The values `x` and `y` are
called Bézout coefficients.

The ordinary Euclidean Algorithm finds only the GCD. The extended version
also tracks how each remainder can be written as a combination of the
original inputs.

## Key idea

The Euclidean Algorithm uses:

`gcd(a, b) = gcd(b, a % b)`

Suppose the recursive call for `(b, a % b)` returns coefficients `p` and `q`:

`b*p + (a % b)*q = g`

Since:

`a % b = a - (a / b)*b`

substitute and collect the coefficients of `a` and `b`:

`a*q + b*(p - (a / b)*q) = g`

So the coefficients for `(a, b)` are:

- `x = q`
- `y = p - (a / b)*q`

In the code, `p` and `q` are `next.x` and `next.y`.

## Recursive algorithm

```text
extendedGCD(a, b):
    if b == 0:
        return (a, 1, 0)

    (g, p, q) = extendedGCD(b, a % b)
    x = q
    y = p - (a / b) * q
    return (g, x, y)
```

The returned triple is `(gcd, x, y)`, and should satisfy
`a*x + b*y = gcd` for the original inputs.

## Base case

When `b == 0`:

`gcd(a, 0) = a`

and:

`a*1 + 0*0 = a`

Therefore the base result is `(a, 1, 0)`.

## Worked example: `a = 4`, `b = 6`

Follow the recursive calls until the second argument is zero:

```text
extendedGCD(4, 6)
  -> extendedGCD(6, 4)
      -> extendedGCD(4, 2)
          -> extendedGCD(2, 0) = (2, 1, 0)
```

Unwind the calls:

1. For `(4, 2)`: `x = 0`, `y = 1 - (4 / 2)*0 = 1`  
   Result: `(2, 0, 1)`, since `4*0 + 2*1 = 2`.
2. For `(6, 4)`: `x = 1`, `y = 0 - (6 / 4)*1 = -1`  
   Result: `(2, 1, -1)`, since `6*1 + 4*(-1) = 2`.
3. For `(4, 6)`: `x = -1`, `y = 1 - (4 / 6)*(-1) = 1`  
   Result: `(2, -1, 1)`, since `4*(-1) + 6*1 = 2`.

Integer division is used: `4 / 6` is `0` in this calculation.

## Java implementation

This matches the recursive structure used by `ExtendedEcluidianAlgo`:

```java
static Result extendedGCD(long a, long b) {
    if (b == 0) return new Result(a, 1, 0);

    Result next = extendedGCD(b, a % b);

    long x = next.y;
    long y = next.x - (a / b) * next.y;
    return new Result(next.gcd, x, y);
}
```

To print the result use its fields (or add a `toString()` method to `Result`):

```java
Result result = extendedGCD(4, 6);
System.out.println(result.gcd + " " + result.x + " " + result.y);
// 2 -1 1
```

## Common applications

### Modular multiplicative inverse

The inverse of `a` modulo `m` is a number `x` satisfying:

`a*x ≡ 1 (mod m)`

Run `extendedGCD(a, m)`. If the result has `gcd == 1`, then `x` is an inverse:

`a*x + m*y = 1`, so `a*x ≡ 1 (mod m)`.

If `gcd(a, m) != 1`, an inverse does not exist. Normalize a possibly negative
inverse with:

`((x % m) + m) % m`

For example, `extendedGCD(3, 11)` gives `gcd = 1`, `x = 4`, `y = -1`.
Thus the inverse of `3 mod 11` is `4`, because `3*4 ≡ 1 (mod 11)`.

### Linear Diophantine equations

The equation `a*x + b*y = c` has integer solutions exactly when
`gcd(a, b)` divides `c`.

If `extendedGCD(a, b)` returns `(g, x0, y0)` and `g` divides `c`, one
particular solution is:

`x = x0 * (c / g)`, `y = y0 * (c / g)`

## Complexity

- **Time:** `O(log(min(a, b)))` for nonnegative inputs, as remainders shrink
  quickly.
- **Call stack:** `O(log(min(a, b)))`.

## Important details and pitfalls

- Use the quotient from the current pair `(a, b)` when calculating
  `y = next.x - (a / b)*next.y`.
- Return `next.gcd` while unwinding; the GCD is determined at the base case.
- Verify results with `a*x + b*y == gcd`.
- The sample implementation is simplest to reason about for nonnegative
  inputs. For negative inputs, normalize the signs so the returned GCD is
  nonnegative and adjust the coefficients accordingly.
- Use a sufficiently wide integer type for products and coefficients when
  inputs can be large; `long` arithmetic can still overflow.
- In Java, printing a `Result` object directly prints its default object
  representation unless `toString()` is implemented.