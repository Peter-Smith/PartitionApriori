import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;


/**
 * Class that sorts ItemSets by length.
 * It also handles rule generation.
 * @author Peter
 *
 */
public class SetsByLengthContainer {
	private ArrayList<ArrayList<ItemSet>> sets;

public SetsByLengthContainer(){
	sets = new ArrayList<ArrayList<ItemSet>>();
}

public void addAll(Collection<ItemSet> c){
	Iterator<ItemSet> setIter = c.iterator();
	ItemSet itst;
	while(setIter.hasNext()){
		itst = setIter.next();
		add(itst,itst.size());
	}
}

private void add(ItemSet o, int i){
	if(sets.size()< i+1){
		addMoreListsUntil(i);
	}
	sets.get(i-1).add(o);
}

private void addMoreListsUntil(int index){
	ArrayList<ItemSet> newList;
	while(sets.size()<index){
		newList = new ArrayList<ItemSet>();
		sets.add(newList);
	}
}

/**
 * Creates rules by comparing the support for item sets and their frequent subsets.
 * @param target Container to store new rules in
 */
public void generateRule(RuleContainer target){
	for(int length = 2; length <= sets.size(); length++){
		Iterator<ItemSet> iter = fetchOfLength(length).iterator();
		while(iter.hasNext()){
			ItemSet a = iter.next();
			generateRule(a,target);
		}
	}
}

private void generateRule(ItemSet superset, RuleContainer target){
	// Once we have a superset, iterate through
	// the lists of itemsets smaller than it, and create rules from em.
	// And have the RuleContainer reject any undesirable rules.
	// Of course, a must contain b!
	for (int length = superset.size()-1; length>0; length--){
		Iterator<ItemSet> iter = fetchOfLength(length).iterator();
		while(iter.hasNext()){
			ItemSet subset = iter.next();
			if(superset.containsAll(subset)){
				Rule r = new Rule(subset,superset);
				target.add(r);
			}
		}
	}
}
	
	private ArrayList<ItemSet> fetchOfLength(int length){
		return sets.get(length-1);
	}

public String toString(){
	String result="";
	Iterator<ArrayList<ItemSet>> listIter = sets.iterator();
	int i = 1;
	while(listIter.hasNext()){
		result+=(i++)+": "+listIter.next().toString()+"\r\n";
	}
	
	return result;
}

}
