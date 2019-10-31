package utilities.primefacesUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.bkeryah.bean.UserMailObj;
import com.bkeryah.service.IDataAccessService;

import utilities.Utils;
 
public class LazyMailDataModel extends LazyDataModel<UserMailObj> {
     
    private List<UserMailObj> datasource;
     
    public LazyMailDataModel(List<UserMailObj> datasource) {
        this.datasource = datasource;
        this.setRowCount(datasource.size());
    }
     
    @Override
    public UserMailObj getRowData(String rowKey) {
        for(UserMailObj mail : datasource) {
            if(mail.getWrkId().equals(rowKey))
                return mail;
        }
 
        return null;
    }
 
    @Override
    public Object getRowKey(UserMailObj mail) {
        return mail.getWrkId();
    }
 
    @Override
    public List<UserMailObj> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        List<UserMailObj> data = new ArrayList<UserMailObj>();
        
        
        
         List<UserMailObj> result = datasource.stream().skip(first).limit(pageSize).collect(Collectors.toList());//loadMail(first, pageSize);
        
 
        //filter
        for(UserMailObj mail : result) {
            boolean match = true;
 
            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        String fieldValue = String.valueOf(mail.getClass().getField(filterProperty).get(mail));
 
                        if(filterValue == null || fieldValue.startsWith(filterValue.toString())) {
                            match = true;
                    }
                    else {
                            match = false;
                            break;
                        }
                    } catch(Exception e) {
                        match = false;
                    }
                }
            }
 
            if(match) {
                data.add(mail);
            }
        }
 
        //sort
        if(sortField != null) {
            Collections.sort(data, new LazySorter(sortField, sortOrder));
        }
 
        //rowCount
        int dataSize = data.size();
        /*this.setRowCount(dataSize);*/
 
        //paginate
        if(dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            }
            catch(IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        }
        else {
            return data;
        }
    }

//	private List<UserMailObj> loadMail(int first, int pageSize) {
//		return dataAccessService.findEmployeeInboxNew(first, pageSize, Utils.findCurrentUser().getUserId());
//	}
}
