// CSC 369 Winter 2016
// Section 03
// summaries.java
// Anusha Praturu
// March 1, 2016
 

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.json.JSONObject;

import com.alexholmes.json.mapreduce.MultiLineJsonInputFormat;

public class summaries extends Configured implements Tool {

  public static class JsonMapper
      extends Mapper<LongWritable, Text, LongWritable, Text> {

    private LongWritable outputKey   = new LongWritable();
    private Text         outputValue = new Text("hello");

    @Override
    public void map(LongWritable key, Text value, Context context)
        throws IOException, InterruptedException {
     try { 
      JSONObject obj = new JSONObject(value.toString());
      long gameID = obj.getLong("game");
      obj.remove("game");
      outputKey.set(gameID);
      outputValue.set(obj.toString());
      context.write(outputKey, outputValue);
    } catch (Exception e) {System.out.println(e); }
    
    }
  }

  public static class JsonReducer
      extends Reducer<LongWritable, Text, LongWritable, Text> {
    private Text result = new Text();

    @Override
    public void reduce(LongWritable key, Iterable<Text> values, Context context)
        throws IOException, InterruptedException {
      int actionNum, moves = 0, regular = 0, special = 0, score = 0, highestMove = 0;
      boolean ended = false;
      JSONObject obj, temp, action;
      String aType;

      try {
         obj = new JSONObject();
         for (Text val : values) {
           temp = new JSONObject(val.toString());
           action = temp.getJSONObject("action");
           aType = action.getString("actionType");
           if(moves == 0) {
             obj.put("user", temp.getString("user"));
           }
           moves++;
           if(aType.equals("SpecialMove")) {
             special++;
           }
           else {
             regular++;
           }
           if(!ended && aType.equals("GameEnd")) {
             ended = true;
             if(action.getString("gameStatus").equals("Win")) {
               obj.put("outcome", "win");
             }
             else {
               obj.put("outcome", "loss");
             }
             score = action.getInt("points");
             obj.put("score", score);
           }
           if(!ended && !aType.equals("GameStart")) {
             actionNum = action.getInt("actionNumber");
             if(actionNum > highestMove) {
               highestMove = actionNum;
               score = action.getInt("points");
             }
           }
         }
         if(!ended) {
            obj.put("outcome", "in progress");
            obj.put("score", score);
         }
         obj.put("moves", moves);
         obj.put("regular", regular);
         obj.put("special", special);
         obj.put("perMove", ((double)score) / ((double)moves));
         result.set(obj.toString(3));
         context.write(key, result);
      }
      catch(Exception e) {
         e.printStackTrace();
      }
    }
  }

  @Override
  public int run(String[] args) throws Exception {
    Configuration conf = super.getConf();
    Job job = Job.getInstance(conf, "befuddled summaries");

    job.setJarByClass(summaries.class);
    job.setMapperClass(JsonMapper.class);
    job.setReducerClass(JsonReducer.class);
    job.setOutputKeyClass(LongWritable.class);
    job.setOutputValueClass(Text.class);
    job.setInputFormatClass(MultiLineJsonInputFormat.class);
    MultiLineJsonInputFormat.setInputJsonMember(job, "game");

    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));

    return job.waitForCompletion(true) ? 0 : 1;
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    int res = ToolRunner.run(conf, new summaries(), args);
    System.exit(res);
  }
}
