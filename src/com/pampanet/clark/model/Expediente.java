package com.pampanet.clark.model;

import java.util.Collection;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Expediente {

	@Expose
	@DatabaseField(unique=true, index=true, id=true)
	private String expno;
	@Expose
	@DatabaseField
	private String expCaratula;
	@Expose
	@DatabaseField
	private String expJuzgado;
	@Expose
	@DatabaseField
	private String expRepresentantes;
	@ForeignCollectionField(foreignFieldName="expediente")
	private Collection<Movimiento> movimientos;

	/**
	 * Required by ORMLite
	 */
	public Expediente(){}
	
	public String getExpno() {
		return expno;
	}

	public void setExpno(String expno) {
		this.expno = expno;
	}

	public String getExpCaratula() {
		return expCaratula;
	}

	public void setExpCaratula(String expCaratula) {
		this.expCaratula = expCaratula;
	}

	public String getExpJuzgado() {
		return expJuzgado;
	}

	public void setExpJuzgado(String expJuzgado) {
		this.expJuzgado = expJuzgado;
	}

	public String getExpRepresentantes() {
		return expRepresentantes;
	}

	public void setExpRepresentantes(String expRepresentantes) {
		this.expRepresentantes = expRepresentantes;
	}

	public Collection<Movimiento> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(Collection<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}
	
	
}
