# Barcode-Scanner

- This application showcases use of <b>Google Play Services Vision library</b>

- It uses Google's on device <b>machine learning</b> kit to scan for barcodes.
                
                
- This application can scan most of the barcode types like
   
   <b> - Linear formats: Codabar, Code 39, Code 93, Code 128, EAN-8, EAN-13, ITF, UPC-A, UPC-E</b>
   
   <b> - 2D formats: Aztec, Data Matrix, PDF417, QR Code</b>
                
                
- This application uses <b>CameraSource</b> and <b>BarcodeDetector</b>.

- These particular types of barcode value is being handled gracefully in this application (For other types of data you would see raw data)
                
    <b>- Wifi (SSID, password and encryption type),
                URL (Title and URL from the barcode), Product (Product Number from the barcode),
                Email address (Email Address, Subject, Body and Type), Phone number (phone number)</b>
- <b>You can long press on the scanned value (on the bottom of the scanner) to copy the value to your clipboard.</b>
- <b>Animation is used to show red color scanning line on the screen</b>.
- It natively supports <b>dark mode</b>.
- <b>It restarts CameraSource if you press rescan button. So you can rescan another barcode.</b>
- Libraries used are <b>Lottie Animation and Google Play Services Vision</b>.


### Screenshot of the startup page


![startupPage_barcodeScanner](https://user-images.githubusercontent.com/6127736/163389084-27caa19a-3eff-4eae-a892-c5d1ff4a95f6.jpg)




### Barcode scanner in action.

![BarcodeScanner](https://media.giphy.com/media/wLXZmE3Guqkp11ifFQ/giphy.gif)
