package org.example;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class SumMapper extends MapReduceBase implements Mapper<LongWritable, Text, IntWritable, IntWritable> {

    // Map function
    public void map(LongWritable key, Text value, OutputCollector<IntWritable, IntWritable> output, Reporter rep) throws IOException {

        IntWritable id, sum = null;
        String priceStr = value.toString().split(":")[1];
        String[] priceStrArr = priceStr.split(" ");
        int length = priceStrArr.length;
        int priceSum = 0;
        for(String str : priceStrArr) {
            priceSum += Integer.parseInt(str.trim());
        }
        sum = new IntWritable(priceSum);

        if(length <= 10) {
            id = new IntWritable(1);
        } else if (length <= 20) {
            id = new IntWritable(2);
        } else if (length <= 30 ){
            id = new IntWritable(3);
        } else if (length <= 40) {
            id = new IntWritable(4);
        } else {
            id = new IntWritable(5);
        }
        output.collect(id, sum);
    }
}
