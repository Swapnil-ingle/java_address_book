package com.github.swapnil.address.book.view;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.github.swapnil.address.book.model.AddressBookEntry;

public class AddressBookBrowser {
	private static int startIdx;

	private static int endIdx;
	
	private static final int BATCH_SIZE = 10; 

	public static void browse(List<AddressBookEntry> entries) {
		initPager();
		Scanner sc = new Scanner(System.in);
		String browseOp = "";

		do {
			List<AddressBookEntry> slicedResults = sliceEntries(entries);
			System.out.println(BROWSE_MODE_HEADER);
			slicedResults.forEach(e -> System.out.println(e));
			System.out.println(BROWSE_MODE_HELP_TEXT);
			System.out.println(String.format(BROWSE_MODE_FOOTER, startIdx, endIdx));
			browseOp = sc.next();

			if (browseOp.equals("n")) {
				if (slicedResults.size() == 0 || slicedResults.size() % 10 != 0) {
					System.out.println("ERROR: Already on last page");
					continue;
				}

				startIdx += 10;
				endIdx = startIdx + 10;
			} else if (browseOp.equals("b")) {
				startIdx -= startIdx >= 10 ? 10 : 0;
				endIdx -= endIdx >= 20 ? 10 : 0;
			}

		} while (!browseOp.equals("q"));

		reloadPager();
	}

	private static List<AddressBookEntry> sliceEntries(List<AddressBookEntry> entries) {
		return entries.stream().skip(startIdx).limit(BATCH_SIZE).collect(Collectors.toList());
	}
	
	private static void reloadPager() {
		initPager();
	}

	private static void initPager() {
		startIdx = 0;
		endIdx = 10;
	}

	private final static String BROWSE_MODE_HEADER =  
			"######################## ADDRESS BOOK BROWSER #############################\n";

	private final static String BROWSE_MODE_FOOTER = 
			"######################## Entries: %d to %d #############################";

	private final static String BROWSE_MODE_HELP_TEXT = 
			"\n--help-- ('n': next page; 'b': back page; 'q': quit browser) --help--";
}
