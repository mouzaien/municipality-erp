/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.bean;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

/**
 *
 * @author IbrahimDarwiesh
 */
public class UserMailLazyList extends LazyDataModel<UserMailObj> {

    private static final long serialVersionUID = 1L;
    private List<UserMailObj> Mails;

    public UserMailLazyList(List<UserMailObj> Mail2) {
        this.Mails = Mail2;
    }

    
    @Override
    public List<UserMailObj> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
        return super.load(first, pageSize, multiSortMeta, filters); //To change body of generated methods, choose Tools | Templates.
    }


    
    
    @Override
    public List<UserMailObj> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
    	   List<UserMailObj> data = new ArrayList<UserMailObj>();
    	   
           //filter
           for(UserMailObj mail : Mails) {
               boolean match = true;
    
               if (filters != null) {
                   for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                       try {
                           String filterProperty = it.next();
                           Object filterValue = filters.get(filterProperty);
                           String fieldValue = String.valueOf(mail.getClass().getField(filterProperty).get(mail));
    
                           if(filterValue == null || fieldValue.contains(filterValue.toString())) {
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
//           if(sortField != null) {
//               Collections.sort(data, new LazySorter(sortField, sortOrder));
//           }
    
           //rowCount
           int dataSize = data.size();
           this.setRowCount(dataSize);
    
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

    /**
     * @return the Mails
     */
    public List<UserMailObj> getMails() {
        return Mails;
    }

    /**
     * @param Mails the Mails to set
     */
    public void setMails(List<UserMailObj> Mails) {
        this.Mails = Mails;
    }

}
