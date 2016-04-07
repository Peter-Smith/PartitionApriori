import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;


public class AllPartitions {
	private HashSet<ItemSet> allCandidates;
	private String fileLocation;
	private static long totalSize;
	
	
public AllPartitions(String fileLocation){
	this.fileLocation = fileLocation;
	allCandidates = new HashSet<ItemSet>();
	totalSize = 0;
}



public void scanPartitions(){
	int fileNum = 0;
	boolean keepGoing = true;
	while(keepGoing){
		keepGoing = scanPartition(fileLocation,fileNum);
		fileNum++;
	}
	
}

public void countFromPartitions(){
	int fileNum = 0;
	boolean keepGoing = true;
	while(keepGoing){
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

public HashSet<ItemSet> getAllCandidates() {
	return allCandidates;
}



public static long getTotalSize() {
	return totalSize;
}

}