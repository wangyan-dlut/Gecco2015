package algorithm.core.component;


public class Initialization {
	public static Population oneDimensionIntPriorityInitialization(int populationSize, int codeSize) {
		Population population = new Population();
		for(int i=0; i<populationSize; i++) {
			Individual individual = new Individual();
			Code code = new Code();
			code = Encode.createIntPriorityCode(codeSize);
			individual.addSection(code);
			population.addIndividual(individual);
		}
		return population;
	}
	public static Population oneDimensionDoublePriorityInitialization(int populationSize, int codeSize) {
		Population population = new Population();
		for(int i=0; i<populationSize; i++) {
			Individual individual = new Individual();
			Code code = new Code();
			code = Encode.createDoublePriorityCode(codeSize);
			individual.addSection(code);
			population.addIndividual(individual);
		}
		return population;
	}
	public static Population populationInitialization(int popSize, int dimension, double lbound, double ubound){
		Population population = new Population();
		for(int i=0; i<popSize; i++){
			Individual individual = new Individual();
			Code code = Encode.createCodeWithBound(dimension, lbound, ubound);
			individual.addSection(code);
			population.addIndividual(individual);
		}
		return population;
	}
}
