package com.pampanet.clark.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Abogado {
	@DatabaseField(generatedId=true)
	private Integer id;
	@DatabaseField
	private String email;
	@DatabaseField
	private String nombre;
	@DatabaseField
	private String apellido;
	@DatabaseField
	private String descripcion;
	
	public Abogado(){}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Override
	public boolean equals(Object o) {
		Abogado ab1;
		if(o instanceof Abogado){
			ab1 = (Abogado) o;
			//El primer elemento es el usr y el segundo el dominio
			String [] email1 = ab1.getEmail().split("@");
			String [] email2 = this.getEmail().split("@");
			for (int i = 0; i < email1.length; i++){
				if (!email1[i].equalsIgnoreCase(email2[i]))
					return false;
			}
		}
		
		return super.equals(o);
	}
	
}
