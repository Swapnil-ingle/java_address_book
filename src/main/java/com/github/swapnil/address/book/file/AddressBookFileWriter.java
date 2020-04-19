package com.github.swapnil.address.book.file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;

import com.github.swapnil.address.book.model.AddressBookEntry;

public class AddressBookFileWriter {
	private FileOutputStream fileOpStream;

	private ObjectOutputStream objOpStream;

	public AddressBookFileWriter(String file) {
		try {
			this.fileOpStream = new FileOutputStream(file);
			this.objOpStream = new ObjectOutputStream(fileOpStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writeEntry(AddressBookEntry entry) {
		writeEntries(Collections.singletonList(entry));
	}

	public void writeEntries(List<AddressBookEntry> entries) {
		try {
			objOpStream.writeObject(entries);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void flush() {
		try {
			this.objOpStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			this.objOpStream.close();
			this.fileOpStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
