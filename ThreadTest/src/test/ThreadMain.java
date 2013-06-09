package test;

import java.util.Date;

public class ThreadMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int sum=0;
		Date startTime = new Date(System.currentTimeMillis());
		Long st = System.currentTimeMillis();
		System.out.println();
		for(int i=0;i<1000000000;i++){
			sum+=1;
		}
		Date endTime = new Date();
		Long et = System.currentTimeMillis();
//		System.out.println("运行时间为："+(et-st));
		System.out.println("运行时间为："+(endTime.getTime()-startTime.getTime())+"毫秒");

	}

}
