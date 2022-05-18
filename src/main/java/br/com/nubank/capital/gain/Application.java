package br.com.nubank.capital.gain;

import br.com.nubank.capital.gain.delivery.controller.OperationController;

import java.util.Scanner;

public class Application {

	private static final OperationController operationController = OperationController.getInstance();

	public static void main(String[] args) {
		String line;
		Scanner stdin = new Scanner(System.in);
		while(stdin.hasNextLine() && !( line = stdin.nextLine() ).equals( "" ))
		{
			System.out.println(operationController.execute(line));
		}
		stdin.close();
	}

}