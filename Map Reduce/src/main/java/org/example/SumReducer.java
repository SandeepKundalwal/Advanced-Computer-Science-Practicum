package org.example;

import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class SumReducer extends MapReduceBase implements Reducer<IntWritable, IntWritable, IntWritable, IntWritable> {

    // Reduce function
    int mod = 1000000009;
    public void reduce(IntWritable key, Iterator<IntWritable> value, OutputCollector<IntWritable, IntWritable> output, Reporter rep) throws IOException {

        int sum = 0;
        // Adding prices
        while (value.hasNext()) {
            IntWritable i = value.next();
            sum = (sum%mod + i.get()%mod)%mod;
        }
        output.collect(key, new IntWritable(sum));
    }
}
