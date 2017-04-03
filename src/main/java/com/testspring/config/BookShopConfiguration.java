package com.testspring.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import com.mysql.jdbc.Driver;
import com.testspring.bookshop.BookShop;
import com.testspring.bookshop.impl.BookShopJDBCImpl;
import com.testspring.bookshop.impl.BookShopJDBCTransactionImpl;
import com.testspring.bookshop.impl.BookShopSpringJDBCTransactionImpl;

@Configuration
public class BookShopConfiguration {

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(Driver.class.getName());
		dataSource.setUrl("jdbc:mysql://localhost/stm");
		dataSource.setUsername("root");
		dataSource.setPassword("Welcome@1");
		return dataSource;
	}

//	@Bean
//	public BookShop bookShop() {
//		BookShopJDBCImpl bookShop = new BookShopJDBCImpl();
//		bookShop.setDataSource(dataSource());
//		return bookShop;
//	}
	
//	@Bean
//	public BookShop bookShopTM() {
//		BookShopJDBCTransactionImpl bookShop = new BookShopJDBCTransactionImpl();
//		bookShop.setDataSource(dataSource());
//		return bookShop;
//	}
//	
	@Bean
	public BookShop bookShopSpringTM() {
		BookShopSpringJDBCTransactionImpl bookShop = new BookShopSpringJDBCTransactionImpl();
		bookShop.setDataSource(dataSource());
		bookShop.setManager(transactionManager());
		return bookShop;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(dataSource());
	return transactionManager;
	}

}
