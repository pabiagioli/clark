package com.pampanet.clark.model;

import java.util.Date;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Movimiento {
	@Expose
	@DatabaseField(id=true , columnName="movNum")
	private Integer movNum;
	@DatabaseField(foreign=true, foreignAutoRefresh = true, foreignColumnName="expno")
	private Expediente expediente;
	@Expose
	@DatabaseField
	private String movCaratula;
	@Expose
	@DatabaseField
	private Date movFecha;
	@Expose
	@DatabaseField
	private Boolean movAudiencia;
	@Expose
	@DatabaseField
	private String movRepresentantes;
	@Expose
	@DatabaseField
	private String movDescripcion;
	@Expose
	@DatabaseField
	private String movActor;
	@Expose
	@DatabaseField
	private String movDemandado;
	@Expose
	@DatabaseField
	private String movAccion;
	
	
	/**
	 * Required by ORMLite
	 */
	public Movimiento (){}

	public Integer getMovNum() {
		return movNum;
	}

	public void setMovNum(Integer movNum) {
		this.movNum = movNum;
	}

	public Expediente getExpediente() {
		return expediente;
	}

	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}

	public String getMovCaratula() {
		return movCaratula;
	}

	public void setMovCaratula(String movCaratula) {
		this.movCaratula = movCaratula;
	}

	public Date getMovFecha() {
		return movFecha;
	}

	public void setMovFecha(Date movFecha) {
		this.movFecha = movFecha;
	}

	public Boolean getMovAudiencia() {
		return movAudiencia;
	}

	public void setMovAudiencia(Boolean movAudiencia) {
		this.movAudiencia = movAudiencia;
	}

	public String getMovRepresentantes() {
		return movRepresentantes;
	}

	public void setMovRepresentantes(String movRepresentantes) {
		this.movRepresentantes = movRepresentantes;
	}

	public String getMovDescripcion() {
		return movDescripcion;
	}
	public void setMovDescripcion(String movDescripcion) {
		this.movDescripcion = movDescripcion;
	}
	
}

