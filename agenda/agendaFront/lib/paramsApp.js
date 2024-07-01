var ambiente = 'local';
var urlParametrosModule = getUrlServidor();
var urlFront = 'http://localhost:88/agenda/agendaFront/';
var urlBackEnd = getUrlServidor();
function getUrlServidor() {
    if (ambiente === 'local') {
        return 'http://localhost:88/agenda/agendaBE/';
    }else if (ambiente === 'desarrollo') {
        return 'http://192.168.1.41:80/agenda/agendaBE/';
    }
}

