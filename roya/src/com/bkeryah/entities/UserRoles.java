package com.bkeryah.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ROLE_DESC")
public class UserRoles implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "ROLE_NAME", nullable = false)
	private String roleName;
	@Column(name = "ROLE_DESC", nullable = false)
	private String roleDesc;
//	@ManyToOne
//	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)
//    private ArcUsers users;
	@ManyToMany(mappedBy="roles")
	private Set<ArcUsers> users;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Set<ArcUsers> getUsers() {
		return users;
	}

	public void setUsers(Set<ArcUsers> users) {
		this.users = users;
	}

//	public Integer getUserID() {
//		return userID;
//	}
//
//	public void setUserID(Integer userID) {
//		this.userID = userID;
//	}
//
//	public String getRole() {
//		return role;
//	}
//
//	public void setRole(String role) {
//		this.role = role;
//	}
//
//	public ArcUsers getUsers() {
//		return users;
//	}
//
//	public void setUsers(ArcUsers users) {
//		this.users = users;
//	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		UserRoles role = (UserRoles) obj;
		return (role.getId().intValue() == id.intValue());
	}

	@Override
	public int hashCode() {
		return id.intValue();
	}
}
