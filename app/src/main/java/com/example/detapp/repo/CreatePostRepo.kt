package com.example.detapp.repo

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.example.detapp.model.KongreDataModel
import com.example.detapp.model.KongreReadModel
import com.example.detapp.model.PostDataModel
import com.example.detapp.model.PostReadModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.firestore
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class CreatePostRepo(private val application: Application ){
    val firebaseUserMutableLiveData: MutableLiveData<FirebaseUser?> = MutableLiveData()
    val userLoggedMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val postReadModelList = mutableListOf<KongreReadModel>()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    init {
        if (auth.currentUser != null) {
            firebaseUserMutableLiveData.postValue(auth.currentUser)
        }
    }


    fun deneme(){
        val database = FirebaseDatabase.getInstance().reference
        val yeniVeri = "Yeni Veri1234"
        val yeniVeriReferansi = database.child("veriler").push()
        yeniVeriReferansi.setValue(yeniVeri)
            .addOnCompleteListener {
                Toast.makeText(application,"oldu", Toast.LENGTH_SHORT)
                    .show()
            }
    }

//    fun denemeFirestore(){
//        val db = Firebase.firestore
//        val user = hashMapOf(
//            "first" to "Ada",
//            "last" to "Lovelace",
//            "born" to 1815,
//        )
//
//        db.collection("users")
//            .add(user)
//            .addOnSuccessListener { documentReference ->
//                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.w(TAG, "Error adding document", e)
//            }
//    }


    // create post with realtime database
//    fun createPost(postDataModel: PostDataModel){
//
//        val userid = auth.currentUser!!.uid + postDataModel.time+ postDataModel.bookname
//        val refPost = FirebaseDatabase.getInstance().getReference("posts").child(userid)
//        if (auth.currentUser != null) {
//            val postMap = HashMap<String, Any>()
//            postMap["usermail"] = postDataModel.usermail
//            postMap["bookname"] = postDataModel.bookname
//            postMap["time"] = postDataModel.time
//
//            refPost.setValue(postMap)
//                .addOnCompleteListener {task->
//                    if (!task.isSuccessful) {
//                        Toast.makeText(application,task.exception?.message.toString(), Toast.LENGTH_SHORT).show()
//                    }else{
//                        Toast.makeText(application,"sorun mu var", Toast.LENGTH_SHORT).show()
//                    }
//
//                }
//        }else{
//            Toast.makeText(application,"problem",Toast.LENGTH_SHORT).show()
//        }
//
//    }



//    fun postList(adapter: PostAdapter, postReadModel: ArrayList<PostReadModel>){
//        val db = Firebase.firestore
//        var data = postReadModel
//        data = arrayListOf()
//        db.collection("Post").
//            addSnapshotListener { value, error ->
//                if (error != null) {
//                    Log.e("some error", error.message.toString())
//                }
//
//                if (value != null) {
//                    for (dc: DocumentChange in value.documentChanges) {
//                        if (dc.type == DocumentChange.Type.ADDED) {
//                            data.add(dc.document.toObject(PostReadModel::class.java))
//                        }
//                    }
//                    adapter.notifyDataSetChanged()
//                }
//            }
//    }
//    fun getPostData(callback: (List<PostReadModel>) -> Unit) {
//        val db = Firebase.firestore
//      db.collection("Post").orderBy("time")
//            .get()
//            .addOnSuccessListener { querySnapshot ->
//                for (document in querySnapshot.documents) {
//                    val bookname = document.getString("bookname") ?: ""
//                    val usermail = document.getString("usermail") ?: ""
//                    val uid = document.getString("uid") ?: ""
//                    val time = document.getString("time") ?: ""
//                    val postReadModel = PostReadModel(bookname, time, uid, usermail)
//                    postReadModelList.add(0,postReadModel)
//                }
//                callback(postReadModelList)
//            }
//            .addOnFailureListener { exception ->
//                Toast.makeText(application, exception.toString(), Toast.LENGTH_SHORT).show()
//                callback(emptyList())
//            }
//    }
fun getPostData(callback: (List<KongreReadModel>) -> Unit) {
    val db = Firebase.firestore
    db.collection("Kongreler").orderBy("time")
        .get()
        .addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot.documents) {
                val kongreSahibiMail = document.getString("kongreSahibiMail") ?: ""
                val kongreAdi = document.getString("kongreAdi") ?: ""
                val alakaliBolumler = document.getString("alakaliBolumler") ?: ""
                val tarihAraligi = document.getString("tarihAraligi") ?: ""
                val bildiriSonTarih = document.getString("bildiriVeOdemeSonTarih") ?: ""
                val kayitVeOdemeSonTarih = document.getString("kayitVeOdemeSonTarih") ?: ""
                val kongreTarihi = document.getString("kongreTarihi") ?: ""
                val time = document.getString("time") ?: ""
                val uid = document.getString("uid") ?: ""
                val konum = document.getString("konum") ?: ""
                val kongreReadModel = KongreReadModel(kongreSahibiMail, kongreAdi, tarihAraligi, alakaliBolumler,bildiriSonTarih,kayitVeOdemeSonTarih,kongreTarihi,time,uid,konum)
                postReadModelList.add(0,kongreReadModel)
            }
            callback(postReadModelList)
        }
        .addOnFailureListener { exception ->
            Toast.makeText(application, exception.toString(), Toast.LENGTH_SHORT).show()
            callback(emptyList())
        }
}

    fun createKongre(kongreDataModel: KongreDataModel){
        val database = Firebase.firestore
        if (auth.currentUser != null){
            val postId = kongreDataModel.kongreSahibiMail+auth.currentUser!!.uid+kongreDataModel.time+kongreDataModel.kongreAdi
            val postIdReplaced = postId.replace(" ", "_")
            var alakaliTumBolumler = kongreDataModel.alakaliBolumler
            alakaliTumBolumler = alakaliTumBolumler.trim().replace("\\s+".toRegex(), " ").replace(" ", ",")
            val postMap = HashMap<String , Any>()
            postMap["kongreSahibiMail"] = kongreDataModel.kongreSahibiMail
            postMap["kongreAdi"] = kongreDataModel.kongreAdi
            postMap["time"] = kongreDataModel.time
            postMap["tarihAraligi"] = kongreDataModel.tarihAraligi
            postMap["alakaliBolumler"] = alakaliTumBolumler
            postMap["bildiriVeOdemeSonTarih"] = kongreDataModel.bildiriSonTarih
            postMap["kayitVeOdemeSonTarih"] = kongreDataModel.kayitVeOdemeSonTarih
            postMap["kongreTarihi"] = kongreDataModel.kongreTarihi
            postMap["uid"] = auth.currentUser!!.uid
            postMap["postid"] = postIdReplaced
            postMap["konum"] = kongreDataModel.konum

            database.collection("Kongreler").document(postIdReplaced).set(postMap)
                .addOnCompleteListener {task->
                    if (!task.isSuccessful) {
                        Toast.makeText(application,task.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(application,"Başarıyla oluşturuldu", Toast.LENGTH_LONG).show()

                    }

                }
        }else{
            Toast.makeText(application,"problem",Toast.LENGTH_SHORT).show()

        }
    }
    @SuppressLint("SuspiciousIndentation")
    fun createPostFirestore(postDataModel: PostDataModel){
        val db = Firebase.firestore
        if (auth.currentUser != null) {
            val postId = postDataModel.usermail+auth.currentUser!!.uid+postDataModel.time+postDataModel.bookname
            val postIdReplaced = postId.replace(" ", "_")
            val postMap = HashMap<String, Any>()
            postMap["usermail"] = postDataModel.usermail
            postMap["bookname"] = postDataModel.bookname
            postMap["time"] = postDataModel.time
            postMap["uid"] = auth.currentUser!!.uid
            postMap["postid"] = postIdReplaced
            db.collection("Post").document(postIdReplaced).set(postMap)
                .addOnCompleteListener {task->
                    if (!task.isSuccessful) {
                        Toast.makeText(application,task.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(application,"Başarıyla oluşturuldu", Toast.LENGTH_LONG).show()
                        
                    }

                }
        }else{
            Toast.makeText(application,"problem",Toast.LENGTH_SHORT).show()
        }
    }
//    @RequiresApi(Build.VERSION_CODES.O)
//    fun stringToLocalDate(dateString: String): LocalDate {
//        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
//        return LocalDate.parse(dateString, formatter)
//    }
}