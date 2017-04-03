package com.testspring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.testspring.bookshop.BookShop;
import com.testspring.bookshop.Cashier;
import com.testspring.config.BookShopAnnotationConfiguration;

public class ApplicationJavaConfigAnnotation {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(BookShopAnnotationConfiguration.class);
		
		// Problem 4: Enable Spring Annotations for Transaction Management
		
//	    BookShop shop = (BookShop) context.getBean("bookShopSpringAnnotationTM");
//		shop.purchase("Book1", "user1");
		
		
		// Problem 5: Setting propagation attribute of Transaction
		
		Cashier cashier = (Cashier) context.getBean("cashierSpringAnnotaionTM");
		List<String> bookList = new ArrayList<String>();
		bookList.add("Book1");
		bookList.add("Book2");
		cashier.checkout(bookList, "user2");
	}
}
