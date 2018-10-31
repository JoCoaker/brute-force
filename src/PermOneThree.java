import java.util.ArrayList;
        import java.util.Collections;
        import java.util.Comparator;
        import java.util.List;
        import java.util.Scanner;

public class PermOneThree {
  static List<String> data;

  public static void main(String[] args) {
    data = new ArrayList<>();
    int n;
    Scanner input = new Scanner(System.in);

    System.out.print("Bitte geben Sie N ein: ");
    n = input.nextInt();
    startPermute(n);



    //Collections.sort(data);



    data.forEach(t -> System.out.println(t));
    System.out.println("Es gab genau "+ data.size() +" Permutationen der verlangten Art.");
  }

  private static void startPermute(int n) {
    List<Integer> test = new ArrayList<>();

    for(int i = 1;i<=n;i++) {
      test.add(i);
    }
    permute(test,0,test.size()-1);
  }


  private static void permute(List<Integer> list, int left, int right) {
    if (left == r) {
      boolean fits = true;
      for(int i = 0;i<list.size()-1;i++) {
        int temp1 = list.get(i);
        int temp2 = list.get(i+1);
        if((temp1-temp2)!=1&&(temp1-temp2)!=-1&&(temp1-temp2)!=4&&(temp1-temp2)!=-4) {
          fits = false;

        }
      }
      if(fits) {
        data.add(list.toString());
      }
    }else{
      for (int i = left; i <= r; i++) {
        Collections.swap(list, left, i);
        permute(list, left+1, r);
        Collections.swap(list, left, i);
      }
    }
  }
}