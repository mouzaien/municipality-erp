package com.bkeryah.hr.managedBeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import com.bkeryah.entities.*;
import com.bkeryah.hr.entities.HrTrain01;
import com.bkeryah.hr.entities.HrTrainCat02;
import com.bkeryah.hr.entities.HrTrainCat03;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;

@ManagedBean
@ViewScoped
public class TrainingBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<HrsMasterFile> employees;
	private Training train=new Training();
	private Training tr=new Training();
	private EmpTraining emptr=new EmpTraining();
	private List<HrTrain01> train01;
	private List<HrTrainCat02> train02;
	private List<HrTrainCat03> train03;
	private List<Training> training;
	
	@PostConstruct
	public void init() {
		employees=dataAccessService.getAllEmployeesActive();
		train01=dataAccessService.getAllHrTrain01();
		train02=dataAccessService.getAllHrTrain02();
		train03=dataAccessService.getAllHrTrain03();
		training=dataAccessService.loadAllTraining();
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<HrsMasterFile> getEmployees() {
		return employees;
	}

	public void setEmployees(List<HrsMasterFile> employees) {
		this.employees = employees;
	}

	public Training getTrain() {
		return train;
	}

	public void setTrain(Training train) {
		this.train = train;
	}

	public Training getTr() {
		return tr;
	}

	public void setTr(Training tr) {
		this.tr = tr;
	}

	public List<HrTrain01> getTrain01() {
		return train01;
	}

	public void setTrain01(List<HrTrain01> train01) {
		this.train01 = train01;
	}

	public List<HrTrainCat02> getTrain02() {
		return train02;
	}

	public void setTrain02(List<HrTrainCat02> train02) {
		this.train02 = train02;
	}

	public List<HrTrainCat03> getTrain03() {
		return train03;
	}

	public void setTrain03(List<HrTrainCat03> train03) {
		this.train03 = train03;
	}
	public void loadEmpData() {
//		tr=dataAccessService.getTrainingByEmp(tr.getEmpNo());
		System.out.println("");
	}
	public void saveTraining() {
		dataAccessService.saveTraining(tr);
		MsgEntry.addInfoMessage("تم الحفظ");
		tr=new Training();
	}

	public EmpTraining getEmptr() {
		return emptr;
	}

	public void setEmptr(EmpTraining emptr) {
		this.emptr = emptr;
	}

	public List<Training> getTraining() {
		return training;
	}

	public void setTraining(List<Training> training) {
		this.training = training;
	}
	public void saveEmpTraining() {
		dataAccessService.saveEmpTraining(emptr);
		MsgEntry.addInfoMessage("تم الحفظ");
		emptr=new EmpTraining();
	}
	public void updateTraining() {
		training=dataAccessService.loadAllTraining();
	}
}
