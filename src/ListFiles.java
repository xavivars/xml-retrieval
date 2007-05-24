import utils.Query;
import utils.QueryReader;

public class ListFiles {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Query query;
		
		QueryReader qr = new QueryReader(args[0]);
		
		qr.analize();
		query = qr.readDocument();
		query.print();
		
		
		/*String newToken;
		String[] temp;
		String finalPunct = "[\\.,?!;:\\]\\)\\}]";
		String beginningPunct = "[\\(\\[\\{]";
		String [] test = {"h63la","adios9","634,","h.la","coma,","(parentesis,",","};
		
		for(String current: test) {
			newToken = current.toLowerCase();
			if (newToken.matches(beginningPunct + "\\w+") || newToken.matches("\\w+" + finalPunct) ||
					newToken.matches(beginningPunct + "\\w+" + finalPunct)) {
				temp = newToken.split("\\W");
				for (String t: temp) {
					if(t.compareTo(" ") != 0 && t.compareTo("") != 0) {
						newToken = t;
					}
				}
			}
			else if (!newToken.matches("\\d+") && !newToken.matches("[\\w&&[^\\d]]+")) {
				newToken = null;
			}
			
			System.out.println(newToken);
		}*/
	}
}


