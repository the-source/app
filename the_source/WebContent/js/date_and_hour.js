/**
 * @author: Luis Eduardo Miranda Barja
 * @returns {String}
 */
function fecha(){
    var date = new Date();
    var months = new Array("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre");
    var days = new Array("Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado");
    var day = date.getDate(); var month = date.getMonth(); var year = date.getFullYear();
    return days[date.getDay()]+", "+(day<10?"0"+day:day)+" de "+months[month]+" del "+year;
}
function hora(){
    var date = new Date();
    var hours = date.getHours(); var minutes = date.getMinutes(); var seconds = date.getSeconds();
    return (hours<10?"0"+hours:hours)+":"+(minutes<10?"0"+minutes: minutes)+":"+(seconds<10?"0"+seconds: seconds);
}
setInterval(function(){
    $("#time_rep").html('<span>'+fecha()+' -- '+hora()+'</span>');
    $("#time_rep").css({
        'color':'white',
        'fontSize':'1em'
    });
},1000);