$(function(){
	$('#paginador').dataTable({
		"bSort": false,
		/*Para Ordenamiento por columnas comentar el anterior
		y descomentar el campo de abajo*/
		/*"aaSorting": [[ 2, "desc" ]],*/
		"sScrollY": 445,
		"sScrollX": 800,
		"bJQueryUI": true,
		"sPaginationType": "full_numbers",
		"oLanguage": {
			"sLengthMenu": "Mostrar _MENU_ datos por página",
			"sSearch" : "Buscar",
			"sZeroRecords": "Disculpe - No se encontró datos",
			"sInfo": "Mostrando _START_ hasta _END_ de _TOTAL_ datos",
			"sInfoEmpty": "Mostrando 0 a 0 de 0 datos",
			"sInfoFiltered": "(filtrados de _MAX_ total datos)",
			"oPaginate": {
				"sFirst":    "Primera",
				"sPrevious": "Anterior",
				"sNext":     "Siguiente",
				"sLast":     "última"
			}
		}
	});
});