
## 
# Copyright (c) 2014, dark_neo
# All rights reserved.
#
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions are met:
#
# 1. Redistributions of source code must retain the above copyright notice,
#    this list of conditions and the following disclaimer.
#
# 2. Redistributions in binary form must reproduce the above copyright notice,
#    this list of conditions and the following disclaimer in the documentation
#    and/or other materials provided with the distribution.
#
# 3. Neither the name of the copyright holder nor the names of its contributors
#    may be used to endorse or promote products derived from this software
#    without specific prior written permission.
#
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
# AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
# IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
# ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
# LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
# CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
# SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
# INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
# CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
# ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
# POSSIBILITY OF SUCH DAMAGE.
##

# Java development

PROJECT_NAME	= SQLite test
JC		= javac
JCFLAGS		= -g
JARLIBS		= lib/sqlite-jdbc-3.8.6.jar
JARCOM		= jar
SRCS		= sch/sqlite_jdbc.java \
			sch/main.java
DELCOM		= rm -rf
VERSION		= 1.0.0
MAINCLASS	= sch.main
JARNAME		= sqlite-test

default: help

help:
	@echo
	@echo "	$(PROJECT_NAME)"
	@echo
	@echo " Sergio Cruz"
	@echo " Copyright (c) 2014. All rights reserved."
	@echo
	@echo
	@echo " -- BUILD OPTIONS --"
	@echo
	@echo " help:		show this text and finish"
	@echo " run:		run program from project-JAR file"
	@echo " all: 		compile sources and create a project-JAR file"
	@echo " build:		compile sources but do not create project-JAR"
	@echo "			file."
	@echo " jar:		create project-JAR file from compile sources"
	@echo " clean:		delete classes files"
	@echo " distclean:	delete classes and project-JAR files"
	@echo " jarclean:	delete project-JAR file"
	@echo

all: distclean build jar
	@echo
	@echo
	@echo "Program version: $(VERSION)"
	@echo

# With `-cp` we tell to compiler the external JARs to search.
run:
	java -classpath "$(JARLIBS)": -jar $(JARNAME)-$(VERSION).jar

build: clean
	$(JC) $(JCFLAGS) -classpath "$(JARLIBS)": $(SRCS)

#jar cvfm $(JARNAME)-$(VERSION).jar Manifest.mf */*.class
jar:
	echo "Main-Class: $(MAINCLASS)" > Manifest.mf
	echo "Class-Path: $(JARLIBS)" >> Manifest.mf
	jar cvfm $(JARNAME)-$(VERSION).jar Manifest.mf */*.class

clean:
	$(DELCOM) */*.class

distclean: clean
	$(DELCOM) $(JARNAME)-$(VERSION).jar Manifest.mf

jarclean:
	$(DELCOM) $(JARNAME)-$(VERSION).jar

