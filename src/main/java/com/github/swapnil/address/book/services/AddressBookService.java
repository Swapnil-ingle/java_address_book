package com.github.swapnil.address.book.services;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.github.swapnil.address.book.file.AddressBookFileWriter;
import com.github.swapnil.address.book.model.AddressBook;
import com.github.swapnil.address.book.model.AddressBookEntry;
import com.github.swapnil.address.book.model.AddressBookEntryComparator;
import com.github.swapnil.address.book.model.AddressBookEntryListCriteria;
import com.github.swapnil.address.book.model.AddressBookEntryListCriteria.fields;
import com.github.swapnil.address.book.model.AddressBookEntryListCriteria.matchBy;

public class AddressBookService {
	private static final int ADDRESS_BOOK_SIZE = 25;

	public List<AddressBookEntry> getEntries(AddressBook addrBook) {
		return getEntries(addrBook, 0, addrBook.getEntries().size());
	}

	public List<AddressBookEntry> getEntries(AddressBook addrBook, int startIdx, int endIdx) {
		return getEntries(addrBook, startIdx, endIdx, AddressBookEntryListCriteria.fields.EMAIL);
	}

	public List<AddressBookEntry> getEntries(AddressBook addrBook, AddressBookEntryListCriteria.fields sortBy) {
		return getEntries(addrBook, 0, addrBook.getEntries().size(), sortBy);
	}

	public List<AddressBookEntry> getEntries(AddressBook addrBook, int startIdx, int endIdx,
			AddressBookEntryListCriteria.fields sortBy) {
		List<AddressBookEntry> entries = addrBook.getEntries();
		if (entries != null && !entries.isEmpty()) {
			return entries.stream().sorted(getSorter(sortBy)).skip(startIdx).limit(endIdx).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public void saveToFile(AddressBook addrBook) {
		AddressBookFileWriter writer = new AddressBookFileWriter(addrBook.getFile());
		writer.writeEntries(addrBook.getEntries());
		writer.close();
		System.out.println("INFO: Data saved to file!");
	}

	public boolean addEntry(AddressBook addrBook, AddressBookEntry entry) throws IOException {
		if (addrBook.getEntries().size() >= ADDRESS_BOOK_SIZE) {
			System.out.println("Address book entry size reached! Address_Book_Size: " + ADDRESS_BOOK_SIZE);
			return false;
		}

		if (addrBook.getEntries().contains(entry)) {
			System.out.println("Error: Cannot add entry '" + entry + "' (Similar record already exists)");
			return false;
		}

		addrBook.getEntries().add(entry);
		return true;
	}

	public void removeEntry(AddressBook addrBook, String email) {
		boolean removed = addrBook.getEntries().removeIf(entry0 -> entry0.getEmail().equals(email));

		if (!removed) {
			System.out.println("ERROR: No records with email " + email + " found!");
		} else {
			System.out.println("INFO: Entry Removed!");
		}
	}

	public void editEntry(AddressBook addrBook, AddressBookEntry input) {
		AddressBookEntry found = addrBook.getEntries().stream()
				.filter(entry0 -> entry0.getEmail().equalsIgnoreCase(input.getEmail())).findFirst().orElse(null);

		if (found == null) {
			System.out.println("ERROR: No entry for email " + input.getEmail());
		} else {
			found.update(input);
			System.out.println("INFO: Updated entry!");
		}
	}

	public List<AddressBookEntry> search(AddressBook addrBook, AddressBookEntryListCriteria.fields field, String key) {
		return addrBook.getEntries().stream().filter(entry -> match(entry, field, key)).collect(Collectors.toList());
	}
	
	public List<AddressBookEntry> search(AddressBook addrBook, AddressBookEntryListCriteria.fields field, String key, AddressBookEntryListCriteria.matchBy match_crit) {
		return addrBook.getEntries().stream().filter(entry -> match(entry, field, key, match_crit)).collect(Collectors.toList());
	}

	private boolean match(AddressBookEntry entry, fields field, String key) {
		return match(entry, field, key, matchBy.FULL);
	}

	private boolean match(AddressBookEntry entry, fields field, String key, matchBy match_crit) {
		switch (field) {
		case FIRST_NAME:
			return match(entry.getFirstName(), key, match_crit);
		case LAST_NAME:
			return match(entry.getLastName(), key, match_crit);
		case EMAIL:
			return match(entry.getEmail(), key, match_crit);
		case ADDRESS:
			return match(entry.getAddress(), key, match_crit);
		case PHONE:
			return match(String.valueOf(entry.getPhone()), key, match_crit);
		default:
			System.out.println("ERROR: Invalid match key provided");
			return false;
		}
	}
	
	private boolean match(String matchThis, String key, matchBy crit) {
		matchThis = matchThis.toLowerCase();
		key = key.toLowerCase();

		switch (crit) {
		case FULL:
			return matchThis.equalsIgnoreCase(key);
		case START_WITH:
			return matchThis.startsWith(key);
		case END_WITH:
			return matchThis.endsWith(key);
		case CONTAINS:
			return matchThis.contains(key);
		default:
			System.out.println("ERROR: Invalid matching criteria provided");
			return false;
		}
	}

	private Comparator<? super AddressBookEntry> getSorter(fields sortBy) {
		switch (sortBy) {
		case FIRST_NAME:
			return new AddressBookEntryComparator().new FirstNameComparator();
		case LAST_NAME:
			return new AddressBookEntryComparator().new LastNameComparator();
		case EMAIL:
			return new AddressBookEntryComparator().new EmailComparator();
		case ADDRESS:
			return new AddressBookEntryComparator().new AddressComparator();
		case PHONE:
			return new AddressBookEntryComparator().new PhoneComparator();
		default:
			System.out.println("ERROR: Invalid sorter field");
			break;
		}

		return null;
	}
}
