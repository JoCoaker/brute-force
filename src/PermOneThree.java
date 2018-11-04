import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Algorithmen Uebung 4.
 *
 * @author Tobias Müller              193683
 * @author Peter Tim Oliver Nauroth   198322
 * @author Lukas Reichert             199034
 */
public class PermOneThree {
  private List<List<Integer>> data = new ArrayList<>();

  /**
   * Konstruktor.
   */
  public PermOneThree() {
    // Scanner zum einlesen erstellen.
    Scanner input = new Scanner(System.in);

    try {
      int n;
      // Hinweis geben und Input einlesen.
      System.out.print("Bitte geben Sie N ein: ");
      n = input.nextInt();

      // Beginne die permutation.
      newStartPermute(n);

      // Sortiere die Permutationen.
      Collections.sort(data,
          (o1, o2) -> {
            for (int i = 0; i < o1.size(); i++) {
              if (o1.get(i) > o2.get(i)) {
                return 1;
              } else if (o1.get(i) < o2.get(i)) {
                return -1;
              }
            }
            return 0; //Passiert in dem fall nie aber w/e.
          });

      // Gib all permuationen aus.
      data.forEach(t -> System.out.println(t));
      System.out.println("Es gab genau " + data.size() + " Permutationen der verlangten Art.");

    }catch (Exception e) {
      System.out.println("Ungueltige Eingabe");
    }
  }

  /**
   * Beginnt die Permuation.
   *
   * @param n Maximal Zahl
   */
  private void newStartPermute(int n) {
    // Von 1 bis n gehen.
    for(int i =1 ; i<=n;i++) {
      List<Integer> test = new ArrayList<>();
      test.add(i);
      newPermute(test,n);
    }
  }

  /**
   * Neue Permutation einfügen.
   *
   * @param list2 Liste mit der derzeitigen Position
   * @param n Maximal Zahl
   */
  private void newPermute(List<Integer> list2 ,int n) {
    List<Integer> list=new ArrayList<>();
    // Werte kopieren
    list.addAll(list2);
    // Überprüfen ob alle Zahlen beinhaltet sind.
    if(list.size()==n) {
      // Füge es der "End" Liste hinzu.
      data.add(list);
    }else {
      // Überprüfen ob die letzte Zahl passend ist (-4).
      if(!list.contains(list.get(list.size()-1)-4)&&list.get(list.size()-1)-4>0) {
        List<Integer> templist = new ArrayList<>();
        // Liste hinzufügen und ...
        templist.addAll(list);
        // ... letztes element hinzufügen.
        templist.add(list.get(list.size()-1)-4);
        // Rekursiver aufruf mit der neuen Liste.
        newPermute(templist,n);
      }

      // Überprüfen ob die letzte Zahl passend ist (+1).
      if(!list.contains(list.get(list.size()-1)+1)&&list.get(list.size()-1)+1<=n) {
        List<Integer> templist = new ArrayList<>();
        // Liste hinzufügen und ...
        templist.addAll(list);
        // ... letztes element hinzufügen.
        templist.add(list.get(list.size()-1)+1);
        // Rekursiver aufruf mit der neuen Liste.
        newPermute(templist,n);
      }

      // Überprüfen ob die letzte Zahl passend ist (+4).
      if(!list.contains(list.get(list.size()-1)+4)&&list.get(list.size()-1)+4<=n) {
        List<Integer> templist = new ArrayList<>();
        // Liste hinzufügen und ...
        templist.addAll(list);
        // ... letztes element hinzufügen.
        templist.add(list.get(list.size()-1)+4);
        // Rekursiver aufruf mit der neuen Liste.
        newPermute(templist,n);
      }

      // Überprüfen ob die letzte Zahl passend ist (-1).
      if(!list.contains(list.get(list.size()-1)-1)&&list.get(list.size()-1)-1>0) {
        List<Integer> templist = new ArrayList<>();
        // Liste hinzufügen und ...
        templist.addAll(list);
        // ... letztes element hinzufügen.
        templist.add(list.get(list.size()-1)-1);
        // Rekursiver aufruf mit der neuen Liste.
        newPermute(templist,n);
      }
    }

  }

  /**
   * Main Methode um das Programm zu starten.
   *
   * @param args Start Argumente
   */
  public static void main(String[] args) {
    new PermOneThree();
  }

}