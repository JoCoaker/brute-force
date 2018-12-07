import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

public class MoneyPayoutOptions {
  // Indices 0 1 2 3 4 5 6 7
  private int betrag[] = {3, 3, 5, 8};

  private int n = betrag.length; // Anzahl Muenzen
  // # Wechselarten fuer Betrag g und Muenzen mit Indices <= i
  private long w[][]; // Tabelle
  private BigInteger table[][]; // Tabelle

  /**
   * Kontruktor MoneyPayoutOptions
   */
  public MoneyPayoutOptions() {
    read();
  }

  /**
   * Endless loop zum einlesen.
   */
  private void read() {
    Scanner s = new Scanner(System.in);

    String input = "";
//
//    for (int i = 0; i <= 13; i++) {
//      int g = (int) Math.pow(5, i);
////      w = new long[g + 1][n]; // w dimensionieren
////      System.out.println("Den Betrag " + g + " (5^" + i + ") kann man auf " +
////          w(g, n - 1) + " verschiedene Arten wecheln. (HEINZ)");
//      int size = (g + 1);
////      if (i > 0) {
//////        size = size / i;
//////      }
//////      if (size < 1000) {
//////        size = g + 1;
//////      }
//      if (size > 1000000) {
//        size = 1000000;
//      }
//      table = new BigInteger[size][betrag.length]; // w dimensionieren
//      System.out.println("Den Betrag " + g + " (5^" + i + ") kann man auf " +
//          calcOptions(g, betrag.length) + " verschiedene Arten auszahlen.");
//    }
    while (true) {
      System.out.println("Bitte geben Sie Ihr Betrag ein: (Zum beenden \"hguone\" eingeben)");
      try {
        input = s.nextLine();

        if (input.equals("hguone")) {
          System.out.println("bye");
          return;
        }

        int g = Integer.parseInt(input); // g lesen
        w = new long[g + 1][n]; // w dimensionieren
        System.out.println("Den Betrag " + g + " kann man auf " +
            w(g, n - 1) + " verschiedene Arten wecheln.");

        for (long[] a :
                w) {

          System.out.println(Arrays.toString(a));
        }
      } catch (Exception e) {
        System.out.println("Unguelitige Eingabe!");
      }
    }
  }

  private long w(int g, int i) { // Methode
    return g < 0 ? 0 :
        i == 0 ? (g % betrag[0] == 0 ? 1 : 0) :
            w[g][i] != 0 ? w[g][i] :
                (w[g][i] = w(g, i - 1) + w(g - betrag[i], i));
  }

  private BigInteger calcOptions(int n, int m) {
    int tableI = 0;
    for (int i = 0; i < n + 1; i++) {
      for (int j = 0; j < m; j++) {
        tableI = i % table.length;
        if (i == 0) {
          table[tableI][j] = BigInteger.ONE;
        } else if (j == 0) {
          if (i % betrag[j] == 0) {
            table[tableI][j] = BigInteger.ONE;
          } else {
            table[tableI][j] = BigInteger.ZERO;
          }
        } else if (betrag[j] > i) {
          table[tableI][j] = table[tableI][j - 1];
        } else {
          int test = tableI - betrag[j];
          if (test < 0) {
            test = table.length + test;
          }
          table[tableI][j] = table[test][j].add(table[tableI][j - 1]);
        }
      }
    }

    return table[tableI][m - 1];
  }

  /**
   * main Methode.
   *
   * @param args String[]
   */
  public static void main(String[] args) {
    new MoneyPayoutOptions(); // Neues Objekt erzeugen.
  }
}