document.addEventListener("DOMContentLoaded", function () {
    const likeButtons = document.querySelectorAll(".like-button");

    likeButtons.forEach(button => {
        button.addEventListener("click", function (e) {
            e.preventDefault();

            const photoId = this.getAttribute("data-id");

            fetch(`/like/${photoId}`)
                .then(response => response.json())
                .then(newLikes => {
                    if (newLikes !== -1) {
                        const likeSpan = document.getElementById(`likes-${photoId}`);
                        likeSpan.textContent = newLikes;
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
