package com.github.swapnil.address.book.file;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import com.github.swapnil.address.book.model.AddressBookEntry;

public class AddressBookFileReader {
	private FileInputStream fileIpStream;

	private ObjectInputStream objIpStream;

	public AddressBookFileReader(String file) {
		try {
			this.fileIpStream = new FileInputStream(file);
			this.objIpStream = new ObjectInputStream(fileIpStream);
		} catch (EOFException e) {
			System.out.println("Input file is empty!");
		} catch (Exception e) {
			System.out.println("Error while reading from file.");
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public List<AddressBookEntry> readEntries() {
		List<AddressBookEntry> entries = new ArrayList<>();
		try {
			if (objIpStream != null) {
				entries = (List<AddressBookEntry>) objIpStream.readObject();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (EOFException e) {
			System.out.println("Input file is empty!");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return entries;
	}

	public void close() {
		try {
			this.objIpStream.close();
			this.fileIpStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
