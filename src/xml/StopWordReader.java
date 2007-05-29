package xml;

import java.util.HashMap;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import utils.StopWord;
import utils.StopWordMap;

/**
 * 
 * @author ebenimeli
 * 
 */
public class StopWordReader extends DOMReader {

	/**
	 * 
	 */
	private StopWordMap stopWordMap;
	
	/**
	 * 
	 */
	private int bottomLimit;
	
	/**
	 * 
	 */
	private int topLimit;
	
	/**
	 * 
	 */
	private HashMap<String,Integer> removeSize;

	/**
	 * 
	 * @param fileName
	 */
	public StopWordReader(String fileName) {
		super(fileName);
		removeSize = new HashMap<String,Integer>();
		this.setBottomLimit(0);
		this.setTopLimit(Integer.MAX_VALUE);
	}

	/**
	 * 
	 * @return
	 */
	public final StopWordMap readDocument() {
		analize();
		try {
			final Element root = getDocument().getDocumentElement();
			readChildren(root);
		} catch (final NullPointerException npe) {
			npe.printStackTrace();
		}
		return stopWordMap;
	}

	/**
	 * 
	 * @param e
	 */
	final private void readChildren(final Element e) {
		stopWordMap = new StopWordMap();

		if (e.hasChildNodes()) {
			final NodeList children = e.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				final Node child = children.item(i);
				if (child instanceof Element) {
					final String nodeName = child.getNodeName();
					if (nodeName.equals("word")) {
						final Element childElement = (Element) child;
						final StopWord stopWord = readStopWord(childElement);
						int f = stopWord.getFrequency();
						if (!removeSize.containsKey(stopWord.getFrequency().toString())) {
						if (f >= getBottomLimit() && f <= getTopLimit()) {
							stopWordMap.put(stopWord.getValue(), stopWord);
						}
						}
					}
				}
				if (child instanceof Text) {
					final Text textElement = (Text) child;
					final String text = textElement.getData();
				}
			}
		}
	}

	/**
	 * 
	 * @param e
	 */
	final private StopWord readStopWord(Element e) {
		StopWord stopWord = null;
		if (e.hasChildNodes()) {
			final NodeList children = e.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				final Node child = children.item(i);
				if (child instanceof Text) {
					stopWord = new StopWord();
					final String sFreq = getAttributeValue(e, "f");
					final Integer iFreq = new Integer(sFreq);
					stopWord.setFrequency(iFreq);

					final Text textElement = (Text) child;
					final String text = textElement.getData();
					stopWord.setValue(text);
				}
			}
		}
		return stopWord;

	}

	/**
	 * @return the stopWordMap
	 */
	@Override
	public final StopWordMap getStopWordMap() {
		return stopWordMap;
	}

	/**
	 * @param stopWordMap
	 *            the stopWordMap to set
	 */
	@Override
	public final void setStopWordMap(StopWordMap stopWordMap) {
		this.stopWordMap = stopWordMap;
	}

	/**
	 * @return the bottomLimit
	 */
	public final int getBottomLimit() {
		return bottomLimit;
	}

	/**
	 * @param bottomLimit the bottomLimit to set
	 */
	public final void setBottomLimit(int bottomLimit) {
		this.bottomLimit = bottomLimit;
	}

	/**
	 * @return the topLimit
	 */
	public final int getTopLimit() {
		return topLimit;
	}

	/**
	 * @param topLimit the topLimit to set
	 */
	public final void setTopLimit(int topLimit) {
		this.topLimit = topLimit;
	}
	
	/**
	 * 
	 * @param size
	 */
	public final void removeSize(Integer size) {
		if (!removeSize.containsKey(size.toString())) {
			removeSize.put(size.toString(), size);
		}
	}

}
