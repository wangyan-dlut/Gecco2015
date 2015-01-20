package algorithm.core.component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;

public class ParameterImport {
	
	public static Parameter gaParameterTxtImport(String fileName) {
		Parameter parameter = new Parameter();
		try {
			BufferedReader re = new BufferedReader(new FileReader(fileName));
		    StreamTokenizer st = new StreamTokenizer(re);
		    st.commentChar('#');
		    st.nextToken();
		    parameter.setPopulationSize((int)st.nval);
		    st.nextToken();
		    parameter.setCrossoverProbability(st.nval);
		    st.nextToken();
		    parameter.setMutationProbability(st.nval);
		    st.nextToken();
		    parameter.setNumOfEvolvedIndividual((int)st.nval);
		} catch(IOException e) {
			System.out.println("Parameter File Improt Error");
		}		
		return parameter;
	}
	
}
