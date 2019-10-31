/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.managedBean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.bkeryah.dao.DataAccess;
import com.bkeryah.dao.DataAccessImpl;

/**
 *
 * @author Ibrahimdarwiesh
 */
@ManagedBean(name = "empsumbean")
@ViewScoped
public class empSumBean implements Serializable {

    /**
     * Creates a new instance of empSumBean
     */
    private BarChartModel barModel;
    DataAccess da  = new DataAccessImpl();
    
    public empSumBean() {

    }

    public BarChartModel getBarModel() {
        BarChartModel model = new BarChartModel();

        ChartSeries boys = new ChartSeries();
        boys.setLabel("أولاد");
        boys.set(2004, 120);
        boys.set("2005", 100);
        boys.set("2006", 44);
        boys.set("2007", 150);
        boys.set("2008", 25);
        model.addSeries(boys);

        return model;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }

    private void createBarModel() {

        this.barModel.setTitle("Bar Chart");
        this.barModel.setAnimate(true);
        this.barModel.setLegendPosition("se");

        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Gender");

        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Births");
        yAxis.setMin(0);
        yAxis.setMax(1000);
    }
    /**
     * ***************************************************
     */
    
     public void itemSelect(ItemSelectEvent event){
     this.da.showErrMessage(""+event.getItemIndex());
          
   }

}
