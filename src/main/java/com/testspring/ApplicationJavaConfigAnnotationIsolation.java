package com.testspring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.testspring.bookshop.BookShop;
import com.testspring.config.BookShopAnnotationConfiguration;

public class ApplicationJavaConfigAnnotationIsolation {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(
				BookShopAnnotationConfiguration.class);

		// Problem 6: Setting isolation Propagation Behavior
		
		
		final BookShop shop = (BookShop) context
				.getBean("bookShopSpringAnnotationTM");

		
		// Problem 6A:  Dirty Read
		Thread t1 = new Thread(() -> {
			try {
				shop.increaseStock("Book1", 5);
			} catch (RuntimeException e) {
			}
		}, "Thread One");

		Thread t2 = new Thread(() -> {
			try {
				shop.checkStock("Book1");
			} catch (RuntimeException e) {
			}
		}, "Thread Two");

		t2.start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		t1.start();
		
// Problem 6B: Repeatable Read
		
//		Thread t1 = new Thread(() -> {
//		try {
//			shop.increaseStock("Book1", 5);
//		} catch (RuntimeException e) {
//		}
//	}, "Thread One");
//
//	Thread t2 = new Thread(() -> {
//		try {
//			shop.checkStock("Book1");
//		} catch (RuntimeException e) {
//		}
//	}, "Thread Two");
//
//	t2.start();
//	
//	try {
//		Thread.sleep(1000);
//	} catch (InterruptedException e) {
//	}
//	
//	t1.start();

	}
}
