package utils;

public class WordFrequency extends Word implements Comparable < WordFrequency>{
	
	/**
	 * 
	 */
	private Integer frequency;
	
	
	public WordFrequency (final String value, final Integer frequency) {
		super(value);
		setFrequency(frequency);
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public int compareTo(WordFrequency arg0) {
		// TODO Auto-generated method stub
		
		if (arg0.getFrequency() < getFrequency()) {
			return 1;
		}
		else if (arg0.getFrequency() > getFrequency()) {
			return -1;
		}
		else {		
			return 0;
		}
	}
}
