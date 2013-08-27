package com.pampanet.clark.dao;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.pampanet.clark.model.Abogado;
import com.pampanet.clark.model.Expediente;
import com.pampanet.clark.model.Movimiento;

public class ClarkDAOHelper extends OrmLiteSqliteOpenHelper {

	// name of the database file for your application -- change to something
	// appropriate for your app
	private static final String DATABASE_NAME = "clarkDb.db";
	// any time you make changes to your database objects, you may have to
	// increase the database version
	private static final int DATABASE_VERSION = 1;

	// the DAO object we use to access the SimpleData table
	private Dao<Abogado, Integer> abogadoDao = null;
	private Dao<Expediente,String> expDao = null;
	private Dao<Movimiento,Integer> movDao = null;
	
	public ClarkDAOHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {

		try{
			TableUtils.createTableIfNotExists(connectionSource, Abogado.class);
			TableUtils.createTableIfNotExists(connectionSource, Expediente.class);
			TableUtils.createTableIfNotExists(connectionSource, Movimiento.class);
		}catch (SQLException e) {
            Log.e(ClarkDAOHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion,
			int newVersion) {
		try {
            List<String> allSql = new LinkedList<String>(); 
            switch(oldVersion) 
            {
              case 1: 
                  //allSql.add("alter table AdData add column `new_col` VARCHAR");
                  //allSql.add("alter table AdData add column `new_col2` VARCHAR");
            }
            for (String sql : allSql) {
                db.execSQL(sql);
            }
        } catch (SQLException e) {
            Log.e(ClarkDAOHelper.class.getName(), "exception during onUpgrade", e);
            throw new RuntimeException(e);
        }

	}

	public Dao<Abogado, Integer> getAbogadoDao() {
		
		if(null == abogadoDao){
			try{
				abogadoDao = getDao(Abogado.class);
			}catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
		}
		
		return abogadoDao;
	}
	
	public Dao<Expediente, String> getExpDao() {
		if(null == expDao){
			try{
				expDao = getDao(Expediente.class);
			}catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
		}
		return expDao;
	}
	
	public Dao<Movimiento,Integer> getMovDao(){
		if(null == movDao){
			try{
				movDao = getDao(Movimiento.class);
			}catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
		}
		return movDao;
	}
	
}
