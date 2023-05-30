//function mostrarConfirmacion() {
//    event.preventDefault(); // Evita que se realice la acción por defecto del enlace
//
//    if (confirm("¿Seguro que deseas eliminar?")) {
//        // Si el usuario confirma la eliminación, redirecciona a la página de eliminación
//        window.location.href = "@{/service_create/delete/__${servicio.id}__}";
//    } else {
//        // Si el usuario cancela, no se realiza ninguna acción
//        return;
//    }
//}

//var eliminarBtn = document.getElementById("eliminarBtn");
//var confirmModal = document.getElementById("confirmModal");
//var confirmYes = document.getElementById("confirmYes");
//var confirmNo = document.getElementById("confirmNo");
//
//eliminarBtn.addEventListener("click", function (e) {
//    e.preventDefault(); // Previene el comportamiento predeterminado del enlace
//    e.stopPropagation(); // Detiene la propagación del evento clic
//    confirmModal.style.display = "block";
//});
//
//confirmYes.addEventListener("click", function () {
//    // Si el usuario confirma la eliminación, redirecciona a la página de eliminación
//    window.location.href = "/admin/service_list";
//});
//
//confirmNo.addEventListener("click", function () {
//    confirmModal.style.display = "none";
//});


