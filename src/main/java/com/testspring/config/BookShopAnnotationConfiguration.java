package com.testspring.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mysql.jdbc.Driver;
import com.testspring.bookshop.BookShop;
import com.testspring.bookshop.Cashier;
import com.testspring.bookshop.impl.BookShopSpringJDBCTransactionAnnotationImpl;
import com.testspring.bookshop.impl.CashierSpringJDBCTransactionalAnnotationImpl;

@Configuration
@EnableTransactionManagement
public class BookShopAnnotationConfiguration {

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(Driver.class.getName());
		dataSource.setUrl("jdbc:mysql://localhost/stm");
		dataSource.setUsername("root");
		dataSource.setPassword("Welcome@1");
		return dataSource;
	}

	@Bean
	public BookShop bookShopSpringAnnotationTM() {
		BookShopSpringJDBCTransactionAnnotationImpl bookShop = new BookShopSpringJDBCTransactionAnnotationImpl();
		bookShop.setDataSource(dataSource());
		return bookShop;
	}
	
	@Bean
	public Cashier cashierSpringAnnotaionTM() {
		CashierSpringJDBCTransactionalAnnotationImpl cashier = new CashierSpringJDBCTransactionalAnnotationImpl();
		cashier.setBookShop(bookShopSpringAnnotationTM());
		return cashier;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(dataSource());
	return transactionManager;
	}

}
