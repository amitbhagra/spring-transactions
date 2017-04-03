package com.testspring.bookshop;

public interface BookShop {
	public void purchase(String isbn, String userName);
	public void increaseStock(String isbn, int count);
	public int checkStock(String isbn);
	public String getBookByISBN(String isbn);
}
