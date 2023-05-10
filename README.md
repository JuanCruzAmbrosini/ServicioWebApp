Proyecto web app
servicios

DESCRIPCIÓN DEL PROYECTO

¡Gracias por mostrar interés en nuestro proyecto! Somos tres barrios cerrados de la
localidad de Chacras de Coria en Mendoza y queremos armar un proveedor de
contacto para servicios. Nos imaginamos que es una aplicación que permite contactar
a un proveedor de servicio (gasistas, plomeros, electricistas, etc) que carga su perfil
con aquello que ofrece. Nosotros queremos dejar comentarios y puntaje a cada perfil
además de contactarlo para que crezca la red de proveedores confiables.

PROBLEMA

Dificultad para encontrar proveedores de servicios confiables y de forma inmediata.

SOLUCIÓN

Brindar una lista de proveedores calificados por la experiencia de otros usuarios.

AUDIENCIA

Proveedores de servicios, propietarios o residentes de los barrios privados que
necesiten de algún proveedor.

CASOS DE USO

● El administrador determinará los servicios que se pueden ofrecer y otorgar
roles.
● Los administradores pueden eliminar/censurar comentarios ofensivos, pero
dejando la calificación.
● El proveedor puede aceptar un trabajo o cancelarlo. También puede marcarlo
como finalizado una vez que el trabajo haya sido aceptado.
● El usuario puede pedir un servicio o cancelarlo. Una vez aceptado el trabajo
puede cancelarlo o darlo por finalizado.
● Una vez que el trabajo fue finalizado el usuario está habilitado a calificar el
servicio, calificación que se agregará al perfil del proveedor.

Proyecto Final
Integrador

● El usuario puede contactar al proveedor, calcular un estimativo de los
honorarios del proveedor por horas y calificarlo luego de prestado el servicio.

REQUERIMIENTOS TÉCNICOS A CUMPLIR

A continuación marcar ☑ si tu proyecto cumpliría con estos requerimientos técnicos y
dejar los optativos tal como están a modo de guía para el resto del equipo, ya que no
son obligatorios.

Requerimientos obligatorios comunes a todos:
Registro y Login con Spring Security
Crear al menos DOS roles distintos para los usuarios.
Incluir tabla html en alguna vista
Carga y actualización de imagen
Crear una Query de búsqueda personalizada
Crear un CRUD
Que haya al menos un formulario.
Crear al menos 3 vistas distintas.
Diagrama UML de entidades
Requerimientos optativos
● Motor de búsqueda
● Tabla con opciones de agregar / modificar y eliminar registros
● Utilizar atributos booleanos de alta y baja de usuarios (y poder
● modificarlos).
● Listado en tabla por filtro (por nombre, fechas, altas o bajas, etc)
● Generar por lo menos 5 vistas distintas que implementen th:fragments.
● Generar un dashboard de administración de la app (el rol
● Administrador tendrá acceso a información que un rol User o Guest no
● tendría)
● Aplicar principios de código limpio y buenas prácticas.
● Añadir diagrama de casos de uso en UML
Requerimientos específicos de este proyecto:
● La app debe contar inicialmente con la posibilidad de crear un perfil de
USUARIO QUE OFRECE SERVICIOS (PROVEEDOR), o un perfil de USUARIO
QUE QUIERE CONTACTAR EL SERVICIO (USER).

Proyecto Final
Integrador

● La app muestra cada perfil con la cantidad de usuarios que lo contactaron y la
cantidad de reseñas/puntaje/calificación que corresponde. (sólo pueden
"calificar a un PROVEEDOR" aquellos USER que ya contactaron/contrataron el
servicio de este perfil)
● Cada PROVEEDOR puede ser calificado por quienes hayan utilizado su
servicio (esto lo decide el equipo, si con promedio entre 1/5 o con estrellitas,
etcétera)
● Un PROVEEDOR (gasista, plomero, etc) genera un perfil propio, con foto,
contacto y descripción del servicio que ofrece.
● Un USER puede acceder a la app, navegar en los servicios que quiera, y
seleccionar a un proveedor de servicios para contactarlo o para calificarlo.
● Un GUEST puede ver los servicios pero no puede ver la información de
contacto.
● Un ADMIN puede otorgar permisos, cambiar roles, eliminar comentarios, crear
nuevos servicios.
● Los USER deben poder encontrar fácilmente a un proveedor por rubro y
ordenarlo bajo distintos criterios.
● Opcionalmente un USER puede elegir pasar su perfil a PROVEEDOR.
