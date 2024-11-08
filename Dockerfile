FROM eclipse-temurin:17-jdk-focal

# Ustawienie katalogu roboczego
WORKDIR /app

# Skopiowanie plików konfiguracyjnych Maven
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Pobranie zależności w trybie offline
RUN ./mvnw dependency:go-offline -B

# Skopiowanie kodu źródłowego aplikacji
COPY src ./src

# Budowanie aplikacji
RUN ./mvnw package -DskipTests -B

# Uruchomienie aplikacji
CMD ["./mvnw", "spring-boot:run"]
