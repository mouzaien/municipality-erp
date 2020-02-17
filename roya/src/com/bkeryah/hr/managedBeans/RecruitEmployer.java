package com.bkeryah.hr.managedBeans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.AjaxBehaviorEvent;

import com.bkeryah.entities.ArcPeople;
import com.bkeryah.entities.Establishment;
import com.bkeryah.entities.EstablishmentId;
import com.bkeryah.entities.HRArea;
import com.bkeryah.entities.HRCity;
import com.bkeryah.entities.HRCountry;
import com.bkeryah.entities.HRNationality;
import com.bkeryah.entities.HRReligion;
import com.bkeryah.entities.HRTitles;
import com.bkeryah.entities.HrQualification;
import com.bkeryah.entities.HrsEmpHistorical;
import com.bkeryah.entities.HrsMasterFile;
import com.bkeryah.entities.HrsSalaryScaleDgrs;
import com.bkeryah.entities.PayBank;
import com.bkeryah.entities.SysBirthCountry;
import com.bkeryah.entities.SysCategoryEmployer;
import com.bkeryah.entities.SysGraduatePlace;
import com.bkeryah.entities.SysNationality;
import com.bkeryah.entities.SysQualification;
import com.bkeryah.entities.SysReligion;
import com.bkeryah.entities.SysSpecialization;
import com.bkeryah.entities.WrkDept;
import com.bkeryah.hr.entities.HrsJobCreation;
import com.bkeryah.service.IDataAccessService;
import com.sun.faces.util.Util;

import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

@ManagedBean
@ViewScoped
// public class RecruitEmployer extends GrigAndHijriCalender {
public class RecruitEmployer {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	HrsMasterFile hrmasfile = new HrsMasterFile();
	List<ArcPeople> listEmployer;
	List<SysCategoryEmployer> listCategory;
	List<SysReligion> listReligion;

	List<SysQualification> listQualified;
	List<SysBirthCountry> listBirtCountry;
	List<SysGraduatePlace> listGraduat;
	List<SysNationality> listNationality;
	List<SysSpecialization> listSpecial;
	List<PayBank> listBank;
	List<WrkDept> listDept;
	List<HrsJobCreation> listJob;
	private Integer createId;
	private Integer dgreeId;
	List<HrsMasterFile> listEmployersWorks;
	List<HrsSalaryScaleDgrs> listScaleDgree;
	HrsJobCreation selectJob = new HrsJobCreation();
	HrsSalaryScaleDgrs selectDegree = new HrsSalaryScaleDgrs();
	Integer empno = 0;
	Integer renderJobStatus;
	HrsEmpHistorical EmpHistoric = new HrsEmpHistorical();
	private boolean arr;
	private boolean updateMode = false;

	private boolean higriMode = true;
	private boolean higriMode2 = true;

	private String birthDate_H;
	private Date birthDate_G;
	private String firstApplicationDate;
	private Date firstApplicationDate_G;
	private String firstServiceDate;
	private Date firstServiceDate_G;
	private String incomDate;
	private Date incomDate_G;
	private List<HRTitles> titles;
	private List<HRCountry> countries;
	private List<HRCity> cities;
	private List<HrQualification> qualific;
	private List<Establishment> establishment;
	private Integer establishid;
	private List<HRReligion> religions;
	private List<HRNationality> nationalities;
	private Establishment establish;
	
	@PostConstruct
	public void init() {
		try {
			
			listCategory = dataAccessService.loadCategoryEmployer();
			listReligion = dataAccessService.loadReligionEmployer();
			listQualified = dataAccessService.loadQualifiedemployer();
			listBirtCountry = dataAccessService.loadBirthCountry();
			listGraduat = dataAccessService.loadGraduatEmploye();
			listNationality = dataAccessService.loadNationalEmploye();
			listSpecial = dataAccessService.loadSpecialEmploye();
			listBank = dataAccessService.loadbank();
			listDept = dataAccessService.findAllDepartments();
			listEmployersWorks = dataAccessService.findAllEmplyersWorks();
			// listJob = dataAccessService.loadAllJobs();
			// System.out.println("listJob.size() =======>>" + listJob.size());
			Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
			if (flash.get("employeeNumber") != null) {
				empno = (Integer) flash.get("employeeNumber");
				loadEmployerData();

			}
			listScaleDgree = dataAccessService.loadScaleDgree(EmpHistoric.getRankNumber());
			loadDetailClass();
			titles=dataAccessService.getAllTitles();
			countries=dataAccessService.getAllCountry();
			cities=dataAccessService.getAllCities();
			qualific=dataAccessService.getAllQualification();
			establishment=dataAccessService.getAllEstablishment();
			religions=dataAccessService.getAllReligions();
			nationalities=dataAccessService.getAllNationalities();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateRankCode() {
		updateMode = true;
	}

	public void loadJobsByCat() {
		System.out.println("hrmasfile.getCactegoryId() =======>>" + hrmasfile.getCactegoryId());
		setRenderJobStatus(1);
		if (hrmasfile.getCactegoryId() == 9)
			setArr(true);
		listJob = dataAccessService.loadHrJob(hrmasfile.getCactegoryId(), MyConstants.JOBEMPTY,
				EmpHistoric.getRankNumber());
	}

	public void loadEmployerData() {
		hrmasfile = dataAccessService.loadEmployerData(getEmpno());

		EmpHistoric = dataAccessService.loadEmployerDataHistorcial(getEmpno());
		loadDates();
		// updateDatesToUpload();
		setRenderJobStatus(1);
		listJob = dataAccessService.loadHrJob(hrmasfile.getCactegoryId(), MyConstants.JOBEMPTY,
				EmpHistoric.getRankNumber());

		// setCreateId(206);

	}

	public void loadRankJobNoByCode() {
		selectDegree = null;
		selectJob = (HrsJobCreation) dataAccessService.loadSelectedJob(EmpHistoric.getJobNumber(),
				MyConstants.JOBEMPTY);
		EmpHistoric.setRankNumber(selectJob.getRankCode());
		EmpHistoric.setJobcode(selectJob.getJobCode());
		// EmpHistoric.setClassNumber(createId);
		// EmpHistoric.setJobNumber(selectJob.getCreateId());
		listScaleDgree = dataAccessService.loadScaleDgree(selectJob.getRankCode());

	}

	public void convertHijriToGrig(AjaxBehaviorEvent abe) {
		// DateFormat read = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("******************Grig******************");
		try {
			System.out.println("BirthDateGrig---------*******" + hrmasfile.getBirthDateGrig());
			// hrmasfile.setBirthDateGrig(read.format(Utils.convertHDateToGDate(hrmasfile.getBirthDateGrig())));
			// //
			// hrmasfile.setBirthDateGrig(read.format(Utils.convertDateToString(hrmasfile.getBirthDate())));
			System.out.println("BirthDateGrig---------*******" + hrmasfile.getBirthDateGrig());

		} catch (Exception e) {
			e.printStackTrace();
		}

		// System.out.println("******************Hijri******************");
		// try {
		// hrmasfile.setBirthDateHij(
		// read.format(HijriCalendarUtil.ConvertgeorgianDatetoHijriDate(hrmasfile.getBirthDateGrig())));
		// System.out.println("BirthDateGrig---------*******" +
		// hrmasfile.getBirthDateHij());
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

	}

	////////////////////////////
	// public void convertDateToHijri(DateTime dtISO,){}

	//////////////////////
	public void loadDetailClass() {

		// selectDegree = dataAccessService.loadSelectedDegree(getDgreeId());
		selectDegree = dataAccessService.loadSelectedDegree(EmpHistoric.getClassNumber(), EmpHistoric.getRankNumber());
		if (selectDegree != null) {
			EmpHistoric.setBasicSalary(selectDegree.getFirstSalary());
			EmpHistoric.setMandateInner(selectDegree.getMandatorIn());
			EmpHistoric.setTransferSalary(selectDegree.getTransferSalary());
		}
	}

	// 1013573652

	ArcPeople arcpeop = new ArcPeople();

	public void loadnameEmployerbynationid() {
		if (hrmasfile.getCactegoryId() == null) {
			MsgEntry.addErrorMessage(MsgEntry.ERROR_CATCODE_EMPLOYER);

		} else {
			int nationalIsFound = dataAccessService.nationalIsFound(hrmasfile.getNationalNumber(),
					hrmasfile.getCactegoryId());
			if (nationalIsFound > 0) {

				MsgEntry.addErrorMessage(MsgEntry.EMPLOYERFOUND);
			} else {
				System.out.println(hrmasfile.getCactegoryId());
				listEmployer = dataAccessService.loadnameEmployerbynationid(new Long(hrmasfile.getNationalNumber()));
				if (!listEmployer.isEmpty()) {
					arcpeop = listEmployer.get(0);
					hrmasfile.setFirstName(arcpeop.getFirstName());
					hrmasfile.setSecondName(arcpeop.getSeconedName());
					hrmasfile.setThirdName(arcpeop.getThirdName());
					hrmasfile.setForthName(arcpeop.getFourthName());
				}
			}
		}
	}

	public String saveEmployer() throws ParseException {
		establish=dataAccessService.getEstablishmentById(hrmasfile.getSchoolId());
		hrmasfile.setSchool(establish.getSchool());
		hrmasfile.setSchoolLoc(establish.getLocation());
		EmpHistoric.setCATegoryId(hrmasfile.getCactegoryId());
//		EmpHistoric.setJobNumber(selectJob.getJobNumber());
//		EmpHistoric.setJobcode(selectJob.getJobCode());
		// EmpHistoric.setClassNumber(getDgreeId());
		// EmpHistoric.setBasicSalary(selectDegree.getFirstSalary());
		// EmpHistoric.setMandateInner(selectDegree.getMandatorIn());
		EmpHistoric.setFlag(1);
		// EmpHistoric.setTransferSalary(selectDegree.getTransferSalary());
		selectJob.setJobstatus(3);
		sortDates();
		if (hrmasfile.getEmployeNumber() == null) {
			empno = dataAccessService.saveEmployer(hrmasfile, EmpHistoric, selectJob);
			MsgEntry.addInfoMessage(MsgEntry.SUCCESS_SAVE_EMPLOYER);
			System.out.println("تم الحفظ");
		}
		// if (HrsMasterFile) event.get
		else {
			// dataAccessService.updateObject(hrmasfile);
			dataAccessService.updateObject(hrmasfile, EmpHistoric);
			// dataAccessService.updateObject(hrmasfile, EmpHistoric,
			// selectJob);
			MsgEntry.addInfoMessage(MsgEntry.SUCCESS_UPDATE_EMPLOYER);
			System.out.println("تم التعديل");

		}

		return "";
	}
	////////////////////////////////////////

	public void sortDates() throws ParseException {

		// hrmasfile.setBirthDateGrig(Utils.convertDateToString(hrmasfile.getBirthDate()));
		// EmpHistoric.setIncomDate(Utils.convertDateToString(EmpHistoric.getIncomDateGrig()));
		// hrmasfile.setFirstApplicationDate(Utils.convertDateToString(hrmasfile.getFirstApplicationGrigDate()));
		// hrmasfile.setFirstServiceDate(Utils.convertDateToString(hrmasfile.getFirstServiceGrigDate()));
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		try {

			if (higriMode) {
				birthDate_G = Utils.convertHDateToGDate(birthDate_H);

			} else {
				birthDate_H = Utils.grigDatesConvert(birthDate_G);
			}

			if (higriMode2) {
				firstApplicationDate_G = Utils.convertHDateToGDate(firstApplicationDate);
				firstServiceDate_G = Utils.convertHDateToGDate(firstServiceDate);
				incomDate_G = Utils.convertHDateToGDate(incomDate);

			} else {
				firstApplicationDate = Utils.grigDatesConvert(firstApplicationDate_G);
				firstServiceDate = Utils.grigDatesConvert(firstServiceDate_G);
				incomDate = Utils.grigDatesConvert(incomDate_G);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrmasfile.setBirthDateHij(birthDate_H);
		hrmasfile.setBirthDateGrig(Utils.convertDateToString(birthDate_G));
		hrmasfile.setFirstApplicationDate(firstApplicationDate);
		hrmasfile.setFirstServiceDate(firstServiceDate);
		EmpHistoric.setIncomDate(incomDate);
	}

	/////////////////////////////////////////////
	public void loadDates() {

		try {
			if (hrmasfile.getBirthDateHij() != null) {
				birthDate_H = hrmasfile.getBirthDateHij();
				birthDate_G = Utils.convertGregStringToDate(hrmasfile.getBirthDateGrig());
			}
			if (hrmasfile.getFirstApplicationDate() != null) {
				firstApplicationDate = hrmasfile.getFirstApplicationDate();
				firstApplicationDate_G = Utils.convertHDateToGDate(hrmasfile.getFirstApplicationDate());
			}
			if (hrmasfile.getFirstServiceDate() != null) {
				firstServiceDate = hrmasfile.getFirstServiceDate();
				firstServiceDate_G = Utils.convertHDateToGDate(hrmasfile.getFirstServiceDate());
			}
			if (EmpHistoric.getIncomDate() != null) {
				incomDate = EmpHistoric.getIncomDate();
				incomDate_G = Utils.convertHDateToGDate(EmpHistoric.getIncomDate());
			}

		} catch (

		Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	/////////////////////////////////////////////
	public void updateDatesToUpload() {

		if (hrmasfile.getBirthDateGrig() != null) {
			try {
				hrmasfile.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(hrmasfile.getBirthDateGrig()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (EmpHistoric.getIncomDate() != null) {
			try {
				EmpHistoric.setIncomDateGrig(new SimpleDateFormat("dd/MM/yyyy").parse(EmpHistoric.getIncomDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (hrmasfile.getFirstApplicationDate() != null) {
			try {
				hrmasfile.setFirstApplicationGrigDate(
						new SimpleDateFormat("dd/MM/yyyy").parse(hrmasfile.getFirstApplicationDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (hrmasfile.getFirstServiceDate() != null) {
			try {
				hrmasfile.setFirstServiceGrigDate(
						new SimpleDateFormat("dd/MM/yyyy").parse(hrmasfile.getFirstServiceDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

	}

	////////////////////////////////////////
	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<ArcPeople> getListEmployer() {
		return listEmployer;
	}

	public void setListEmployer(List<ArcPeople> listEmployer) {
		this.listEmployer = listEmployer;
	}

	public HrsMasterFile getHrmasfile() {
		return hrmasfile;
	}

	public void setHrmasfile(HrsMasterFile hrmasfile) {
		this.hrmasfile = hrmasfile;
	}

	public ArcPeople getArcpeop() {
		return arcpeop;
	}

	public void setArcpeop(ArcPeople arcpeop) {
		this.arcpeop = arcpeop;
	}

	public List<SysCategoryEmployer> getListCategory() {
		return listCategory;
	}

	public void setListCategory(List<SysCategoryEmployer> listCategory) {
		this.listCategory = listCategory;
	}

	public List<SysReligion> getListReligion() {
		return listReligion;
	}

	public void setListReligion(List<SysReligion> listReligion) {
		this.listReligion = listReligion;
	}

	public List<SysQualification> getListQualified() {
		return listQualified;
	}

	public void setListQualified(List<SysQualification> listQualified) {
		this.listQualified = listQualified;
	}

	public List<SysBirthCountry> getListBirtCountry() {
		return listBirtCountry;
	}

	public void setListBirtCountry(List<SysBirthCountry> listBirtCountry) {
		this.listBirtCountry = listBirtCountry;
	}

	public List<SysGraduatePlace> getListGraduat() {
		return listGraduat;
	}

	public void setListGraduat(List<SysGraduatePlace> listGraduat) {
		this.listGraduat = listGraduat;
	}

	public List<SysNationality> getListNationality() {
		return listNationality;
	}

	public void setListNationality(List<SysNationality> listNationality) {
		this.listNationality = listNationality;
	}

	public List<SysSpecialization> getListSpecial() {
		return listSpecial;
	}

	public void setListSpecial(List<SysSpecialization> listSpecial) {
		this.listSpecial = listSpecial;
	}

	public List<PayBank> getListBank() {
		return listBank;
	}

	public void setListBank(List<PayBank> listBank) {
		this.listBank = listBank;
	}

	public List<WrkDept> getListDept() {
		return listDept;
	}

	public void setListDept(List<WrkDept> listDept) {
		this.listDept = listDept;
	}

	public List<HrsJobCreation> getListJob() {
		return listJob;
	}

	public void setListJob(List<HrsJobCreation> listJob) {
		this.listJob = listJob;
	}

	public Integer getCreateId() {
		return createId;
	}

	public void setCreateId(Integer createId) {
		this.createId = createId;
	}

	public HrsJobCreation getSelectJob() {
		return selectJob;
	}

	public void setSelectJob(HrsJobCreation selectJob) {
		this.selectJob = selectJob;
	}

	public List<HrsSalaryScaleDgrs> getListScaleDgree() {
		return listScaleDgree;
	}

	public void setListScaleDgree(List<HrsSalaryScaleDgrs> listScaleDgree) {
		this.listScaleDgree = listScaleDgree;
	}

	public Integer getDgreeId() {
		return dgreeId;
	}

	public void setDgreeId(Integer dgreeId) {
		this.dgreeId = dgreeId;
	}

	public HrsSalaryScaleDgrs getSelectDegree() {
		return selectDegree;
	}

	public void setSelectDegree(HrsSalaryScaleDgrs selectDegree) {
		this.selectDegree = selectDegree;
	}

	public Integer getEmpno() {
		return empno;
	}

	public void setEmpno(Integer empno) {
		this.empno = empno;
	}

	public HrsEmpHistorical getEmpHistoric() {
		return EmpHistoric;
	}

	public void setEmpHistoric(HrsEmpHistorical empHistoric) {
		EmpHistoric = empHistoric;
	}

	public List<HrsMasterFile> getListEmployersWorks() {
		return listEmployersWorks;
	}

	public void setListEmployersWorks(List<HrsMasterFile> listEmployersWorks) {
		this.listEmployersWorks = listEmployersWorks;
	}

	public Integer getRenderJobStatus() {
		return renderJobStatus;
	}

	public void setRenderJobStatus(Integer renderJobStatus) {
		this.renderJobStatus = renderJobStatus;
	}

	public boolean isArr() {
		return arr;
	}

	public void setArr(boolean arr) {
		this.arr = arr;
	}

	public boolean isUpdateMode() {
		return updateMode;
	}

	public void setUpdateMode(boolean updateMode) {
		this.updateMode = updateMode;
	}

	public boolean isHigriMode() {
		return higriMode;
	}

	public void setHigriMode(boolean higriMode) {
		this.higriMode = higriMode;
	}

	public String getBirthDate_H() {

		try {
			if (hrmasfile.getBirthDateGrig() != null) {
				birthDate_H = Utils.grigDatesConvert(Utils.convertGregStringToDate(hrmasfile.getBirthDateGrig()));
			} else if (hrmasfile.getBirthDateHij() != null) {
				birthDate_H = hrmasfile.getBirthDateHij();

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return birthDate_H;
	}

	public void setBirthDate_H(String birthDate_H) {
		this.birthDate_H = birthDate_H;
	}

	public Date getBirthDate_G() {
		try {
			if (hrmasfile.getBirthDateGrig() != null) {
				birthDate_G = Utils.convertGregStringToDate(hrmasfile.getBirthDateGrig());
			} else if (hrmasfile.getBirthDateHij() != null) {
				birthDate_G = Utils.convertHDateToGDate(hrmasfile.getBirthDateHij());

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return birthDate_G;
	}

	public void setBirthDate_G(Date birthDate_G) {
		this.birthDate_G = birthDate_G;
	}

	public String getFirstApplicationDate() {
		if (hrmasfile.getFirstApplicationDate() != null)
			firstApplicationDate = hrmasfile.getFirstApplicationDate();

		return firstApplicationDate;
	}

	public void setFirstApplicationDate(String firstApplicationDate) {
		this.firstApplicationDate = firstApplicationDate;
	}

	public Date getFirstApplicationDate_G() {
		if (hrmasfile.getFirstApplicationDate() != null)
			try {
				firstApplicationDate_G = Utils.convertHDateToGDate(hrmasfile.getFirstApplicationDate());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return firstApplicationDate_G;
	}

	public void setFirstApplicationDate_G(Date firstApplicationDate_G) {
		this.firstApplicationDate_G = firstApplicationDate_G;
	}

	public String getFirstServiceDate() {
		if (hrmasfile.getFirstServiceDate() != null) {
			firstServiceDate = hrmasfile.getFirstServiceDate();
		}

		return firstServiceDate;
	}

	public void setFirstServiceDate(String firstServiceDate) {
		this.firstServiceDate = firstServiceDate;
	}

	public Date getFirstServiceDate_G() {
		if (hrmasfile.getFirstServiceDate() != null) {

			try {
				firstServiceDate_G = Utils.convertHDateToGDate(hrmasfile.getFirstServiceDate());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return firstServiceDate_G;
	}

	public void setFirstServiceDate_G(Date firstServiceDate_G) {
		this.firstServiceDate_G = firstServiceDate_G;
	}

	public String getIncomDate() {
		if (EmpHistoric.getIncomDate() != null) {
			incomDate = EmpHistoric.getIncomDate();
		}
		return incomDate;
	}

	public void setIncomDate(String incomDate) {
		this.incomDate = incomDate;
	}

	public Date getIncomDate_G() {
		if (EmpHistoric.getIncomDate() != null) {

			try {
				incomDate_G = Utils.convertHDateToGDate(EmpHistoric.getIncomDate());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return incomDate_G;
	}

	public void setIncomDate_G(Date incomDate_G) {
		this.incomDate_G = incomDate_G;
	}

	public boolean isHigriMode2() {
		return higriMode2;
	}

	public void setHigriMode2(boolean higriMode2) {
		this.higriMode2 = higriMode2;
	}

	public List<HRTitles> getTitles() {
		return titles;
	}

	public void setTitles(List<HRTitles> titles) {
		this.titles = titles;
	}

	public List<HRCountry> getCountries() {
		return countries;
	}

	public void setCountries(List<HRCountry> countries) {
		this.countries = countries;
	}

	public List<HRCity> getCities() {
		return cities;
	}

	public void setCities(List<HRCity> cities) {
		this.cities = cities;
	}

	public List<HrQualification> getQualific() {
		return qualific;
	}

	public void setQualific(List<HrQualification> qualific) {
		this.qualific = qualific;
	}

	public List<Establishment> getEstablishment() {
		return establishment;
	}

	public void setEstablishment(List<Establishment> establishment) {
		this.establishment = establishment;
	}

	

	

	public List<HRReligion> getReligions() {
		return religions;
	}

	public void setReligions(List<HRReligion> religions) {
		this.religions = religions;
	}

	public List<HRNationality> getNationalities() {
		return nationalities;
	}

	public void setNationalities(List<HRNationality> nationalities) {
		this.nationalities = nationalities;
	}

	public Integer getEstablishid() {
		return establishid;
	}

	public void setEstablishid(Integer establishid) {
		this.establishid = establishid;
	}

	public Establishment getEstablish() {
		return establish;
	}

	public void setEstablish(Establishment establish) {
		this.establish = establish;
	}

}