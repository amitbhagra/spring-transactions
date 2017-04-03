package com.testspring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.testspring.bookshop.BookShop;
import com.testspring.config.BookShopConfiguration;

public class ApplicationJavaConfig {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(BookShopConfiguration.class);
	    
		// Problem 1. Problems in DB if Transactions are not used
		
//		BookShop shop = (BookShop) context.getBean("bookShop");
//		shop.purchase("Book1", "user1");
		
		// Problem 2: Enable JDBC Transactions (JDBC Specific )
		
//		BookShop shop = (BookShop) context.getBean("bookShopTM");
//		shop.purchase("Book1", "user1");

		
		
		// Problem 3: Programmatically applying Springs Transaction Management
		
		BookShop shop = (BookShop) context.getBean("bookShopSpringTM");
		shop.purchase("Book1", "user1");
	}
}
