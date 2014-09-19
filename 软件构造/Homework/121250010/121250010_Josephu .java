
/**
 * @function: 1.the function name is josephu
 * 2.aim:Solving the Josephu problem 
 * 3.parameters: the people(int) ,the start number of the people (int) and counting number (int).
 * 4.return: the last person number.  
 * 5. 20+lines
 *
 * @time: 2014/7/18--2014/7/20
 * @author wenxin
 */

public class 121250010_Josephu {

	//the number of last one  
	private static int finalNum=0;
	
	//Solving the Josephu problem 
	public int Josephu(int people,int startNum,int countNum){
		
		/*
		 * 1.establish  an array to show if the person is picked out.
		 * 2.Value 1 represents no, 0 represents yes.
		 */
		int[] peopleNo=new int[people];
		for(int i=0;i<people;i++)
			peopleNo[i]=1;
	
		
		
		int start=startNum-1;
		int count=0;
		/*
		 * delete people until there is only one person.
		 */
		do{	
			if(peopleNo[start%people]==1){	
				count++;
				if(count==countNum){
					count=0;
					start=start%people;
					peopleNo[start%people]=0;
					System.out.println("pick out the "+(start%people+1)+"th person.");
				}
			}	
			start++;
			
		}while(!oneKey(peopleNo));
		
		return finalNum+1;
		
		}
	
	/*
	 * function: to make sure there is only one element in the array.
	 * 
	 * */
	public static boolean oneKey(int[] person){
		
		int count=0;
		for(int i=0;i<person.length;i++){
			if(person[i]==1){
				count++;
				finalNum=i;
			}
		}
		
		return count==1;
	}
	
	
	/*
	 * function: by linklist
	 */
	public void linklist(){
		
	}
	
}

