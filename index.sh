ant compile
date > time.txt
#java -Xmx400m -jar lib/xml-retrieval.jar xml-orig
#java -jar lib/xml-retrieval.jar xml-orig
date >> time.txt
java -Xmx400m -cp lib/xml-retrieval.jar Index $1 $2 $3 $4
#java -cp lib/xml-retrieval.jar Search stop-words-final.xml root_index.xml topics/prueba.xml
