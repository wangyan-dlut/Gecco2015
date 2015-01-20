package algorithm.core.component;

import java.util.*;

public class Code {
	List<Gene> codeList;
	
	public Code() {
		codeList = new ArrayList<Gene>();
	}
	
	public void addGene(int gene) {
		IntGene g = new IntGene();
		g.set(gene);
		codeList.add(g);
	}
	public void setGene(int id, int gene) {
		IntGene g = new IntGene();
		g.set(gene);
		codeList.set(id, g);
	}
	public int getIntGene(int id) {
		return codeList.get(id).getInt();
	}
	
	public void addGene(double gene) {
		DoubleGene g = new DoubleGene();
		g.set(gene);
		codeList.add(g);
	}
	public void setGene(int id, double gene) {
		DoubleGene g = new DoubleGene();
		g.set(gene);
		codeList.set(id, g);
	}
	public double getDoubleGene(int id) {
		return codeList.get(id).getDouble();
	}
	
	public int getSize() {
		return codeList.size();
	}
	
	public void addGene(Gene gene) {
		codeList.add(gene);
	}
	
	public void delGene(Gene gene) {
		codeList.remove(gene);
	}

	public void delGene(int id) {
		codeList.remove(id);
	}
	public void setGene(int id, Gene gene) {
		codeList.set(id, gene);
	}
	public Gene getGene(int id) {
		return codeList.get(id);
	}
	@Override
	public Code clone(){
		Code res = new Code();
		for(int i=0; i<codeList.size(); i++){
			res.addGene(codeList.get(i).clone());
		}
		return res;
	}

}
