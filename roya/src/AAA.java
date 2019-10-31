
public class AAA extends AA {
	int as = 1;
	String b = "asd";
 public void print(int a, String b){
	 System.out.println("AAA");
	 a = 2;
	 b = "ddd";
 }
 
 public static void main(String[] args) {
	AAA a = new AAA();
	a.print();
	System.out.println(a.as+a.b);
}
}
