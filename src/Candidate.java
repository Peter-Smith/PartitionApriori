import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;


public class Candidate {
  private TreeSet<String> items;
  private TreeSet<Short> tidList;
  
 public Candidate(){
	 items = new TreeSet<String>();
	 tidList = new TreeSet<Short>();
 }
 
 public Candidate(String attribute){
	 items = new TreeSet<String>();
	 items.add(attribute);
	 tidList = new TreeSet<Short>(); 
 }
 
 public int supportCount(){
	 return tidList.size();
 } 
 public Candidate(TreeSet<String> items, TreeSet<Short> tids){
	 this.items = items;
	 this.tidList = tids;
 }
  
public TreeSet<String> getItems() {
	return items;
}


public TreeSet<Short> getTidList() {
	return tidList;
}

public void addTid(Short s){
	tidList.add(s);
}

public String toString(){
	return items.toString()+" - "+tidList.toString();
}



//Returns a new candidate based on the intersection of two candidate sets.
//Does not pass judgment on their support (since the candidates don't know about their partition.)
// But it does know their count.
public Candidate intersect(Candidate o){
	TreeSet<String> candidateItems = new TreeSet<String>();
	TreeSet<Short> candidateTids = new TreeSet<Short>();
	candidateItems.addAll(items);
	candidateItems.addAll(o.getItems());
	candidateTids.addAll(tidList);
	candidateTids.retainAll(o.getTidList());
	Candidate nc = new Candidate(candidateItems,candidateTids);
	return nc;
}

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