import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


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
