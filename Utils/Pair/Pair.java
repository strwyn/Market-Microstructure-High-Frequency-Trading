package class2;

public class Pair <K, V>{
	
	private K k;
	private V v;
	
	public Pair(K key, V value){
		k = key;
		v = value;
	}
	
	public K getKey(){ return k;}
	public V getValue(){ return v;}
	
	public static void main(String[] args){
		Pair<String,Integer> pair = new Pair<String, Integer>("key", 1);
		String key = pair.getKey();
		String value = (String) pair.getValue();
		Integer i = pair.getValue();
	}

}
