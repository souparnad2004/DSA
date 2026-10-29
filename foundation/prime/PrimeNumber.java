package foundation.prime;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrimeNumber {

    /**
     * Trial-division prime factorization.
     * Time: O(sqrt(n)); Space: O(1), excluding printed output.
     */
    static void PrimeFactors(int n) {
        if (n <= 1)
            return;

        while (n % 2 == 0) {
            System.out.print(2 + " ");
            n /= 2;
        }

        for (int i = 3; i <= n / i; i += 2) {
            while (n % i == 0) {
                System.out.print(i + " ");
                n /= i;
            }
        }

        if (n > 1) {
            System.out.print(n);
        }
    }

    /**
     * Tests primality by checking odd divisors up to sqrt(n).
     * Time: O(sqrt(n)); Space: O(1).
     */
    static boolean isPrime(int n) {

        if (n <= 1)
            return false;

        if (n == 2)
            return true;

        if (n % 2 == 0)
            return false;

        for (int i = 3; i <= n / i; i += 2) {

            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Finds all primes up to limit with the Sieve of Eratosthenes.
     * Time: O(limit log log limit); Space: O(limit), including the result.
     */
    static List<Integer> simpleSieve(int limit) {

        boolean[] isPrime = new boolean[limit + 1];

        Arrays.fill(isPrime, true);

        isPrime[0] = false;

        if (limit >= 1) {
            isPrime[1] = false;
        }

        for (int i = 2; i <= limit / i; i++) {

            if (isPrime[i]) {

                for (int j = i * i; j <= limit; j += i) {
                    isPrime[j] = false;
                }
            }
        }

        List<Integer> primes = new ArrayList<>();

        for (int i = 2; i <= limit; i++) {

            if (isPrime[i]) {
                primes.add(i);
            }
        }

        return primes;
    }

    /**
     * Finds primes in [L, R] using a base sieve and a marked segment.
     * Let N = R - L + 1. Time: O(sqrt(R) log log R + N log log R);
     * Space: O(sqrt(R) + N), including the base primes and result.
     */
    static List<Long> segmentedSieve(long L, long R) {

        if (L < 0 || R < L) {
            throw new IllegalArgumentException("Invalid range");
        }

        if (R - L + 1 > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Range too large");
        }

        if (R < 2) {
            return new ArrayList<>();
        }

        long root = (long) Math.sqrt(R);

        while ((root + 1) <= R / (root + 1)) {
            root++;
        }

        while (root > R / root) {
            root--;
        }

        if (root > Integer.MAX_VALUE - 1L) {
            throw new IllegalArgumentException(
                    "Base sieve limit too large");
        }

        int limit = (int) root;

        List<Integer> primes = simpleSieve(limit);

        int size = (int) (R - L + 1);

        boolean[] isPrime = new boolean[size];

        Arrays.fill(isPrime, true);

        for (int p : primes) {

            long prime = p;

            // First multiple of p >= L
            long start = (L / prime) * prime;

            if (start < L) {
                start += prime;
            }

            // Never start before p²
            start = Math.max(start, prime * prime);

            for (long j = start; j <= R;) {

                isPrime[(int) (j - L)] = false;

                if (R - j < prime) {
                    break;
                }

                j += prime;
            }
        }

        // 0 and 1 are not prime
        for (long x = L; x <= Math.min(R, 1L); x++) {
            isPrime[(int) (x - L)] = false;
        }

        List<Long> result = new ArrayList<>();

        for (int i = 0; i < size; i++) {

            if (isPrime[i]) {
                result.add(L + i);
            }
        }

        return result;
    }

    /**
     * Computes the GCD using Euclid's algorithm.
     * Time: O(log(min(a, b))); Space: O(1).
     */
    static long gcd(long a, long b) {
        while(b != 0) {
            long remainder = a % b;

            a = b;
            b = remainder;
        }

        return a;
    }

    /**
     * Computes the LCM using the GCD.
     * Time: O(log(min(a, b))); Space: O(1).
     */
    static long lcm(long a, long b) {
        if(a <= 0 || b <= 0) {
            throw new IllegalArgumentException("Both number must be positive");
        }

        long g = gcd(a, b);

        return (a / g) * b;
    }

    public static void main(String[] args) {
        System.out.println(isPrime(4));
        System.out.println(isPrime(18));
        
        System.out.println(segmentedSieve(30, 50));
        System.out.println(segmentedSieve(100, 120));
    }
}
