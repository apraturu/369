
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
import java.text.DecimalFormat;

import com.alexholmes.json.mapreduce.MultiLineJsonInputFormat;

public class accounting extends Configured implements Tool {

  public static class JsonMapper
      extends Mapper<LongWritable, Text, Text, DoubleWritable> {

    private Text        outputKey   = new Text();
    private IntWritable outputValue = new IntWritable(1);

    @Override
    public void map(LongWritable key, Text value, Context context)
        throws IOException, InterruptedException {
     try { 
      JSONObject message = new JSONObject(value.toString());
      String messageText = message.getString("text");
      String userId = message.getString("user");
      int numBytes = messageText.length();
      double messageCharge = 5;
      messageCharge += Math.round(numBytes/10);
      if(numBytes > 100) {
         messageCharge += 5;
      }
      double charge = messageCharge/100;
       context.write(new Text(userId), new DoubleWritable(charge));
    } 
    catch (Exception e) 
       {System.out.println(e); }
    }
  }

  public static class JsonReducer
      extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {
    private DoubleWritable result = new DoubleWritable();

    @Override
    public void reduce(Text key, Iterable<DoubleWritable> values, Context context)
        throws IOException, InterruptedException {
      double sum = 0.0;
      int numMessages = 0;
      for (DoubleWritable val : values) {
        sum += val.get();
        numMessages++;
      } 
      if(numMessages > 100){
         sum = sum * 0.95;
      }
      sum = Math.round(sum*100.00)/100.00;
      context.write(key, new DoubleWritable(sum));
    }
  }

  @Override
  public int run(String[] args) throws Exception {
    Configuration conf = super.getConf();
    Job job = Job.getInstance(conf, "accouning job");

    job.setJarByClass(accounting.class);
    job.setMapperClass(JsonMapper.class);
    job.setReducerClass(JsonReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(DoubleWritable.class);
    job.setInputFormatClass(MultiLineJsonInputFormat.class);
    MultiLineJsonInputFormat.setInputJsonMember(job, "messageId");

    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));

    return job.waitForCompletion(true) ? 0 : 1;
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    int res = ToolRunner.run(conf, new accounting(), args);
    System.exit(res);
  }
}
