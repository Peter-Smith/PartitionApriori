import java.util.Iterator;


/**
 * @author Peter
 * A rule of form antecedent -> (antecedent,consequent).
 * (Where antecedent is a subset of consequent.)
 * This is effectively unchanged from my assignment 3 and is not novel code.
 */
public class Rule {
	ItemSet antecedent, consequent;
	
	public Rule(ItemSet a, ItemSet b){
		antecedent = a;
		consequent = b.clone();
		consequent.removeAll(antecedent);
		
		
		
	}
	
	
	/**
	 * @return The confidence, support(X,Y)/support(X).
	 */
	public double confidence(){
		return ((double)consequent.getCount() / (double)antecedent.getCount());
	}
	
	/**
	 * @return The support, Support(X,Y).
	 */
	public double support(){
		return consequent.support();
	}
	
	public String toString(){
		String line1, line2, line3;
		line1 = "(Support="+String.format("%.2f",support())+", Confidence="+String.format("%.2f",confidence())+")\r\n";
		line2 = antecedent.getItems().toString()+"\r\n";
		line3 = "----> " + consequent.getItems().toString()+"\r\n";
		return line1+line2+line3;
	}
	
}
