package com.github.swapnil.address.book;

import org.apache.log4j.Logger;

public class Driver {
	private static final Logger logger = Logger.getLogger(Driver.class);

	public static void main(String[] args) {
		try {
			AppStarter starter = new AppStarter();
			starter.init();
		} catch (Exception e) {
			logger.error("Error while running application: ");
			e.printStackTrace();
		}
	}
}
