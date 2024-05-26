package com.example.detapp.view

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.CalendarContract.Events
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.detapp.R
import com.example.detapp.adapter.FilterAdapter
import com.example.detapp.adapter.PostAdapter
import com.example.detapp.databinding.FragmentPostFragmentBinding
import com.example.detapp.model.ButtonModelData
import com.example.detapp.model.KongreReadModel
import com.example.detapp.model.PostReadModel
import com.example.detapp.viewmodel.AuthViewModel
import com.example.detapp.viewmodel.PostViewModel
import com.google.android.gms.common.wrappers.Wrappers
import com.google.firebase.auth.FirebaseUser
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Calendar
import java.util.Locale


class Post_fragment : Fragment() , PostAdapter.ItemClickListener, FilterAdapter.ItemClickListener {

    private var _binding : FragmentPostFragmentBinding? = null
    private val binding get() = _binding !!
    private lateinit var postViewModel: PostViewModel
    private var authViewModel: AuthViewModel? =null
    private lateinit var adapter: PostAdapter
    private lateinit var filterAdapter: FilterAdapter
    override fun onFilterButtonClick(position: ButtonModelData){
        adapter.filter(position.filterName)
    }
    fun stringToMillis(dateString: String?, pattern: String = "dd/MM/yyyy"): Long? {
        return try {
            val sdf = SimpleDateFormat(pattern, Locale.getDefault())
            val date = dateString?.let { sdf.parse(it) }
            date?.time
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    fun subtractDays(dateString: String, days: Int, pattern: String = "dd/MM/yyyy"): String? {
        return try {
            val sdf = SimpleDateFormat(pattern, Locale.getDefault())
            val date = sdf.parse(dateString)
            if (date != null) {
                val calendar = Calendar.getInstance()
                calendar.time = date
                calendar.add(Calendar.DAY_OF_MONTH, -days)
                sdf.format(calendar.time)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


     override fun onButtonClick(position: KongreReadModel) {
         val kongreTarih = subtractDays(position.kongreTarihi,1)
         val tarih = stringToMillis(kongreTarih)
         val intent = Intent(Intent.ACTION_INSERT).apply {
                 data = Events.CONTENT_URI
                 putExtra(Events.TITLE, position.kongreAdi)
                 putExtra(Events.EVENT_LOCATION, position.konum)
                 putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true)
                 putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                     tarih
                 )
         }
         startActivity(intent)
     }
    override fun onClick1(position1: KongreReadModel){
        val kongreTarih = subtractDays(position1.bildiriSonTarih,6)
        val tarih = stringToMillis(kongreTarih)
        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = Events.CONTENT_URI
            putExtra(Events.TITLE, position1.kongreAdi)
            putExtra(Events.EVENT_LOCATION, position1.konum)
            putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true)
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                tarih
            )


        }
        startActivity(intent)
    }
    override fun onClick2(position2: KongreReadModel){
        val kongreTarih = subtractDays(position2.kayitVeOdemeSonTarih,2)
        val tarih = stringToMillis(kongreTarih)
        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = Events.CONTENT_URI
            putExtra(Events.TITLE, position2.kongreAdi)
            putExtra(Events.EVENT_LOCATION, position2.konum)
            putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true)
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                tarih
            )
        }
        startActivity(intent)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
            .getInstance(activity?.application!!)).get(PostViewModel::class.java)
        authViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
            .getInstance(activity?.application!!)).get(AuthViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val filterButtonItem: List<ButtonModelData> = listOf(
            ButtonModelData("Tıp"),
            ButtonModelData("Bilgisayar"),
            ButtonModelData("Matematik"),
            ButtonModelData("Eğitim"),
            ButtonModelData("İktisat"),
            ButtonModelData("Arkeoloji"),
            ButtonModelData("Antropoloji"),
            ButtonModelData("Mühendislik"),
        )
        _binding = FragmentPostFragmentBinding.inflate(inflater, container, false)

            val recyclerView: RecyclerView = binding.recyclerPost
            val filterListRecyclerView :RecyclerView = binding.searchView

            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            postViewModel.postReadModelList.observe(viewLifecycleOwner, Observer { postList ->

                adapter = PostAdapter(postList,postList,"post",requireContext(),{position1 -> onClick1(position1)  },{position2 -> onClick2(position2)}) { position ->
                    onButtonClick(position)
                }
                recyclerView.adapter = adapter


            })


            filterListRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            filterAdapter = FilterAdapter(filterButtonItem){position ->
                onFilterButtonClick(position)
            }
            filterListRecyclerView.adapter = filterAdapter
//        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                adapter.filter(newText.orEmpty())
//                return true
//            }
//        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}