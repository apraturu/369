all50: summaries.java
	hadoop fs -rm labs/lab7/output/*
	hadoop fs -rmdir labs/lab7/output
	rm *.class
	javac -cp hadoop-core-1.2.1.jar:json-mapreduce-1.0.jar:org.json-20120521.jar  summaries.java 
	jar cvf summaries.jar *.class
	hadoop jar summaries.jar summaries -libjars ./json-mapreduce-1.0.jar,./org.json-20120521.jar labs/lab7/bf50.json labs/lab7/output

all500: summaries.java
	hadoop fs -rm labs/lab7/output/*
	hadoop fs -rmdir labs/lab7/output
	rm *.class
	javac -cp hadoop-core-1.2.1.jar:json-mapreduce-1.0.jar:org.json-20120521.jar  summaries.java 
	jar cvf summaries.jar *.class
	hadoop jar summaries.jar summaries -libjars ./json-mapreduce-1.0.jar,./org.json-20120521.jar labs/lab7/bf500.json labs/lab7/output

all5000: summaries.java
	hadoop fs -rm labs/lab7/output/*
	hadoop fs -rmdir labs/lab7/output
	rm *.class
	javac -cp hadoop-core-1.2.1.jar:json-mapreduce-1.0.jar:org.json-20120521.jar  summaries.java 
	jar cvf summaries.jar *.class
	hadoop jar summaries.jar summaries -libjars ./json-mapreduce-1.0.jar,./org.json-20120521.jar labs/lab7/bf5000.json labs/lab7/output

output: 
	hadoop fs -cat labs/lab7/output/part-r-00000

run50: summaries.jar
	hadoop jar summaries.jar summaries -libjars ./json-mapreduce-1.0.jar,./org.json-20120521.jar labs/lab7/bf50.json labs/lab7/output

run500: summaries.jar
	hadoop jar summaries.jar summaries -libjars ./json-mapreduce-1.0.jar,./org.json-20120521.jar labs/lab7/bf500.json labs/lab7/output

run5000: summaries.jar
	hadoop jar summaries.jar summaries -libjars ./json-mapreduce-1.0.jar,./org.json-20120521.jar labs/lab7/bf5000.json labs/lab7/output

compile: summaries.java
	rm *.class
	javac -cp hadoop-core-1.2.1.jar:json-mapreduce-1.0.jar:org.json-20120521.jar  summaries.java 
	jar cvf summaries.jar *.class

clean:
	hadoop fs -rm labs/lab7/output/*
	hadoop fs -rmdir labs/lab7/output
