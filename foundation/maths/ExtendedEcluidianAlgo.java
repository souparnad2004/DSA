package foundation.maths;


public class ExtendedEcluidianAlgo {
    static class Result {
        long gcd;
        long x;
        long y;

        Result(long gcd, long x, long y) {
            this.gcd = gcd;
            this.x = x;
            this.y = y;
        }
    }

    static Result extendedGCD(long a, long b) {
        if (b == 0) return new Result(a, 1, 0);

        Result next = extendedGCD(b, a % b);
        
        long x = next.y;
        long y = next.x - (a / b) * next.y;
        return new Result(next.gcd, x, y);
    }

    public static void main(String[] args) {
        System.out.println(extendedGCD(4, 6));
    }
}
