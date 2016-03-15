


// CSC 369: Distributed Computing
// Section 03
// Anusha Praturu & Varsha Roopreddy
// Lab 8
// March 13, 2016

// SeedNormKNN.java

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

public class SeedNormKNN {


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

   long byteOff = key.get(), line;
   
   //get line number
   if(byteOff == 43) {
      line = 2;
   } //if
   else if(byteOff >= 351 && byteOff <=439) {
      line = (long)((double)byteOff / 44.0) + 2;
   } //else if
   else if(byteOff <= 5015) {
      line = (long)((double)byteOff / 44.0) + 1;
   } //else if
   else {
      line = (long)((double)byteOff / 44.0);
   } //else
   
   //to each seed, emit this seed's info and its id
   for(int i = 1; i < 211; i++) { 
     context.write(new LongWritable(i), new Text(line + "," + value.toString()));
   } //for

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

  String outStr = "", value = ""; 
  HashMap<Long, Vector> vectors = new HashMap<Long, Vector>();
  Vector<Double> thisVec = new Vector<Double>(), otherVec;
  String[] vecVals, tempVals;
  long line, temp;
  ArrayList<Pair> pairs = new ArrayList<Pair>();
  Pair p;
  Iterator it;
  ArrayList<ArrayList<Double>> dims = new ArrayList<ArrayList<Double>>();
  Double dimVal;

  for(int i = 0; i < 7; i++) { //initialize dimension holders for normalization
     dims.add(new ArrayList<Double>());
  }
  for (Text val : values) {
    value = val.toString();
    tempVals = value.split(",");
    line = Long.parseLong(tempVals[0]); //get line number of current seed vector
    value = tempVals[1]; //get the actual vector
    vecVals = value.split("\\s+");
    if(key.get() == line) { //if this is the CURRENT vector aka same as key
      for(int i = 0; i < vecVals.length - 1; i++) { //create vector object from text
         dimVal = new Double(vecVals[i]);
         dims.get(i).add(dimVal);
         thisVec.add(dimVal.doubleValue());
      } //for
      vectors.put(new Long(line), thisVec); //add to hash map of vectors
    } //if
    else {
      otherVec = new Vector<Double>();
      for(int i = 0; i < vecVals.length - 1; i++) { //create vector object
         dimVal = new Double(vecVals[i]);
         dims.get(i).add(dimVal);
         otherVec.add(dimVal.doubleValue());
      } //for
      vectors.put(new Long(line), otherVec); //add to map of vectors
    } //else
  } // for
  
  vectors = normalize(vectors, dims); //normalize the vectors in the map
  it = vectors.entrySet().iterator();
  while (it.hasNext()) { //iterate through all vectors
      Map.Entry pair = (Map.Entry)it.next();
      temp = ((Long)pair.getKey()).longValue();
      if(temp != key.get()) { //do not add if it is the current vector (do not compare to self)
        pairs.add(new Pair(temp, dist(((Vector<Double>)(pair.getValue())), //calculate dist between current vector
          thisVec)));                                                      // and THIS vector and add to list as a pair
      }                                                                    // that contains (id, distToThisVector)
      it.remove();
  } //while
  Collections.sort(pairs);  //sort list by distance to current vector
  outStr = pairs.get(0).toString() + ", " + pairs.get(1).toString() + ", " //output first 5 elements in sorted list
   + pairs.get(2).toString() + ", " + pairs.get(3).toString() + ", " 
   + pairs.get(4).toString();
  // emit final output
  context.write(key, new Text(outStr)); 


 } // reduce

 private HashMap<Long, Vector> normalize(HashMap<Long, Vector> vectors, ArrayList<ArrayList<Double>> dims) {
   ArrayList<Double> mins = new ArrayList<Double>();
   ArrayList<Double> maxes = new ArrayList<Double>();
   ArrayList<Double> avgs = new ArrayList<Double>();
   ArrayList<Double> current;
   double temp, min, max, sum;
   Iterator it;
   HashMap<Long, Vector> newVecs = new HashMap<Long, Vector>();
   long line;
   Vector<Double> v;

   for(int i = 0; i < dims.size(); i++) { //calculate the min, max, and average for each dimension
      current = dims.get(i);
      min = current.get(0).doubleValue();
      max = current.get(0).doubleValue();
      sum = current.get(0).doubleValue();
      for(int j = 0; j < current.size(); j++) {
         temp = current.get(j);
         min = temp < min ? temp : min;
         max = temp > max ? temp : max;
         sum += temp;
      } //for
      mins.add(min);
      maxes.add(max);
      avgs.add(sum / ((double)current.size()));
   } //for

   it = vectors.entrySet().iterator(); 
   while (it.hasNext()) { //iterate through vectors
       Map.Entry pair = (Map.Entry)it.next();
       v = ((Vector<Double>)pair.getValue());
       for(int i = 0; i < v.size(); i++) {
         v.set(i, ((v.get(i).doubleValue() - avgs.get(i).doubleValue()) //set the vector dimension to
          / (maxes.get(i).doubleValue() - mins.get(i).doubleValue()))); // the normalized value
       } //for
       newVecs.put((Long)pair.getKey(), v); //add updated vector to new hash map
       it.remove();
   } //while
   return newVecs; //return hash map of updated (normalized) vectors
 } //normalize

 private double dist(Vector<Double> v1, Vector<Double> v2) { //calculated euclidean distance between 2 vectors
    double sum = 0;
    for(int i = 0; i < v1.size(); i++) { //for each dimension
      sum += Math.pow((v1.get(i).doubleValue() - v2.get(i).doubleValue()), 2); //square of difference in values
    }
    return Math.sqrt(sum); //return euclidean distance
 } //dist

 private class Pair implements Comparable<Pair> { //class to hold a seed id and its distance to a certain CURRENT seed
    private long id; //seed id (line number)
    private double dist; //euclidean distance to current seed
    
    public Pair(long i, double d) { //constructor
      id = i;
      dist = d;
    }
    public int compareTo(Pair other) { //needs to implement compareTo so we can sort the list
      return (new Double(this.dist)).compareTo(new Double(other.dist)); //uses Double's compareTo
    }
    public String toString() { //returns string with both elements in form "(id, dist)"
      return "(" + id + ", " + dist + ")";
    }
 } //Pair

} // reducer


//  MapReduce Driver


  // we do everything here in main()
  public static void main(String[] args) throws Exception {

     // step 1: get a new MapReduce Job object
     Job  job = Job.getInstance();  //  job = new Job() is now deprecated
     
    // step 2: register the MapReduce class
      job.setJarByClass(SeedNormKNN.class);  

   //  step 3:  Set Input and Output files
       //FileInputFormat.addInputPath(job, new Path("/datasets/seeds_dataset.txt")); // put what you need as input file
       FileInputFormat.addInputPath(job, new Path(args[0])); // put what you need as input file
       FileOutputFormat.setOutputPath(job, new Path(args[1])); // put what you need as output file

   // step 4:  Register mapper and reducer
      job.setMapperClass(SeedMapper.class);
      job.setReducerClass(SeedReducer.class);
  
   //  step 5: Set up output information
       job.setOutputKeyClass(LongWritable.class); // specify the output class (what reduce() emits) for key
       job.setOutputValueClass(Text.class); // specify the output class (what reduce() emits) for value

   // step 6: Set up other job parameters at will
      job.setJobName("seeds normalized");

   // step 7:  ?

   // step 8: profit
      System.exit(job.waitForCompletion(true) ? 0:1);


  } // main()


} // MyMapReduceDriver




