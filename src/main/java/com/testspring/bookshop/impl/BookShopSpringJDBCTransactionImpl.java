package com.testspring.bookshop.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.testspring.bookshop.BookShop;

public class BookShopSpringJDBCTransactionImpl extends JdbcDaoSupport implements
		BookShop {

	private PlatformTransactionManager manager;

	public void setManager(PlatformTransactionManager manager) {
		this.manager = manager;
	}

	public void purchase(String isbn, String userName) {
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = manager.getTransaction(def);
		try {

			int price = getJdbcTemplate().queryForObject(
					"select PRICE from BOOK where ISBN = ?",
					new Object[] { isbn }, Integer.class);

			getJdbcTemplate().update(
					"update BOOK_STOCK set STOCK = STOCK - 1 where ISBN = ?",
					new Object[] { isbn });

			getJdbcTemplate()
					.update("update ACCOUNT set BALANCE = BALANCE - ? where USERNAME = ?",
							new Object[] { price, userName });

			manager.commit(status);

		} catch (DataAccessException e) {
			manager.rollback(status);
			throw e;
		}
	}

	public String getBookByISBN(String isbn) {
		// TODO Auto-generated method stub
		return null;
	}

	public void increaseStock(String isbn, int count) {
		// TODO Auto-generated method stub
		
	}

	public int checkStock(String isbn) {
		// TODO Auto-generated method stub
		return 0;
	}

}
