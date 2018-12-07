import java.util.Arrays;

class MaxHeapSort {

  private static int heap_size;

  public static void main(String[] args) {
    for (int n = 0; n <= 55; n++) {
      System.out.println("n="+n+" : " + heapsOnNElements(n));
    }


    int[] g = new int[]{1, 6, 2, 5, 7, 9, 8, 4, 0, 3};
    System.out.println(Arrays.toString(g));
    buildMaxHeap(g);
    System.out.println(Arrays.toString(g));
  }

  private static void buildMaxHeap(int[] array) {
    heap_size = array.length;
    int w = (int)Math.floor(heap_size / 2.0);
    for (int i = w; i >= 1; i--) {
      maxHeapify(array, i);
    }
  }

  private static void maxHeapify(int[] array, int i) {
    int left = 2*i; // Linke Grenze
    int right = 2*i + 1; // Rechte Grenze
    int largest;

    if ((left <= heap_size) && (array[left - 1] > array [i - 1]))
      largest = left;
    else
      largest = i;

    if ((right <= heap_size) && (array[right - 1] > array [largest - 1]))
      largest = right;

    if (largest != i) {
      int tmp = array[i - 1];
      array[i - 1] = array[largest - 1];
      array[largest - 1] = tmp;

      System.out.println(i + " : " + Arrays.toString(array));

      maxHeapify(array, largest);
    }
  }

  public static void heapsort(int[] array) {
    buildMaxHeap(array);
    for (int i = array.length - 1; i >= 2; i--) {
      int tmp = array[1];
      array[1] = array[i];
      array[i] = tmp;
      heap_size--;
      maxHeapify(array, 1);
    }
  }

  private static double heapsOnNElements(double n) {
    if (n == 0 || n == 1)
      return 1;


    double h = Math.floor(Math.log10(n + 1)/Math.log10(2)) - 1;
    double b = Math.pow(2, h) - 1;
    double r = n - 1 - 2*b;
    double r1 = r - Math.floor(r/Math.pow(2, h))*(r-Math.pow(2, h));
    double r2 = r - r1;
    return binomial(n -1, b+r1)* heapsOnNElements(b+r1)* heapsOnNElements(b+r2);
  }

  private static double binomial(double n, double k)
  {
    if (k>n-k)
      k=n-k;

    double b=1;
    for (double i=1, m=n; i<=k; i++, m--)
      b=b*m/i;
    return b;
  }
}
