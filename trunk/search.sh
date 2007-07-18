ant compile
date > time.txt
java -Xmx400m -cp lib/xml-retrieval.jar Search $1 $2 $3 $4 $5
date >> time.txt