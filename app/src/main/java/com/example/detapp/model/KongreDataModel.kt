package com.example.detapp.model

import com.google.type.DateTime
import java.io.Serializable

data class KongreDataModel(
    var kongreSahibiMail : String = "",
    var kongreAdi : String =  "",
    var tarihAraligi : String = "",
    var alakaliBolumler : String = "",
    var bildiriSonTarih : String ,
    var kayitVeOdemeSonTarih : String  ,
    var kongreTarihi : String  ,
    var time : String,
    var konum : String,

): Serializable
//post -> Kongre adı
//Tarih aralığı
//alakalı bölümler
//-----------------
//Kayıt ve Ödeme için Son Tarih
//Tarihi
//-----------------
//Bildiri Özet Gönderimi için Son
//Tarihi
//-----------------
//Kongrenin tam Tarihi:
//Tarih