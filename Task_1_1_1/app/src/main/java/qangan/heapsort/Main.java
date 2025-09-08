
package qangan.heapsort;

import java.util.Arrays;

/** Entry point. */

public class Main {
    /** Example usage. */
    public static void main(String[] args) {
        int[] a = {1, 2, 9, 8, 7, 6, 99};
        Heapsort.heapSort(a);
        System.out.println(Arrays.toString(a));
    }
}
