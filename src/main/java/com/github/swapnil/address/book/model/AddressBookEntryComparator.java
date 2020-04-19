package com.github.swapnil.address.book.model;

import java.util.Comparator;

public class AddressBookEntryComparator {
	public class FirstNameComparator implements Comparator<AddressBookEntry> {
		@Override
		public int compare(AddressBookEntry o1, AddressBookEntry o2) {
			return o1.getFirstName().compareToIgnoreCase(o2.getFirstName());
		}
	}

	public class LastNameComparator implements Comparator<AddressBookEntry> {
		@Override
		public int compare(AddressBookEntry o1, AddressBookEntry o2) {
			return o1.getLastName().compareToIgnoreCase(o2.getLastName());
		}
	}

	public class EmailComparator implements Comparator<AddressBookEntry> {
		@Override
		public int compare(AddressBookEntry o1, AddressBookEntry o2) {
			return o1.getEmail().compareToIgnoreCase(o2.getEmail());
		}
	}

	public class PhoneComparator implements Comparator<AddressBookEntry> {
		@Override
		public int compare(AddressBookEntry o1, AddressBookEntry o2) {
			return o1.getPhone().compareTo(o2.getPhone());
		}
	}

	public class AddressComparator implements Comparator<AddressBookEntry> {
		@Override
		public int compare(AddressBookEntry o1, AddressBookEntry o2) {
			return o1.getAddress().compareToIgnoreCase(o2.getAddress());
		}
	}
}
