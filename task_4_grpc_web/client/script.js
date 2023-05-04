const {DoubleResult, ListArgument, NumberType, StringType} = require('./gen/proto/web_service_pb.js');
const {WebServiceClient} = require('./gen/proto/web_service_grpc_web_pb.js');

var client = new WebServiceClient('http://127.0.0.1:9090');

const factorialInput = document.getElementById('factorial_input');
const factorialButton = document.getElementById('factorial_btn');
const factorialResult = document.getElementById('factorial_result');

factorialButton.addEventListener('click', () => {
    var number = new NumberType();
    number.setArg(factorialInput.value);
    client.factorial(number, {}, (err, response) => {
        factorialResult.innerText = 'Result: ' + response.getArg();
    })
})

const uppercaseInput = document.getElementById('uppercase_input');
const uppercaseButton = document.getElementById('uppercase_btn');
const uppercaseResult = document.getElementById('uppercase_result');

uppercaseButton.addEventListener('click', () => {
    var text = new StringType();
    text.setValue(uppercaseInput.value);
    client.toUpperCase(text, {}, (err, response) => {
        uppercaseResult.innerText = 'Result: ' + response.getValue();
    })
})

const averageInput = document.getElementById('average_input');
const averageButton = document.getElementById('average_btn');
const averageResult = document.getElementById('average_result');

averageButton.addEventListener('click', () => {
    var numbers = averageInput.value.split(';').map(Number)
    var wrappedNumbers = new ListArgument();
    for (var number of numbers) {
        wrappedNumbers.addValues(number);
    }
    client.getAverage(wrappedNumbers, {}, (err, response) => {
        averageResult.innerText = 'Result: ' + response.getValue();
    })
})

const randomInput = document.getElementById('random_input');
const randomButton = document.getElementById('random_btn');
const randomResult = document.getElementById('random_result');

randomButton.addEventListener('click', () => {
    randomResult.innerText = 'Result: ';
    var number = new NumberType()
    number.setArg(randomInput.value)
    var stream = client.generateRandomNumbers(number, {})
    stream.on('data', (data) => {
        randomResult.innerText += ' ' + data.getArg();
    })
    stream.on('status', (status) => {
        console.log(status.code);
    });
    stream.on('end', (end) => {    
    });
})