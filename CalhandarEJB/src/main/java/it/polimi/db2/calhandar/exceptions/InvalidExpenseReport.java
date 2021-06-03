package it.polimi.db2.calhandar.exceptions;

public class InvalidExpenseReport extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidExpenseReport(String message) {
		super(message);
	}
}
