package test.com.edifixio.simplElastic.configFactory;

public abstract class TestUtils {
	
	public static String RemoveWhiteChar(String str){
		String[] strs=str.split("[\\s\"]");
		StringBuilder result=new StringBuilder();
		for(int i=0;i<strs.length;i++){
			result.append(strs[i]);
		}
		return result.toString();
	}
	

	
	public static  int numberOfOccurence(String str,String ... subStr){
		int count=0;
	
		
		
		for(int i=0;i<subStr.length;i++){
			count+=numberOfOccurence(str,subStr[i]);
		}
	
		return count;
	}
	
	public static  int numberOfOccurence(String str,String subStr){
		int count=0;
		int indexSubStr;
		
		StringBuilder strBuild= new StringBuilder(str);
		indexSubStr=strBuild.indexOf(subStr);
		
		while(indexSubStr>=0){
			count++;
			strBuild.delete(0, indexSubStr+1);
			indexSubStr=strBuild.indexOf(subStr);
		}
		return count;
	}
	
	/*public static void main(String args[]){
		//System.out.println(numberOfOccurence("ccd[,,,,,,]", "[","]"));
	}*/
	
}
