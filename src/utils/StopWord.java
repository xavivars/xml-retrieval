package utils;

/**
 * 
 * @author ebenimeli
 * 
 */
public class StopWord extends SimpleWord {

	/**
	 * 
	 */
	private Integer frequency;

	/**
	 * 
	 * 
	 */
	public StopWord() {
		super();
	}

	/**
	 * 
	 * @param value
	 */
	public StopWord(String value) {
		super(value);
	}

	/**
	 * @return the frequency
	 */
	public final Integer getFrequency() {
		return frequency;
	}

	/**
	 * @param frequency
	 *            the frequency to set
	 */
	public final void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

}
