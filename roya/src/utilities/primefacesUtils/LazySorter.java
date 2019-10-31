package utilities.primefacesUtils;

import java.util.Comparator;
import org.primefaces.model.SortOrder;

import com.bkeryah.bean.UserMailObj;
 
public class LazySorter implements Comparator<UserMailObj> {
 
    private String sortField;
     
    private SortOrder sortOrder;
     
    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }
 
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public int compare(UserMailObj mail1, UserMailObj mail2) {
        try {
            Object value1 = UserMailObj.class.getField(this.sortField).get(mail1);
            Object value2 = UserMailObj.class.getField(this.sortField).get(mail2);
 
            int value = ((Comparable)value1).compareTo(value2);
             
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }
}