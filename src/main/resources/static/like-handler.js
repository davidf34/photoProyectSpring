document.addEventListener("DOMContentLoaded", function () {
    const likeButtons = document.querySelectorAll(".like-button");

    likeButtons.forEach(button => {
        button.addEventListener("click", function (e) {
            e.preventDefault();

            const photoId = this.getAttribute("data-id");

            fetch(`/like/${photoId}`)
                .then(response => response.json()) //se convierte el json del back a un objeto de JS, tipo JSON
                .then(newLikes => { //acá se obtiene el valor del JSON anterior es decir zSi el backend devolvió { newLikes: 5 }, newLikes será 5.
                    if (newLikes !== -1) {
                        const likeSpan = document.getElementById(`likes-${photoId}`);
                        likeSpan.textContent = newLikes; //.textContent: Es la propiedad que asigna el nuevo valor al contenido de texto del <span>
                    } else {
                        alert("Error al dar like.");
                    }
                })
                .catch(err => {
                    console.error("Error al dar like:", err);
                });
        });
    });
});