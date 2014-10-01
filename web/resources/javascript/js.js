
//Controla los colores de las solicitudes, verde para un normal y rosado para una Emergencia
setInterval(function(){
  $('.solicitud-tipo.Normal').parent().css('background-color', '#D1FFD1')
  $('.solicitud-tipo.Emergencia').parent().css('background-color', '#FFDADA')
},2000);

//if (!checkRangeTime()) {
//  $('.btn-nueva-solicitud').hide();
//}
//else {
//  $('.btn-nueva-solicitud').show();
//}

//Controla el boton de Nueva solicitud cada minuto. Verificando q el sistema esta operando 
//dentro del horario permitido.
setInterval(function() {
//  if (!checkRangeTime()) {
//    $('.btn-nueva-solicitud').hide();
//  }
//  else {
//    $('.btn-nueva-solicitud').show();
//  }
}, 60 * 1000);

function checkRangeTime() {
  var date = new Date();
  var hour = date.getHours();
  var min = date.getMinutes();

  return (hour === 15 && min > 30) ? false : (hour < 7 || hour >= 16) ? false : true;
}