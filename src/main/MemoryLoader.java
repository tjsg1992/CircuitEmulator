package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import transistor.Connection;

/**
 * Loads a memory array with the contents of a machine language file.
 * 
 * @author Taylor Gorman
 * @version 1.0, 12/9/15
 *
 */
public class MemoryLoader {
	private Connection[] myInputs;
	private Connection[] myDecoderInputs;
	private Connection myWE;
	private File myFile;
	
	public MemoryLoader(Connection[] theInputs, Connection[] theDecoderInputs,
			Connection theWE, String theFileName) {
		if(theInputs == null || theInputs.length < 1) {
			throw new IllegalArgumentException("Input Connections Invalid");
		}
		if(theDecoderInputs == null || theDecoderInputs.length < 1) {
			throw new IllegalArgumentException("Decoder Inputs Invalid");
		}
		
		myInputs = theInputs;
		myFile = new File(theFileName);
		
		if(!myFile.exists() || myFile.isDirectory()) { 
		    throw new IllegalArgumentException("Invalid Input File");
		}
	}
	
	public void loadMemory() {
		List<String> fileContents = new ArrayList<String>();
		try {
			Scanner s = new Scanner(myFile);
			while (s.hasNext()){
				fileContents.add(s.next());
			}
			s.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < myDecoderInputs.length; i++) {
			myDecoderInputs[i].powerOff();
		}
		
		myWE.powerOff();
		
		for(String line : fileContents) {
			inputFileLine(line, myInputs);
			myWE.powerOn();
			myWE.powerOff();
			incrementDecoder(myDecoderInputs);
		}
	}
	
	private void incrementDecoder(Connection[] theDecoderInputs) {
		for(int i = 0; i < theDecoderInputs.length; i++) {
			if(theDecoderInputs[i].hasPower()) {
				theDecoderInputs[i].powerOff();
			} else {
				theDecoderInputs[i].powerOn();
				return;
			}
		}
	}
	
	private void inputFileLine(String theLine, Connection[] theInputs) {
		if(theLine.length() != theInputs.length) {
			throw new IllegalArgumentException();
		}
		
		char[] lineArray = theLine.toCharArray();
		for(int i = 0; i < lineArray.length; i++) {
			if(lineArray[i] == '1') {
				theInputs[i].powerOn();
			} else if(lineArray[i] == '0') {
				theInputs[i].powerOff();
			} else {
				throw new IllegalArgumentException();
			}
		}
	}

}
