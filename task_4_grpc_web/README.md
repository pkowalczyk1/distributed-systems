# gRPC-Web
### Instrukcja uruchomienia:
1. Uruchomić serwer gRPC w Javie
2. W katalogu `envoy` wykonać komendę `docker-compose up`
3. W katalogu `client` wykonać komendę `npm install` (musi być zainstalowany npm i NodeJS)
4. Wykonać komendę `npm start` (katalog `client`, być może trzeba będzie wykonać komendę `export NODE_OPTIONS=--openssl-legacy-provider` jeśli pojawią się błędy)
5. Otworzyć w przeglądarce `localhost:8080`