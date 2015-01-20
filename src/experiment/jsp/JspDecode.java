package experiment.jsp;

import java.util.ArrayList;
import java.util.List;

import algorithm.core.component.Code;
import algorithm.core.scheduling.Operation;
import algorithm.core.scheduling.Schedule;

public class JspDecode {
	public static Schedule intPriorityDecode(Code intPriorityCode, Jsp jspData) {
		Schedule schedule = new Schedule();
		int operationId[] = new int[jspData.getMachineSize()];
		int size = jspData.getJobSize()*jspData.getMachineSize();
		int priority = size;
		double jobEndingTime[] = new double[jspData.getJobSize()];
		
		while(priority!=0) {
			for(int i=0; i<size; i++) {
				if(intPriorityCode.getIntGene(i)==priority-1) {
					int jobNum = i/jspData.getJobSize();
					int operationNum = operationId[jobNum];

					Operation operation = new Operation();
					operation = jspData.getJob(jobNum).getOperation(operationNum);
					
					//starting time
					double tempStartingTime = jobEndingTime[jobNum];
					int tempMachineNum = operation.getRessourceAttributeID(0);
					
					boolean sw=true;
					while(sw) {
						boolean sw1=true;
						for(int j=0; j<schedule.getSize(); j++) {
							if(schedule.getOperation(j).getRessourceAttributeID(0)==tempMachineNum) {
								if(overlap(tempStartingTime, operation.getProcessingTime(), 
										schedule.getOperation(j).getStartingTime(), 
										schedule.getOperation(j).getEndingTime())) {
									tempStartingTime = schedule.getOperation(j).getEndingTime();
									sw1=false;
									break;
								} 
							}
						}
						
						if(sw1) {
							sw=false;
						}
					}
					
					operation.setStartingTime(tempStartingTime);
					operation.setEndingTime(tempStartingTime+operation.getProcessingTime());
					
					jobEndingTime[jobNum]=operation.getEndingTime();
					schedule.addOperation(operation, jobNum);
										
					operationId[jobNum]++;
					priority--;
					break;
				}
			}
		}
		
		double makespan=0.0;
		
		for(int i=0; i<schedule.getSize(); i++) {
			if(schedule.getOperation(i).getEndingTime()>makespan) {
				makespan = schedule.getOperation(i).getEndingTime();
			}
		}
		schedule.setMakespan(makespan);
		
		//printJspSchedule(schedule);
		
		return schedule;
	}

	public static Schedule doublePriorityDecode(Code doublePriorityCode, Jsp jspData) {
		Schedule schedule = new Schedule();
		int operationId[] = new int[jspData.getMachineSize()];
		int size = jspData.getJobSize()*jspData.getMachineSize();
		int priority = size;
		double jobEndingTime[] = new double[jspData.getJobSize()];
		
		List<Double> priList = new ArrayList<Double>();
		List<Integer> assList = new ArrayList<Integer>();
		for(int i=0; i<size; i++) {
			priList.add(doublePriorityCode.getDoubleGene(i));
		}
		
		double maxNumber = 0.0;
		int id = 0;
		for(int i=0; i<size; i++) {
			maxNumber = -1.0;
			id = 0;
			for(int j=0; j<priList.size(); j++) {
				if(priList.get(j)>maxNumber) {
					maxNumber = priList.get(j);
					id=j;
				}
			}
			assList.add(id);
			priList.set(id, -1.0);
		}
		
		for(int i=0; i<size; i++) {
			int jobNum = assList.get(i)/jspData.getJobSize();
			int operationNum = operationId[jobNum];

			Operation operation = new Operation();
			operation = jspData.getJob(jobNum).getOperation(operationNum);
			
			//starting time
			double tempStartingTime = jobEndingTime[jobNum];
			int tempMachineNum = operation.getRessourceAttributeID(0);
			
			boolean sw=true;
			while(sw) {
				boolean sw1=true;
				for(int j=0; j<schedule.getSize(); j++) {
					if(schedule.getOperation(j).getRessourceAttributeID(0)==tempMachineNum) {
						if(overlap(tempStartingTime, operation.getProcessingTime(), 
								schedule.getOperation(j).getStartingTime(), 
								schedule.getOperation(j).getEndingTime())) {
							tempStartingTime = schedule.getOperation(j).getEndingTime();
							sw1=false;
							break;
						} 
					}
				}
				
				if(sw1) {
					sw=false;
				}
			}
			
			operation.setStartingTime(tempStartingTime);
			operation.setEndingTime(tempStartingTime+operation.getProcessingTime());
			
			jobEndingTime[jobNum]=operation.getEndingTime();
			schedule.addOperation(operation, jobNum);
								
			operationId[jobNum]++;
			//priority--;
			//break;
		}
		
		/*
		while(priority!=0) {
			for(int i=0; i<size; i++) {
				if(doublePriorityCode.getDoubleGene(i)==(double)priority-1) {
				}
			}
		}
		*/
		
		double makespan=0.0;
		
		for(int i=0; i<schedule.getSize(); i++) {
			if(schedule.getOperation(i).getEndingTime()>makespan) {
				makespan = schedule.getOperation(i).getEndingTime();
			}
		}
		
		schedule.setMakespan(makespan);
		
		
		return schedule;
	}
	
	
	
	public static void printJspSchedule(Schedule schedule) {
		for(int i=0; i<schedule.getSize(); i++) {
			System.out.print("("+schedule.getJobID(i)+"-"+schedule.getOperation(i).getID()
					+"-"+schedule.getOperation(i).getRessourceAttributeID(0)
					+":"+schedule.getOperation(i).getStartingTime()
					+"-"+schedule.getOperation(i).getEndingTime()+") ");
		}
		System.out.println("");
		System.out.println("Makespan="+schedule.getMakespan());		
		
		double makespan=0.0;
		
		for(int i=0; i<schedule.getSize(); i++) {
			if(schedule.getOperation(i).getEndingTime()>makespan) {
				makespan = schedule.getOperation(i).getEndingTime();
			}
		}
		System.out.println(">> "+makespan);
	}
	
	private static boolean overlap(double startTime_a, double processingTime, double startTime_b, double endingTime_b) {
		boolean resoult = false;
		
		if(startTime_b<startTime_a && startTime_a<endingTime_b) {
			resoult = true;
		} else if (startTime_b<startTime_a+processingTime && startTime_a+processingTime<endingTime_b) {
			resoult = true;
		} else if (startTime_a<=startTime_b && startTime_b<=startTime_a+processingTime) {
			resoult = true;
		}

		return resoult;
	}
	
}
