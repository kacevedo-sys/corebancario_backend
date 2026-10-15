# ---------- Etapa 1: compilar (JDK 21 + Gradle wrapper del proyecto) ----------
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
# -x test: los tests levantan la app y no deben depender de Oracle/Keycloak durante el build
RUN chmod +x gradlew && ./gradlew --no-daemon shadowJar -x test

# ---------- Etapa 2: ejecutar (solo JRE 21, imagen pequeña) ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# No correr como root dentro del contenedor
RUN useradd --system --no-create-home appuser

# Fat jar generado por el plugin Shadow: build/libs/backend-prueba-0.1-all.jar
COPY --from=build /app/build/libs/*-all.jar app.jar

USER appuser
EXPOSE 8081

# El JVM respeta el límite de memoria del contenedor
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75"

ENTRYPOINT ["java", "-jar", "app.jar"]
