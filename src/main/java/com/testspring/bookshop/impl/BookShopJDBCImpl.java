package com.testspring.bookshop.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.testspring.bookshop.BookShop;

public class BookShopJDBCImpl implements BookShop {

	private DataSource dataSource;
	 
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void purchase(String isbn, String userName) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			PreparedStatement stmt = connection.prepareStatement("select PRICE from BOOK where ISBN = ?");
			stmt.setString(1, isbn);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			int price = rs.getInt("PRICE");
			stmt.close();
			
			
			PreparedStatement stmt2 = connection.prepareStatement("update BOOK_STOCK set STOCK = STOCK - 1 where ISBN = ?");
			stmt2.setString(1, isbn);
			stmt2.executeUpdate();
			stmt2.close();
			
			PreparedStatement stmt3 = connection.prepareStatement("update ACCOUNT set BALANCE = BALANCE - ? where USERNAME = ?");
			stmt3.setInt(1, price);
			stmt3.setString(2, userName);
			stmt3.executeUpdate();
			stmt3.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
		}

	}

	public String getBookByISBN(String isbn) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			PreparedStatement stmt = connection.prepareStatement("select BOOK_NAME from BOOK where ISBN = ?");
			stmt.setString(1, isbn);
			ResultSet rs = stmt.executeQuery();
			String name = "";
			if (rs.next()) {
				name = rs.getString("BOOK_NAME");
			}
			return name;
			
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	public void increaseStock(String isbn, int count) {
		// TODO Auto-generated method stub
		
	}

	public int checkStock(String isbn) {
		// TODO Auto-generated method stub
		return 0;
	}

}
