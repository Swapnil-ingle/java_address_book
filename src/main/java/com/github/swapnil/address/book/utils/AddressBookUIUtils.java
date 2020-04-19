package com.github.swapnil.address.book.utils;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import com.github.swapnil.address.book.model.AddressBook;
import com.github.swapnil.address.book.model.AddressBookEntry;
import com.github.swapnil.address.book.model.AddressBookEntryListCriteria;
import com.github.swapnil.address.book.model.AddressBookEntryListCriteria.fields;
import com.github.swapnil.address.book.model.AddressBookEntryListCriteria.matchBy;

public class AddressBookUIUtils {
	private static AddressBookUIUtils single_instance = null;

	public static AddressBookUIUtils getInstance() {
		if (single_instance == null) {
			single_instance = new AddressBookUIUtils();
		}

		return single_instance;
	}

	public AddressBook loadAddressBook(Scanner sc) {
		System.out.println("Enter filepath to load records from file");
		String file = sc.next();
		AddressBook addrBook = new AddressBook(file);
		System.out.println("INFO: Loaded records from the file: " + file);
		return addrBook;
	}

	public List<AddressBookEntry> getAddressBookEntriesFromUI(Scanner sc) {
		List<AddressBookEntry> entries = new ArrayList<>();
		String quitChar = "q";
		System.out.println("Enter multiple entries in the following format (enter '" + quitChar + "' when finished)");
		System.out.println("FirstName | Lastname | Email | Address | Phone");
		sc.nextLine();

		String input = "";

		do {
			input = sc.nextLine();

			String args[] = input.split("\\|");
			if ((args.length <= 0 || args.length == 1)) {
				if (args[0].equals(quitChar)) {
					continue;
				}

				System.out.println("---Info: Blank or invalid row above---");
				continue;
			}

			if (StringUtils.isEmpty(args[2])) {
				System.out.println("Error: Email cannot be null");
				continue;
			}

			AddressBookEntry entry = new AddressBookEntry();
			entry.setFirstName(args[0].trim());
			entry.setLastName(args[1].trim());
			entry.setEmail(args[2].trim());
			entry.setAddress(args[3].trim());
			entry.setPhone(Long.valueOf(args[4].trim()));

			entries.add(entry);
		} while (!input.equalsIgnoreCase(quitChar));

		return entries;
	}
	
	public fields getAddrBookEntryFieldFromUI(Scanner sc) {
		while (true) {
			try {
				System.out.println("1) First Name; 2) Last Name; 3) Email; 4) Phone; 5) Address");
				int searchOp = sc.nextInt();
				switch (searchOp) {
				case (1):
					return fields.FIRST_NAME;
				case (2):
					return fields.LAST_NAME;
				case (3):
					return fields.EMAIL;
				case (4):
					return fields.PHONE;
				case (5):
					return fields.ADDRESS;
				default:
					System.out.println("Error: Invalid operation, try again");
				}
			} catch (InputMismatchException e) {
				System.out.println("Error: Invalid operation, try again");
			}
		}
	}

	public AddressBookEntryListCriteria.matchBy getMatchingCriteria(Scanner sc) {
		while (true) {
			try {
				System.out.println("1) Full Match; 2) Starts With; 3) Ends With; 4) Contains");
				int searchOp = sc.nextInt();
				switch (searchOp) {
				case (1):
					return matchBy.FULL;
				case (2):
					return matchBy.START_WITH;
				case (3):
					return matchBy.END_WITH;
				case (4):
					return matchBy.CONTAINS;
				default:
					System.out.println("Error: Invalid operation, try again");
				}
			} catch (InputMismatchException e) {
				System.out.println("Error: Invalid operation, try again");
			}
		}
	}

	public AddressBookEntry getAddressBookEntryFromUI(Scanner sc) {
		System.out.println("Enter record details...");
		System.out.println("First Name:");
		String firstName = sc.next();
		System.out.println("Last Name:");
		String lastName = sc.next();
		System.out.println("Email:");
		String email = sc.next();
		System.out.println("Phone:");
		Long phone = sc.nextLong();
		System.out.println("Address:");
		String address = sc.next();

		AddressBookEntry entry = new AddressBookEntry();
		entry.setFirstName(firstName);
		entry.setLastName(lastName);
		entry.setEmail(email);
		entry.setPhone(phone);
		entry.setAddress(address);

		return entry;
	}

	public boolean tryAgain(Scanner sc) {
		System.out.println("Try again? (y/n)");
		String input = sc.next();
		return input.equalsIgnoreCase("y") ? true : false;
	}
}
