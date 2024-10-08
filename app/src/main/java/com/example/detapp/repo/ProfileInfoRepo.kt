package com.example.detapp.repo
import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.detapp.model.KongreReadModel
import com.example.detapp.model.PostReadModel
import com.example.detapp.model.ProfileInfoDataModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class ProfileInfoRepo(private val application: Application) {
    val firebaseUserMutableLiveData: MutableLiveData<FirebaseUser?> = MutableLiveData()
    val userLoggedMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val postReadModelList = mutableListOf<KongreReadModel>()

    init {
        if (auth.currentUser != null) {
            firebaseUserMutableLiveData.postValue(auth.currentUser)
        }
    }

//
//    private val ref = FirebaseDatabase.getInstance().getReference("users")


//    fun profileDeneme () : Flow<ProfileInfoDataModel>{
//        Thread.sleep(1_000)
//        return ref.child(auth.currentUser!!.uid).snapshots.mapNotNull {
//            it.getValue<ProfileInfoDataModel>()
//        }.flowOn(Dispatchers.IO)
//    }
    fun getProfilePostData(callback: (List<KongreReadModel>) -> Unit) {
        val db = Firebase.firestore
        db.collection("Kongreler").orderBy("time")
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                        if(document.getString("uid") == auth.uid){
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
                }
                callback(postReadModelList)
            }
            .addOnFailureListener { exception ->
                callback(emptyList())
            }
    }



    fun profileInfo (): LiveData<ProfileInfoDataModel> {
        val db = Firebase.firestore
        val users = db.collection("Users").document(auth.currentUser!!.uid)
        val profileLiveData = MutableLiveData<ProfileInfoDataModel>()
        users.get()
            .addOnSuccessListener {snapshot->
                profileLiveData.value = snapshot.toObject<ProfileInfoDataModel>()
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception) }
            return profileLiveData
    }
    fun deletePost(postId : String){
        val db = Firebase.firestore
        db.collection("Kongreler").document(postId)
            .delete()
            .addOnSuccessListener { Toast.makeText(application,"Başarıyla silindi", Toast.LENGTH_SHORT).show() }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
    }

//    fun deneme(): LiveData<ProfileInfoDataModel> {
//        val profileLiveData = MutableLiveData<ProfileInfoDataModel>()
//        val postRef = FirebaseDatabase.getInstance().getReference("users")
//
//        postRef.child(auth.currentUser!!.uid).get().addOnSuccessListener { dataSnapshot ->
//            val name = dataSnapshot.child("name").value.toString()
//            val email = dataSnapshot.child("email").value.toString()
//            val surname = dataSnapshot.child("surname").value.toString()
//            val username = dataSnapshot.child("username").value.toString()
//            val profileDataModel = ProfileInfoDataModel(name, surname, username, email)
//            profileLiveData.value = profileDataModel
//        }.addOnFailureListener {
//            Toast.makeText(application, "sorun var ", Toast.LENGTH_SHORT).show()
//        }
//
//        return profileLiveData
//    }
}