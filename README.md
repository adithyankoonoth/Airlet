Airlert - IoT Car Cabin Safety System A complete IoT project designed to prevent carbon monoxide (CO) poisoning in vehicles by providing real-time monitoring and remote alerts. This repository contains the code for both the hardware firmware and the companion Android application.

The Problem: The Silent Threat Carbon monoxide is a colorless, odorless gas produced by a running car engine. In an enclosed space, it can quickly build up to lethal levels, posing a serious risk to anyone inside, especially those who are sleeping. "Airlert" was created to be a vigilant guardian against this silent threat.

How It Works Airlert is a classic three-part IoT system that creates a bridge between the physical environment inside the car and the user's mobile phone.

Hardware (The Detector): An ESP8266 microcontroller paired with an MQ-7 gas sensor constantly monitors the air inside the vehicle. If CO levels exceed a predefined safe threshold, it immediately triggers a loud, local buzzer to alert anyone inside.

Cloud (The Bridge): Simultaneously, the ESP8266 connects to the internet via Wi-Fi and sends an urgent alert payload to a Google Firebase Realtime Database. This acts as a reliable, instant messaging service.

Software (The Alerter): A native Android app is connected to the Firebase database with a real-time listener. The moment the alert data is written to the database, the app receives it and triggers a critical push notification on the user's phone, providing the CO level and a warning message.

(You can create a simple diagram and upload it to a site like Imgur to get a link for this image)

Technology Stack Hardware Microcontroller: NodeMCU (ESP8266)

Sensor: MQ-7 Carbon Monoxide Gas Sensor

Alert: 5V Active Buzzer

Cloud & Software Database: Google Firebase Realtime Database

Firmware: C++ (Arduino IDE)

Android App: Kotlin (Android Studio)

Hardware Setup Components NodeMCU ESP8266 Board MQ-7 Sensor Module 5V Buzzer Breadboard and Jumper Wires

Software Setup This project consists of two main codebases: the firmware for the ESP8266 and the Android application.

ESP8266 Firmware (Airlert.ino) IDE: Arduino IDE with the ESP8266 Board Manager installed.
Configuration: Before uploading, you must fill in your Wi-Fi credentials (WIFI_SSID and WIFI_PASSWORD) and your Firebase project details (API_KEY and DATABASE_URL) in the code.

Libraries: This code requires the ESP8266WiFi and WiFiClientSecure libraries.

Android App (Airlert - Android Studio Project) IDE: Android Studio
Language: Kotlin

Firebase Setup: To connect the app to your project, you must download your google-services.json file from your Firebase project settings and place it in the app/ directory of the Android project.

Permissions: The app requires android.permission.INTERNET in the AndroidManifest.xml to connect to Firebase.

Project Status This project is a fully functional prototype. It successfully demonstrates a complete, end-to-end IoT solution for a critical real-world safety problem.
