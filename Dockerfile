##################################################
# BUILDER CONTAINER
##################################################

FROM ubuntu:22.04 as builder

USER root

WORKDIR /usr/src/dependencies

# INSTALL ESSENTIAL TOOLS
RUN apt-get update && apt-get install -y \
    build-essential \
    wget \
    unzip \
    curl \
    sqlite3 \
    libsqlite3-dev \
    && rm -rf /var/lib/apt/lists/*

# INSTALL SQLITE3 FROM SOURCE (optional, you can skip if system sqlite3 is enough)
RUN wget https://www.sqlite.org/2023/sqlite-autoconf-3410200.tar.gz \
    && tar -zxf sqlite-autoconf-3410200.tar.gz \
    && cd sqlite-autoconf-3410200 \
    && ./configure \
    && make \
    && make install

# COPY MAKEFILE AND DATABASE SCRIPTS
COPY Makefile Makefile
COPY database/sqlite database/sqlite

# CREATE DEV SQLITE DATABASE
RUN make sqlite-db-refresh

##################################################
# GRADLE BUILD CONTAINER
##################################################

FROM gradle:8.10.2-jdk21 as builder-gradle

WORKDIR /home/gradle/source

# COPY PROJECT FILES
COPY build.gradle build.gradle
COPY settings.gradle settings.gradle
COPY gradlew gradlew
COPY gradle gradle
COPY src src

# MAKE GRADLE WRAPPER EXECUTABLE
RUN chmod +x gradlew

# BUILD THE SPRING BOOT JAR
RUN ./gradlew bootJar --no-daemon

##################################################
# FINAL CONTAINER
##################################################

FROM eclipse-temurin:21-jre-alpine

WORKDIR /usr/src/app

USER root

# ARG FOR VERSION TAGGING
ARG VERSION

# copy jar, dev db, and dev resource files
COPY --from=gradleimage /home/gradle/source/build/libs/ga4gh-starter-kit-passport-broker-${VERSION}.jar ga4gh-starter-kit-passport-broker.jar
COPY --from=builder /usr/src/dependencies/ga4gh-starter-kit.dev.db ga4gh-starter-kit.dev.db
COPY src/test/resources/ src/test/resources/

ENTRYPOINT ["java", "-jar", "ga4gh-starter-kit-passport-broker.jar"]
