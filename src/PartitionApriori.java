
public class PartitionApriori {
	public final static String PARTITIONS_LOCATION = "D:\\Scrap\\nycTaxiTripData2013\\output\\output";
	
	private static double support_threshhold;

	public static double getSupport_threshhold() {
		return support_threshhold;
	}

	public static void setSupport_threshhold(double support_threshhold) {
		PartitionApriori.support_threshhold = support_threshhold;
	}
	
	public static void main(String[] args){
		support_threshhold = 0.3;

		AllPartitions ap = new AllPartitions(PARTITIONS_LOCATION);
		ap.scanPartitions();
		ap.countFromPartitions();
		System.out.println(ap.getAllCandidates().toString());
		ap.testCandidates();
		System.out.println(ap.getAllCandidates().toString());
		System.out.println(ap.getTotalSize());
		
	}
}
