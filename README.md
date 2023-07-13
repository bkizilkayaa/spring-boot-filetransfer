# [TR] -> Bu projede RestTemplate kullanarak :8080 portundan :8081 portunun Http Metodlarının çağrılması amaçlanmıştır. <br/> Sunucu tarafından (:8080) portundan, client tarafına (:8081) portuna dosya yazabilir, okuyabilirsiniz. <br/> Aşağıda projenin herkes tarafından anlaşılabilmesi için örnek bir kullanımını yaptım ve ingilizce doküman hazırladım.  <br/> Umarım faydalı olacaktır. ⏬ ⏬ ⏬

<br/>

# [EN] -> This project uses RestTemplate to call the Http methods of port :8081 from port :8080. <br/> You can write and read files on the Client-Side (:8081). ⏬ ⏬ ⏬ <br/>


## 1-) Get Specific File on server-side
### 
```diff
+ localhost:8080/files/downloadFile/{fileName}
```
### Lists specific file on server-side.
### Method: GET
### StatusCode : 200 (OK)
### Port : :8080
### Param : "fileName" (String)

<img width="1078" alt="1" src="https://github.com/bkizilkayaa/spring-boot-filetransfer/assets/88281419/bced4504-8d0b-4a50-8f99-443c3c5ca432">

</br>

### if file not found, you will get http not found error.

<img width="1080" alt="2" src="https://github.com/bkizilkayaa/spring-boot-filetransfer/assets/88281419/666d47c2-e347-4a47-909b-b47d4f2221a5">
