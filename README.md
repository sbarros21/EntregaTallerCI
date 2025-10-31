### TALLER CI/CD



A. MongoDB

Crear la base de datos en MongoDB Compass

![img.png](img.png)

Probamos un POST por medio de Postman para crear una receta 

![img_1.png](img_1.png)

Lo vemos en el navegador por medio de LocalHost para verificar que se creo y que quedo guardado en la base de datos

![img_2.png](img_2.png)

B. Cree las tres pruebas que especifica en el enunciado, me enfoque en la logica, no en los endpoints asi que se hicieron
para el servicio. Evidencia:

![img_3.png](img_3.png)

C. Debe realizar CI/CD con Github Actions

a. ejecute las pruebas cada que haga push y pull request en develop

![img_6.png](img_6.png)

![img_7.png](img_7.png)

b. despliegue automaticamente cuando haga push a main

![img_8.png](img_8.png)

![img_4.png](img_4.png)

![img_5.png](img_5.png)

D. El acceso a swagger esta en este link luego de correr la apliación: http://localhost:8080/swagger-ui/index.html