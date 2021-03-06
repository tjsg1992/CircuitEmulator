package main;

import java.util.List;

import processor.lc3.LC3;
import transistor.Connection;

public class CircuitBuilder {

	public static void main(String[] args) {
		LC3 myLC3 = new LC3();

		while(true)
		try {
			Thread.sleep(1);
			printOutputs(myLC3.getCurrentOutput());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public static void printOutputs(Connection[] theOutputs) {
		for(int i = theOutputs.length - 1; i >= 0; i--) {
			if(theOutputs[i].hasPower()) {
				System.out.print("1");
			} else {
				System.out.print("0");
			}
		}
		System.out.print("\n");
	}

}
