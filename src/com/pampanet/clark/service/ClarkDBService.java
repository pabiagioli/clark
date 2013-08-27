package com.pampanet.clark.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;

import com.pampanet.clark.dao.ClarkDAOHelper;
import com.pampanet.clark.model.Abogado;
import com.pampanet.clark.model.Expediente;
import com.pampanet.clark.model.Movimiento;

/**
 * Class to handle all DataBase related CRUD methods.
 * It acts as a merge of all Entities' Service classes
 * It's going to have all the DAOs "injected" or instanced once
 * 
 * @author pablo
 *
 */
public class ClarkDBService {

	static private ClarkDBService instance;

    static public void init(Context ctx) {
        if (null==instance) {
            instance = new ClarkDBService(ctx);
        }
    }

    static public ClarkDBService getInstance() {
        return instance;
    }

    private ClarkDAOHelper helper;
    private ClarkDBService(Context ctx) {
        helper = new ClarkDAOHelper(ctx);
    }

    private ClarkDAOHelper getHelper() {
        return helper;
    }

    /**
     * ***********************
     * 		Abogados
     * ***********************
     */
    
    public List<Abogado> getAllAbogados() {
        List<Abogado> abogados = null;
        try {
            abogados = getHelper().getAbogadoDao().queryForAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return abogados;
    }
    
   public void saveAbogado(Abogado abogado) throws SQLException {
	   getHelper().getAbogadoDao().createOrUpdate(abogado);
   }
   
   /**
    * *********************
    * 		Expedientes
    * *********************
    */
   
   public List<Expediente> getAllExpedientes(){
	   List <Expediente> expedientes = null;
	   try{
		   expedientes = getHelper().getExpDao().queryForAll();
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	   return expedientes;
   }
   
   public void saveExpediente(Expediente exp) throws SQLException {
	   getHelper().getExpDao().createOrUpdate(exp);
	   if(exp.getMovimientos() != null)
		   for(Movimiento mov: exp.getMovimientos())
		   getHelper().getMovDao().createOrUpdate(mov);
   }
   
   public Expediente getExpedienteByNo(String num) throws SQLException {
	   //Integer id = Integer.valueOf(num);
	   return getHelper().getExpDao().queryForId(num);
   }
   
   public String getAnhoExp(Expediente exp){
	   return exp.getExpno().split("/")[1];
   }
   
   /**
    * *********************
    * 		Movimientos
    * *********************
    */
   
   public List<Movimiento> getMovimientosByExp(String expNo) throws SQLException{
	   List<Movimiento> result = new LinkedList<Movimiento>();
	   Expediente exp = getExpedienteByNo(expNo);
	   
	   result.addAll(exp.getMovimientos());
	   return result;
   }
   
   public void saveMovimiento(Movimiento mov) throws SQLException {
	   getHelper().getMovDao().createOrUpdate(mov);
	   Expediente exp = getHelper().getExpDao().queryForId(mov.getExpediente().getExpno());
	   //exp.getMovimientos().add(mov.getMovNum());
	   //getHelper().getExpDao().createOrUpdate(exp);
   }
   
   public long generateMovId() throws SQLException{
	   return (getHelper().getMovDao().countOf() + 1);
   }
   
   public Movimiento getMovimientoByNo(String num) throws SQLException{
	   Integer id = Integer.valueOf(num);
	   return getHelper().getMovDao().queryForId(id);
   }
   
   public List<Movimiento> getMovsByFecha(Date date) throws SQLException{
	   return getHelper().getMovDao().queryForEq("movFecha", date);
   }
   
   public Expediente getExpedienteByMov(Movimiento mov) throws SQLException{
	   return getHelper().getMovDao().queryForSameId(mov).getExpediente();
	   
   }
}
