package test;

import java.util.Date;

public class ThreadTest {

	public int sum1=0;
	public int sum2=0;
	/**
	 * ͬ����
	 */
	private Integer visitedTimes = new Integer(0);
	/**
	 * �����߳̿�ʼ��ʱ��
	 */
	private Date startTime;
	/**
	 * �����߳̽�����ʱ��
	 */
	private Date endTime;
	/**
	 * �߳�1��ʼ��ʱ��
	 */
	private Date startTime1;
	/**
	 * �߳�1��ʼ��ʱ��
	 */
	private Date startTime2;
	/**
	 * �߳�2������ʱ��
	 */
	private Date endTime1;
	/**
	 * �߳�2������ʱ��
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
		 * Ҫѭ������Ĵ���
		 */
		private int calculateTimes;
		/**
		 * ��������ʾ���ĸ��߳�
		 */
		private String name;
		/**
		 * otherThread��������Է����߳�
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
			System.out.println(name+"��ʼ�ˣ�");
			
			while(i>0){
				i--;
				sum+=1;
			}
			System.out.println(name+"�����ˣ�");
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
			 * ���ݶԷ��̵߳�״̬���ж϶Է��ǲ��ǽ�������ͳ��ʱ��
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
				System.out.println("���������̼߳����ʱ��Ϊ��"+(endTime.getTime()-startTime.getTime())+"����");
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
				System.out.println("���������̼߳��㿪ʼ�ˣ�");
			}
			if(visitedTimes==3){
				this.endTime = new Date();
				sum2+=sum1;
				System.out.println("���������̼߳�������ˣ�");
				System.out.println("���������̼߳����ʱ��Ϊ��"+(endTime.getTime()-startTime.getTime())+"����");
				System.out.println("���������̼߳���Ľ��Ϊ��"+sum2);
			}
			visitedTimes++;
		}
	}
	
}
