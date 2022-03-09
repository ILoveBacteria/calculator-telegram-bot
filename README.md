# Calculator Telegram Bot

## Description

This is a simple calculator working with Telegram bot. 
Including addition, subtraction, multiplication and division

![Preview of bot](https://github.com/ILoveBacteria/calculator-telegram-bot/blob/master/assets/preview.png)

## Libraries
This project uses the library introduced on the [Telegram website](https://core.telegram.org/bots/samples#java).
You can find more information about this library [here](https://github.com/rubenlagus/TelegramBots)

### How to import library?

1. Using Maven Central Repository:

```xml
    <dependency>
        <groupId>org.telegram</groupId>
        <artifactId>telegrambots</artifactId>
        <version>5.7.1</version>
    </dependency>
```

2. Using Gradle:

```gradle
    implementation 'org.telegram:telegrambots:5.7.1'
```

3. Using Jitpack from [here](https://jitpack.io/#rubenlagus/TelegramBots/5.7.1)
4. Download the jar(including all dependencies) from [here](https://mvnrepository.com/artifact/org.telegram/telegrambots/5.7.1)

Import the library *.jar* direclty to your project. Depending on the IDE you are using, the process to add a library 
is different, here is a video that may help with [Intellij](https://www.youtube.com/watch?v=NZaH4tjwMYg) or
[Eclipse](https://www.youtube.com/watch?v=VWnfHkBgO1I)

## Compile And Run

if you enjoy this project and want to see how it works, 

1. clone the project:

```shell
git clone https://github.com/ILoveBacteria/calculator-telegram-bot.git
```

2. You must enter the token you received from the [BotFather](https://telegram.me/BotFather) 
in the `CalculatorBot` class in the `getBotToken()` method

3. You must enter the username of your bot without '@' in the `CalculatorBot` class in the `getBotUsername()` method
4. Install [JDK 17](https://www.oracle.com/java/technologies/downloads)
5. Install [Maven 3](https://maven.apache.org/download.cgi)
6. Build the project:

```shell
mvn clean install
```

Maven will read the `pom.xml` and download all dependencies automatically then gives you a jar file named 
`CalculatorTelegramBot-{version}-jar-with-dependencies.jar`

7. Run this jar file and enjoy:
```shell
java -jar CalculatorTelegramBot-{version}-jar-with-dependencies.jar
```

## Last Release

You can download and run the last release from [here](https://github.com/ILoveBacteria/calculator-telegram-bot/releases)
