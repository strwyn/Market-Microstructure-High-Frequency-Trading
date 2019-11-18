package class2;

public class PairNonGeneric {
	
	private Object key,value;
	
	public PairNonGeneric(Object key, Object value){
		this.key = key;
		this.value = value; 
	}
	
	public Object getKey(){ return key;}
	
	public Object getValue(){ return value;}
	
	public static void main(String[] args){
		PairNonGeneric pair = new PairNonGeneric("key",1);
		String key = (String) pair.getKey();
		String value = (String) pair.getValue();
		Integer i = (Integer) pair.getValue();

	}

}
