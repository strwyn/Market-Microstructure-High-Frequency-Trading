package lecture3;

public interface StatsQInterface {

	public void    addLast( Double dataPoint );
	public Double  removeFirst();
	public boolean isReady();
	public boolean hasElementsToRemove();
	
}

