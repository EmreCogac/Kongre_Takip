package com.example.detapp.adapter
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.detapp.R
import com.example.detapp.model.KongreReadModel
import com.example.detapp.model.PostReadModel

class PostAdapter(
    private var originalPostReadModel: List<KongreReadModel>,
    private var postReadModel: List<KongreReadModel>,
    private val State: String,
    private val application: Context,
    private val itemClickListener: (KongreReadModel) -> Unit,
    private val buttonClickListener: (KongreReadModel) -> Unit,
    private val buttonClickListener1: (KongreReadModel) -> Unit,

    ): RecyclerView.Adapter<PostAdapter.PostChildHolder>(){
    interface ItemClickListener{
        fun onButtonClick(position: KongreReadModel )
        fun onClick1(position1: KongreReadModel)
        fun onClick2(position2: KongreReadModel)
    }
    @SuppressLint("NotifyDataSetChanged")
    fun filter(text: String) {
        postReadModel = if (text.isEmpty()) {
            originalPostReadModel
        } else {
            originalPostReadModel.filter { it.alakaliBolumler.contains(text, ignoreCase = true) }
        }
        notifyDataSetChanged()
    }

//    @SuppressLint("NotifyDataSetChanged")
//    fun setData(data: List<PostReadModel>) {
//        originalPostReadModel = data
//        postReadModel = data
//        notifyDataSetChanged()
//    }

    inner class PostChildHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val kongreAd: TextView = itemView.findViewById(R.id.KongreAd)
        val alakaliBolumler : TextView = itemView.findViewById(R.id.Turu)
        val tarihAraligi: TextView = itemView.findViewById(R.id.Tarih)
        val bildiriOzetTarihi: TextView = itemView.findViewById(R.id.BildiriTarih)
        val tarihKayitOdemeSonTarih: TextView = itemView.findViewById(R.id.TarihKayitOdeme)
        val konumKongre : TextView = itemView.findViewById(R.id.Konum)
        val kongreTarih: TextView = itemView.findViewById(R.id.KongreTarih)
        val btn : ImageButton = itemView.findViewById(R.id.deneme)
        val btn1 : ImageButton = itemView.findViewById(R.id.DahaFazla)
        val btn2 : ImageButton = itemView.findViewById(R.id.deneme2)
        val btn3 : ImageButton = itemView.findViewById(R.id.deneme1)
        val bildiriOzetLayout : ConstraintLayout = itemView.findViewById(R.id.BildiriOzetLayout)
        val kayitOdemeSonLayout : ConstraintLayout = itemView.findViewById(R.id.KayıtOdemeSonLayout)
        val kongreAnaTarih : ConstraintLayout = itemView.findViewById(R.id.kongreAnaTarih)
        
        init {
            btn.setOnClickListener {
                val buttonPosition = absoluteAdapterPosition
                if (buttonPosition != RecyclerView.NO_POSITION) {
                    val clickedItem = originalPostReadModel[buttonPosition]
                    itemClickListener(clickedItem)
                }
            }
            btn1.setOnClickListener {
                val buttonPosition = absoluteAdapterPosition
                if (buttonPosition != RecyclerView.NO_POSITION) {
                    val clickedItem = originalPostReadModel[buttonPosition]
                    itemClickListener(clickedItem)
                }
                }
            btn3.setOnClickListener {
                val buttonPosition = absoluteAdapterPosition
                if (buttonPosition != RecyclerView.NO_POSITION) {
                    val clickedItem = originalPostReadModel[buttonPosition]
                    buttonClickListener(clickedItem)
                }
                }
            btn2.setOnClickListener {
                val buttonPosition = absoluteAdapterPosition
                if (buttonPosition != RecyclerView.NO_POSITION) {
                    val clickedItem = originalPostReadModel[buttonPosition]
                    buttonClickListener1(clickedItem)
                }
                }


        }
    }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostChildHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.post_list, parent, false)
            return PostChildHolder(view)
        }

        override fun onBindViewHolder(holder: PostChildHolder, position: Int) {
            val box = postReadModel.get(position)
            holder.kongreAd.text = box.kongreAdi
            holder.alakaliBolumler.text = box.alakaliBolumler
            holder.tarihAraligi.text = box.tarihAraligi
            holder.bildiriOzetTarihi.text = box.bildiriSonTarih
            holder.tarihKayitOdemeSonTarih.text = box.kayitVeOdemeSonTarih
            holder.kongreTarih.text = box.kongreTarihi
            holder.konumKongre.text = box.konum

            var counter  = 0
            holder.btn1.setOnClickListener {
                when(counter){
                    1 ->{
                        holder.bildiriOzetLayout.visibility = View.GONE
                        holder.kayitOdemeSonLayout.visibility = View.GONE
                        holder.kongreAnaTarih.visibility = View.GONE
                        holder.btn1.setImageResource(R.drawable.okdown)
                        counter--
                    }
                    0 -> {
                        holder.bildiriOzetLayout.visibility = View.VISIBLE
                        holder.kayitOdemeSonLayout.visibility = View.VISIBLE
                        holder.kongreAnaTarih.visibility = View.VISIBLE
                        holder.btn1.setImageResource(R.drawable.ok)
                        counter++
                    }
                }
            }




        }

        override fun getItemCount(): Int {
            return postReadModel.size
        }

}