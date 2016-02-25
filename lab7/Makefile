all: MultilineJsonJob.java
	hadoop fs -rm labs/lab7/output/*
	hadoop fs -rmdir labs/lab7/output
	rm *.class
	javac -cp hadoop-core-1.2.1.jar:json-mapreduce-1.0.jar:org.json-20120521.jar  MultilineJsonJob.java 
	jar cvf MultilineJsonJob.jar *.class
	hadoop jar MultilineJsonJob.jar MultilineJsonJob -libjars ./json-mapreduce-1.0.jar,./org.json-20120521.jar labs/lab7/test.json labs/lab7/output

output: 
	hadoop fs -cat labs/lab7/output/part-r-00000

run: MultilineJsonJob.jar
	hadoop jar MultilineJsonJob.jar MultilineJsonJob -libjars ./json-mapreduce-1.0.jar,./org.json-20120521.jar labs/lab7/test.json labs/lab7/output

compile: MultilineJsonJob.java
	rm *.class
	javac -cp hadoop-core-1.2.1.jar:json-mapreduce-1.0.jar:org.json-20120521.jar  MultilineJsonJob.java 
	jar cvf MultilineJsonJob.jar *.class

clean:
	hadoop fs -rm labs/lab7/output/*
	hadoop fs -rmdir labs/lab7/output
