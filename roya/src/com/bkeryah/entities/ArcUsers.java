package com.bkeryah.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ARC_USERS")
public class ArcUsers implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "USER_ID")
	private Integer userId;
	@Column(name = "LOGIN_NAME", nullable = false)
	private String loginName;
	@Column(name = "PASSWORD", nullable = false)
	private String password;
	@Column(name = "F_ACTIVE")
	private Integer isActive;
	@Column(name = "FNAME", nullable = false)
	private String firstName;
	@Column(name = "LNAME", nullable = false)
	private String lastName;
	@Column(name = "TITLE_ID")
	private Integer titleId;
	@Temporal(TemporalType.DATE)
	@Column(name = "U_DATE")
	private Date userLastUpdate;
	@Column(name = "JOB_ID")
	private Integer jobId;
	@ManyToOne
	@JoinColumn(name = "JOB_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	WrkJobs userjob;
	@Column(name = "DEPT_ID")
	private Integer deptId;
	@ManyToOne
	@JoinColumn(name = "DEPT_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	private WrkDept userDept;
	@Column(name = "MGR_ID")
	private Integer mgrId;
	@Column(name = "WRK_ROLE_ID")
	private Integer wrkRoleId;
	@Column(name = "SEC_ID")
	private Integer wrkSectionId;
	@ManyToOne
	@JoinColumn(name = "SEC_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	WrkSection wrkSection;
	@Column(name = "MOBILE")
	private String mobileNumber;
	@Column(name = "IS_MOBILE")
	private Integer hasMobile;
	
	@Column(name = "EMPNO", nullable = true)
	
	private Integer employeeNumber;
	@Column(name = "EMPNAME")
	private String employeeName;
	@Column(name = "STRUCT")
	private Integer structId;
	@Column(name = "FORM_NAME")
	private String formName;
	@Column(name = "EMAIL")
	private String email;
	@Column(name = "EMP_ENAME")
	private String EmployeeEname;
	@Column(name = "EXT")
	private String ext;
	@OneToOne
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)
	private WrkProfile wrkProfile;
	@OneToOne
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)
	private WrkProfileSign wrkProfileSign;
	/*@OneToOne
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", insertable = true, updatable = true)
	@Cascade(value = CascadeType.ALL)
	private ArcUsersExtension arcUsersExtension;
	*/@ManyToMany( cascade = { javax.persistence.CascadeType.ALL },fetch = FetchType.EAGER)
	@JoinTable(name = "ROLE_PRIV", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "ROLE_ID") })
	private Set<UserRoles> roles;
	@Transient
	private boolean signed;

	
	
//	@ManyToOne(cascade={javax.persistence.CascadeType.ALL})
//	@JoinColumn(name="USER_ID", insertable = false, updatable = false)
//	private ArcDocumentStruct parent;
	
	
//	@OneToMany(mappedBy="parent")
//	private Set<ArcUsers> children = new HashSet<>();
//	
	
	
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getTitleId() {
		return titleId;
	}

	public void setTitleId(Integer titleId) {
		this.titleId = titleId;
	}

	// public SysTitle getUserTitle() {
	// return userTitle;
	// }
	// public void setUserTitle(SysTitle userTitle) {
	// this.userTitle = userTitle;
	// }
	public Date getUserLastUpdate() {
		return userLastUpdate;
	}

	public void setUserLastUpdate(Date userLastUpdate) {
		this.userLastUpdate = userLastUpdate;
	}

	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	public WrkJobs getUserjob() {
		return userjob;
	}

	public void setUserjob(WrkJobs userjob) {
		this.userjob = userjob;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public WrkDept getUserDept() {
		return userDept;
	}

	public void setUserDept(WrkDept userDept) {
		this.userDept = userDept;
	}

	public Integer getMgrId() {
		return mgrId;
	}

	public void setMgrId(Integer mgrId) {
		this.mgrId = mgrId;
	}

	// public ArcUsers getManager() {
	// return manager;
	// }
	// public void setManager(ArcUsers manager) {
	// this.manager = manager;
	// }
	// public Set<ArcUsers> getUsers() {
	// return users;
	// }
	// public void setUsers(Set<ArcUsers> users) {
	// this.users = users;
	// }
	public Integer getWrkRoleId() {
		return wrkRoleId;
	}

	public void setWrkRoleId(Integer wrkRoleId) {
		this.wrkRoleId = wrkRoleId;
	}

	// public WrkRoles getWrkRole() {
	// return wrkRole;
	// }
	// public void setWrkRole(WrkRoles wrkRole) {
	// this.wrkRole = wrkRole;
	// }
	public WrkSection getWrkSection() {
		return wrkSection;
	}

	public void setWrkSection(WrkSection wrkSection) {
		this.wrkSection = wrkSection;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Integer getHasMobile() {
		return hasMobile;
	}

	public void setHasMobile(Integer hasMobile) {
		this.hasMobile = hasMobile;
	}

	public Integer getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(Integer employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Integer getStructId() {
		return structId;
	}

	public void setStructId(Integer structId) {
		this.structId = structId;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmployeeEname() {
		return EmployeeEname;
	}

	public void setEmployeeEname(String employeeEname) {
		EmployeeEname = employeeEname;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	// public Integer getUserDeptJob() {
	// return userDeptJob;
	// }
	// public void setUserDeptJob(Integer userDeptJob) {
	// this.userDeptJob = userDeptJob;
	// }
	public WrkProfile getWrkProfile() {
		return wrkProfile;
	}

	public void setWrkProfile(WrkProfile wrkProfile) {
		this.wrkProfile = wrkProfile;
	}

	/*
*/
	public Set<UserRoles> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRoles> roles) {
		this.roles = roles;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		ArcUsers user = (ArcUsers) obj;
		return (user.getUserId().intValue() == userId.intValue());
	}

	@Override
	public int hashCode() {
		return userId.intValue();
	}

	public boolean isSigned() {
		return signed;
	}

	public void setSigned(boolean signed) {
		this.signed = signed;
	}

	@Override
	public String toString() {
		return "userId :" + userId + " name : " + firstName;
	}

	public Integer getWrkSectionId() {
		return wrkSectionId;
	}

	public void setWrkSectionId(Integer wrkSectionId) {
		this.wrkSectionId = wrkSectionId;
	}

	public WrkProfileSign getWrkProfileSign() {
		return wrkProfileSign;
	}

	public void setWrkProfileSign(WrkProfileSign wrkProfileSign) {
		this.wrkProfileSign = wrkProfileSign;
	}

//	public Set<ArcUsers> getChildren() {
//		return children;
//	}

//	public void setChildren(Set<ArcUsers> children) {
//		this.children = children;
//	}

//	public ArcDocumentStruct getParent() {
//		return parent;
//	}
//
//	public void setParent(ArcDocumentStruct parent) {
//		this.parent = parent;
//	}

}
