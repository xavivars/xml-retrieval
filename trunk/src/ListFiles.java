public class ListFiles {

	public void method() {
		String hola;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String newToken;
		String[] temp;
		String [] test = {"h6la","adiós","634,","h.la","coma,",",parentesis,",","};
		
		for(String current: test) {
			
			newToken = current.toLowerCase();
			if ((newToken.matches("\\w+[\\W\\d]+\\w+") || newToken.matches("\\W+")) && !newToken.matches("\\d+")) {
				newToken = null;
			}
			else {
				temp = newToken.split("\\W");
				for (String t: temp) {
					if(t.compareTo(" ") != 0 && t.compareTo("") != 0) {
						newToken = t;
					}
				}
			}
			System.out.println(newToken);
			
				
			
		/*	if (newToken.length() == 1) {
				if (newToken.matches("\\W")) {
					newToken = null;
				}
			} else {
				temp = newToken.split("\\W");
				if (temp.length == 1) {
					newToken = temp[0];
				}
			}
	*/
		}
	}
}


