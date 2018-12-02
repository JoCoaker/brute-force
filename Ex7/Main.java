
public class Main {

    public static void main(String[] args) {
	 for (long n = 0; n < 22; n++) {
	     if (n == 21) {
	         n = 100;
	         // Stimmt für 100 nicht, nimm unteren link um das zu berechnen:
             // https://www.wolframalpha.com/input/?x=10&y=5&i=ceil(evaluate+log+base+2+of+(100!))
         }
         long result = (long) Math.ceil(Math.log10(faculty(n)) / Math.log10(2));
         System.out.println("a(" + n + ") = " + ((int)result));
     }
    }

    public static long faculty(long input) {
        long result = 1;
        for (int i=1; i<=input; i++)
        {
            result *= i;
        }

        return result;
    }
}
