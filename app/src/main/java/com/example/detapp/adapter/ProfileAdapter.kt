package com.example.detapp.adapter

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
import com.example.detapp.model.KongreDataModel
import com.example.detapp.model.KongreReadModel
import com.example.detapp.model.PostReadModel

class ProfileAdapter (
    private var postReadModel: List<KongreReadModel>,
    private var itemClickListener: (KongreReadModel,Int,MutableList<KongreReadModel>) -> Unit

    ): RecyclerView.Adapter<ProfileAdapter.ProfileChildHolder>(){
    interface ItemClickListener{
        fun onButtonClick(position: KongreReadModel , absulatePosition : Int, postList: MutableList<KongreReadModel>)
    }
    inner class ProfileChildHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val kongreAd: TextView = itemView.findViewById(R.id.KongreAd)
        val alakaliBolumler : TextView = itemView.findViewById(R.id.Turu)
        val tarihAraligi: TextView = itemView.findViewById(R.id.Tarih)
        val bildiriOzetTarihi: TextView = itemView.findViewById(R.id.BildiriTarih)
        val tarihKayitOdemeSonTarih: TextView = itemView.findViewById(R.id.TarihKayitOdeme)
        val konumKongre : TextView = itemView.findViewById(R.id.Konum)
        val kongreTarih: TextView = itemView.findViewById(R.id.KongreTarih)
        val btn : ImageButton = itemView.findViewById(R.id.Sil)
        val btn1 : ImageButton = itemView.findViewById(R.id.DahaFazla)
        val bildiriOzetLayout : ConstraintLayout = itemView.findViewById(R.id.BildiriOzetLayout)
        val kayitOdemeSonLayout : ConstraintLayout = itemView.findViewById(R.id.KayıtOdemeSonLayout)
        val kongreAnaTarih : ConstraintLayout = itemView.findViewById(R.id.kongreAnaTarih)
        init {
            btn.setOnClickListener {
                val buttonPosition = absoluteAdapterPosition
                if (buttonPosition != RecyclerView.NO_POSITION) {
                    val clickedItem = postReadModel[buttonPosition]
                    postReadModel = postReadModel.toMutableList()
                    itemClickListener(
                        clickedItem, buttonPosition,
                       postReadModel as MutableList<KongreReadModel>
                    )
                }
            }
//            }
//            btn3.setOnClickListener {
//                val buttonPosition = absoluteAdapterPosition
//                if (buttonPosition != RecyclerView.NO_POSITION) {
//                    val clickedItem = postReadModel[buttonPosition]
//                    buttonClickListener(clickedItem)
//                }
//            }
//            btn2.setOnClickListener {
//                val buttonPosition = absoluteAdapterPosition
//                if (buttonPosition != RecyclerView.NO_POSITION) {
//                    val clickedItem = postReadModel[buttonPosition]
//                    buttonClickListener1(clickedItem)
//                }
//            }
//            delete.setOnClickListener {
//                val buttonPosition = absoluteAdapterPosition
//                if (buttonPosition != RecyclerView.NO_POSITION) {
//                    val clickedItem = postReadModel[buttonPosition]
//                    deleteClick(clickedItem,
//                }
//            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileAdapter.ProfileChildHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_list, parent, false)
        return ProfileChildHolder(view)
    }

    override fun getItemCount(): Int {
        return postReadModel.size
    }

    override fun onBindViewHolder(holder: ProfileAdapter.ProfileChildHolder, position: Int) {
        val box = postReadModel.get(position)
        holder.kongreAd.text = box.kongreAdi
        holder.alakaliBolumler.text = box.alakaliBolumler
        holder.tarihAraligi.text = box.tarihAraligi
        holder.bildiriOzetTarihi.text = box.bildiriSonTarih
        holder.tarihKayitOdemeSonTarih.text = box.kayitVeOdemeSonTarih
        holder.kongreTarih.text = box.kongreTarihi
        holder.konumKongre.text = box.konum
        holder.btn.visibility = View.VISIBLE

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

}