//Varsha Roopreddy and Anusha Praturu
import java.util.ArrayList;
import java.util.ListIterator;

import org.apache.hadoop.io.IntWritable; // Hadoop's serialized int wrapper class
import org.apache.hadoop.io.LongWritable; // Hadoop's serialized int wrapper class
import org.apache.hadoop.io.Text;        // Hadoop's serialized String wrapper class
import org.apache.hadoop.mapreduce.Mapper; // Mapper class to be extended by our Map function
import org.apache.hadoop.mapreduce.Reducer; // Reducer class to be extended by our Reduce function
import org.apache.hadoop.mapreduce.Job; // the MapReduce job class that is used a the driver
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat; // class for "pointing" at input file(s)
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat; // class for  standard text input
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs; // class for "pointing" at input file(s)
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat; // class for "pointing" at output file
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat; // key-value input files
import org.apache.hadoop.conf.Configuration; // Hadoop's configuration object


import org.apache.hadoop.fs.Path;                // Hadoop's implementation of directory path/filename


// Exception handling

import java.io.IOException;


public class LargestValue {


// Mapper  Class Template


public static class fileOneMapper     // Need to replace the four type labels there with actual Java class names
     extends Mapper<Object, Text, IntWritable, IntWritable> {

// @Override   // we are overriding Mapper's map() method

// map methods takes three input parameters
// first parameter: input key 
// second parameter: input value
// third parameter: container for emitting output key-value pairs

public void map(Object key, Text value, Context context)
      throws IOException, InterruptedException {
   String text[] = value.toString().split(",");
   int sum = 0;
   for(int i = 1; i <10; i+=2){
      if(Integer.parseInt(text[i])==1) {
         sum += 14;
      }
      else {
         sum += Integer.parseInt(text[i]);
      }
   }
   context.write(new IntWritable(1), new IntWritable(sum));
 } // map

}  // mapper class


public static class fileTwoMapper     // Need to replace the four type labels there with actual Java class names
     extends Mapper< Object, Text, IntWritable, IntWritable> {

// @Override   // we are overriding Mapper's map() method

// map methods takes three input parameters
// first parameter: input key 
// second parameter: input value
// third parameter: container for emitting output key-value pairs

public void map(Object key, Text value, Context context)
      throws IOException, InterruptedException {
   String text[] = value.toString().split(",");
   int sum = 0;
   for(int i = 1; i <10; i+=2){
      if(Integer.parseInt(text[i])==1) {
         sum += 14;
      }
      else {
         sum += Integer.parseInt(text[i]);
      }
   }
   context.write(new IntWritable(2), new IntWritable(sum));
 } // map




} // MyMapperClass


//  Reducer Class Template

public static class JoinReducer   // needs to replace the four type labels with actual Java class names
      extends  Reducer< IntWritable, IntWritable, Text, Text> {

 // note: InValueType is a type of a single value Reducer will work with
 //       the parameter to reduce() method will be Iterable<InValueType> - i.e. a list of these values

//@Override  // we are overriding the Reducer's reduce() method

// reduce takes three input parameters
// first parameter: input key
// second parameter: a list of values associated with the key
// third parameter: container  for emitting output key-value pairs

public void reduce( IntWritable key, Iterable<IntWritable> values, Context context)
     throws IOException, InterruptedException {
  int highestNumber = 0;
  int numInstances = 0;
  for (IntWritable val : values) {
     int num = val.get();
     if(num == highestNumber){
        numInstances++;
     }
     else if(num > highestNumber) {
        highestNumber = num;
        numInstances = 1;
     }
  }
  String outputKey = "File: " + key.toString();
  String outputValue = "Highest Value: " + Integer.toString(highestNumber)  + " Frequency: " + Integer.toString(numInstances);
  context.write(new Text(outputKey), new Text(outputValue));
 } // reduce


} // reducer


//  MapReduce Driver


  // we do everything here in main()
  public static void main(String[] args) throws Exception {

		// Step 0: let's  get configuration

      Configuration conf = new Configuration();
      //conset("mapreduce.input.keyvaluelinerecordreader.key.value.separator",","); // learn to process two-column CSV files.


     // step 1: get a new MapReduce Job object
     Job  job = Job.getInstance(conf);  //  job = new Job() is now deprecated
     
    // step 2: register the MapReduce class
      job.setJarByClass(LargestValue.class);  

   //  step 3:  Set Input and Output files
       MultipleInputs.addInputPath(job, new Path(args[0]),
                       TextInputFormat.class, fileOneMapper.class ); // put what you need as input file
       MultipleInputs.addInputPath(job, new Path(args[1]),
                       TextInputFormat.class, fileTwoMapper.class ); // put what you need as input file
       job.setMapOutputKeyClass(IntWritable.class);
       job.setMapOutputValueClass(IntWritable.class);

       FileOutputFormat.setOutputPath(job, new Path(args[2])); // put what you need as output file

   // step 4:  Register  reducer
      job.setReducerClass(JoinReducer.class);
  
   //  step 5: Set up output information
       job.setOutputKeyClass(Text.class); // specify the output class (what reduce() emits) for key
       job.setOutputValueClass(Text.class); // specify the output class (what reduce() emits) for value

   // step 6: Set up other job parameters at will
      job.setJobName("Largest Value");

   // step 7:  ?

   // step 8: profit
      System.exit(job.waitForCompletion(true) ? 0:1);


  } // main()


} // MyMapReduceDriver




