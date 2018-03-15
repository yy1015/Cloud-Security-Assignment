package assignment;

public class RsaSignatureSchema {
    
    private static long gcd(long p, long q) {
        while (q != 0) {
            long tmp = q;
            q = p % q;
            p = tmp;
        }
        return p;
    }
    
    private static long lcm(long a, long b) {
        return a * (b / gcd(a, b));
    }
    
    private static long[] xgcd(long a, long b) {
        long[] retvals = {0, 0, 0};
        long aa[] = {1, 0}, bb[] = {0, 1}, q = 0;
        while (true) {
            q = a / b;
            a = a % b;
            aa[0] = aa[0] - q * aa[1];
            bb[0] = bb[0] - q * bb[1];
            if (a == 0) {
                retvals[0] = b;
                retvals[1] = aa[1];
                retvals[2] = bb[1];
                return retvals;
            }
            q = b / a;
            b = b % a;
            aa[1] = aa[1] - q * aa[0];
            bb[1] = bb[1] - q * bb[0];
            if (b == 0) {
                retvals[0] = a;
                retvals[1] = aa[0];
                retvals[2] = bb[0];
                return retvals;
            }
        }
    }
    
    private static boolean isPrime(long n) {
        long i;
        for (i = 2; i <= Math.sqrt(n); i ++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
    
    private static long generateRandomPrimeNumber(long upper, long lower) {
        long rn = (long) (Math.random() * (upper - lower) + lower);
        while (!isPrime(rn)) rn++;
        return rn;
    }
    
    private static long fastExponentiation(long a, long b, long c) {
        long res = 1;
        a = a % c;

        while (b > 0) {
            if (b % 2 == 1) {
                res = (res * a) % c;
            }
            b /= 2;
            a = (a * a) % c;
        }
        return res;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        long p = generateRandomPrimeNumber(200, 400);
        long q = generateRandomPrimeNumber(200, 400);
        long n = p * q;
        long lamda = lcm((p - 1), (q - 1));
        System.out.println(lamda);
        long e = 100;
        while (gcd(e, lamda) != 1 && e < lamda) {
            e ++;
        }
        
        long[] ret = xgcd(e, lamda);
        long d = lamda + ret[1];
        System.out.println(String.format("Randomly chose p: %d, q: %d, get modulus: %d, public key: %d, private key: %d", p, q, n, e, d));
        long m = (long)(Math.random() * 100);
        System.out.println(String.format("Randomly chose message: %d", m));
        long encrypted = fastExponentiation(m, e, n);
        System.out.println(String.format("After signing: %d", encrypted));
        long decrypted = fastExponentiation(encrypted, d, n);
        System.out.println(String.format("Done verification: %d", decrypted));
    }

}
