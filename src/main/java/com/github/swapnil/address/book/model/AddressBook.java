package com.github.swapnil.address.book.model;

import java.util.LinkedList;
import java.util.List;

import com.github.swapnil.address.book.file.AddressBookFileReader;

public class AddressBook {
	private List<AddressBookEntry> entries = new LinkedList<>();

	private AddressBookFileReader reader;

	private String file;

	public AddressBook(String file) {
		try {
			this.reader = new AddressBookFileReader(file);
			this.file = file;

			loadEntries();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<AddressBookEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<AddressBookEntry> entries) {
		this.entries = entries;
	}

	public AddressBookFileReader getReader() {
		return reader;
	}

	public void setReader(AddressBookFileReader reader) {
		this.reader = reader;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public void close() {
		reader.close();
	}

	private void loadEntries() {
		entries.addAll(reader.readEntries());
	}
}
