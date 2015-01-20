package run;

import experiment.jsp.Jsp;
import experiment.jsp.JspDecode;
import experiment.jsp.JspImport;
import algorithm.core.component.*;
import algorithm.core.scheduling.Schedule;

public class PriGa_Jsp_Test {
	
	Jsp jsp;
	Parameter parameter;
	Population population;
	Individual gbest;
	
	public void run(String JspDataFileName, String GaParameterFileName) {

		jsp = JspImport.txtDataImport(JspDataFileName);
		parameter = ParameterImport.gaParameterTxtImport(GaParameterFileName);
		
		gbest = new Individual();

		// initialization
		population = Initialization.oneDimensionIntPriorityInitialization
				(parameter.getPopulationSize(), jsp.getJobSize()*jsp.getMachineSize());
		for(int i=0; i<parameter.getPopulationSize(); i++) {
			population.getIndividual(i).setSolution
				(JspDecode.intPriorityDecode(population.getIndividual(i).getSection(0), jsp));
			Schedule s = (Schedule)population.getIndividual(i).getSolution();
			population.getIndividual(i).setFitness(s.getMakespan());
		}
		
		gbest.setFitness(Double.MAX_VALUE);
		for(int i=0; i<population.getSize(); i++) {
			if(gbest.getFitness()>population.getIndividual(i).getFitness()) {
				gbest = population.getIndividual(i).clone();
			}
		}
		//gbest = SolutionUpdate.oneMinObjectiveUpdate(population);
		
		int t = 0;
		while(t<parameter.getNumOfEvolvedIndividual()) {
			// Crossover
			Population crossoverOffspring = Crossover.wmxCrossover(population, parameter.getCrossoverProbability());
			for(int i=0; i<crossoverOffspring.getSize(); i++) {
				crossoverOffspring.getIndividual(i).setSolution
					(JspDecode.intPriorityDecode(crossoverOffspring.getIndividual(i).getSection(0), jsp));
				Schedule s = (Schedule)crossoverOffspring.getIndividual(i).getSolution();
				crossoverOffspring.getIndividual(i).setFitness(s.getMakespan());
			}
			population.addPopulation(crossoverOffspring);
			
			// Mutation
			Population mutationOffspring = Mutation.swapMutation(population, parameter.getMutationProbability());
			for(int i=0; i<mutationOffspring.getSize(); i++) {
				mutationOffspring.getIndividual(i).setSolution
					(JspDecode.intPriorityDecode(mutationOffspring.getIndividual(i).getSection(0), jsp));
				Schedule s = (Schedule)mutationOffspring.getIndividual(i).getSolution();
				mutationOffspring.getIndividual(i).setFitness(s.getMakespan());
				
			}
			population.addPopulation(mutationOffspring);
			
			// Immigration
			Population immigration = Initialization.oneDimensionIntPriorityInitialization
					(5, jsp.getJobSize()*jsp.getMachineSize());
			for(int i=0; i<immigration.getSize(); i++) {
				immigration.getIndividual(i).setSolution
					(JspDecode.intPriorityDecode(immigration.getIndividual(i).getSection(0), jsp));
				Schedule s = (Schedule)immigration.getIndividual(i).getSolution();
				immigration.getIndividual(i).setFitness(s.getMakespan());
			}
			population.addPopulation(immigration);
			
			Individual pbest = SolutionUpdate.oneMinObjectiveUpdate(population);
			//gbest = SolutionUpdate.oneMinObjectiveUpdate(population, gbest);
			for(int i=0; i<population.getSize(); i++) {
				if(gbest.getFitness()>population.getIndividual(i).getFitness()) {
					gbest = population.getIndividual(i).clone();
				}
			}
			
			
			population = Selection.minRouletteWheelSelection(population, parameter.getPopulationSize());
			//population = Selection.minElitistSelection(population, parameter.getPopulationSize());
			//population = Selection.replaceWorstIndividual(population, pbest);
			//population = Selection.replaceWorstIndividual(population, gbest);
			
			t=t+mutationOffspring.getSize()+crossoverOffspring.getSize();

			System.out.println("gBest: "+gbest.getFitness());
		}
		
		System.out.println("Final Best:" + gbest.getFitness());
		Encode.printIntCode(gbest.getSection(0));
		JspDecode.printJspSchedule((Schedule)gbest.getSolution());

		
	}

	public static void main(String[] args) {
		PriGa_Jsp_Test ga = new PriGa_Jsp_Test();
		ga.run("data/jsp/10x10.txt", "data/GaParameter.txt");
	}
}
