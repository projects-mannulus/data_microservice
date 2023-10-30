
const tiposDeTiempo = {
    millisecond: 1,
    second: 1000,
    minute: 60000,
    hour: 3600000,
    day: 86400000,
    week: 604800000,
};

function println(msg) {
    document.getElementById("messages").innerHTML += "<span class='log'>" + msg + "</span><br>";
}

// const {faker} = require("@faker-js/faker");
var client;

function startConnect() {

    clientID = "clientID - " + parseInt(Math.random() * 100);
    // protocol = document.getElementById("protocol").value;
    host = document.getElementById("host").value;
    port = document.getElementById("port").value;
    userId = document.getElementById("username").value;
    passwordId = document.getElementById("password").value;

    document.getElementById("messages").innerHTML += `<span> Connecting to ${host} on port ${port} </span><br>`;
    document.getElementById("messages").innerHTML += `<span> Using the client Id ${clientID} </span><br>`;

    client = new Paho.MQTT.Client(host, Number(port), clientID);

    client.onConnectionLost = onConnectionLost;
    client.onMessageArrived = onMessageArrived;

    client.connect({
        onSuccess: onConnect
//        userName: userId,
        //       passwordId: passwordId
    });


}


function onConnect() {
    topic = document.getElementById("topic_s").value;

    document.getElementById("messages").innerHTML += "<span> Subscribing to topic " + topic + "</span><br>";

    client.subscribe(topic);
}

function onConnectionLost(responseObject) {
    document.getElementById("messages").innerHTML += "<span> ERROR: Connection is lost.</span><br>";
    if (responseObject != 0) {
        document.getElementById("messages").innerHTML += "<span> ERROR:" + responseObject.errorMessage + "</span><br>";
    }
}

function onMessageArrived(message) {
    console.log("OnMessageArrived: " + message.payloadString);
    document.getElementById("messages").innerHTML += "<span> Topic:" + message.destinationName + "| Message : " + message.payloadString + "</span><br>";
}

function startDisconnect() {
    client.disconnect();
    document.getElementById("messages").innerHTML += "<span> Disconnected. </span><br>";


}

function publishMessage(msg, deviceid) {
    // msg = document.getElementById("Message").value;
    // const deviceid = document.getElementById("deviceUUID").value;

    Message = new Paho.MQTT.Message(msg);
    //topico donde el gateway publica los mensajes de los dispositivos y el backend los recibe
    Message.destinationName = document.getElementById("topic_s").value;
    MessageUser = new Paho.MQTT.Message(msg);

    deviceid = deviceid ?? document.getElementById("deviceUUID").value;

    MessageUser.destinationName = ("device-" + deviceid);

    //se envia el mensaje al backend y al usuario.
    client.send(Message);
    client.send(MessageUser);
    println("[PUBLISH]: Message to topic " + Message.destinationName + " is sent");
    println("[PUBLISH]: Message to topic " + MessageUser.destinationName + " is sent");
}

let tiempoInicial;

function generarRegistro(keys, min, max, deviceid) {
    if (deviceid === undefined) {
        deviceid = document.getElementById("deviceUUID").value;
    }
    if (min === undefined) {
        min = Number(document.getElementById("min").value);
    }
    if (max === undefined) {
        max = Number(document.getElementById("max").value);
    }
    if (keys === undefined) {
        keys = document.getElementById("keys").value.split(",");
    }
    const topic = ("device-" + deviceid);

    const intervalo = document.getElementById("intervalo").value;
    tiempoInicial = tiempoInicial.add(1, intervalo);

    let values = {};
    keys.forEach(key => {
        values[key] = faker.random.number({min: min, max: max, precision: 1});
    });
    return JSON.stringify({
        deviceUUID: deviceid,
        topic: topic,
        timeStamp: tiempoInicial.toDate(),
        values: values,
        status: 'OK',
        alert: false,
    });
}

async function generateData(type) {
    const count = Number(document.getElementById("count").value);
    //tiempo de espera entre cada registro
    const timeout = document.getElementById("intervalo").value;
    //fecha inicial
    const fechaDesde = document.getElementById("fechaDesde").value;
    tiempoInicial = moment(fechaDesde) ?? moment();

    //tiempo de sincronizacion (para enviar los datos segun el cada cuanto tiempo se envian y no todos de una vez)
    const syncTime = document.getElementById("syncTime").value === 'on';
    console.log(syncTime)
    //valida que se haya ingresado datos en los campos
    if (count === null || timeout === null) {
        println("Debe llenar los campos");
        return;
    }
    let timeToSync = tiposDeTiempo[timeout];
    println("Generando " + count + " registros cada " + timeout + " "+ timeout);
    for (let i = 0; i < count; i++) {
        if(i>0 && syncTime){
            await new Promise(resolve => setTimeout(resolve, timeToSync));
        }
        if (type === "co2") {
            publishMessage(generarRegistro(["C02 ppm"], 1500, 3000, 1),1);
        } else if (type === "energia") {
            publishMessage(generarRegistro(["Centic", "CT", "LP"], 600, 1400, 2),2);
        } else if (type === "temp") {
            publishMessage(generarRegistro(["Asignadas", "Prestadas", "NO prestadas"], 0, 30, 3),3);
        } else if (type === "ruido") {
            publishMessage(generarRegistro(["decibeles"], 30, 90, 4),4);
        } else{
            publishMessage(generarRegistro());
        }
    }
}

function clearLog() {
    document.getElementById("messages").innerHTML = "";
}

// Generar una serie de datos correlacionados linealmente
function generarDatosCorrelacionados() {
    const datos = [];
    // Ejemplo de uso:
    const tamanoMuestra = Number(document.getElementById("count").value);
    const pendiente = 2; // Pendiente de la línea
    const intercepto = 10; // Intercepción en el eje y
    const ruido = 5; // Amplitud del ruido aleatorio
    for (let i = 0; i < tamano; i++) {
        const x = i;
        const y = pendiente * x + intercepto + (Math.random() - 0.5) * ruido;
        //datos.push({ x, y });
        let values = {};
        keys.forEach(key => {
            values[key] = pendiente * x + intercepto + (Math.random() - 0.5) * ruido;
        });
        msj = JSON.stringify({
            deviceUUID: deviceid,
            topic: topic,
            timeStamp: tiempoInicial.toDate(),
            values: values,
            status: 'OK',
            alert: faker.random.boolean(),
        });
        publishMessage(msj);
    }
    return datos;
}

console.log(document.getElementById("fechaDesde"))
$(document).ready(function () {
   console.log("ready")
    $("#fechaDesde").val(moment().format("YYYY-MM-DDTHH:mm:ss"));
    //document.getElementById("fechaDesde").value = moment().format("YYYY-MM-DDTHH:mm:ss");
});
