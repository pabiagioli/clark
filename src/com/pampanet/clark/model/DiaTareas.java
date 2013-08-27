package com.pampanet.clark.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.pampanet.clark.dao.ClarkDAOHelper;
import com.pampanet.clark.service.ClarkDBService;

@DatabaseTable
public class DiaTareas {

	@DatabaseField(generatedId=true)
	private Integer id;
	@DatabaseField
	public String notas;
	@ForeignCollectionField
	public ForeignCollection<Movimiento> movimientos;
	
	public DiaTareas(){
	}
	
	public DiaTareas build(Date date) {
		DiaTareas result = new DiaTareas();
		try {
			movimientos.addAll(ClarkDBService.getInstance().getMovsByFecha(date));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
