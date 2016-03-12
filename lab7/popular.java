import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.io.IntWritable; 
import org.apache.hadoop.io.LongWritable; 
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

import com.alexholmes.json.mapreduce.MultiLineJsonInputFormat;

public class popular extends Configured implements Tool  {
public static class getPopularMapper     // Need to replace the four type labels there with actual Java class names
     extends Mapper< LongWritable, Text, Text, LongWritable > {

@Override
public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {

   try {
      JSONObject message = new JSONObject(value.toString());
      String messageText = message.getString("text");
      String userId = message.getString("user");
      String[] words = messageText.split("\\W+");
      for(int i = 0; i< words.length; i++) {
         if(!words[i].equals("") && !words[i].equals(" ")) {
            context.write(new Text(words[i]), new LongWritable(1));
         }
      }
    }
    catch (Exception e) {
       System.out.println(e);
    }

 } // map

} //counterMapper

//  Reducer Class Template

public static class getPopularReducer   // needs to replace the four type labels with actual Java class names
      extends  Reducer< Text, LongWritable, Text, LongWritable> {

 // note: InValueType is a type of a single value Reducer will work with
 //       the parameter to reduce() method will be Iterable<InValueType> - i.e. a list of these values

@Override  // we are overriding the Reducer's reduce() method


public void reduce( Text key, Iterable<LongWritable> values, Context context)
     throws IOException, InterruptedException {

// output the word with the number of its occurrences

 long frequency = 0;
 for(LongWritable i : values) {
    frequency++;
  }
 context.write(key, new LongWritable(frequency));

 } // reduce


} // reducer



public static class orderMapper     // Need to replace the four type labels there with actual Java class names
     extends Mapper< LongWritable, Text, LongWritable, Text> {

// start counting word occurrences

@Override
public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {
      String text[] =  value.toString().split("\\s+");
      String word = text[0];
      Long frequency = Long.parseLong(text[1]);
      context.write(new LongWritable(frequency), new Text(word));

 } // map



} // totalCountMapper

//  Reducer Class Template

public static class orderReducer   // needs to replace the four type labels with actual Java class names
      extends  Reducer< LongWritable, Text, LongWritable, Text> {

 // note: InValueType is a type of a single value Reducer will work with
 //       the parameter to reduce() method will be Iterable<InValueType> - i.e. a list of these values

@Override  // we are overriding the Reducer's reduce() method
public void reduce( LongWritable key, Iterable<Text> values, Context context)
     throws IOException, InterruptedException {
 for(Text word : values) {
   context.write(key, word);
 }
} // reduce
} // reducer

//  MapReduce Driver
@Override
public int run(String[] args) throws Exception {
     Configuration conf = super.getConf();
     Job job = Job.getInstance(conf, "popular job");  
      job.setJarByClass(popular.class);  
       FileInputFormat.addInputPath(job, new Path(args[0])); 
       FileOutputFormat.setOutputPath(job, new Path(args[1]));
      job.setMapperClass(getPopularMapper.class);
      job.setReducerClass(getPopularReducer.class);
       job.setOutputKeyClass(Text.class); 
       job.setOutputValueClass(LongWritable.class);
      job.setInputFormatClass(MultiLineJsonInputFormat.class);
    MultiLineJsonInputFormat.setInputJsonMember(job, "messageId"); 
      job.setJobName("Popular Job 1");
      job.waitForCompletion(true);

   Job countAllJob = Job.getInstance(conf, "popular job 2");
   countAllJob.setJarByClass(popular.class);
   countAllJob.setSortComparatorClass(LongWritable.DecreasingComparator.class);
   FileInputFormat.addInputPath(countAllJob, new Path(args[1], "part-r-00000"));
   FileOutputFormat.setOutputPath(countAllJob, new Path(args[2])); 
   countAllJob.setMapperClass(orderMapper.class);
   countAllJob.setReducerClass(orderReducer.class);
   countAllJob.setOutputKeyClass(LongWritable.class);
   countAllJob.setOutputValueClass(Text.class);
   countAllJob.setJobName("Popular Job 2");
   return countAllJob.waitForCompletion(true) ? 0 : 1;
  } 
 
    public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    int res = ToolRunner.run(conf, new popular(), args);
    System.exit(res);
  }
} // MyMapReduceDriver





