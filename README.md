# tfbug
This is a demo project for tensorflow bugtracker. It demonstrates the difference between Android and python prediction output using the same model and the same input data.

**Android output:**

    2022-06-10 23:52:46.146 20317-20317/com.android.tfbug I/TFBug: File input.dat md5: 99cc12fcae36b2b07ce8e3afe8c1ed86
    2022-06-10 23:52:46.252 20317-20317/com.android.tfbug I/TFBug: File model.tflite md5: eb302aa27bd246fb2759e768ad3c3bc0
    2022-06-10 23:52:46.587 20317-20317/com.android.tfbug I/TFBug: Model output: 
        12 14 14 16 10 253 
        12 14 15 17 10 253 
        12 14 15 17 10 253 
        18 17 16 18 10 253 
        18 17 18 21 10 253 
        18 15 20 23 10 253 
        25 18 15 16 10 253 
        25 18 16 18 10 253 
        25 18 17 19 10 253 
        32 18 15 16 10 253


**Python output:**

    File input.dat checksum: 99cc12fcae36b2b07ce8e3afe8c1ed86
    File model.tflite checksum: eb302aa27bd246fb2759e768ad3c3bc0
    Model output:
    [[ 12  13  14  16  10 253]
     [ 12  13  15  17  10 253]
     [ 12  13  15  17  10 253]
     [ 18  17  16  18  10 253]
     [ 18  17  18  21  10 253]
     [ 18  15  20  22  10 253]
     [ 25  18  15  16  10 253]
     [ 25  18  16  18  10 253]
     [ 25  18  17  19  10 253]
     [ 32  18  15  17  10 253]]


**Python Google Colab:**

https://colab.research.google.com/drive/1J_gE3JYiqS9MNml9phFiryznI-Bcc3-M#scrollTo=j-whdp8PX6JR


**How to run Android code:**

Just clone this repo, open it in Android Studio, compile and run.
The output will be in the logcat.
