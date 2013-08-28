'use strict';

  

/**
* Main Controller: Panel y DAO en el scope principal
*
*/
function MainCtrl($scope) {

$scope.expedientesLst = [];
$scope.movimientosLst = [];
$scope.currentExp = {};
$scope.currentMov = {"movAudiencia":"off"};
$scope.abogadosLst = JSON.parse(window.JSAbogados.getAbogados());
$scope.saveExpediente = function(expObj){
		
		var movsTemp = expObj.movimientos;

		if(expObj.movimientos != undefined && expObj.movimientos != null && expObj.movimientos.length < 1)
			expObj.movimientos = null;

		var response = window.JSExpedientes.saveExpediente(JSON.stringify(expObj));

		if(response == "true"){

			try{
			var arrDuplicates = $.grep($scope.expedientesLst, function(n,e){ return n.expno == expObj.expno});
		
			if(arrDuplicates.length < 1)
				$scope.expedientesLst.push(expObj);
			}catch(e){
				if($scope.expedientesLst == null)
					$scope.expedientesLst = [];	
				$scope.expedientesLst.push(expObj);
			}
			
			expObj.movimientos = movsTemp;	
			
			$('#exp-listview').listview('refresh');
			setTimeout(function(){
				$('#exp-listview').listview('refresh');
			},100);	
		}
		
	};

$scope.saveMovimiento = function(movObj){
		console.log("currentMov = "+ JSON.stringify(movObj));
		
		if($scope.currentMov.movAudiencia == 'si')
			$scope.currentMov.movAudiencia = true;
		else
			$scope.currentMov.movAudiencia = false;

		var response = window.JSMovimientos.saveMovimiento(JSON.stringify($scope.currentExp), JSON.stringify(movObj));
		
		if(response == "true"){
			if($scope.movimientosLst == undefined || $scope.movimientosLst == null)
				$scope.movimientosLst = [];

			try{
				var arrDuplicates = $.grep($scope.movimientosLst, function(n,e){ return n.movNum == movObj.movNum});
			
				if(arrDuplicates.length < 1)
					$scope.movimientosLst.push(movObj);
			}catch(e){
				if($scope.movimientosLst == null)
					$scope.movimientosLst = [];	
				$scope.movimientosLst.push(movObj);
			}

			$('#mov-listview').listview('refresh');
			setTimeout(function(){
				$('#mov-listview').listview('refresh');
			},100);
		}
			
	};

	
	
	$scope.getExpedientes = function (){
		var result;
		try{
			result = JSON.parse(window.JSExpedientes.getAllExpedientes());	
		}catch(e){
			result = [];
		}
		
		return result;
	}
	$scope.newExpediente = function(){
		$scope.currentExp = JSON.parse('{"expno": null,"expCaratula":null,"expJuzgado":null,"expRepresentantes":null,"movimientos": null }');
		/* a Gson no le gusta [] para serializar un Array */
		
		
		
	};
	$scope.getExp = function(number){
		var result = JSON.parse(window.JSExpedientes.getExpedienteByNo(number));
		$scope.currentExp = result;
		
	}

	$scope.MovimientosLst = function(expno){
		var result;
		try{
			if(expno == undefined){
				result = [];
			}else{
				result = JSON.parse(window.JSMovimientos.getMovimientosByExpno(expno));
			}
		}catch(e){
			result = [];
		}
		$scope.movimientosLst = result;
		for(var i=0; i<result.length;i++){
			if($scope.movimientosLst[i].movAudiencia == false)
				$scope.movimientosLst[i].movAudiencia = "off";
			else
				$scope.movimientosLst[i].movAudiencia = "on";
		}
		try{
			$('#mov-listview').listview('refresh');
			setTimeout(function(){
				$('#mov-listview').listview('refresh');
			},100);	
		}catch(e){}
		
		console.log(JSON.stringify($scope.movimientosLst));
	}
	
	$scope.newMovimiento = function(expObj){
		$scope.currentMov = JSON.parse('{ "movNum":"" ,"movCaratula":null,"movDescripcion":null,"movFecha":null,"movAudiencia":"on","movActor":null,"movDemandado":null,"movAccion:null,"movRepresentantes":null}');
		console.log("currentMov = " + JSON.stringify($scope.currentMov));
		//console.log("calling generateMovId: " + (window.JSMovimientos.generateMovId()).toString());
		
		$scope.currentMov.movNum = parseInt((window.JSMovimientos.generateMovId()).toString());
		console.log("currentMov.movNum = "+ $scope.currentMov.movNum);
		
	};

	$scope.getMov = function(number){
		var result = JSON.parse(window.JSMovimientos.getMovimientoByNo(number));
		if(result.movAudiencia == false)
				result.movAudiencia = "off";
			else
				result.movAudiencia = "on";
		$scope.currentMov = result;
		
		$('#flip-1').val(result.movAudiencia); //failsafe workaround
		$('#flip-1').slider().slider('refresh'); //workaround
	}

	$scope.hoy = function(){
		return (new Date()).toString();
	};

	$scope.tareas = [{ juzgado: 'D 01 1243/2011',  actor:'Juan Perez' , demandado:'José Perez', accion:" Partición", movimiento: 'entrar expediente con escrito', hecho: false }, 
				{ juzgado: 'D 01 1243/2011',  actor:'Juan Perez' , demandado:'José Perez', accion:" Partición", movimiento: 'sacar expediente', hecho: true },
				{ juzgado: 'D 01 1243/2011',  actor:'Juan Carlos Batman', demandado:'Ricardo Tapia', accion:'Partición', movimiento: 'entrar expediente con escrito', hecho: false },
				{ juzgado: 'D 01 1243/2011',  actor:'Juan Perez' , demandado:'José Perez', accion:" Partición", movimiento: 'sacar fotocopias', hecho: false }];
				
				
	$scope.saveTareas = function (){
	
	};

	//Initialize current variables
	$scope.expedientesLst = $scope.getExpedientes();
	//$scope.currentExp = $scope.newExpediente();
	//$scope.currentMov = $scope.newMovimiento(null);
	$scope.abogadosLst = JSON.parse(window.JSAbogados.getAbogados());
	 
	// try{
	// 	$scope.currentExp.movimientos = JSON.parse(window.JSMovimientos.getMovimientosByExpno($scope.currentExp.expno));	
	// }catch(e){
	// 	console.log(e);

	// }
	

}