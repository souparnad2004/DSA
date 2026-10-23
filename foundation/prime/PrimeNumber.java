package foundation.prime;

public class PrimeNumber {

    static void PrimeFactors(int n) {
        if (n <= 1) return;

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

    public static void main(String[] args) {
        System.out.println(isPrime(4));
        System.out.println(isPrime(18));

        PrimeFactors(18);
    }
}
