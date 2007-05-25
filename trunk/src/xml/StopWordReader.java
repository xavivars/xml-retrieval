package xml;

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
public class StopWordReader extends XMLReader {

	/**
	 * 
	 */
	private StopWordMap stopWordMap;

	/**
	 * 
	 * @param fileName
	 */
	public StopWordReader(String fileName) {
		super(fileName);
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
						stopWordMap.put(stopWord.getValue(), stopWord);
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

}
