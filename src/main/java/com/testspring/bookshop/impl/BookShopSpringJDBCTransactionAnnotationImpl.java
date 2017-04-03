package com.testspring.bookshop.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.testspring.bookshop.BookShop;

public class BookShopSpringJDBCTransactionAnnotationImpl extends JdbcDaoSupport
		implements BookShop {

	//@Transactional
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void purchase(String isbn, String userName) {
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

		} catch (DataAccessException e) {
			throw e;
		}
	}

	public String getBookByISBN(String isbn) {
		// TODO Auto-generated method stub
		return null;
	}

	
	// Problem 6a: Dirty Reads
	
	
//	@Transactional
//	public void increaseStock(String isbn, int count) {
//		String threadName = Thread.currentThread().getName();
//		System.out.println(threadName + "- Prepare to increase book stock");
//
//		getJdbcTemplate().update(
//				"UPDATE BOOK_STOCK SET STOCK = STOCK + ? " + "WHERE ISBN = ?",
//				new Object[] { count, isbn });
//
//		System.out.println(threadName + "- Book stock increased by " + count);
//		sleep(threadName, 5000L);
//
//		System.out.println(threadName + "- Book stock rolled back");
//		throw new RuntimeException("Increased by mistake");
//	}
//
//	//@Transactional(isolation = Isolation.READ_UNCOMMITTED)
//	@Transactional(isolation = Isolation.READ_COMMITTED)
//	public int checkStock(String isbn) {
//		String threadName = Thread.currentThread().getName();
//		System.out.println(threadName + "- Prepare to check book stock");
//
//		int stock = getJdbcTemplate().queryForObject(
//				"SELECT STOCK FROM BOOK_STOCK WHERE ISBN = ?",
//				new Object[] { isbn }, Integer.class);
//
//		System.out.println(threadName + "- Book stock is " + stock);
//		sleep(threadName, 3000L);
//		stock = getJdbcTemplate().queryForObject(
//				"SELECT STOCK FROM BOOK_STOCK WHERE ISBN = ?",
//				new Object[] { isbn }, Integer.class);
//		System.out.println(threadName + "- Returning from  check book stock. Stock now is " + stock);
//
//		return stock;
//	}
	
	
	
	
	
	
		// Problem 6b: Non Repeatable Reads	
	
		@Transactional
		public void increaseStock(String isbn, int count) {
			String threadName = Thread.currentThread().getName();
			System.out.println(threadName + "- Prepare to increase book stock");

			getJdbcTemplate().update(
					"UPDATE BOOK_STOCK SET STOCK = STOCK + ? " + "WHERE ISBN = ?",
					new Object[] { count, isbn });

			System.out.println(threadName + "- Book stock increased by " + count + " and going to commit");
		}

		
		//@Transactional(isolation = Isolation.READ_COMMITTED)
		@Transactional(isolation = Isolation.REPEATABLE_READ)
		public int checkStock(String isbn) {
			String threadName = Thread.currentThread().getName();
			System.out.println(threadName + "- Prepare to check book stock");

			int stock = getJdbcTemplate().queryForObject(
					"SELECT STOCK FROM BOOK_STOCK WHERE ISBN = ?",
					new Object[] { isbn }, Integer.class);

			System.out.println(threadName + "- Book stock is " + stock);
			sleep(threadName, 5000L);
			stock = getJdbcTemplate().queryForObject(
					"SELECT STOCK FROM BOOK_STOCK WHERE ISBN = ?",
					new Object[] { isbn }, Integer.class);
			System.out.println(threadName + "- Returning from  check book stock. Stock now is " + stock);

			return stock;
		}
	
	
	
	
	

	private void sleep(String threadName, Long ms) {
		System.out.println(threadName + "- Sleeping");
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
		}
		System.out.println(threadName + "- Wake up");
	}

}
