/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package com.pampanet.clark;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.cordova.DroidGap;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pampanet.clark.model.Abogado;
import com.pampanet.clark.model.Expediente;
import com.pampanet.clark.model.Movimiento;
import com.pampanet.clark.service.ClarkDBService;

public class ClarkActivity extends DroidGap
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Set by <content src="index.html" /> in config.xml
        
        //Init the DB
        ClarkDBService.init(this);
        
        super.loadUrl("file:///android_asset/app/index.html");
        super.appView.addJavascriptInterface(new JStoast(), "JStoast");
        super.appView.addJavascriptInterface(new JSAbogados(), "JSAbogados");
        super.appView.addJavascriptInterface(new JSExpedientes(), "JSExpedientes");
        super.appView.addJavascriptInterface(new JSMovimientos(), "JSMovimientos");
        
    }

    
    public class JStoast {
    	/** Show a toast from the web page */
        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(getContext(), toast, Toast.LENGTH_SHORT).show();
        }
        
    }
    
    public class JSExpedientes {
    	
    	@JavascriptInterface
    	public String getAllExpedientes(){
    		List<Expediente> expedientes = ClarkDBService.getInstance().getAllExpedientes();
    		Gson g = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    		
    		return g.toJson(expedientes);
    	}
    	
    	@JavascriptInterface
    	public String saveExpediente(String json){
    		Gson g = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    		
    		try{
    			Expediente exp = g.fromJson(json, Expediente.class);
    			if(exp.getExpno().indexOf("/") < 1 ){
    				String ex = "El numero de Expediente debe tener de la forma: numero/año";
    				Toast.makeText(getContext(), ex , Toast.LENGTH_SHORT).show();
    				throw new Exception(ex);
    			}
    			
    			try{
    				exp.setMovimientos(ClarkDBService.getInstance().getMovimientosByExp(exp.getExpno().toString()));
    			}catch(Exception sqlEx){
    				sqlEx.printStackTrace();
    			}
    			ClarkDBService.getInstance().saveExpediente(exp);
    			Toast.makeText(getContext(), "Expediente creado con éxito", Toast.LENGTH_SHORT).show();
    			return "true";
    		}catch(Exception ex){
    			ex.printStackTrace();
    			Toast.makeText(getContext(), "No se pudo crear el expediente", Toast.LENGTH_SHORT).show();
    		}
    		return "false";
    	}
    	
    	@JavascriptInterface
    	public String getExpedienteByNo(String number){
    		try {
    			Gson g = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    			return g.toJson(ClarkDBService.getInstance().getExpedienteByNo(number));
			} catch (SQLException e) {
				Toast.makeText(getContext(), "No se pudo obtener el expediente:\n Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
    		return null;
    	}
    }
    public class JSMovimientos{
    	
    	@JavascriptInterface
    	public String generateMovId(){
    		try{
    			long idgen = ClarkDBService.getInstance().generateMovId();
    			String result = String.valueOf(idgen);
    			Toast.makeText(getContext(), "Ultimo Movimiento creado fue el #"+Long.toString(idgen-1), Toast.LENGTH_LONG).show();
    			return result;
    		}catch(SQLException e){
    			Toast.makeText(getContext(), "No se pudo generar el ID para el Movimiento:\n Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
    		}
			return "1";
    	}
    	
    	@JavascriptInterface
    	public String getMovimientosByExpno(String number){
    		try {
    			Gson g = new GsonBuilder().setDateFormat("dd/MM/yyyy").excludeFieldsWithoutExposeAnnotation().create();
    			return g.toJson(ClarkDBService.getInstance().getMovimientosByExp(number));
			} catch (SQLException e) {
				Toast.makeText(getContext(), "No se pudo obtener el movimiento:\n Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
    		return null;
    	}
    	@JavascriptInterface
    	public String getMovimientoByNo(String number){
    		try {
    			Gson g = new GsonBuilder().setDateFormat("dd/MM/yyyy").excludeFieldsWithoutExposeAnnotation().create();
    			return g.toJson(ClarkDBService.getInstance().getMovimientoByNo(number));
			} catch (SQLException e) {
				Toast.makeText(getContext(), "No se pudo obtener el movimiento:\n Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
    		return null;
    	}
    	@JavascriptInterface
    	public String saveMovimiento(String expjson, String movjson){
    		Gson gMov = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
    		Gson g = new Gson();
    		
    		try{
    			Movimiento mov = gMov.fromJson(movjson, Movimiento.class);
    			Expediente exp = g.fromJson(expjson, Expediente.class);
    			if(mov.getMovNum() == null){
    				mov.setMovNum(Integer.parseInt(String.valueOf(ClarkDBService.getInstance().generateMovId())));
    			}
    			try{
    				mov.setExpediente(ClarkDBService.getInstance().getExpedienteByNo(exp.getExpno().toString()));
    			}catch(SQLException sqlEx){
    				sqlEx.printStackTrace();
    			}
    			ClarkDBService.getInstance().saveMovimiento(mov);
    			Toast.makeText(getContext(), "Movimiento creado con éxito", Toast.LENGTH_SHORT).show();
    			createEvent(mov);
    			return "true";
    		}catch(Exception ex){
    			ex.printStackTrace();
    			Toast.makeText(getContext(), "No se pudo crear el movimiento", Toast.LENGTH_SHORT).show();
    		}
    		return "false";
    	}
    	
    	
		@SuppressLint("NewApi")
		private void createEvent(Movimiento mov){
    		Calendar beginTime = Calendar.getInstance();
    		beginTime.setTime(mov.getMovFecha());
    		Calendar endTime = beginTime;
    		
    		if (Build.VERSION.SDK_INT >= 14) {
    	        Intent intent = new Intent(Intent.ACTION_INSERT)
    	        .setData(Events.CONTENT_URI)
    	        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
    	        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
    	        .putExtra(Events.TITLE, "[Clark] "+ mov.getExpediente().getExpJuzgado() + " " + mov.getExpediente().getExpno() + " " + mov.getExpediente().getExpCaratula())
    		        .putExtra(Events.DESCRIPTION, "[Clark] Caratula: "+mov.getExpediente().getExpCaratula() + 
    		        		" Expediente Nro: "+ mov.getExpediente().getExpno() +
    		        		"Descripción "+ mov.getMovDescripcion() +
    		        		" Audiencia: "+ mov.getMovAudiencia().toString())
    		        .putExtra(Events.EVENT_LOCATION, "[Clark] Juzgado: "+mov.getExpediente().getExpJuzgado())
    		        .putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY)
    		        .putExtra(Intent.EXTRA_EMAIL, "pabiagioli@gmail.com,pabiagioli@gmail.com");
    	         startActivity(intent);
    		} else {
    	        Calendar cal = Calendar.getInstance();              
    	        Intent intent = new Intent(Intent.ACTION_EDIT);
    	        intent.setType("vnd.android.cursor.item/event");
    	        intent.putExtra("beginTime", cal.getTimeInMillis());
    	        intent.putExtra("allDay", true);
    	        intent.putExtra("rrule", "FREQ=YEARLY");
    	        intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
    	        intent.putExtra("title", "[Clark] Caratula: "+mov.getExpediente().getExpCaratula());
    	        startActivity(intent);
    	        }
    		
    	}
    	
    }
    public class JSAbogados{
    	
    	@JavascriptInterface
    	public String getAbogados(){
    		List<Abogado> abogados = ClarkDBService.getInstance().getAllAbogados();
    		if(abogados != null){
	    		Abogado abog = new Abogado();
	    		abog.setNombre("Perry");
	    		abog.setApellido("Mason");
	    		abog.setDescripcion("abogado penalista");
	    		Abogado abog1 = new Abogado();
	    		abog1.setNombre("Good");
	    		abog1.setApellido("Wife");
	    		abog1.setDescripcion("abogado matrimonial");
	    		Abogado abog2 = new Abogado();
	    		abog2.setNombre("Johnny");
	    		abog2.setApellido("Cochrane");
	    		abog2.setDescripcion("abogado criminalista");
	    		
	    		abogados = new ArrayList<Abogado>();
	    		abogados.add(abog);abogados.add(abog1);abogados.add(abog2);
	    		
	    		try{
		    		ClarkDBService.getInstance().saveAbogado(abog);
		    		ClarkDBService.getInstance().saveAbogado(abog1);
		    		ClarkDBService.getInstance().saveAbogado(abog2);
	    		}catch(Exception ex){
	    			Toast.makeText(getContext(), "No se pudieron crear los abogados", Toast.LENGTH_SHORT).show();
	    		}
	    		
    		}
    		
    		Gson g = new Gson();
    		
    		return g.toJson(abogados);
    	}
    	
    } 
    
}
