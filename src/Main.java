import java.util.Scanner;

public class Main {
	public static Scanner sc = new Scanner(System.in); 
	
	public static void main(String[] args) {
		System.out.println("Digite o primeiro número ");
		int num1 = sc.nextInt();
		System.out.println("Digite o segundo número ");
		int num2 = sc.nextInt();
		int total = num1 + num2;
		System.out.println("Total: " + total);
	}
}
