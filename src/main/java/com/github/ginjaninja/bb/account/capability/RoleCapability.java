/** Capabilities assigned to roles **/
package com.github.ginjaninja.bb.account.capability;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.github.ginjaninja.bb.account.role.Role;
import com.github.ginjaninja.bb.domain.DomainObject;

@NamedQueries({
	@NamedQuery(
	    name="getRoleCapability",
	    query="SELECT rc FROM RoleCapability rc "
	    		+ "JOIN rc.capability c "
	    		+ "JOIN rc.role r "
	    		+ "WHERE r.id = :roleId "
	    		+ "AND c.id = :capId "
	    		+ "AND rc.activeInd = 'Y'"
	)
})

@Entity
@Table(name="acct_role_capability")
public class RoleCapability extends DomainObject{
	@Id
    @GeneratedValue
    @Column(name = "id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "capability_id", nullable = false)
	private Capability capability;
	
	@ManyToOne
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;
	
	/** Whether entity is active or not (can be put in trash without deleting permanently) */
    @Column(name = "active_ind", length = 1, nullable = false)
    private String activeInd;
     
    /** Date/Time last updated **/
    @Column(name = "activity_dt_tm", nullable = false) 
    private Date activityDtTm;
    
    /** Date/Time created **/
    @Column(name = "created_dt_tm", nullable = false) 
    private Date createdDtTm;

    
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Capability getCapability() {
		return capability;
	}

	public void setCapability(Capability capability) {
		this.capability = capability;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
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
    
    
}
