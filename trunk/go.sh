ant compile
#java -Xmx400m -jar lib/xml-retrieval.jar inex-1.4/xml/an stop-words-ok.xml
java -cp lib/xml-retrieval.jar Search stop-words-final.xml root_index.xml topics/prueba.xml
