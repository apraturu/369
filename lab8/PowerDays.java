//Varsha Roopreddy and Anusha Praturu
// Section 1: Imports
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.io.IntWritable; 
import org.apache.hadoop.io.LongWritable; 
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;        
import org.apache.hadoop.mapreduce.Mapper; 
import org.apache.hadoop.mapreduce.Reducer; 
import org.apache.hadoop.mapreduce.Job; 
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat; 
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.fs.Path;                
import java.io.IOException;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.json.JSONObject;
import java.lang.*;
import java.util.*;

/*
Date;Time;Global_active_power;Global_reactive_power;Voltage;Global_intensity;Sub_met
ering_1;Sub_metering_2;Sub_metering_3
16/12/2006;17:24:00;4.216;0.418;234.840;18.400;0.000;1.000;17.000
*/
public class PowerDays extends Configured implements Tool  {
public static class counterMapper     // Need to replace the four type labels there with actual Java class names
     extends Mapper< LongWritable, Text, Text, DoubleWritable > {

@Override
public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {
   String text[] = value.toString().split(";");
   String day = text[0];
   if (day.charAt(0)!='D'){
      if(!text[2].equals("?")){
         Double GAP = Double.parseDouble(text[2]);
         context.write(new Text(day), new DoubleWritable(GAP));
      }
   }
 } // map

} //counterMapper

//  Reducer Class Template

public static class counterReducer   // needs to replace the four type labels with actual Java class names
      extends  Reducer< Text, DoubleWritable, Text, DoubleWritable> {

 // note: InValueType is a type of a single value Reducer will work with
 //       the parameter to reduce() method will be Iterable<InValueType> - i.e. a list of these values

@Override  // we are overriding the Reducer's reduce() method


public void reduce( Text key, Iterable<DoubleWritable> values, Context context)
     throws IOException, InterruptedException {

// output the word with the number of its occurrences

 double totalGAP = 0;
 double powerConsumption = 0;
 for(DoubleWritable i : values) {
    totalGAP += i.get();
  }
 powerConsumption = (totalGAP*1000)/60;
 context.write(key, new DoubleWritable(powerConsumption));

 } // reduce


} // reducer



public static class totalCountMapper     // Need to replace the four type labels there with actual Java class names
     extends Mapper< LongWritable, Text, Text, Text> {

// start counting word occurrences
/*16/12/2006  23.4*/
@Override
public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {
      String text[] =  value.toString().split("\\s+");
      String day = text[0];
      String tPowerCons = text[1];
      String dateSplit[] = day.split("/");
      String year = dateSplit[2];
      String outputValue = day + " " + tPowerCons;
      context.write(new Text(year), new Text(outputValue));
      //context.write(key, value);

 } // map



} // totalCountMapper

//  Reducer Class Template

public static class totalCountReducer   // needs to replace the four type labels with actual Java class names
      extends  Reducer< Text, Text, Text, Text> {

 // note: InValueType is a type of a single value Reducer will work with
 //       the parameter to reduce() method will be Iterable<InValueType> - i.e. a list of these values

@Override  // we are overriding the Reducer's reduce() method
public void reduce( Text key, Iterable<Text> values, Context context)
     throws IOException, InterruptedException {
 double highest = 0;
 String day = "";
 for(Text word : values) {
   String split[] = word.toString().split(" ");
   day = split[0];
   double gap = Double.parseDouble(split[1]);
   if (gap > highest) {
      highest = gap;
   }
 }
 String highestString = Double.toString(highest);
 String outputValue = day + " " + highestString;
 context.write(key, new Text(outputValue));
} // reduce
} // reducer

//  MapReduce Driver
@Override
public int run(String[] args) throws Exception {
     Configuration conf = super.getConf();
     Job job = Job.getInstance(conf, "Power Days job");  
      job.setJarByClass(PowerDays.class);  
       FileInputFormat.addInputPath(job, new Path(args[0])); 
       FileOutputFormat.setOutputPath(job, new Path(args[1]));
      job.setMapperClass(counterMapper.class);
      job.setReducerClass(counterReducer.class);
       job.setOutputKeyClass(Text.class); 
       job.setOutputValueClass(DoubleWritable.class);
      job.setJobName("Power Days");
      job.waitForCompletion(true);

   Job countAllJob = Job.getInstance(conf, "Power Days job 2");
   countAllJob.setJarByClass(PowerDays.class);
   FileInputFormat.addInputPath(countAllJob, new Path(args[1], "part-r-00000"));
   FileOutputFormat.setOutputPath(countAllJob, new Path(args[2])); 
   countAllJob.setMapperClass(totalCountMapper.class);
   countAllJob.setReducerClass(totalCountReducer.class);
   countAllJob.setOutputKeyClass(Text.class);
   countAllJob.setOutputValueClass(Text.class);
   countAllJob.setJobName("Count em All!");
   return countAllJob.waitForCompletion(true) ? 0 : 1;
  } 
 
    public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    int res = ToolRunner.run(conf, new PowerDays(), args);
    System.exit(res);
  }
} // MyMapReduceDriver





