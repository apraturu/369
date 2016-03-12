// CSC 369 Winter 2016
// Multi Line JSON handling example
// Chris Wu
 
// run with  hadoop jar job.jar MultilineJsonJob -libjars /path/to/json-20151123.jar,/path/to/json-mapreduce-1.0.jar /input /output


import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.json.JSONObject;
import java.lang.*;
import java.util.*;

import com.alexholmes.json.mapreduce.MultiLineJsonInputFormat;

public class hashtags extends Configured implements Tool {

  public static class JsonMapper
      extends Mapper<LongWritable, Text, Text, Text> {

    private Text        outputKey   = new Text();
    private IntWritable outputValue = new IntWritable(1);

    @Override
    public void map(LongWritable key, Text value, Context context)
        throws IOException, InterruptedException {
     try { 
      JSONObject message = new JSONObject(value.toString());
      String messageText = message.getString("text");
      String userId = message.getString("user");
      String[] words = messageText.split("\\W+");
      for (int i = 0; i < words.length; i++) {
         if(!words[i].equals("the") && !words[i].equals("in") &&
            !words[i].equals("on") && !words[i].equals("I") &&
            !words[i].equals("he") && !words[i].equals("she") &&
            !words[i].equals("it") && !words[i].equals("there") &&
            !words[i].equals("it") && !words[i].equals("a")) {
     	       context.write(new Text(userId), new Text(words[i]));
            }
      }
    } 
    catch (Exception e) 
       {System.out.println(e); }
    }
  }

  public static class JsonReducer
      extends Reducer<Text, Text, Text, Text> {

    @Override
    public void reduce(Text key, Iterable<Text> values, Context context)
        throws IOException, InterruptedException {
      String hashtags = "";
      HashMap<String, Integer> words = new HashMap<String, Integer>();
      for(Text t : values) {
         //context.write(key, t);
         String word = t.toString();
         Integer previousCount = words.get(word);
         if(previousCount == null) {
            words.put(word, 1);
         }
         else {
            words.put(word, previousCount+1);
         }
      }
      words.remove(" ");
      words.remove("");
      Integer largestVal = 0;
      List<String> largestList = new ArrayList<String>();
      for (Map.Entry<String, Integer> i : words.entrySet()){
         if (largestVal == null || largestVal  < i.getValue()){
            largestVal = i.getValue();
            largestList.clear();
            largestList.add(i.getKey());
         }
         else if (largestVal == i.getValue()){
            largestList.add(i.getKey());
         }
      }
      if(largestList.isEmpty()){
         hashtags += "No hashtags";
      }
      else {
         for(String s: largestList) {
            hashtags += s + " ";
         }
      }
      context.write(key, new Text(hashtags));
    }
  }

  @Override
  public int run(String[] args) throws Exception {
    Configuration conf = super.getConf();
    Job job = Job.getInstance(conf, "hashtags job");

    job.setJarByClass(hashtags.class);
    job.setMapperClass(JsonMapper.class);
    job.setReducerClass(JsonReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    job.setInputFormatClass(MultiLineJsonInputFormat.class);
    MultiLineJsonInputFormat.setInputJsonMember(job, "messageId");

    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));

    return job.waitForCompletion(true) ? 0 : 1;
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    int res = ToolRunner.run(conf, new hashtags(), args);
    System.exit(res);
  }
}
