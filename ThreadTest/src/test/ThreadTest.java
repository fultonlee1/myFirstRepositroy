package test;

import java.util.Date;

public class ThreadTest {

	public int sum1=0;
	public int sum2=0;
	/**
	 * 同步锁
	 */
	private Integer visitedTimes = new Integer(0);
	/**
	 * 并发线程开始的时间
	 */
	private Date startTime;
	/**
	 * 并发线程结束的时间
	 */
	private Date endTime;
	/**
	 * 线程1开始的时间
	 */
	private Date startTime1;
	/**
	 * 线程1开始的时间
	 */
	private Date startTime2;
	/**
	 * 线程2结束的时间
	 */
	private Date endTime1;
	/**
	 * 线程2结束的时间
	 */
	private Date endTime2;
//	private volatile int visited =0;
	public static int givenTimes = 1000000000/2;
	public static void main(String[] args) {
		
		ThreadTest test =  new ThreadTest();
		Calculate r1 = test.new Calculate();
		r1.setName("thread1");
		r1.setCalculateTimes(givenTimes);
		Calculate r2 = test.new Calculate();
		r2.setName("thread2");
		r2.setCalculateTimes(givenTimes);
		Thread thread1 = new Thread(r1);
		Thread thread2 = new Thread(r2);
		r1.setOtherThread(thread2);
		r2.setOtherThread(thread1);
		thread1.start();
		thread2.start();
		
	}

	class Calculate implements Runnable{

		/**
		 * 要循环计算的次数
		 */
		private int calculateTimes;
		/**
		 * 类名来表示是哪个线程
		 */
		private String name;
		/**
		 * otherThread变量保存对方的线程
		 */
		private Thread otherThread;
		@Override
		public void run() {
			int i = calculateTimes;
			int sum=0;
			if("thread1".equals(name)){
				sum = sum1;
				startTime1 = new Date();
			}else{
				sum = sum2;
				startTime2 = new Date();
			}
//			visited++;	
			System.out.println(name+"开始了！");
			
			while(i>0){
				i--;
				sum+=1;
			}
			System.out.println(name+"结束了！");
//			checkTime();
//			visited++;
			if("thread1".equals(name)){
				sum1 = sum;
				endTime1 = new Date();
				
			}else{
				sum2 = sum;
				endTime2 = new Date();
			}
			/**
			 * 根据对方线程的状态来判断对方是不是结束，来统计时间
			 */
			if(otherThread.getState().equals(Thread.State.TERMINATED)){
				if(startTime1.getTime()<startTime2.getTime()){
					startTime = startTime1;
				}else{
					startTime = startTime2;
				}
				if(endTime1.getTime()>endTime2.getTime()){
					endTime = endTime1;
				}else{
					endTime = endTime2;
				}
				System.out.println("两个并发线程计算的时间为："+(endTime.getTime()-startTime.getTime())+"毫秒");
			}
//			if(visited==4){
//				
//			}
			
		}
		public void setCalculateTimes(int calculateTimes) {
			this.calculateTimes = calculateTimes;
		}
		public void setName(String name) {
			this.name = name;
		}
		public void setOtherThread(Thread otherThread) {
			this.otherThread = otherThread;
		}
	
		
	}
	
	private void checkTime(){
		synchronized (visitedTimes) {
			if(visitedTimes==0){
				this.startTime = new Date();
				System.out.println("两个并发线程计算开始了！");
			}
			if(visitedTimes==3){
				this.endTime = new Date();
				sum2+=sum1;
				System.out.println("两个并发线程计算结束了！");
				System.out.println("两个并发线程计算的时间为："+(endTime.getTime()-startTime.getTime())+"毫秒");
				System.out.println("两个并发线程计算的结果为："+sum2);
			}
			visitedTimes++;
		}
	}
	
}
