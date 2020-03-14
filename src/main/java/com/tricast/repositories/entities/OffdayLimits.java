package com.tricast.repositories.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.tricast.repositories.entities.enums.OffdayType;

import java.math.BigDecimal;


/**
 * The persistent class for the offdaylimits database table.
 * 
 */
@Entity
@Table(name="offdaylimits")
@NamedQuery(name="OffdayLimits.findAll", query="SELECT o FROM OffdayLimits o")
public class OffdayLimits implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_OFFDAYLIMITS", sequenceName = "SEQ_OFFDAYLIMITS")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_OFFDAYLIMITS")
	private long id;
	
	@Column(name = "maximumamount")
	private BigDecimal maximumAmount;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private OffdayType type;
	
	@Column(name = "userid")
	private Integer userId;
	
	@Column(name = "year")
	private String year;

	public OffdayLimits() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getMaximumAmount() {
		return this.maximumAmount;
	}

	public void setMaximumAmount(BigDecimal maximumamount) {
		this.maximumAmount = maximumamount;
	}

	public OffdayType getType() {
		return this.type;
	}

	public void setType(OffdayType type) {
		this.type = type;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userid) {
		this.userId = userid;
	}

	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

}