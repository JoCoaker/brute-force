import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class PermOneThree {
  static List<List<Integer>> data;

  public static void main(String[] args) {
    data = new ArrayList<>();
    int n;
    Scanner input = new Scanner(System.in);

    System.out.print("Bitte geben Sie N ein: ");
    n = input.nextInt();
    newstartPermute(n);



    //Collections.sort(data);

    Collections.sort(data,
            (o1, o2) ->{

              for(int i = 0; i < o1.size(); i++) {
                if(o1.get(i)>o2.get(i)) {
                  return 1;
                }else if(o1.get(i)<o2.get(i)) {
                  return -1;
                }

              }
              return 0; //Passiert in dem fall nie but w/e
            });

    data.forEach(t -> System.out.println(t));
    System.out.println("Es gab genau "+ data.size() +" Permutationen der verlangten Art.");
  }

  private static void newstartPermute(int n) {
    for(int i =1 ; i<=n;i++) {
      List<Integer> test = new ArrayList<>();
      test.add(i);
      newPermute(test,n);
    }



  }

  private static void newPermute(List<Integer> list2 ,int n) {
    List<Integer> list=new ArrayList<>();
    list.addAll(list2);
    if(list.size()==n) {
      data.add(list);
    }else {

      if(!list.contains(list.get(list.size()-1)-4)&&list.get(list.size()-1)-4>0) {
        List<Integer> templist = new ArrayList<>();
        templist.addAll(list);
        templist.add(list.get(list.size()-1)-4);
        newPermute(templist,n);
      }

      if(!list.contains(list.get(list.size()-1)+1)&&list.get(list.size()-1)+1<=n) {
        List<Integer> templist = new ArrayList<>();
        templist.addAll(list);
        templist.add(list.get(list.size()-1)+1);
        newPermute(templist,n);
      }


      if(!list.contains(list.get(list.size()-1)+4)&&list.get(list.size()-1)+4<=n) {
        List<Integer> templist = new ArrayList<>();
        templist.addAll(list);
        templist.add(list.get(list.size()-1)+4);
        newPermute(templist,n);
      }

      if(!list.contains(list.get(list.size()-1)-1)&&list.get(list.size()-1)-1>0) {
        List<Integer> templist = new ArrayList<>();
        templist.addAll(list);
        templist.add(list.get(list.size()-1)-1);
        newPermute(templist,n);
      }


    }

  }

}