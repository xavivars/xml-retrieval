package utils;

/**
 * 
 * @author ebenimeli
 * 
 */
public class SimpleWord {

	/**
	 * 
	 */
	protected String value;

	/**
	 * 
	 * 
	 */
	public SimpleWord() {

	}

	/**
	 * 
	 * @param value
	 */
	public SimpleWord(final String value) {
		setValue(value);
	}

	/**
	 * @return the value
	 */
	protected String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	protected void setValue(String value) {
		this.value = value;
	}

	/**
	 * 
	 * 
	 */
	public void printInfo() {
		System.out.println("Word:      '" + getValue() + "'");
	}

}
