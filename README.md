# ErasmusApp

ErasmusApp es un sistema web para el procesado de solicitudes erasmus.

## Ejecutar la aplicación

Una vez descargada la aplicición se puede lanzar con el comando en el directorio / de la aplicación:

`mvn test tomcat7:run`

La aplicación usa por defecto una base de datos en memoria **HSQL**, aunque tambien se puede utilizar un base **postgresSQL**. Para hacer el cambio es necesario modificar el `config\ApplicationConfig` cambiando a `persistence-pgsql.properties`
