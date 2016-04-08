import java.util.ArrayList;
import java.util.Iterator;


/**
 * @author Peter
 * A container for rules that rejects rules with too low confidence.
 * This is effectively unchanged from my assignment 3 and is not novel code.
 */
public class RuleContainer {
	/**
	 * @param confidence Confidence threshhold.
	 */
	public RuleContainer(double confidence) {
		super();
		this.rules = new ArrayList<Rule>();
		this.confidence = confidence;
	}

	private ArrayList<Rule> rules;
	private double confidence;
	
	/**
	 * @param r Rule to add if its confidence meets the confidence threshhold
	 */
	public void add(Rule r){
		if(r.confidence()>=confidence){
			rules.add(r);
		}
	}
	public String toString(){
		String result = "Rules: \r\n";
	    Iterator<Rule> iter = rules.iterator();
		int i = 1;
		while(iter.hasNext()){
			result = result + "Rule#"+i+": "+iter.next().toString()+"\r\n";
			i++;
		}
		return result;
	}
	public double getConfidence() {
		return confidence;
	}
	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}
}
