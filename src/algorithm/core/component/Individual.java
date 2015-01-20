package algorithm.core.component;

import java.util.ArrayList;
import java.util.List;

import algorithm.core.component.Code;
import algorithm.core.component.Individual;

public class Individual implements Cloneable{
	private double fitness;
	private List<Code> codeSection;
	private Object solution;
	
	public Individual() {
		fitness = 0.0;
		codeSection = new ArrayList<Code>();
		solution = new Object();
	}
	
	public void addSection(Code code) {
		codeSection.add(code);
	}
	public Code getSection(int codeId) {
		return codeSection.get(codeId);
	}
	public Code getSingleSection() {
		return codeSection.get(0);
	}
	public int getSectionSize() {
		return codeSection.size();
	}
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	public double getFitness() {
		return this.fitness;
	}
	public void setSolution(Object solution) {
		this.solution = solution;
	}
	public Object getSolution() {
		return this.solution;
	}

	@Override
	public Individual clone(){
		Individual res = new Individual();
		for(int i=0; i<codeSection.size(); i++){
			res.addSection(codeSection.get(i).clone());
		}
		res.setFitness(fitness);
		res.setSolution(solution);
		return res;
	}
	
	public Object clone(int x){
	    Object o=null;    
	    try {    
	    	o=(Individual)super.clone();    
	    }    
	    catch(CloneNotSupportedException e) {    
	        System.out.println(e.toString());    
	    }

		if(x==0){
			Individual temp = new Individual();
			temp.setFitness(this.fitness);
			temp.setSolution(this.solution);
			Code tcode = new Code();
			for(int i = 0; i<this.getSection(0).getSize(); i++ ){
				tcode.addGene(this.getSection(0).getDoubleGene(i));
			}
			temp.addSection(tcode);

			return temp;

		}
		else{
			Individual temp = new Individual();
			temp.setFitness(this.fitness);
			temp.setSolution(this.solution);
			Code tcode = new Code();
			for(int i = 0; i<this.getSection(0).getSize(); i++ ){
				tcode.addGene(this.getSection(0).getIntGene(i));
			}
			temp.addSection(tcode);

			return temp;
		}
	}
	
	public void showIndivdual(String context){
		for(int i=0; i<codeSection.size(); i++){
			context = context + '\n';
			for(int j=0; j<codeSection.get(i).getSize();j++){
				context += codeSection.get(i).getGene(j).getString() + "  ";
			}
		}
		System.out.println(context);
	}
}
