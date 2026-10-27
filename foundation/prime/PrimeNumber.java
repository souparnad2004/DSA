package foundation.prime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrimeNumber {

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

    public static void main(String[] args) {
        System.out.println(isPrime(4));
        System.out.println(isPrime(18));
        
        System.out.println(segmentedSieve(30, 50));
        System.out.println(segmentedSieve(100, 120));
    }
}
