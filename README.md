## [TR] -> Bu projede RestTemplate kullanarak :8080 portundan :8081 portunun Http Metodlarının çağrılması amaçlanmıştır. (veya tam tersi) <br/> Sunucu tarafından (:8080) portundan, client tarafına (:8081) portuna dosya yazabilir, okuyabilirsiniz. <br/> <br/> Aşağıda projenin herkes tarafından anlaşılabilmesi için örnek bir kullanımını yaptım ve ingilizce doküman hazırladım. <br/> Umarım faydalı olacaktır. ⏬ ⏬ ⏬
## [EN] -> This project uses RestTemplate to call the Http methods of port :8081 from port :8080. <br/> You can write and read files on the Client-Side (:8081). ⏬ ⏬ ⏬ <br/>



<img width="1088" alt="diagram" src="https://github.com/bkizilkayaa/spring-boot-filetransfer/assets/88281419/bd47475c-5dda-46b8-94c1-bf42d1b202b2">



<br/>


## 1-) Get file
### 
```diff
+ localhost:8080/files/downloadFile/{fileName}
```
### Explanation: Get specific file on server-side.
### Method: GET
### StatusCode : 200 (OK)
### Port : (:8080)
### RequestParam : "fileName" (as a String)

<img width="1078" alt="1" src="https://github.com/bkizilkayaa/spring-boot-filetransfer/assets/88281419/bced4504-8d0b-4a50-8f99-443c3c5ca432">

</br>

### if file not found, you will get http not found error.

<img width="1080" alt="2" src="https://github.com/bkizilkayaa/spring-boot-filetransfer/assets/88281419/666d47c2-e347-4a47-909b-b47d4f2221a5">

<br/>

## 2-) Upload file
### 
```diff
+ localhost:8080/files/upload/client
```
### Explanation: Upload file to client-side 
### Method: POST
### StatusCode : 201 (CREATED)
### Port : (:8080)
### RequestParam : "file" (as a MultipartFile)

<img width="1079" alt="3" src="https://github.com/bkizilkayaa/spring-boot-filetransfer/assets/88281419/9bbfa2f1-4af5-438f-8357-afeb49637f46">

### As you can see, there is no file named "springboot.png" on the client side. 

<br/> 
 
```diff
-> After the POST request
-> File named "springboot.png" will be added to the server running on port :8081 with RestTemplate.
```

### As you can see, we successfully added file to client-side. (springboot.png)

<img width="1080" alt="4" src="https://github.com/bkizilkayaa/spring-boot-filetransfer/assets/88281419/84f7b867-004a-4e91-8ca3-e8d9e1597ec4">

<br/>

## 3-) Get file
### 
```diff
+ localhost:8081/test/downloadFile/{fileName}
```
### Explanation: Get file from client-side
### Method: GET
### StatusCode : 200 (OK)
### Port : (:8081)
### RequestParam : "fileName" (as a String)

<br/>

<img width="1079" alt="5" src="https://github.com/bkizilkayaa/spring-boot-filetransfer/assets/88281419/1d991ec7-564f-4791-8a5a-dfffd83c78bd">


