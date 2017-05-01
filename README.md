# PSD2UI

This tool converts any given Photoshop Document(PSD) to Android XML layout files or Swift Layout files(Storyboard source code).

### How to set file for conversion
Copy the PSD file to /src/psd/parser/ and set this file as source in TestPSD.java located in the same directory.
```java
Psd psd = new Psd(new File(dir+"/src/psd/parser/YOUR_PSD_FILE.psd"));
```

## Basic guidelines for conversion
 * To convert to Android XML layout set the variable android=1 located in TestPSD.java. Converted files will go to
 src/psd/parser/android/.(Dimensions of output UI files are best suited for nexus 5 device, you can adjust size for different devices )
 * To convert to Swift layout set the variable android=0 located in TestPSD.java. Converted files will go to
 src/psd/parser/storyboard/.(Dimensions of output UI files are best suited for 4.7" screen devices, you can adjust size for different devices )
 * You can use these files in respective development platforms(Android Studio or Xcode).

### Guidelines for converting additional information
 * To make a button in UI, name that layer in PSD as "Button".
 * To make a Editable Text Box in UI, name that layer in PSD as "EditText".
