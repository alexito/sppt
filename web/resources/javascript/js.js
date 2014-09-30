setInterval(function(){
  $('.solicitud-tipo.Normal').parent().css('background-color', '#D1FFD1')
  $('.solicitud-tipo.Emergencia').parent().css('background-color', '#FFDADA')
},2000);






if (!checkRangeTime()) {
  $('.btn-nueva-solicitud').hide();
}
else {
  $('.btn-nueva-solicitud').show();
}

setInterval(function() {
  if (!checkRangeTime()) {
    $('.btn-nueva-solicitud').hide();
  }
  else {
    $('.btn-nueva-solicitud').show();
  }
}, 60 * 1000);

function checkRangeTime() {
  var date = new Date();
  var hour = date.getHours();
  var min = date.getMinutes();

  return (hour === 15 && min > 30) ? false : (hour < 7 || hour >= 16) ? false : true;
}