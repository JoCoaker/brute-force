import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Perm extends Thread {
  private static List<int[]> result = new ArrayList<>();

  private int[] a; // a Arbeitsarray
  private int max; // maximaler Index
  private boolean mayread = false; // Kontrolle

  public int j = 0;

  Perm(int n) { // Konstruktor
    a = new int[n]; // Indices: 1 .. n
    max = n; // Maximaler Index
    for (int i = 0; i < max; i++) a[i] = i + 1; // a fuellen
    this.start(); // run-Methode beginnt zu laufen
  } // end Konstruktor

  public void run() {// die Arbeits-Methode
    perm(0); // a[0] bleibt fest; permutiere ab 1
    a = null; // Anzeige, dass fertig
    put(); // ausliefern
  } // end run

  private void perm(int i) { // permutiere ab Index i
    j++;
    if (i >= max) put(); // eine Permutation fertig
    else {
      for (int j = i; j < max; j++) { // jedes nach Vorne   LZ: n-1
        swap(i, j); // vertauschen
        perm(i + 1); // und Rekursion   LZ: T(n-1)
      }
      int h = a[i]; // restauriere Array
      System.arraycopy(a, i + 1, a, i, max - i - 1); // shift links
      a[max - 1] = h;
    }
  } // end perm

  private void swap(int i, int j) { // tausche a[i] <-> a[j]
    if (i != j) {
      int h = a[i];
      a[i] = a[j];
      a[j] = h;
    }
  } // end swap

  synchronized int[] getNext() { // liefert naechste Permutation
    mayread = false; // a darf geaendert werden
    notify(); // wecke anderen Thread
    try {
      while (!mayread) wait(); // non busy waiting
    } catch (InterruptedException e) {
    }
    return a; // liefere Permutationsarray
  } // end getNext

  private synchronized void put() { // uebergebe array
    mayread = true; // a wird gelesen
    notify(); // wecke anderen Thread
    try {
      if (a != null)
        while (mayread) wait(); // non busy waiting
    } catch (InterruptedException e) {
    }
  } // end put

  public static void main(String[] arg) {
    for (int j = 1; j < 16; j++) {

      result = new ArrayList<>();
      Perm t = new Perm(j);
      int[] a;
      long start = System.currentTimeMillis();
      int count = 0;
      while ((a = t.getNext()) != null) {
        boolean success = true;
        for (int i = 0; i < a.length - 2; i++) {
          for (int l = i + 2; l < a.length; l++) {
            double compare = (a[i] + a[l]) / 2.0;

            if (compare % 1 != 0) {
              continue;
            }

            for (int k = i + 1; k < l; k++) {
              if (compare == a[k]) {
                success = false;
                break;
              }
            }
            if (!success) {
              break;
            }
          }
          if (!success) {
            break;
          }
        }
        if (success) {
          result.add(a);
//          System.out.println(Arrays.toString(a));
        }
//        count++;
      }
      long diff = System.currentTimeMillis() - start;
      System.out.println("n=" + j);
      System.out.println("Anzahl der permutationen:  " + result.size());
      System.out.println("Zeit: " + (diff / 1000.0) + " s");


      System.out.println();
    }
  }
}