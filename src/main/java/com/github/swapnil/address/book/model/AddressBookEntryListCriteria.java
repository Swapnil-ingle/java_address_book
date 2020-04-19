package com.github.swapnil.address.book.model;

public class AddressBookEntryListCriteria {
	public enum fields {
		FIRST_NAME, LAST_NAME, PHONE, EMAIL, ADDRESS
	}

	public enum matchBy {
		FULL, START_WITH, END_WITH, CONTAINS
	}

	private AddressBookEntryListCriteria.fields searchBy;

	private Object searchText;

	public void setSearchBy(AddressBookEntryListCriteria.fields field) {
		this.searchBy = field;
	}

	public AddressBookEntryListCriteria.fields getSearchBy() {
		return this.searchBy;
	}

	public void setSearchText(Object searchText) {
		this.searchText = searchText;
	}

	public Object getSearchText() {
		return this.searchText;
	}
}
