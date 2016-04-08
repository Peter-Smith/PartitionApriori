import java.util.TreeSet;

/**
 * ItemSet that is known to be locally frequent in one partition and is now being considered
 * in its global context.
 * @author Peter
 *
 */
public class ItemSet{
	private TreeSet<String> items;
	private long count;
	/**
	 * Creates a new ItemSet from a set of items.
	 * @param items A set of items.
	 */
	public ItemSet(TreeSet<String> items) {
		this.items = items;
		count = 0;
	}
	
	/**
	 * Creates a new ItemSet with the same items as provided candidate.
	 * @param c Candidate
	 */
	public ItemSet(Candidate c){
		this.items = c.getItems();
		count = 0;
	}
	
	/**
	 * Creates new itemSet with known items and count.
	 * @param items Set of items
	 * @param count Count of transactions containing this ItemSet
	 */
	public ItemSet(TreeSet<String> items, long count){
		this.items = items;
		this.count = count;
	}
	
	public ItemSet clone(){
		return new ItemSet((TreeSet<String>) items.clone(),count);
	}
	
	public String toString(){
		return items.toString()+" count: "+count;
	}
	



	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemSet other = (ItemSet) obj;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		return true;
	}
	
	public int compareTo(ItemSet o){
		return size()-o.size();
	}

	public void plus(){
		count++;
	}
	
	public double support(){
		return (double)count/AllPartitions.getTotalSize();
	}

	public TreeSet<String> getItems() {
		return items;
	}

	public long getCount() {
		return count;
	}
	
	public void removeAll(ItemSet o){
		items.removeAll(o.getItems());
	}
	
	public boolean containsAll(ItemSet o){
		return items.containsAll(o.getItems());
	}
	
	public int size(){
		return items.size();
	}
	
}
