import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


/**
 * Object that generates locally relevant candidate item sets.
 * @author Peter
 *
 */
public class Partition {
	private File sourceFile;
	ArrayList<ArrayList<Candidate>> candidateLists; // by size
	private short size;
	private boolean done; // thing that knows whether we're still making item sets
	
/**
 * Creates a new Partition, with reference to a partition file.
 * @param sourceFile A partition file
 */
public Partition(File sourceFile){
	this.sourceFile = sourceFile;
	size = 0;
	candidateLists = new ArrayList<ArrayList<Candidate>>();
	done = false;
}

	
private void make1ItemSets(){
	short tid = 0;
	HashMap<String,Candidate> oneSets = new HashMap<String,Candidate>();
	try {
		Scanner sc = new Scanner(sourceFile);
		String attributeLine;
		while(sc.hasNextLine()){
			attributeLine = sc.nextLine();
			Iterator<String>attIter = Arrays.asList(attributeLine.split(",")).iterator();
			while(attIter.hasNext()){
				String att = attIter.next();
				if(!oneSets.containsKey(att)){
					Candidate c = new Candidate(att);
					oneSets.put(att, c);
				}
				oneSets.get(att).addTid(tid);
			}
			tid++;
		}
		sc.close();
		ArrayList<Candidate> oneSetList = new ArrayList<Candidate>();
		oneSetList.addAll(oneSets.values());
		candidateLists.add(oneSetList);
		size = tid;
		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	

	
}

/**
 * Makes length 1 itemsets, then joins them (while fruitful) to generate all candidates.
 * Uses the Candidate class's join rules.
 */
public void generateCandidates(){
	make1ItemSets();
	while(!done){
		joinSets();
	}
}

/**
 * After generateCandidates() has been run, returns the result list.
 * @return A list of all candidates.
 */
public ArrayList<Candidate> allCandidates(){
	ArrayList<Candidate> result = new ArrayList<Candidate>();
	Iterator<ArrayList<Candidate>> listIter = candidateLists.iterator();
	while(listIter.hasNext()){
		result.addAll(listIter.next());
	}
	return result;
}

//joins sets of length n to make sets of length n+1
private void joinSets(){
	ArrayList<Candidate> toJoin = candidateLists.get(candidateLists.size()-1);
	ArrayList<Candidate> newCandidates = new ArrayList<Candidate>();
	Candidate a, b, result;
	Iterator<Candidate> iter1, iter2;
	iter1 = toJoin.iterator();
	while(iter1.hasNext()){
		a = iter1.next();
		iter2 = toJoin.iterator();
		while(iter2.hasNext()){
			b = iter2.next();
			if(a.shouldJoin(b)){
				result = a.join(b);
				if(relevant(result)){
					newCandidates.add(result);
				}
			}
		}
	}
	if(newCandidates.size() > 0){
		candidateLists.add(newCandidates);
	}
	else{
		done = true;
	}
}

private boolean relevant(Candidate c){
	return ((double)c.supportCount() / size) >= PartitionApriori.getSupport_threshhold();
}

/**
 * Adds the local support of considered ItemSets to the running tally for those ItemSets.
 * @param sets Set of ItemSets to count support for
 * @throws FileNotFoundException If partition file does not exist
 */
public void countSupport(Set<ItemSet> sets) throws FileNotFoundException{
	Iterator<ItemSet> setsIter;
	String attributeLine;
	Scanner sc = new Scanner(sourceFile);
	while(sc.hasNextLine()){
		attributeLine = sc.nextLine();
		setsIter = sets.iterator();
		while (setsIter.hasNext()){
			ItemSet itst = setsIter.next();
			List<String> attList = Arrays.asList(attributeLine.split(","));
			if(attList.containsAll(itst.getItems())){
				itst.plus();
			}
		}
	}
	sc.close();
		
	}


/**
 * Returns size of this partition, known after candidates have been generated from it.
 * @return The size of this partition
 */
public short getSize() {
	return size;
}

}