
const guessButton = document.getElementById('guessButton');
let inputName = document.getElementById('inputName');
const lista = document.getElementById('lista');

guessButton.addEventListener('click', function() {
  const inputValue = inputName.value.trim();

  if (inputValue !== '') {
    const listItem = document.createElement('li');

    listItem.textContent = inputValue;
    
    lista.appendChild(listItem);
    inputName.value = '';
    (agregarNombres > 4)
  } else {
    alert ("ya no puedes agregar mas nombres. inicia el juego.")
    document.getElementById("guessButton").disabled = true;
  }
});

let nombreSecreto = Array (0);  
let intentos = 3;
document.getElementById("secretButton").addEventListener("click", function() {
    let input = document.getElementById("input").value;
    if (input.trim() !== '') {
    if (input === nombreSecreto) {
        alert("¡Acertaste el nombre secreto!");
    } else {
        intentos--;
        if (intentos > 0) {
            alert(`Escogiste el nombre equivocado. Te quedan ${intentos} intentos.`);
        } else {
            alert("Has agotado todos los intentos. El juego ha terminado.");
            document.getElementById("secretButton").disabled = true;  // Deshabilitar el botón al agotar intentos
        }
    }
}}

);
const secretButton = document.getElementById("secretButton");
const images = ["../img/photo-1595524288414-a7fda0a12d0c.avif"];
let currentIndex = 0;
secretButton.addEventListener('click', function() {
  currentIndex = (currentIndex + 1) % images.length; 
  document.body.style.backgroundImage = `url('${images[currentIndex]}')`;

const secretButtonButton = document.getElementById('changeButton');
const inputName = document.getElementById('inputName');

const correctName = 'secreto'; // Este es el nombre que debe ingresar el usuario

const imagenes = ["img/987fb4b6e21114878003a88a97523f06.gif","img/images.jpg"];
let currentIndex = 0;

// Seleccionamos el contenedor de felicitación
const messageContainer = document.getElementById('messageContainer');
const message = document.getElementById('message');
const congratulationImage = document.getElementById('congratulationImage');

secretButton.addEventListener('click', function() {
  const inputValue = inputName.value.trim();
  if (inputValue === correctName) {
    // Incrementamos el índice para ir a la siguiente imagen
    currentIndex = (currentIndex + 1) % images.length; 
    document.body.style.backgroundImage = `url('${images[currentIndex]}')`;

    // Mostramos el mensaje de felicitación y la imagen
    messageContainer.style.display = 'block';  // Hacemos visible el contenedor
    message.innerText = '¡Felicidades! ¡El nombre es correcto!';
    inputName.value = '';
  }});
});