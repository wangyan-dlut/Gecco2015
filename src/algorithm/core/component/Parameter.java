package algorithm.core.component;

public class Parameter {
	private int populationSize;
	private double crossoverProbability;
	private double mutationProbability;
	private int numOfEvolvedIndividual;
	
	public Parameter() {
		populationSize=0;
		crossoverProbability=0.0;
		mutationProbability=0.0;
		numOfEvolvedIndividual=0;
	}
	
	public void setPopulationSize(int size) {
		this.populationSize = size;
	}
	public int getPopulationSize() {
		return this.populationSize;
	}
	
	public void setCrossoverProbability(double pc) {
		this.crossoverProbability = pc;
	}
	public double getCrossoverProbability() {
		return this.crossoverProbability;
	}
	
	public void setMutationProbability(double pm) {
		this.mutationProbability = pm;
	}
	public double getMutationProbability() {
		return this.mutationProbability;
	}
	
	public void setNumOfEvolvedIndividual(int numbers) {
		this.numOfEvolvedIndividual = numbers;
	}
	public int getNumOfEvolvedIndividual() {
		return this.numOfEvolvedIndividual;
	}

}
