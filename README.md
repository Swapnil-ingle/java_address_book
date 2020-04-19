## Java Word Counter
![alt text](<URL> "Banner image")

## Problem Statement
Write a program that functions as an address book. It should have entries containing at least the following information: first and last name, phone number, address, and email address. You should be able to add entries and remove entries, as well as printing out any given entry. It should save entries to a file, and be able to read in entries from a file as well.

[Full problem statement](https://programmingbydoing.com/a/project-address-book.html)

## How to setup

1. `git clone https://github.com/Swapnil-ingle/java_address_book`

2. `gradle clean`

3. `gradle installApp`

## How to run

The shell script and batch script for running the program are generated in the dir **$Java_Address_Book/build/install/word-counter/bin** after the plugin is setup.

> `cd $Java_Address_Book/build/install/java_address_book/bin`

**Linux:**
> `./java_address_book<ABSOLUTE-PATH-TO-INPUT-FILES>`

**Windows:**
> `java_address_book <ABSOLUTE-PATH-TO-INPUT-FILES>`

**Note:**

1. This was tested on Gradle v2.0

2. The shell script and batch script for the program are in $Java_Address_Book/build/install/java_address_book/bin.

## Operations
### Load from file
The records are saved in an external file. Give the full path of the file to load records from the file. 
![alt text](<URL> "Load From File Operation")

### Save to file
This is operation is equivalent to the conventional "save" icon operation.
![alt text](<URL> "Save to File Operation")

### Add entry/entries
#### Adding single entry
Adding single entry to AddressBook

This is a guided step-by-step form-filling operation. 

![alt text](<URL> "Adding Single Entry Operation")
**Note:** Email is mandatory and unique per record entry.  

#### Adding multiple entries
Adding multiple records in a single go. This is much more convenient for doing a bulk-insert to the address-book.

![alt text](<URL> "Adding Multiple Entries Operation")

### Remove an entry
This operation removes an entry from the address-book.

![alt text](<URL> "Removing Entry Operation")

**If the mail does not exists:** 

![alt text](<URL> "Removing Entry Operation Failed")
### Edit an existing entry
This edits an existing entry in the address book. It uses email field as a key to match, edit and update the underlying record.

![alt text](<URL> "Edit Entry Operation")

### Records Browser Mode (Browse Records)
This is record browser mode. It shows the records in paginated form, each page holds 10 records.
Using this, the user can navigate through the records, each page at a time.

The browser mode is engaged whenever the user browses, searches, sorts through the records or attempt to move a record to other file.

![alt text](<URL> "Record Browser Mode")

**Note:**
* Enter 'n' --> To visit the next page.
* Enter 'b' --> To visit the previous page.
* Enter 'q' --> To quit the browser mode.
 
### Sort the address book
This operation sorts and display the records based on the input sorting key.

**Example: Sorting by first_name**
![alt text](<URL> "Record Sorting: FirstName")

**Example: Sorting by last_name**
![alt text](<URL> "Record Sorting: LastName")

### Search for a specific entry
This operation searches for a specific entry and displays the results (in browsing mode) based on the given **search key** and the **matching criteria**

![alt text](<URL> "Record Searching: FULL")

![alt text](<URL> "Record Searching: CONTAINS")

![alt text](<URL> "Record Searching: STARTS_WITH")

### Move an entry to other address book
The user can move an entry from one address-book file to other.
**Steps involved are:** 
1. Search for a record
2. Enter the full-path of the file to which you want to move the record

**Note:** User's search query must result in a single record.

![alt text](<URL> "Record Movement cross file")

#### Misc
* The program can handle multiple files and can switch between files during the normal operational workflow.
* The program is pretty robust and does not crib against faulty inputs.
