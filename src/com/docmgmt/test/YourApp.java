package com.docmgmt.test;
import java.util.ArrayList;
import java.util.List;

class Pair<T, U> {
    public  T key;
    public  U value;

    public Pair(T key, U value) {
        this.key = key;
        this.value = value;
    }
}

class YourApp {
    @SuppressWarnings("unchecked")
	public static void main(String[] args) {
	int sum=0;
        List<Pair<Character, Integer>> charList = new ArrayList<Pair<Character, Integer>>();
        
        
        charList.add(new Pair('b', 10));
        charList.add(new Pair('c', 50));
        charList.add(new Pair('a', 90));
        charList.add(new Pair('b', 20));
		charList.add(new Pair('c', 30));
		charList.add(new Pair('b', 30));
		charList.add(new Pair('c', 30));
		charList.add(new Pair('b', 30));
		charList.add(new Pair('a', 50));
		charList.add(new Pair('a', 20));
	/*
		Set<Pair<Character, Integer>> x=new HashSet<Pair<Character, Integer>>(charList);

		Set keys = x.keySet();
	
		System.out.println(keys);

		*/
		//System.out.println(charList);
		
		//for (Pair<Character, Integer> pair : charList) {

	//			System.out.println(charList.size());
//        for (Pair<Character, Integer> pair : charList) {
//
//	}
        Character k=new Character('a');
		int count=0;
		Pair<Character, Integer> keyValue;
		Pair<Character, Integer> keyValue1;
		for (int i=0;i<charList.size();i++) 
		{
			keyValue=charList.get(i);
			//System.out.println(k);
			for (int j=i+1;j<charList.size();j++) 
			{
				keyValue1=charList.get(j);
				
				if(keyValue.key==keyValue1.key){
						try{
							charList.set(i, new Pair(keyValue.key, (charList.get(i).value + keyValue1.value)));
						}catch(Exception e){
							e.printStackTrace();
						}
						charList.remove(j);
						j--;
				}
			}
			
		}
		
		for (Pair<Character, Integer> pair1 : charList) 
				System.out.println(pair1.key + "----"+pair1.value);

/*if(pair.key==pair1.key)
				{
					pair.value=pair.value+pair1.value;
					//System.out.println(pair.key);
					break;
				}
 /*				if(pair.key!=pair1.key)
				{
					pair.value=pair.value;
					System.out.println(pair.key);
					break;
				}
	*/		

		
		//	sum=sum+pair.value;
				
			//System.out.println(pair.key);
			/*	for(char c:pair.key)
				{
						if(
				}
		*/
		
	/*	}
				System.out.println(pair.key + " ->	 " + pair.value);
				
		}*/
      //  }
	//	System.out.println(sum);
					//	System.out.println(pair.key);
		
    }
	}
