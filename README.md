# Secret Lock Library
An android library which provide lock according to system settings.

#### Settings Implemented
1. Wifi
2. Bluetooth
3. Airplane Mode
4. Ring 
5. Location
6. Auto Rotate

## Installation
Add it in your root build.gradle at the end of repositories:
```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Add the dependency
```gradle
	dependencies {
	        implementation 'com.github.Abhishek0706:SecretLockLibrary::0.0.2'
	}
```

## Initializing Lock
```java
SecretLock secretlock = new SecretLock();
```

## Methods
The library have three methods

### 1. getLockValue()
This method return true if the lock is open.

```java
Boolean isLockOpen = secretLock.getLockValue(getApplicationContext());
``` 
### 2. openSettings()
It opens an AlertBox in which user can change the settings

```java
secretLock.openSettings(YourActivity.this);
```
<img src="https://raw.githubusercontent.com/abhishek0706/SecretLockLibrary/master/screenshots/alertBox.jpeg" width="200px">

### 3. setPreferenceValue()
This method is used to change the settings Programatically.
It takes Hashmap<String,Boolean> as one of argument.
String can be one of the following values.

| Setting(String) | Default value(Boolean) |
| :------------ |:---------------:| 
| wifi_status | false|
| bluetooth_status | false |
| airplanemode_status | false |
| ring_status | true |
| gps_status | false |
| rotate_status |false |

#### example:
```java
Hashmap<String,Boolean> myMap = new Hashmap<>();
myMap.put("wifi_status",true);
myMap.put("bluetooth_status",true);
secretLock.setPreferenceValue(YourActivity.this, myMap);
```

