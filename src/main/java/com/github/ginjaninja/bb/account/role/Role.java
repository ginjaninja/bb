/**
 * Roles that can be assigned to users
 */
package com.github.ginjaninja.bb.account.role;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.github.ginjaninja.bb.account.capability.RoleCapability;
import com.github.ginjaninja.bb.account.user.User;
import com.github.ginjaninja.bb.domain.DomainObject;

@NamedQueries({
	@NamedQuery(
	    name="getRoleWithCapability",
	    query="SELECT r FROM RoleCapability rc "
	    		+ "JOIN rc.capability c "
	    		+ "JOIN rc.role r "
	    		+ "WHERE r.id = :roleId "
	    		+ "AND c.id = :capId "
	    		+ "AND rc.activeInd = 'Y'"
	)
})

@Entity
@Table(name="acct_role")
public class Role extends DomainObject{
	@Id
    @GeneratedValue
    @Column(name = "id")
	private Integer id;
	
	/** Role name **/
	@Column(name = "name", length = 30, nullable = false)
    private String name;
	
	/** Whether entity is active or not (can be put in trash without deleting permanently) */
    @Column(name = "active_ind", length = 1, nullable = false)
    private String activeInd;
     
    /** Date/Time last updated **/
    @Column(name = "activity_dt_tm", nullable = false) 
    private Date activityDtTm;
    
    /** Date/Time created **/
    @Column(name = "created_dt_tm", nullable = false) 
    private Date createdDtTm;

    /** users for Role **/
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private Collection<User> users;
    
    /** Capabilities for Role **/
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private Collection<RoleCapability> capabilities;
    
    
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getActiveInd() {
		return activeInd;
	}

	
	public void setActiveInd(String activeInd) {
		this.activeInd = activeInd;
	}

	
	public Date getActivityDtTm() {
		return activityDtTm;
	}

	
	public void setActivityDtTm(Date activityDtTm) {
		this.activityDtTm = activityDtTm;
	}

	
	public Date getCreatedDtTm() {
		return createdDtTm;
	}

	
	public void setCreatedDtTm(Date createdDtTm) {
		this.createdDtTm = createdDtTm;
	}

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	public Collection<RoleCapability> getCapabilities() {
		return capabilities;
	}

	public void setCapabilities(Collection<RoleCapability> capabilities) {
		this.capabilities = capabilities;
	}
    
    
}
