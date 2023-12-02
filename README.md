# Eggplant-biotic-stress-detection-app-using-CNN
Eggplant biotic stress detection app using CNN

Eggplant Disease Detection Android App
# Overview
This Android app is designed to detect and classify biotic diseases in eggplants. It utilizes the PythonAnywhere API for disease prediction and connects with a Raspberry Pi running Flask to facilitate communication between the Android app and the hardware.

# Features
11 Disease Classes: The app can identify and classify eggplant diseases into 11 different classes.
Real-time Data: Utilizes Flask on Raspberry Pi to provide real-time data transfer between the Android app and the hardware.
User-friendly Interface: The Android app offers an intuitive and user-friendly interface for interacting with the disease detection system.
# Requirements
Android Studio
PythonAnywhere account
Raspberry Pi with Flask installed

# Setup
 # Clone the Repository:

Copy code
git clone [https://github.com/your-username/eggplant-disease-app.git]
cd eggplant-disease-app
 # Android Studio:

Open the project in Android Studio.
Configure the necessary Android SDK and dependencies.
 # PythonAnywhere API:

Sign up for a PythonAnywhere account.
Obtain API credentials.
Update the credentials in the Android app code where necessary.
# Raspberry Pi Setup:
Install Flask on your Raspberry Pi.
install tensorflow in Raspberry pi and upload your model from your pc
Run the Flask server script.
# Run the App:

Build and run the Android app in Android Studio.

Ensure the Android device is connected to the same network as the Raspberry Pi.

 # Usage
Open the Android app.

Capture an image of an eggplant leaf with potential disease symptoms.

The app will send the image to the Flask server on the Raspberry Pi.

The Flask server will use the PythonAnywhere API to predict the disease.

Use local host ip address for realtime view

The predicted disease class will be displayed on the Android app.
