import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DKnV {

  private int countTotalSwaps = 0;
  private int countSwaps = 0;
  private List<Integer> cacheSwaps = new ArrayList<>();

  private List<Integer[]> results = new ArrayList<>();

  public DKnV() {
    int n = 3;

    double t = recursiv(n);

    System.out.println("" + Math.floor(Math.floor(t) / Main.faculty(n)));

    List<Integer> array = new ArrayList<>();

    for (int i = 0; i < n; i++) {
      array.add(i + 1);
    }

    permute(array, 0);

    for (int i = 0; i < results.size(); i++) {
      countSwaps = 0;
      Integer[] ar = results.get(i);

      sort(ar);

      cacheSwaps.add(countSwaps);
    }

    System.out.println("Total swaps: " + countTotalSwaps);
    System.out.println("Total swaps: " + t);
    System.out.println("Swaps: " + Arrays.toString(cacheSwaps.toArray()));

    System.out.println("Average: " + (countTotalSwaps / cacheSwaps.size()));
  }

  private double recursiv(double n) {
    return n == 0 ? 0 : (recursiv(n-1) * n + Main.faculty((int) n-1) * (Math.pow(2, n-1) -1));
  }

  private Integer[] sort(Integer[] array) {

    for (int i = 1; i < array.length; i++) {
      if (array[i] < array[i - 1]) {
        array = toFront(array, i);
        i = 0;
      }
    }

    return array;
  }

  private Integer[] toFront(Integer[] array, int swapIndex) {
    countSwaps++;
    countTotalSwaps++;

    int temp = array[swapIndex];
    int tempTemp;

    for (int i = 0; i <= swapIndex; i++) {
      tempTemp = array[i];
      array[i] = temp;
      temp = tempTemp;
    }

    return array;
  }

  private void permute(List<Integer> arr, int k) {
    for (int i = k; i < arr.size(); i++) {
      Collections.swap(arr, i, k);
      permute(arr, k + 1);
      Collections.swap(arr, k, i);
    }
    if (k == arr.size() - 1) {
      Integer[] a = arr.toArray(new Integer[arr.size()]);
      results.add(a);
    }
  }

  /**
   * Methode "main".
   *
   * @param args String[]
   */
  public static void main(String[] args) {
    new DKnV(); // Neues Objekt erzeugen.
  }
}
