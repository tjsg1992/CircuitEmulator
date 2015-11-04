package main;

import processor.lc3.LC3;
import circuit.storage.RSLatch;
import transistor.Connection;

public class CircuitBuilder {

	public static void main(String[] args) {
		LC3 myLC3 = new LC3();

		while(true) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			printOutputs(myLC3.getCurrentOutput());
		}
		
		
		
	}
	
	private static void printOutputs(Connection[] theOutputs) {
		for(int i = 3; i >= 0; i--) {
			if(theOutputs[i].hasPower()) {
				System.out.print("1");
			} else {
				System.out.print("0");
			}
		}
		System.out.print("\n");
	}

}
