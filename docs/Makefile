VERSION=1.0

FILES=xml-retrieval.tex style.sty
all: xml-retrieval.pdf

xml-retrieval.pdf: $(FILES)
	latex xml-retrieval
	latex xml-retrieval
	latex xml-retrieval
	latex xml-retrieval
	dvipdfm xml-retrieval

dist: $(FILES) xml-retrieval.pdf Makefile
	rm -Rf xml-retrieval-documentation-es-$(VERSION)
	mkdir xml-retrieval-documentation-es-$(VERSION)
	cp $(FILES) xml-retrieval.pdf Makefile xml-retrieval-documentation-es-$(VERSION)
	tar cvf xml-retrieval-documentation-es-$(VERSION).tar xml-retrieval-documentation-es-$(VERSION)
	rm -Rf xml-retrieval-documentation-es-$(VERSION)
	gzip -9 -f xml-retrieval-documentation-es-$(VERSION).tar
	
