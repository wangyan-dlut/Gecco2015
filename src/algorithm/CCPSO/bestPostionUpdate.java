package algorithm.CCPSO;

import algorithm.core.component.Individual;
import experiment.jsp.Jsp;
import experiment.jsp.JspDecode;
import algorithm.core.scheduling.Schedule;

import java.util.ArrayList;

/**
 * Created by wwhh on 2014/6/22.
 */
public class bestPostionUpdate {
	public static double benefit(ArrayList<Integer> index, Individual individual ,Individual gBest, Jsp jsp){
		Individual temp = (Individual)gBest.clone(0);
		for(int i=0; i<index.size(); i++){
			temp.getSection(0).setGene(index.get(i),
					individual.getSection(0).getDoubleGene(index.get(i)));
		}
		//temp.getSection(0).setGene(0,1.1);
		//assist.showIn(temp);
		//assist.showIn(gBest);
		//System.out.println(""+index.get(0));
		temp.setSolution(JspDecode.doublePriorityDecode(temp.getSection(0),jsp));
		Schedule s = (Schedule)temp.getSolution();
		return s.getMakespan();
	}
	public static Individual replace(ArrayList<Integer> index, Individual invariant, Individual variant){
		for(int i=0; i<index.size(); i++){
			variant.getSection(0).setGene(index.get(i),
					invariant.getSection(0).getDoubleGene(index.get(i)));
		}
		return variant;
	}
	public static Individual getLoaclBest(ArrayList<Integer> index, Individual pre,
										  Individual mid, Individual next ,Individual gBest, Jsp jsp){
		double sPre = bestPostionUpdate.benefit(index, pre, gBest, jsp);
		double sMid = bestPostionUpdate.benefit(index, mid, gBest, jsp);
		double sNext = bestPostionUpdate.benefit(index, next, gBest, jsp);
		//System.out.println(""+sPre+"  "+sMid+"  "+sNext);
		if(sMid <= sPre && sMid <= sNext) {
			return mid;
		}
		if(sPre <= sMid && sPre <= sNext){
			return pre;
		}
		else{
			return next;
		}
	}
	public static int getLocalBest(int pre, int mid, int next, double[] tmpGbest){
		double sPre = tmpGbest[pre];
		double sMid = tmpGbest[mid];
		double sNext = tmpGbest[next];
		if(sMid <= sPre && sMid <= sNext) {
			return mid;
		}
		if(sPre <= sMid && sPre <= sNext){
			return pre;
		}
		else{
			return next;
		}
	}
}
