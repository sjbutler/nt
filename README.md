# nt

nt is a Java library for part of speech tagging of identifier names.

## Copyright and Licence
nt is Copyright (C) Simon Butler 2017 and is open source software released
under the terms of version 2 of the Apache Licence.

## Use
Using nt is straightforward. Simply instantiate the tagger or parser you need
and use it to process tokenised identifier names where the tokens are provided
as a list. If you need a name tokeniser, intt (https://github.com/sjbutler/intt)
is available.

## Requirements
### Java
nt requires Java 8

### Dependencies
nt depends on:
* Apache OpenNLP v1.7.2
* SLF4J v1.7.24 When using nt read the instructions at https://www.slf4j.org/manual.html to integrate nt with your chosen logging system.

## Build
nt comes with a Gradle configuration file. Most IDEs will recognise nt as a
Gradle project and, at worst, will require a Gradle plugin to be installed.

## Cite
nt provides an open source implementation of the class and reference name
PoS taggers and parsers used in research reported in the following papers:

* Butler, S.; Wermelinger, M.; Yu, Y. & Sharp, H. Mining Java Class Naming Conventions Proc. of the 27th IEEE Int'l Conf. on Software Maintenance, IEEE, 2011, 93-102
* Butler, S.; Wermelinger, M. & Yu, Y. A Survey of the Forms of Java Reference Names Proc. of the 23rd Int'l Conf. on Program Comprehension, IEEE, 2015, 196-206
* Butler, S.; Wermelinger, M. & Yu, Y. Investigating Naming Convention Adherence in Java References Proc. of the 31st Int'l Conf. on Software Maintenance and Evolution, IEEE, 2015, 41-50

Please cite both 2015 papers where you have used nt to support your research.

## Caveat
nt is research software the code and the API are unstable and there is no guarantee
the library will be maintained.
