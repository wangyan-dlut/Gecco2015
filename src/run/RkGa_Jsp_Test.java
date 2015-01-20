package run;

import experiment.jsp.Jsp;
import experiment.jsp.JspDecode;
import experiment.jsp.JspImport;
import algorithm.core.component.*;
import algorithm.core.scheduling.Schedule;

public class RkGa_Jsp_Test {
	
	Jsp jsp;
	Parameter parameter;
	Population population;
	Individual gbest;

	public void run(String JspDataFileName, String GaParameterFileName) {
		jsp = JspImport.txtDataImport(JspDataFileName);
		parameter = ParameterImport.gaParameterTxtImport(GaParameterFileName);
		gbest = new Individual();
		
		// initialization
		population = Initialization.oneDimensionDoublePriorityInitialization
				(parameter.getPopulationSize(), jsp.getJobSize()*jsp.getMachineSize());
		
		for(int i=0; i<parameter.getPopulationSize(); i++) {
			population.getIndividual(i).setSolution
				(JspDecode.doublePriorityDecode(population.getIndividual(i).getSection(0), jsp));
			Schedule s = (Schedule)population.getIndividual(i).getSolution();
			population.getIndividual(i).setFitness(s.getMakespan());
		}		
		
		
		
		
		for(int i=0; i<parameter.getPopulationSize(); i++) {
			Encode.printDoubleCode(population.getIndividual(i).getSection(0));
			JspDecode.printJspSchedule((Schedule)population.getIndividual(i).getSolution());
		}		
		
	}
	
	public static void main(String[] args) {
		RkGa_Jsp_Test ga = new RkGa_Jsp_Test();
		ga.run("data/jsp/10x10.txt","data/GaParameter.txt");
	}

}
