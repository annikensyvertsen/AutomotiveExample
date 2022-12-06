# AutomotiveExample
In this repo you will find a very simlpified code example with a basic Android Automotive setup for an application with the POI category.
If you want to change the category of the application, this can be done in the AndroidManifest.xml file, just search for "POI" and replace it with the cateogry you want.

Below, I will include a guide to setup an Android Automotive project. 
You should also read the documentation before you start:
- https://developer.android.com/training/cars/apps

## Prerequisites

### Android Studio
Make sure you have the correct version of Android Studio (not Android Studio preview) downloaded
I used Android Studio Dolphin | 2021.3.1 Patch (6th December 2022) 

![image](https://user-images.githubusercontent.com/31958950/205895835-ab3e9a4b-bea9-464c-8797-f6bf279742a7.png)

You can check what version you have by clicking “Android Studio” -> About Android Studio 

### Google Account
You also need to have a Google Account so that you can sign in on Google Play store in the emulator and download the app/update the system etc.

## Initialize Project
- Click File -> New -> New Project 
- Choose Automotive from the Templates list, and then “No Activity” 
- Click "Next"

![image](https://user-images.githubusercontent.com/31958950/205915770-a15da317-1e8a-4901-aa55-c73b1e1bc91d.png)

- Give a name to your application, I named mine "Automotive-Example"
- Change the package name if you want to. By default it says com.example.nameOfYourApplication, but you can change it to com.companyName.nameOfYourApplication. Make sure you switch out companyName with the name of your company, and nameOfYourApplication with the name of your application. If you don't have a company name you use .example.

![image](https://user-images.githubusercontent.com/31958950/205916314-7fb64041-b25e-4c03-a81f-457e24830bb2.png)

- Make sure you have chosen the correct Minimum SDK. I used API 28: Android 9.0 (Pie)

## Setup project

- Create a folder in automotive/res called xml, and add a new file automotive_app_desc.xml 

![image](https://user-images.githubusercontent.com/31958950/205916984-c667c657-d086-456c-8cd1-b76362bd25c2.png)

- Paste the code below in the file:

```
<automotiveApp>  
  <uses name="template"/>  
</automotiveApp> 
```

- Open the automotive/src/main/AndroidManifest.xml file and paste in the code below

```
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <uses-feature
        android:name="android.hardware.type.automotive"
        android:required="true" />

    <uses-feature
        android:name="android.software.car.templates_host"
        android:required="true" />
    <uses-feature
        android:name="android.hardware.wifi"
        android:required="false" />
    <uses-feature
        android:name="android.hardware.screen.portrait"
        android:required="false" />
    <uses-feature
        android:name="android.hardware.screen.landscape"
        android:required="false" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        >

        <service
            android:name=".MyCarAppService"
            android:exported="true">
            <intent-filter>
                <action android:name="androidx.car.app.CarAppService"/>
                <category android:name="androidx.car.app.category.POI"/>
            </intent-filter>
        </service>

        <activity
            android:exported="true"
            android:theme="@android:style/Theme.DeviceDefault.NoActionBar"
            android:name="androidx.car.app.activity.CarAppActivity"
            android:launchMode="singleTask"
            android:label="Your app name">


            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>

            <meta-data android:name="distractionOptimized" android:value="true" />

        </activity>
        <meta-data android:name="com.android.automotive"
            android:resource="@xml/automotive_app_desc"/>
        <meta-data
            android:name="androidx.car.app.minCarApiLevel"
            android:value="1"/>

    </application>

</manifest>
```

