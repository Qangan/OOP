#!bash

mkdir -p tmp/classes
javac -d tmp/classes $(find app/src/main/java -name '*.java')
javadoc -d build/docs -sourcepath app/src/main/java -subpackages qangan
jar -cfe tmp/app.jar qangan.heapsort.Main -C tmp/classes .
java -jar tmp/app.jar

rm -rf tmp


