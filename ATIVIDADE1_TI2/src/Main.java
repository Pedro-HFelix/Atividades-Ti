import java.util.Scanner;

public class Main {
	public static Scanner sc = new Scanner(System.in); 
	
	public static void main(String[] args) {
		int num1, num2, total;
		System.out.println("Digite o primeiro número ");
		num1 = sc.nextInt();

		System.out.println("Digite o segundo número ");
		num2 = sc.nextInt();
		
		total = num1 + num2;
		System.out.println("Total: " + total);
	}
}
