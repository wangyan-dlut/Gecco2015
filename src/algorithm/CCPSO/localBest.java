package algorithm.CCPSO;

import algorithm.core.component.Individual;
import algorithm.core.component.Population;
import experiment.jsp.Jsp;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by wwhh on 2014/6/30.
 */
public class localBest {
	public static void getLocalbest(ArrayList<Integer> randomIndex, Population pBest,
									Population lBest, Individual gBest, Jsp jsp){
		for(int i=0; i<lBest.getSize(); i++){
			Individual tmp = getIndividuallBest(randomIndex,pBest.getIndividual(i),gBest,jsp);
			bestPostionUpdate.replace(randomIndex,tmp,lBest.getIndividual(i));
		}

	}
	public static Individual getIndividuallBest(ArrayList<Integer> randomIndex, Individual individual,Individual gBest, Jsp jsp){
		// 求出要交换的三个坐标
		Random random = new Random();
		int index = random.nextInt(randomIndex.size());
		int pre = index==0? index : index-1;
		int next = index==randomIndex.size()-1? index : index+1;

		Individual InPre = (Individual)individual.clone(0);
		Individual InNext = (Individual)individual.clone(0);

		//开始交换
		double tmp;
		tmp = InPre.getSection(0).getDoubleGene(randomIndex.get(pre));
		InPre.getSection(0).setGene(randomIndex.get(pre),
				InPre.getSection(0).getDoubleGene(randomIndex.get(index)));
		InPre.getSection(0).setGene(randomIndex.get(index),tmp);

		tmp = InNext.getSection(0).getDoubleGene(randomIndex.get(next));
		InNext.getSection(0).setGene(randomIndex.get(next),
				InNext.getSection(0).getDoubleGene(randomIndex.get(index)));
		InNext.getSection(0).setGene(randomIndex.get(index),tmp);

		//交换之后求最好的
		double sPre = bestPostionUpdate.benefit(randomIndex, InPre, gBest, jsp);
		double sMid = bestPostionUpdate.benefit(randomIndex, individual, gBest, jsp);
		double sNext = bestPostionUpdate.benefit(randomIndex, InNext, gBest, jsp);
		if(sMid <= sPre && sMid <= sNext) {
			return (Individual)individual.clone(0);
		}
		if(sPre <= sMid && sPre <= sNext){
			return InPre;
		}
		else{
			return InNext;
		}

	}
}
