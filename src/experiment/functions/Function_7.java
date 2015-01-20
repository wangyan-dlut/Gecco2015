package experiment.functions;

import algorithm.core.component.Code;
import algorithm.core.component.Encode;

/**
 * Created by wwhh on 2015/1/18.
 */
public class Function_7 extends Function {
	private int groupSize;
	public Function_7(){
		super(-100, 100, 1000);
		groupSize = 50;
	}
	@Override
	public double run(Code code){
		Code P = Encode.createIntPriorityCode(code.getSize());
		Code z = getShiftedSolution(code);
		Code z1 = new Code();
		for(int i=0; i<groupSize; i++){
			z1.addGene(z.getDoubleGene(P.getIntGene(i)));
		}
		Code z2 = new Code();
		for(int i=groupSize; i<code.getSize(); i++){
			z2.addGene(z.getDoubleGene(P.getIntGene(i)));
		}
		return schwefelFunction(z1) * 1e6 + sphereFunction(z);
	}
}
