import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;


/**
 * Class managing I/O to a series of different partition files.
 * Capable of generating candidates, as well as receiving final counts on those candidates.
 * @author Peter
 *
 */
public class AllPartitions {
	private HashSet<ItemSet> allCandidates;
	private String fileLocation;
	private static long totalSize;
	
	
/**
 * Creates an AllPartitions lookup object with reference to a series of files of form fileLocation,
 * where fileLocation+#+.csv will find files.
 * @param fileLocation
 */
public AllPartitions(String fileLocation){
	this.fileLocation = fileLocation;
	allCandidates = new HashSet<ItemSet>();
	totalSize = 0;
}



/**
 * Scans partitions to generate candidates.
 */
public void scanPartitions(){
	int fileNum = 0;
	boolean keepGoing = true;
	System.out.println("Scanning...");
	while(keepGoing){
		System.out.println(fileNum);
		keepGoing = scanPartition(fileLocation,fileNum);
		fileNum++;
	}
	
}

/**
 * After candidates have been generated, checks the final count on all candidates.
 */
public void countFromPartitions(){
	int fileNum = 0;
	boolean keepGoing = true;
	System.out.println("Counting...");
	while(keepGoing){
		System.out.println(fileNum);
		keepGoing = countFromPartition(fileLocation,fileNum);
		fileNum++;
	}
}

private boolean scanPartition(String location, int fileNum){
	File f = new File(location+fileNum+".csv");
	Iterator<Candidate> candIter;
	ItemSet itst;
	if(f.exists()){
		Partition p = new Partition(f);
		p.generateCandidates();
		totalSize += p.getSize();
		candIter = p.allCandidates().iterator();
		while(candIter.hasNext()){
			itst = new ItemSet(candIter.next());
			allCandidates.add(itst);
		}
		return true;
	}
	else{
		return false;
	}
}

private boolean countFromPartition(String location, int fileNum){
	File f = new File(location+fileNum+".csv");
	if(f.exists()){
		Partition p = new Partition(f);
		try {
			p.countSupport(allCandidates);
		} catch (FileNotFoundException e) {
			// since we just checked existence, this likely won't trip
			e.printStackTrace();
		}
		return true;
	}
	else{
		return false;
	}
}

// keep only candidates that pass minimum support
/**
 * Rejects candidates that do not meet the minimum support.
 * Only use after counting, or will discard all candidates.
 */
public void testCandidates(){
	HashSet<ItemSet> oldSet = allCandidates;
	allCandidates = new HashSet<ItemSet>();
	Iterator<ItemSet> itemIter = oldSet.iterator();
	ItemSet itst;
	while(itemIter.hasNext()){
		itst = itemIter.next();
		if(( (double) itst.getCount() / totalSize) > PartitionApriori.getSupport_threshhold()){
			allCandidates.add(itst);
		}
	}
}

/**
 * Returns all candidates, along with their counts, if they've been counted.
 * (Else, counts will be 0.)
 * @return Set of all candidates
 */
public HashSet<ItemSet> getAllCandidates() {
	return allCandidates;
}



/**
 * @return Total size found by last scan (by any AllPartitions object.)
 */
public static long getTotalSize() {
	return totalSize;
}

}