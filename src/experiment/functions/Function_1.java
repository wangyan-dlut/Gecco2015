package experiment.functions;

import algorithm.core.component.Code;

/**
 * Created by wwhh on 2015/1/16.
 */
public class Function_1 extends Function {
	public Function_1(){
		super(-100, 100, 1000);
	}
	@Override
	public double run(Code code){
		Code shiftedSolution = getShiftedSolution(code);
		return ellipticFunction(shiftedSolution);
	}
}
