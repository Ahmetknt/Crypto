package com.ahmetkanat.crypto.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmetkanat.crypto.R
import com.ahmetkanat.crypto.model.CryptoModel
import kotlinx.android.synthetic.main.recycler_row.view.*

class CryptoAdapter(val cryptoList : ArrayList<CryptoModel>,private val listener : Listener) : RecyclerView.Adapter<CryptoAdapter.CryptoHolder>() {

    interface Listener{
        fun onItemClick(cryptoModel : CryptoModel)
    }
    private val colors : Array<String> = arrayOf("#54BDB6","#6D54BD","#D0D83F","#65D83F","#D83F7C","#954F89","#F04E4B","#F0BB4B","#2A82DD")

    class CryptoHolder(view : View) : RecyclerView.ViewHolder(view){

        fun bind(cryptoModel : CryptoModel,listener : Listener,colors : Array<String>,position: Int){
            itemView.setOnClickListener {
                listener.onItemClick(cryptoModel)
            }
            itemView.exchange.text = cryptoModel.exchange
            itemView.market.text = cryptoModel.market
            itemView.base.text = cryptoModel.base
            itemView.quote.text = cryptoModel.quote
            itemView.setBackgroundColor(Color.parseColor(colors[position % 9]))
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row,parent,false)
        return CryptoHolder(view)

    }

    override fun onBindViewHolder(holder: CryptoHolder, position: Int) {
        holder.bind(cryptoList[position],listener,colors,position)
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }
}