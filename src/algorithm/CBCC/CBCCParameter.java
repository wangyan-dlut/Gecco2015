package algorithm.CBCC;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;

/**
 * Created by wwhh on 2015/1/18.
 */
public class CBCCParameter {
	private int dimension;
	private int populationSize;
	private int iteration;
	private int interval;
	public CBCCParameter(String fileName){
		try {
			BufferedReader re = new BufferedReader(new FileReader(fileName));
			StreamTokenizer st = new StreamTokenizer(re);
			st.commentChar('#');
			st.nextToken();
			dimension = (int)st.nval;
			st.nextToken();
			populationSize = ((int)st.nval);
			st.nextToken();
			iteration = ((int)st.nval);
			st.nextToken();
			interval = (int)st.nval;
		} catch(IOException e) {
			System.out.println("Parameter File Improt Error");
		}
	}
	public int getDimension(){return dimension;}
	public int getPopulationSize(){return populationSize;}
	public int getIteration(){return iteration;}
	public int getInterval(){return interval;}
}
