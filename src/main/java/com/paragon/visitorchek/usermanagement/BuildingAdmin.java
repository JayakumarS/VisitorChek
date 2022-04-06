package com.paragon.visitorchek.usermanagement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "guard")
public class BuildingAdmin {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Integer id;
	
	//@NotBlank
	@Size(max = 20)
	@Column(name = "talentid")
    private String talentId;
    
	//@NotBlank(message = "Please enter UserName")
    @Column(name = "userid")
    private String userId;
	
    @Column(name = "password")
    private String password;	
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "guarding_gate")
    private String guardingGate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTalentId() {
		return talentId;
	}

	public void setTalentId(String talentId) {
		this.talentId = talentId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGuardingGate() {
		return guardingGate;
	}

	public void setGuardingGate(String guardingGate) {
		this.guardingGate = guardingGate;
	}
    
}
