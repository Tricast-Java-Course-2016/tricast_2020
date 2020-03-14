package com.tricast.repositories.entities;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.*;


/**
 * The persistent class for the specialdays database table.
 * 
 */
@Entity
@Table(name="specialdays")
@NamedQuery(name="Specialday.findAll", query="SELECT s FROM Specialday s")
public class Specialday implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SPECIALDAYS_ID_GENERATOR", sequenceName="SEQ_SPECIALDAYS")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SPECIALDAYS_ID_GENERATOR")
	@Column(name="id")
	private long id;
	
	@Column(name="date")
	private ZonedDateTime date;

	public Specialday() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ZonedDateTime getDate() {
		return this.date;
	}

	public void setDate(ZonedDateTime date) {
		this.date = date;
	}

}