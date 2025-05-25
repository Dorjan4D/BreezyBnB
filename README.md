Upute za instalaciju
1. Preuzmite repozitorij lokalno
2. U terminalu pozicionirajte se u folder front u preuzetom projektu
3. pokrenute narebdu: "npm install"

Postavljanje baze (morate imati postgreSQL instaliran)
1. otvorite file application.properties na poziciji Back\src\main\resources unutar projekta
2. variablu spring.datasource.url podesite tako da se spaja na vašu postgreSQL bazu (najvjerovatnije treba pormijeniti samo port)
3. spring.datasource.password podesite tako da upišete svoju šifru baze

Upute za pokretanje
1. otvorite folder back u IntelliJ razvojnom okruženju i kliknite na gumb run
2. U terminalu se pozicionirajte u folder front, zatim pokrenite naredbu "npm run start" te otvorite "http://localhost:3000" u web pregledniku 

