package com.pampanet.clark.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Juzgado {
	@DatabaseField(generatedId=true)
	public Integer id;
	@DatabaseField
	public String nombre;
	@DatabaseField
	public String domicilio;
}
