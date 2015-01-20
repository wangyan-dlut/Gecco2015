package run;


import algorithm.CCPSO.*;
import algorithm.core.component.*;
import experiment.jsp.Jsp;
import experiment.jsp.JspDecode;
import experiment.jsp.JspImport;
import algorithm.core.scheduling.Schedule;

import java.util.ArrayList;

/**
 * Created by wwhh on 2014/7/1.
 */
public class CCPSO_GA_Jsp {
	public static void main(String args[]) {
		int[] S = {2,5,10,50,100};
		//CCInitialization.getRandomIndex(100, S);

		//读入文件
		Jsp jsp = JspImport.txtDataImport("JspData400.txt");  // 这里算是使用了代理
		Parameter parameter = ParameterImport.gaParameterTxtImport("GaParameter.txt");

		// output
		Individual gBest = new Individual();
		Individual kBest = new Individual();

		// initialization
		int t = 0;
		boolean isUpdate = false;
		ArrayList<ArrayList<Integer>> randomIndex = new ArrayList<ArrayList<Integer>>();

		Population population = Initialization.oneDimensionDoublePriorityInitialization    // 初始化时创建 double 的 code
				(parameter.getPopulationSize(), jsp.getJobSize() * jsp.getMachineSize()); //注意 codesize

		for (int i = 0; i < parameter.getPopulationSize(); i++) {  //每个 individual 有自己的 code ，即一个染色体
			population.getIndividual(i).setSolution
					(JspDecode.doublePriorityDecode(population.getIndividual(i).getSection(0), jsp));
			Schedule s = (Schedule) population.getIndividual(i).getSolution();
			population.getIndividual(i).setFitness(s.getMakespan());
		}
		gBest = SolutionUpdate.oneMinObjectiveUpdate(population);
		kBest = (Individual) gBest.clone(0);

		Population pBest = CCInitialization.getCopyPopulation(population);
		Population lBest = CCInitialization.getCopyPopulation(population);

		//assist.showPo(population);
		//assist.showPo(pBest);
		//assist.showPo(lBest);
		//assist.showIn(gBest);
		while (t < parameter.getNumOfEvolvedIndividual()) {
			if (!isUpdate) {
				randomIndex = CCInitialization.getRandomIndex    //如果没有得到更好的结果，重新分组
						(jsp.getJobSize() * jsp.getMachineSize(), S);
			}
			// 主程序
			for (int j = 0; j < randomIndex.size(); j++) {
				// 更新Gbest，pbest
				for (int i = 0; i < population.getSize(); i++) {
					double sPostion = bestPostionUpdate.benefit(randomIndex.get(j),
							population.getIndividual(i), gBest, jsp);
					double spBest = bestPostionUpdate.benefit(randomIndex.get(j),
							pBest.getIndividual(i), gBest, jsp);
					double sKBest = bestPostionUpdate.benefit(randomIndex.get(j),
							kBest, gBest, jsp);

					if (sPostion < spBest) {
						bestPostionUpdate.replace(randomIndex.get(j),
								population.getIndividual(i), pBest.getIndividual(i));
						if (sPostion < sKBest) {
							bestPostionUpdate.replace(randomIndex.get(j),
									population.getIndividual(i), kBest);
							//System.out.println("111");
						}
					} else if (spBest < sKBest) {
						bestPostionUpdate.replace(randomIndex.get(j), pBest.getIndividual(i), kBest);
					}
				}
				//更新localbest
				double[] tmpGbest = new double[population.getSize()];
				for (int i = 0; i < pBest.getSize(); i++) {
					tmpGbest[i] = bestPostionUpdate.benefit(randomIndex.get(j), pBest.getIndividual(i), gBest, jsp);
				}
				for (int i = 0; i < pBest.getSize(); i++) {
					int pre = i == 0 ? i : i - 1;
					int next = i == population.getSize() - 1 ? i : i + 1;
					int temp = bestPostionUpdate.getLocalBest(pre, i, next, tmpGbest);
					bestPostionUpdate.replace(randomIndex.get(j), pBest.getIndividual(temp), lBest.getIndividual(i));
					//System.out.println(""+pre+"  "+i+"  "+next);
				}


				//更新gBest
				double sKBest = bestPostionUpdate.benefit(randomIndex.get(j),
						kBest, gBest, jsp);
				Schedule s = (JspDecode.doublePriorityDecode(gBest.getSection(0), jsp));
				if (sKBest < s.getMakespan()) {
					bestPostionUpdate.replace(randomIndex.get(j), kBest, gBest);
					gBest.setFitness(sKBest);
					isUpdate = true;

				} else {
					isUpdate = false;
				}
			}

			// 更新位置
			for (int j = 0; j < randomIndex.size(); j++) {
				for (int i = 0; i < population.getSize(); i++) {
					postionUpdate.udpdate(randomIndex.get(j), population.getIndividual(i),
							pBest.getIndividual(i), lBest.getIndividual(i), 0.5);
				}
			}
			if (t % 2 == 0) {

				System.out.println(" " + t + ":  " + gBest.getFitness());

			}


			for(int j=0; j<randomIndex.size(); j++){
				Population mutationOffspring = GA.swapMutation(population,randomIndex.get(j),parameter.getMutationProbability());
				Population crossoverOffspring = GA.wmxCrossover(population,randomIndex.get(j),parameter.getCrossoverProbability());
				Population tmp = population.clone();
				tmp.addPopulation(mutationOffspring);
				tmp.addPopulation(crossoverOffspring);
				tmp = GA.minElitistSelection(tmp,randomIndex.get(j),parameter.getPopulationSize(),gBest,jsp);
				GA.populationReplace(randomIndex.get(j),tmp,population);
				t = t + mutationOffspring.getSize() + crossoverOffspring.getSize();
			}



			t = t + 5*population.getSize()*randomIndex.size();
			//System.out.println("1111");

		}
	}
}
