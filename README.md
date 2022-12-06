# AutomotiveExample
In this repo you will find a very simlpified code example with a basic Android Automotive setup for an application with the POI category.
If you want to change the category of the application, this can be done in the AndroidManifest.xml file, just search for "POI" and replace it with the cateogry you want.

You can find the code in this repository, but below, I will include a guide to setup an Android Automotive project. 
I highly recommend reading the documentation before you start if you have not done it yet.
- https://developer.android.com/training/cars/apps

Disclaimer: this guide might be outdated, and there might be deviations based on what computer you are using, what IDE etc. However, this worked for me so i will post it here since I struggled to find a comprehensive guide online myself.

## Prerequisites
I am using a MacBook Pro with Apple M1 Max chip and macOS Monterey 12.6 while creating this guide.

### Android Studio
Make sure you have the correct version of Android Studio (not Android Studio preview) downloaded
I used Android Studio Dolphin | 2021.3.1 Patch (6th December 2022) 

![image](https://user-images.githubusercontent.com/31958950/205895835-ab3e9a4b-bea9-464c-8797-f6bf279742a7.png)

If you already have Android Studio, you can check what version you have by clicking “Android Studio” -> About Android Studio.
If you don't, you should download it here: https://developer.android.com/studio?gclid=Cj0KCQiA7bucBhCeARIsAIOwr-8Olx-kgnMiuyfVE34AQYHM2wlM5ha-HMTmFfzzrhZBvWsS3DWbpRQaAn3CEALw_wcB&gclsrc=aw.ds 

### Google Account
You also need to have a Google Account so that you can sign in on Google Play store in the emulator and download the app/update the system etc. This can be done here https://accounts.google.com/signup/v2/webcreateaccount?flowName=GlifWebSignIn&flowEntry=SignUp. 

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

- Open the build.gradlle (Module: nameOfYourProject) file and paste the code below

```
plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
}

android {
    namespace 'com.nameOfYourOrganisation.nameOfYourApplication'
    compileSdk 33

    defaultConfig {
        applicationId "com.nameOfYourOrganisation.nameOfYourApplication"
        minSdk 29
        targetSdk 33
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
}

dependencies {
    implementation 'androidx.core:core-ktx:1.9.0'
    implementation 'androidx.appcompat:appcompat:1.5.1'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.4'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.0'
    implementation "androidx.car.app:app-automotive:1.3.0-beta01"
    testImplementation "androidx.car.app:app-testing:1.3.0-beta01"
}
```

- Make sure you replace "com.nameOfYourOrganisation.nameOfYourApplication" with your package name, both at nameSpace and applicationId
- Make sure you have the correct targetSdk and minSdk (you can read about the difference between the two of them here https://stackoverflow.com/questions/24510219/what-is-the-difference-between-min-sdk-version-target-sdk-version-vs-compile-sd)
- If you have trouble with the SDK setup, you might find information here: https://developer.android.com/about/versions/13/setup-sdk 
- Make sure you have the right version of androidx. In this example, I use 1.3.0-beta01. More on androidx versions here: https://developer.android.com/jetpack/androidx/releases/car-app 

- After changing the gradle file, you will be notified that you should sync since "Gralde files have changed since last project sync". Click “Sync now” 

![image](https://user-images.githubusercontent.com/31958950/205921905-9ef061ca-42a0-4a07-9cc4-36a72e1cbb5e.png)

- Then you need Session, Screen and CarAppService - three main concepts of Android Automotive applications. Create three files in java/com-example.automotive_example: HomeScreen.kt, MyCarAppService.kt, MyCarAppSession.kt and paste the code below:

HomeScreen.kt

```
package com.nameOfYourOrganisation.nameOfYourApplication

import androidx.car.app.CarContext
import androidx.car.app.Screen
import androidx.car.app.model.*

class HomeScreen(carContext: CarContext) : Screen(carContext) {
    override fun onGetTemplate(): Template {
        val row = Row.Builder().setTitle("Hello World!").build()
        val pane = Pane.Builder().addRow(row).build()
        return PaneTemplate.Builder(pane)
            .setHeaderAction(Action.APP_ICON)
            .build()
    }
}
```

MyCarAppService.kt

```
package com.nameOfYourOrganisation.nameOfYourApplication

import android.content.pm.ApplicationInfo
import androidx.car.app.CarAppService
import androidx.car.app.Session
import androidx.car.app.validation.HostValidator

class MyCarAppService : CarAppService(){
    override fun onCreateSession(): Session {
        return MyCarAppSession()
    }
    override fun createHostValidator(): HostValidator {
        return if(applicationInfo.flags and  ApplicationInfo.FLAG_DEBUGGABLE !=0){
            HostValidator.ALLOW_ALL_HOSTS_VALIDATOR
        }
        else{
            HostValidator.Builder(applicationContext)
                .addAllowedHosts(androidx.car.app.R.array.hosts_allowlist_sample)
                .build()
        }
    }
}
```

MyCarAppSession.kt

```
package com.nameOfYourOrganisation.nameOfYourApplication

import android.content.Intent
import androidx.car.app.Screen
import androidx.car.app.Session
import com.example.automotive_example.HomeScreen

class MyCarAppSession : Session() {

    override fun onCreateScreen(intent: Intent): Screen {
        return HomeScreen(carContext)
    }
}
```
- Again: make sure you replace "com.nameOfYourOrganisation.nameOfYourApplication" with your package name.
- In the documentation you can read more about why you need Screen, Session and CarAppService: https://developer.android.com/training/cars/apps#key-terms-concepts

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

In ```<category android:name="androidx.car.app.category.POI"/>``` the category of the application is sat. The supported categories you can choose between is provided here: https://developer.android.com/training/cars#supported-app-categories. Set your desired cateogry after "androidx.car.app.category."

## Run Project in Emulator

- If you don't have an emulator, click "device manager" in the side menu, or "tools -> device manager" in the top menu

<img width="323" alt="image" src="https://user-images.githubusercontent.com/31958950/205924889-e087d007-e3b0-41e7-a07e-b91c7920d399.png">

- Click "create device"

![image](https://user-images.githubusercontent.com/31958950/205925041-ff0e1e67-e422-4d6e-ba9d-454f0cee375c.png)

- Choose "Automotive" as Category and then Automotive (1024p landscape). This is the default emulator from Android Studio.
- Click next.
- Choose the emulator by selecting it in Device Manager. Launch the emulator by pressing the "run" button on the device. The emulator should be launched.
- Make sure you have the right run configurations and emulator chosen (automotive and Automotive(1024p landscape) API 32)

![image](https://user-images.githubusercontent.com/31958950/205926167-cbeff90d-5e77-4a8c-9e27-1093cac9d5ce.png)

- Click "run -> run" from the top menu, or the green "run"-button, to run the application on the emulator
- Then, the app will be downloaded to your emulator and it looks like this:
- 
![image](https://user-images.githubusercontent.com/31958950/205926342-835f9f2b-74a9-4ba9-8230-18e8d397cb9e.png)

The name of the application can be changed by finding "Your app name" in the project, and then replacing it.

- You might get a notification that “you have to update the system”. To do this you have to sign in with your google account.  

![image](https://user-images.githubusercontent.com/31958950/205926550-966c51e3-cf7a-4e27-9739-63040ba4aae3.png)

- In that case, click “check for updates” and then follow the instructions (click “update”). 

![image](https://user-images.githubusercontent.com/31958950/205926645-0614d8bf-01d3-4875-afd5-b26acf6e740e.png)

- After signing in, and then clicking "Update" on Google Automotive App Host in Google Play Store, you can try to open the application again. Hopefully it will look like this:

![image](https://user-images.githubusercontent.com/31958950/205926881-8ab1a409-4688-4289-bf0b-fe1456706e99.png)

And there you go! I hope the guide was of some help.


### Still not working?

Make sure you have: 
- the correct version of Android Studio 
- added an Android Emulator  
- your gradle plugin and gradle version is correct  
- correct version of androidx
- replaced the packagename with your package name all places
- the correct minSdk and targetSdk in the build.gradle file

If you have tried to use Volvo or Polestar emulator and your Mac uses Apple chip, it might not be compatible, so try with the Automotive emulator that is default in Android Studio instead. At this day, the emulator is (unfortunatly enough) not provided in Android Studio, so I had to download Android Studio Preview to access it. 

You must include CarAppService and use the templates provided from Google. This is not optional. However, if you create a login/sig in page, this can be customized using View or Jetpack Compose if you want.  

