


// CSC 369: Distributed Computing
// Section 03
// Anusha Praturu & Varsha Roopreddy
// Lab 8
// March 13, 2016

// SeedKNN.java

// Section 1: Imports


                  // Data containers for Map() and Reduce() functions

                  // You would import the data types needed for your keys and values
import org.apache.hadoop.io.IntWritable; // Hadoop's serialized int wrapper class
import org.apache.hadoop.io.LongWritable; // Hadoop's serialized int wrapper class
import org.apache.hadoop.io.Text;        // Hadoop's serialized String wrapper class


                 // For Map and Reduce jobs

import org.apache.hadoop.mapreduce.Mapper; // Mapper class to be extended by our Map function
import org.apache.hadoop.mapreduce.Reducer; // Reducer class to be extended by our Reduce function

                 // To start the MapReduce process

import org.apache.hadoop.mapreduce.Job; // the MapReduce job class that is used a the driver


                // For File "I/O"

import org.apache.hadoop.mapreduce.lib.input.FileInputFormat; // class for "pointing" at input file(s)
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat; // class for "pointing" at output file
import org.apache.hadoop.fs.Path;                // Hadoop's implementation of directory path/filename


// Exception handling

import java.io.IOException;

import java.util.Vector;
import java.lang.Double;
import java.lang.Long;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Collections;
import java.lang.Math;
import java.lang.Comparable;

public class SeedKNN {


// Mapper  Class Template


public static class SeedMapper     // Need to replace the four type labels there with actual Java class names
     extends Mapper< LongWritable, Text, LongWritable, Text > {

// @Override   // we are overriding Mapper's map() method

// map methods takes three input parameters
// first parameter: input key 
// second parameter: input value
// third parameter: container for emitting output key-value pairs

public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {

   //String vals[] = value.toString().split("\\s+");
   
/*
   String s = newKey.toLowerCase();
   Text outKey, outVal;
   for(int i = 1; i < s.length(); i++){
      if(s.charAt(i - 1) == s.charAt(i)) {
         outKey = new Text(newKey);
         outVal = new Text(String.valueOf(s.charAt(i)));
         context.write(outKey, outVal);
         break;
      }
   }
   */
   long byteOff = key.get(), line;
   if(byteOff == 43) {
      line = 2;
   }
   else if(byteOff >= 351 && byteOff <=439) {
      line = (long)((double)byteOff / 44.0) + 2;
   }
   else if(byteOff <= 5015) {
      line = (long)((double)byteOff / 44.0) + 1;
   }
   else {
      line = (long)((double)byteOff / 44.0);
   }

   for(int i = 1; i < 211; i++) {
      //if(i == line) {
         context.write(new LongWritable(i), new Text(line + "," + value.toString()));
      //}
      //else {
         //context.write(new LongWritable(i), value);
      //}
   }

 } // map


} // MyMapperClass


//  Reducer Class Template

public static class SeedReducer   // needs to replace the four type labels with actual Java class names
      extends  Reducer< LongWritable, Text, LongWritable, Text> {

 // note: InValueType is a type of a single value Reducer will work with
 //       the parameter to reduce() method will be Iterable<InValueType> - i.e. a list of these values

@Override  // we are overriding the Reducer's reduce() method

// reduce takes three input parameters
// first parameter: input key
// second parameter: a list of values associated with the key
// third parameter: container  for emitting output key-value pairs

public void reduce( LongWritable key, Iterable<Text> values, Context context)
     throws IOException, InterruptedException {

  String value = ""; 
  HashMap<Long, Vector> vectors = new HashMap<Long, Vector>();
  Vector<Double> thisVec = new Vector<Double>(), otherVec;
  String[] vecVals, tempVals;
  long line, temp;
  ArrayList<Pair> pairs = new ArrayList<Pair>();
  Pair p;
  Iterator it;
  String outStr = "";
  for (Text val : values) {
    value = val.toString();
    tempVals = value.split(",");
    line = Long.parseLong(tempVals[0]);
    value = tempVals[1];
    vecVals = value.split("\\s+");
    if(key.get() == line) {
      for(int i = 0; i < vecVals.length - 1; i++) {
         thisVec.add(Double.parseDouble(vecVals[i]));
      } //for
      vectors.put(new Long(line), thisVec);
    } //if
    else {
      otherVec = new Vector<Double>();
      for(int i = 0; i < vecVals.length - 1; i++) {
         otherVec.add(Double.parseDouble(vecVals[i]));
      } //for
      vectors.put(new Long(line), otherVec);
    } //else
  } // for
  
  it = vectors.entrySet().iterator();
  while (it.hasNext()) {
      Map.Entry pair = (Map.Entry)it.next();
      temp = ((Long)pair.getKey()).longValue();
      if(temp != key.get()) {
        pairs.add(new Pair(temp, dist(((Vector<Double>)(pair.getValue())), 
          thisVec)));
      }
      it.remove(); // avoids a ConcurrentModificationException
  } //while
  Collections.sort(pairs);
  outStr = pairs.get(0).toString() + ", " + pairs.get(1).toString() + ", " 
   + pairs.get(2).toString() + ", " + pairs.get(3).toString() + ", " 
   + pairs.get(4).toString();
 /* 
  for(int i = 1; i < 211; i++) {
    if(((long)i) != key.get()) {
      p = new Pair(i, dist(vectors.get(i), thisVec));
      pairs.add(p);
    } //if
  } //for*/
  // emit final output
  context.write(key, new Text(outStr)); 


 } // reduce

 private double dist(Vector<Double> v1, Vector<Double> v2) {
    double sum = 0;
    for(int i = 0; i < v1.size(); i++) {
      sum += Math.pow((v1.get(i).doubleValue() - v2.get(i).doubleValue()), 2);
    }
    return Math.sqrt(sum);
 }

 private class Pair implements Comparable<Pair> {
    private long id;
    private double dist;
    
    public Pair(long i, double d) {
      id = i;
      dist = d;
    }
    public double getDist() {
      return dist;
    }
    public int compareTo(Pair other) {
      return (new Double(this.dist)).compareTo(new Double(other.dist));
    }
    public String toString() {
      return "(" + id + ", " + dist + ")";
    }
 }

} // reducer


//  MapReduce Driver


  // we do everything here in main()
  public static void main(String[] args) throws Exception {

     // step 1: get a new MapReduce Job object
     Job  job = Job.getInstance();  //  job = new Job() is now deprecated
     
    // step 2: register the MapReduce class
      job.setJarByClass(SeedKNN.class);  

   //  step 3:  Set Input and Output files
       //FileInputFormat.addInputPath(job, new Path("/datasets/seeds_dataset.txt")); // put what you need as input file
       FileInputFormat.addInputPath(job, new Path("/datasets/seeds_dataset.txt")); // put what you need as input file
       FileOutputFormat.setOutputPath(job, new Path("test/seedout")); // put what you need as output file

   // step 4:  Register mapper and reducer
      job.setMapperClass(SeedMapper.class);
      job.setReducerClass(SeedReducer.class);
  
   //  step 5: Set up output information
       job.setOutputKeyClass(LongWritable.class); // specify the output class (what reduce() emits) for key
       job.setOutputValueClass(Text.class); // specify the output class (what reduce() emits) for value

   // step 6: Set up other job parameters at will
      job.setJobName("seeds");

   // step 7:  ?

   // step 8: profit
      System.exit(job.waitForCompletion(true) ? 0:1);


  } // main()


} // MyMapReduceDriver




