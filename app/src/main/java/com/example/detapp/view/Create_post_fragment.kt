package com.example.detapp.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.detapp.databinding.FragmentCreatePostFragmentBinding
import com.example.detapp.model.KongreDataModel
import com.example.detapp.model.PostDataModel
import com.example.detapp.viewmodel.AuthViewModel
import com.example.detapp.viewmodel.PostViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.type.DateTime
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Calendar


class Create_post_fragment : Fragment() {

    private var _binding : FragmentCreatePostFragmentBinding? = null
    private val binding get() = _binding !!

    private var postViewModel: PostViewModel? = null
    private var authViewModel: AuthViewModel? =null
    private var tamTarihKongre : String = ""
    private var bildiriSonTarihKongre : String = ""
    private var kayitSonTarihKongre: String = ""
    @SuppressLint("SetTextI18n")
    private fun showDatePickerDialog(textView: TextView) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        var dateTime = ""

        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            dateTime =  "$selectedDay/${selectedMonth + 1}/$selectedYear"
            textView.text = dateTime

    }, year, month, day)

    datePickerDialog.show()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
            .getInstance(activity?.application!!)).get(PostViewModel::class.java)
        authViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
            .getInstance(activity?.application!!)).get(AuthViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatePostFragmentBinding.inflate(inflater, container, false)


        binding.apply {
//            var kitapname = kitapadoEditText.text.toString()
            createPostFragment.visibility = View.GONE
            hesap.visibility = View.VISIBLE
            authViewModel?.userData?.observe(viewLifecycleOwner, Observer<FirebaseUser?> { firebaseUser ->
                firebaseUser.let {

//                    kitapadoText.visibility = View.VISIBLE
//                    kitapadoEditText.visibility = View.VISIBLE
//                    postKitap.visibility = View.VISIBLE
//                    hesap.visibility = View.GONE
//                    postdeneme.visibility = View.VISIBLE

                    val current = LocalDate.now().toString()
//                    postKitap.setOnClickListener {
//
//                        val kitapadi = kitapadoEditText.text.toString()
//                        if(kitapadi.isNotEmpty()){
//                            val model = PostDataModel(kitapadi,current, firebaseUser!!.email.toString())
//                            postViewModel?.createPostFirestore(model)
//                        }
//                        kitapadoEditText.text.clear()
//                    }
                    createPostFragment.visibility = View.VISIBLE
                    hesap.visibility = View.GONE
                    postKongreButton.setOnClickListener {
                            val kongreAdEdit = kongreAdEditText.text.toString()
                            val kongreBolumlerEdit = kongreBolumlerEditText.text.toString()
                            val tamTarihAraligiEdit = tamTarihAraligi.text.toString()
                            val kongreKonum = konum.text.toString()
                            tamTarihKongre = tamTarihText.text.toString()
                            bildiriSonTarihKongre = bildiriSonText.text.toString()
                            kayitSonTarihKongre = kayitSonText.text.toString()
                        if (tamTarihKongre.isNotEmpty()&& bildiriSonTarihKongre.isNotEmpty()&& kayitSonTarihKongre.isNotEmpty()&& kongreAdEdit.isNotEmpty() && kongreBolumlerEdit.isNotEmpty() && tamTarihAraligiEdit.isNotEmpty() && kongreKonum.isNotEmpty())
                            {
                                val model = KongreDataModel(firebaseUser!!.email.toString(),kongreAdEdit,tamTarihAraligiEdit,kongreBolumlerEdit,bildiriSonTarihKongre,kayitSonTarihKongre , tamTarihKongre,current,kongreKonum)
                                postViewModel?.createFirestore(model)
                            }

//                            val model = KongreDataModel(firebaseUser!!.email.toString(),"kongre1","19-20 aralık","tıp bilgisayar        hukuk",current,current ,current,current )
//                            postViewModel?.createFirestore(model)
                    }
                    tamTarih.setOnClickListener{
                        showDatePickerDialog(tamTarihText)
                    }
                    bildiriSon.setOnClickListener{
                        showDatePickerDialog(bildiriSonText)
                    }
                    kayitSon.setOnClickListener{
                        showDatePickerDialog(kayitSonText)
                    }


                }
            })
//            postdeneme.setOnClickListener{
//                postViewModel?.firestore()
//            }

        }


        return binding.root
    }

}