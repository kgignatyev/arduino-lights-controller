ARDUINO_LIBS=/Applications/Arduino.app/Contents/Resources/Java
JLAYER=$HOME/java/lib/JLayer1.0.1/jl1.0.1.jar

VER=2.0

mvn install:install-file -Dfile=$ARDUINO_LIBS/core.jar -DgroupId=arduino -DartifactId=core -Dversion=$VER -Dpackaging=jar
mvn install:install-file -Dfile=$ARDUINO_LIBS/ecj.jar -DgroupId=arduino -DartifactId=ecj -Dversion=$VER -Dpackaging=jar
mvn install:install-file -Dfile=$ARDUINO_LIBS/jna.jar -DgroupId=arduino -DartifactId=jna -Dversion=$VER -Dpackaging=jar
mvn install:install-file -Dfile=$ARDUINO_LIBS/RXTXcomm.jar -DgroupId=arduino -DartifactId=rxtx -Dversion=$VER -Dpackaging=jar

mvn install:install-file -Dfile=$JLAYER -DgroupId=jlayer -DartifactId=jlayer -Dversion=1.0.1 -Dpackaging=jar