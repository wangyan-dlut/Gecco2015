package run;

import algorithm.CBCC.CBCCMain;
import algorithm.CBCC.CBCCParameter;
import experiment.functions.*;

/**
 * Created by evolab.cn on 2015/1/18.
 */
public class CBCCTest {
	public static void main(String args[]){
		CBCCParameter ccParameter = new CBCCParameter("data/CBCCParameter");
		Function function = new Function_1();
		CBCCMain test = new CBCCMain(function);
		test.CCRun(ccParameter.getDimension(), ccParameter.getPopulationSize(), ccParameter.getIteration(), ccParameter.getInterval());
	}
}
