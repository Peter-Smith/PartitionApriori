import java.util.TreeSet;


/**
 * A candidate itemset being considered by one partition.
 * Tracks items and a transaction ID list.
 * @author Peter
 *
 */
public class Candidate {
  private TreeSet<String> items;
  private TreeSet<Short> tidList;
  
 /**
 * Creates an empty candidate.
 */
public Candidate(){
	 items = new TreeSet<String>();
	 tidList = new TreeSet<Short>();
 }
 
 /**
  * Creates a candidate of length 1 containing a single item.
 * @param item One item.
 */
public Candidate(String item){
	 items = new TreeSet<String>();
	 items.add(item);
	 tidList = new TreeSet<Short>(); 
 }
 
 /**
 * @return The cardinality of the tidList (and thus, local support)
 */
public int supportCount(){
	 return tidList.size();
 } 
 /**
  * Creates a candidate with supplied item list items and supplied TIDlist tids.
 * @param items
 * @param tids
 */
public Candidate(TreeSet<String> items, TreeSet<Short> tids){
	 this.items = items;
	 this.tidList = tids;
 }
  
/**
 * @return The set of items in that candidate
 */
public TreeSet<String> getItems() {
	return items;
}


/**
 * @return The transaction ID list
 */
public TreeSet<Short> getTidList() {
	return tidList;
}

/**
 * Adds a transaction ID this candidate is found in.
 * In our case, these are line numbers.
 * @param s TID to add
 */
public void addTid(Short s){
	tidList.add(s);
}

public String toString(){
	return items.toString()+" - "+tidList.toString();
}



//Returns a new candidate based on the intersection of two candidate sets.
//Does not pass judgment on their support (since the candidates don't know about their partition.)
// But it does know their count.
/**
 * Returns a new candidate based on the intersection of two candidate sets.
 * Items in new candidate are the union of the two original,
 * TIDlist is the intersection of the two original.
 * @param o Other candidate
 * @return New candidate from intersetion
 */
public Candidate join(Candidate o){
	TreeSet<String> candidateItems = new TreeSet<String>();
	TreeSet<Short> candidateTids = new TreeSet<Short>();
	candidateItems.addAll(items);
	candidateItems.addAll(o.getItems());
	candidateTids.addAll(tidList);
	candidateTids.retainAll(o.getTidList());
	Candidate nc = new Candidate(candidateItems,candidateTids);
	return nc;
}

/**
 * Returns true if a join between the two candidates would be useful.
 * This uses the traditional AP rules (lengths same, match on all but last, last1 < last2)
 * Plus another - items of the form X=Y, X=Z are mutually exclusive in our application (counts will be 0)
 * So we reject any joins that would have two of those items.
 * @param o Other candidate to consider joining with
 * @return true if join is fruitful in AP
 */
public boolean shouldJoin(Candidate o){
	boolean shouldJoin = false;
	TreeSet<String> a,b;
	a = (TreeSet<String>)items.clone();
	b = (TreeSet<String>)o.getItems().clone();
	String lastA = a.last();
	String lastB = b.last();
	a.remove(lastA);
	b.remove(lastB);
	if(a.equals(b)){
		if(lastA.compareTo(lastB)<0){
			shouldJoin = !lastA.split("=")[0].equals(lastB.split("=")[0]);
		}
	}
	
	
	return shouldJoin;
}

}