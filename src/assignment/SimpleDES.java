package assignment;

import java.util.Scanner;

public class SimpleDES {
    
    private static int[] permutationP10(int key[])
    {
        int[] ret = new int[10];
        ret[0] = key[2];
        ret[1] = key[4];
        ret[2] = key[1];
        ret[3] = key[6];
        ret[4] = key[3];
        ret[5] = key[9];
        ret[6] = key[0];
        ret[7] = key[8];
        ret[8] = key[7];
        ret[9] = key[5];
        return ret;
    }
    
    private static int[] permutationP8(int key[])
    {
        int[] ret = new int[8];  
        ret[0] = key[5];
        ret[1] = key[2];
        ret[2] = key[6];
        ret[3] = key[3];
        ret[4] = key[7];
        ret[5] = key[4];
        ret[6] = key[9];
        ret[7] = key[8];  
        return ret;  
    }
    
    private static int[] leftshiftLS1(int key[])
    {
        int[] ret = new int[10];
        ret[0] = key[1];
        ret[1] = key[2];
        ret[2] = key[3];
        ret[3] = key[4];
        ret[4] = key[0];
      
        ret[5] = key[6];
        ret[6] = key[7];
        ret[7] = key[8];
        ret[8] = key[9];
        ret[9] = key[5];
        return ret;
    }
    
    private static int[] leftshiftLS2(int key[])
    {
        int[] ret = new int[10];     
        ret[0] = key[2];
        ret[1] = key[3];
        ret[2] = key[4];
        ret[3] = key[0];
        ret[4] = key[1];
      
        ret[5] = key[7];
        ret[6] = key[8];
        ret[7] = key[9];
        ret[8] = key[5];
        ret[9] = key[6];
        return ret;
      
    }
    
    private static int[] initialPermutation(int pt[])
    {
        int[] ret = new int[8];
        ret[0] = pt[1];
        ret[1] = pt[5];
        ret[2] = pt[2];
        ret[3] = pt[0];
        ret[4] = pt[3];
        ret[5] = pt[7];
        ret[6] = pt[4];
        ret[7] = pt[6];
        return ret;

    } 
    
    private static int[] inverseInitialPermutation(int pt[])
    {
        int[] ret = new int[8];
      
        ret[0] = pt[3];
        ret[1] = pt[0];
        ret[2] = pt[2];
        ret[3] = pt[4];
        ret[4] = pt[6];
        ret[5] = pt[1];
        ret[6] = pt[7];
        ret[7] = pt[5];
      
        return ret;     
    }
    
    private static int[] switchSW(int[] in)
    {
      
        int[] ret = new int[8];
        ret[0] = in[4];
        ret[1] = in[5];
        ret[2] = in[6];
        ret[3] = in[7];
    
        ret[4] = in[0];
        ret[5] = in[1];
        ret[6] = in[2];
        ret[7] = in[3];    
        return ret;
    }
    
    private static int[] mappingF(int[] R, int[] SK)
    {
        int[] temp = new int[8];
      
        temp[0]  = R[3];
        temp[1]  = R[0];
        temp[2]  = R[1];
        temp[3]  = R[2];
        temp[4]  = R[1];
        temp[5]  = R[2];
        temp[6]  = R[3];
        temp[7]  = R[0];
       
        // Bit by bit XOR with sub-key
        temp[0] ^= SK[0];
        temp[1] ^= SK[1];
        temp[2] ^= SK[2];
        temp[3] ^= SK[3];
        temp[4] ^= SK[4];
        temp[5] ^= SK[5];
        temp[6] ^= SK[6];
        temp[7] ^= SK[7];
       
        // S-Boxes
        final int[][] S0 = { {1,0,3,2}, {3,2,1,0}, {0,2,1,3}, {3,1,3,2}} ;
        final int[][] S1 = { {0,1,2,3}, {2,0,1,3}, {3,0,1,0}, {2,1,0,3}} ;
      
        int o1 = S0[temp[0]*2+temp[3]][temp[1]*2+temp[2]];
        int o2 = S1[temp[4]*2+temp[7]][temp[5]*2+temp[6]];
          
        //4 output bits from 2 s-boxes
        int[] out = new int[4];
        out[0] = o1 / 2;
        out[1] = o1 % 2;
        out[2] = o2 / 2;
        out[3] = o2 % 2;
        
        int [] ret = new int[4];
        ret[0] = out[1];
        ret[1] = out[3];
        ret[2] = out[2];
        ret[3] = out[0];
       
        return ret;
    }
    
    /** fK(L, R, SK) = (L (XOR) mappingF(R, SK), R) .. returns 8-bit output**/
    private static int[] functionFk(int[] i,int[] SK)
    { 
        int[] L = new int[4];
        int[] R = new int[4];
        
        L[0] = i[0];
        L[1] = i[1];
        L[2] = i[2];
        L[3] = i[3];
        R[0] = i[4];
        R[1] = i[5];
        R[2] = i[6];
        R[3] = i[7];
        
        int[] temp = new int[4];
        int[] out = new int[8];

        temp = mappingF(R,SK);

        out[0] = L[0] ^ temp[0];
        out[1] = L[1] ^ temp[1];
        out[2] = L[2] ^ temp[2];
        out[3] = L[3] ^ temp[3];
      
        out[4] = R[0];
        out[5] = R[1];
        out[6] = R[2];
        out[7] = R[3];

        return out;
    }
    
    private static int[] convertStringToBinary(int n, String inputKey) {
        int [] ret = new int[n];
        if (inputKey.length() != n) {
            System.err.println(String.format("The key needs to be %d digits, the one you typed in is %d", n, inputKey.length()));
            System.exit(1);
        }
        for (int i = 0; i < inputKey.length(); i ++) {
            if (inputKey.charAt(i) == '0') {
                ret[i] = 0;
            } else if (inputKey.charAt(i) == '1') {
                ret[i] = 1;
            } else {
                System.err.println("Character " + inputKey.charAt(i) + " is not valid");
                System.exit(1);
            }
        }
        return ret;
    }
    
    private static void printKeyArray(int key[]) {
        for (int i : key) {
            System.out.print(String.valueOf(i) + " ");
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the key (10 digits):");
        String inputKey = scanner.nextLine();
        int[] key = convertStringToBinary(10, inputKey);
        int[] key1 = permutationP8(leftshiftLS1(permutationP10(key)));
        int[] key2 = permutationP8(leftshiftLS2(leftshiftLS1(permutationP10(key))));
        System.out.println("Two keys are generated:");
        printKeyArray(key1);
        printKeyArray(key2);
        System.out.println("Please enter the clear text (8 digits):");
        String clearText = scanner.nextLine();
        int[] ct = convertStringToBinary(8, clearText);
        int[] et = inverseInitialPermutation(functionFk(switchSW(functionFk(initialPermutation(ct), key1)), key2));
        System.out.println("Encrypted text is:");
        printKeyArray(et);
        int[] det = inverseInitialPermutation(functionFk(switchSW(functionFk(initialPermutation(et), key2)), key1));
        System.out.println("Then we decrypt the encrypted text:");
        printKeyArray(det);
        scanner.close();
    }
}