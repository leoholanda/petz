**PETZ**
----

* **DOCUMENTAÇÃO**

  _http://localhost:8080/swagger-ui.html_

* **URL**

  _http://localhost:8080/cliente_
  
 * **Method:**
 
  `GET` | `POST` | `PUT` | `DELETE`
  
  * **Cadastra cliente:**
  _http://localhost:8080/cliente_
  
  `POST`
  
	{
	"nome":"Leonardo Holanda"
	}
  
	
* **URL**

  _http://localhost:8080/pet_
  
 * **Method:**
    
  `GET` | `POST` | `PUT` | `DELETE`
   
  
`POST`

* **Cadastra pet:**
  _http://localhost:8080/pet_
  
      {
      "nome":"James Gosling",
      "clienteId":1
      }

`PUT`

* **Altera informações pelo ID:**
  _http://localhost:8080/pet/{id}_
  
