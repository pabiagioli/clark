function showPopup(){
	window.JStoast.showToast("Javascript Magic!");
}
function saveExpediente (){
		var expediente = $('#expediente-form').serialize();
		window.JSExpedientes.saveExpediente(JSON.stringify(expediente));
	};