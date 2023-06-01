package com.docmgmt.test;


public class RelayTest {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		int secondCouter=0;
		RelayTest rt=new RelayTest();
		rt.getRandom();
		int intervalCounter=0;
//		while(true){
//			Thread.sleep(1000);
//			System.out.println("1");
//			secondCouter++;
//			if(secondCouter%4==0){
//				Thread.sleep(1000);
//				System.out.println(++intervalCounter +" Interval");
//			}
//			if(secondCouter==20)
//				break;
//		}
		

	}
	public void getRandom(){
		int a=3;
		int b=3;
		int c=	getc(a,b);
		System.out.println(c);
		
	}
	public int getc(int a,int b)
	{
		int arr[]={1,2,3};
		int c=0;
		for(int i=0;i<arr.length;i++){
			if(arr[i]!=a && arr[i]!=b){
				c=arr[i];
			}			
		}
		return c;
	}
	

}
