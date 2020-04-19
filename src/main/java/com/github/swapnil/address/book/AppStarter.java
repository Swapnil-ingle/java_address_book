package com.github.swapnil.address.book;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.github.swapnil.address.book.model.AddressBook;
import com.github.swapnil.address.book.model.AddressBookEntry;
import com.github.swapnil.address.book.model.AddressBookEntryListCriteria;
import com.github.swapnil.address.book.services.AddressBookService;
import com.github.swapnil.address.book.utils.AddressBookUIUtils;
import com.github.swapnil.address.book.view.AddressBookBrowser;

public class AppStarter {
	private AddressBookService addrBookSvc = new AddressBookService();

	public void init() throws IOException {
		Scanner sc = new Scanner(System.in);

		System.out.println(ADDRESS_BOOK_BANNER);
		AddressBook addrBook = AddressBookUIUtils.getInstance().loadAddressBook(sc);
		boolean quit = false;

		while (!quit) {
			System.out.println("Please choose what you'd like to do with the database:");
			System.out.println(HELP_TEXT);
			String op = sc.next();

			switch (op) {
			case ("0"):
				System.out.println(HELP_TEXT);
				break;
			case ("1"):
				addrBook = AddressBookUIUtils.getInstance().loadAddressBook(sc);
				break;
			case ("2"):
				saveToFile(addrBook);
				break;
			case ("3"):
				if (addEntry(sc, addrBook)) {
					System.out.println("Info: Entrie(s) added!");
				} else {
					System.out.println("Error: Error while adding some entries!");
				}
				break;
			case ("4"):
				removeEntry(sc, addrBook);
				break;
			case ("5"):
				editEntry(sc, addrBook);
				break;
			case ("6"):
				sortEntries(sc, addrBook);
				break;
			case ("7"):
				AddressBookBrowser.browse(searchEntries(sc, addrBook));
				break;
			case ("8"):
				AddressBookBrowser.browse(addrBookSvc.getEntries(addrBook));
				break;
			case("9"):
				moveRecordCrossFiles(sc, addrBook);
				break;
			case ("x"):
				System.out.println("INFO: Bye");
				addrBook.close();
				quit = true;
				break;
			default:
				System.out.println("Error: Incorrect input!");
				break;
			}
		}

		sc.close();
	}

	private void moveRecordCrossFiles(Scanner sc, AddressBook addrBook) throws IOException {
		List<AddressBookEntry> results = new ArrayList<>();
		while (true) {
			System.out.println("Select an entry to move to other book");
			results = searchEntries(sc, addrBook);
			AddressBookBrowser.browse(results);

			if (results.isEmpty()) {
				System.out.println("No search result found!");
				if (AddressBookUIUtils.getInstance().tryAgain(sc)) {
					continue;
				} else {
					break;
				}
			}

			if (results.size() > 1) {
				System.out.println("Found more than one search result, only a single result is expected.");
				if (AddressBookUIUtils.getInstance().tryAgain(sc)) {
					continue;
				} else {
					break;
				}
			}
			
			System.out.println("Entry found: " + results.get(0));
			break;
		}

		if (results.size() != 1) {
			System.out.println("Aborting operation!");
		}

		System.out.println("Enter the address book path to which you want to move this entry");
		String newAddrBookPath = sc.next();
		addEntryToNewAddrBook(newAddrBookPath, results.get(0));
		addrBookSvc.removeEntry(addrBook, results.get(0).getEmail());
		addrBookSvc.saveToFile(addrBook);
	}

	private void addEntryToNewAddrBook(String path, AddressBookEntry entry) throws IOException {
		AddressBook addrBookTmp = new AddressBook(path);
		addrBookSvc.addEntry(addrBookTmp, entry);
		addrBookSvc.saveToFile(addrBookTmp);
		addrBookTmp.close();
		System.out.println("INFO: Added entry to the address book: " + path);
	}

	private List<AddressBookEntry> searchEntries(Scanner sc, AddressBook addrBook) {
		if (addrBook == null) {
			System.out.println("Error: No address book loaded");
			return Collections.emptyList();
		}
		
		System.out.println("Searching for field...");
		AddressBookEntryListCriteria.fields searchBy = AddressBookUIUtils.getInstance().getAddrBookEntryFieldFromUI(sc);
		System.out.println("Enter Matching Criteria");
		AddressBookEntryListCriteria.matchBy match_crit = AddressBookUIUtils.getInstance().getMatchingCriteria(sc);
		System.out.println("Search key: ");
		String searchKey = sc.next();
		System.out.println(
				String.format("INFO: Searching for records by '%s' field, with criteria '%s' with search key '%s'",
						searchBy.name(), match_crit.name(), searchKey));
		return addrBookSvc.search(addrBook, searchBy, searchKey, match_crit);
	}

	private void sortEntries(Scanner sc, AddressBook addrBook) {
		if (addrBook == null) {
			System.out.println("Error: No address book loaded");
			return;
		}
		System.out.println("Enter the sort key:");
		AddressBookEntryListCriteria.fields key = AddressBookUIUtils.getInstance().getAddrBookEntryFieldFromUI(sc);
		AddressBookBrowser.browse(addrBookSvc.getEntries(addrBook, key));
	}

	private void editEntry(Scanner sc, AddressBook addrBook) {
		if (addrBook == null) {
			System.out.println("Error: No address book loaded");
			return;
		}

		addrBookSvc.editEntry(addrBook, AddressBookUIUtils.getInstance().getAddressBookEntryFromUI(sc));
	}

	private void removeEntry(Scanner sc, AddressBook addrBook) {
		if (addrBook == null) {
			System.out.println("Error: No address book loaded");
			return;
		}

		System.out.println("Enter email for the record to be removed");
		String email = sc.next();
		addrBookSvc.removeEntry(addrBook, email);
	}

	private boolean addEntry(Scanner sc, AddressBook addrBook) throws IOException {
		if (addrBook == null) {
			System.out.println("Error: No address book loaded");
			return false;
		}
		boolean success = true;

		while (true) {
			System.out.println("Select option: 1) Single Entry, 2) Multiple Entries");
			int entryOp = sc.nextInt();

			if (entryOp == 1) {
				AddressBookEntry entry = AddressBookUIUtils.getInstance().getAddressBookEntryFromUI(sc);
				success = addrBookSvc.addEntry(addrBook, entry);
				System.out.println(entry);
				return success;
			} else if (entryOp == 2) {
				List<AddressBookEntry> entries = AddressBookUIUtils.getInstance().getAddressBookEntriesFromUI(sc);
				boolean entryPassed = true;
				for (AddressBookEntry entry : entries) {
					entryPassed = addrBookSvc.addEntry(addrBook, entry);
					success = entryPassed == false ? false : success;
				}
				return success;
			} else {
				System.out.println("Error: Select valid option");
			}
		}
	}

	private void saveToFile(AddressBook addrBook) {
		if (addrBook == null) {
			System.out.println("Error: No address book loaded");
			return;
		}
		addrBookSvc.saveToFile(addrBook);
	}

	public static final String ADDRESS_BOOK_BANNER = 
			"###################################################################################################\n" + 
			"## --------------------------------- ADDRESS BOOK APPLICATION ---------------------------------- ##\n" + 
			"###################################################################################################\n";

	public static final String HELP_TEXT = 
		"\n########################## \n" +
		"0) Display help menu \n" + 
		"1) Load from file \n" + 
		"2) Save to file \n" + 
		"3) Add entry/entries \n" + 
		"4) Remove an entry \n" + 
		"5) Edit an existing entry \n" + 
		"6) Sort the address book \n" + 
		"7) Search for a specific entry \n" +
		"8) Browse Records \n" +
		"9) Move an entry to other address book \n" +
		"x) Quit \n" + 
		"########################## \n";
}
