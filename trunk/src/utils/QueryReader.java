package utils;

import java.util.HashMap;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class QueryReader extends XMLReader {

	private Query query;
	private StopWordMap stopWords;
	
	public QueryReader(String fileName, StopWordMap swm) {
		super(fileName);
		query = new Query ();
		stopWords = swm;
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param e
	 * @param tagName
	 * @return
	 */
	protected Query readQuery(final Element e, final String path) {
		boolean exit = false;
		final HashMap<String, Position> positions = new HashMap<String, Position>();		
		if (e.hasChildNodes()) {
			final NodeList children = e.getChildNodes();
			for (int i = 0; i < children.getLength() && !exit; i++) {
				final Node child = children.item(i);
				if (child instanceof Element) {
					// System.out.println("Found: <" + child.getNodeName() +
					// ">");
					final String nodeName = child.getNodeName();
					Position pos = null;
					if (!positions.containsKey(nodeName)) {
						positions.put(nodeName, new Position());
						pos = positions.get(nodeName);
					} else {
						pos = positions.get(nodeName);
						pos.next();
					}

					final Element childElement = (Element) child;
					readQuery(childElement, path + "/"
							+ child.getNodeName() + "[" + (pos.getPos()) + "]");
				}
				if (child instanceof Text &&
					child.getParentNode().getNodeName().compareTo("title") == 0) {
					final Text textElement = (Text) child;
					final String text = textElement.getData();
					final String [] words = text.split("\\s+");
					for(String word: words) {
						word = word.toLowerCase();
						if(!word.isEmpty() && !stopWords.containsKey(word)) {
							query.add(new SimpleWord(word));
						}
//						System.out.println(word);
						
					}
					exit = true;
				}	
			}
		}
		return query;
	}
	
	/**
	 * 
	 * @return
	 */
	public final Query readDocument() {
		try {
		//System.out.println("Reading document '" + getFileName() + "'");
		final Element root = getDocument().getDocumentElement();
		final String path = "/article[1]";
		readQuery(root,path);
		return query;
		} catch( NullPointerException npe) {
			npe.printStackTrace();
		}
		return null;
	}
	
}
