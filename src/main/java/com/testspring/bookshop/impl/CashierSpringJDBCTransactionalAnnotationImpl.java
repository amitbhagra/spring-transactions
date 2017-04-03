package com.testspring.bookshop.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.testspring.bookshop.BookShop;
import com.testspring.bookshop.Cashier;

public class CashierSpringJDBCTransactionalAnnotationImpl implements Cashier {

	private BookShop bookShop;
	
	@Transactional
	public void checkout(List<String> isbns, String username) {
		for (String isbn : isbns) {
			bookShop.purchase(isbn, username);
		}

	}

	
	public void setBookShop(BookShop bookShop) {
		this.bookShop = bookShop;
	}

}
