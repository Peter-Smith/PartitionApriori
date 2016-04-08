import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class PartitionApriori {
	private static String partitionsLocation, rulesFileLocation;
	
	private static double support_threshhold, confidence_threshhold;

	public static double getSupport_threshhold() {
		return support_threshhold;
	}

	public static void setSupport_threshhold(double support_threshhold) {
		PartitionApriori.support_threshhold = support_threshhold;
	}
	
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the directory under which partition files (outputN.csv) are contained:");
		partitionsLocation = sc.nextLine()+"\\output";
		System.out.println("Write rules file to what location and name?:");
		rulesFileLocation = sc.nextLine();
		System.out.println("Enter minimum support:");
		support_threshhold = sc.nextDouble();
		System.out.println("Enter minimum confidence:");
		confidence_threshhold = sc.nextDouble();
		
		File rulesFile = new File(rulesFileLocation);
		try {
			rulesFile.createNewFile();
			FileWriter fr = new FileWriter(rulesFile);

			AllPartitions ap = new AllPartitions(partitionsLocation);
			ap.scanPartitions();
			ap.countFromPartitions();
			System.out.println(ap.getAllCandidates().toString());
			ap.testCandidates();		
			SetsByLengthContainer s = new SetsByLengthContainer();
			RuleContainer rc = new RuleContainer(confidence_threshhold);
			s.addAll(ap.getAllCandidates());
			s.generateRule(rc);
		
			fr.write(rc.toString());
			fr.close();
			System.out.println("AM Complete, from "+AllPartitions.getTotalSize()+" records.");
		} catch (IOException e) {
			System.out.println("File at "+rulesFileLocation+" could not be created or written to.");
		}
		
	}
}
