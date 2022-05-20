package br.com.nubank.capital.gain;

import br.com.nubank.capital.gain.delivery.controller.OperationController;

import java.util.Scanner;

public class Application {

	private static final OperationController operationController = OperationController.getInstance();

	public static void main(String[] args) {
		Scanner stdin = new Scanner(System.in);
		String line;
		while(stdin.hasNextLine() && !( line = stdin.nextLine() ).equals( "" ))
		{
			try{
				operationController.execute(line);
			} catch (Exception ex){
				System.out.println(ex.getMessage().concat(". ").concat(ex.getCause().getMessage()));
			}
		}
		stdin.close();
	}

}