#  Places To Visit
## Orhan Uçar Ödev 9
 
Bu örnek projede, kullanıcı mail ve password ile Firebase veritabanına kayıt olur. Kayıtlı kullanıcı mail ve password yardımı ile uygulamaya giriş yapar ve gezilecek yerleri not eder. Eklenen yerler anasayfa da en son eklenen en üstte görünecek şekilde RecyclerView içinde kullanıcıya gösterilir, kullanıcı uzun tıklama işlemi gerçekleştirerek eklenen veriyi silebilir. Gezilecek yerler için kullanıcı Resim- Başlık- Şehir- Notlar bilgilerini girer. Kullanıcı resim eklemek istemezse default olarak bir resim eklenir. Eğer kullanıcı şifresini unutursa Şifre Yenileme Ekranı'ndan mail adresine bir şifre yenileme e-postası gönderilir.
## Kütüphaneler ve Özellikler
- Firebase Auth
- Firebase Analytics
- Firebase Firestore
- Firebase Storage
- Firebase Realtime Database
- Picasso
- RecyclerView
<h2>İzinler</h2>

Uygulama, internete erişim sağlamak için aşağıdaki izne ihtiyaç duyar:
<pre><code>&lt;uses-permission android:name="android.permission.INTERNET"/&gt;</code></pre>

Uygulama, galeriye erişim için aşağıdaki izne ihtiyaç duyar:
<pre><code>&lt;uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/&gt;</code></pre>

<h2>plugins</h2>
<pre><code>plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
    id 'com.google.gms.google-services'
}</code></pre>

 
<h2>dependencies</h2>

<pre><code>dependencies {
    implementation 'androidx.core:core-ktx:1.10.1'
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.9.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    implementation 'com.google.firebase:firebase-auth:22.0.0'
    implementation 'com.google.firebase:firebase-auth-ktx:22.0.0'
    implementation 'com.google.firebase:firebase-firestore-ktx:24.6.1'
    implementation 'com.google.firebase:firebase-storage-ktx:20.2.0'
    implementation 'com.google.firebase:firebase-storage:20.2.0'
    testImplementation 'junit:junit:4.13.2'
    implementation platform('com.google.firebase:firebase-bom:28.1.0')
    implementation 'com.google.firebase:firebase-analytics-ktx'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
    implementation 'com.squareup.picasso:picasso:2.71828'
}</code></pre>    

<h2>Uygulama Ekran Görüntüleri</h2>
<p float="left">
  <img src=https://github.com/orhanucr/Places-To-Visit/blob/main/screenshots/Screenshot_1686149573.png width="30%" />
  <img src=https://github.com/orhanucr/Places-To-Visit/blob/main/screenshots/Screenshot_1686149570.png width="30%" />
  <img src=https://github.com/orhanucr/Places-To-Visit/blob/main/screenshots/Screenshot_1686149554.png width="30%" />
  <img src=https://github.com/orhanucr/Places-To-Visit/blob/main/screenshots/Screenshot_1686149549.png width="30%" />
  <img src=https://github.com/orhanucr/Places-To-Visit/blob/main/screenshots/Screenshot_1686149562.png width="30%" />
  <img src=https://github.com/orhanucr/Places-To-Visit/blob/main/screenshots/Screenshot_1686149583.png width="30%" />
</p>



    
