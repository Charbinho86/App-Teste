package com.example.appteste.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appteste.R
import com.example.appteste.data.entity.AppTesteEntity
import kotlinx.android.synthetic.main.list_item.view.*

class AppTesteListAdapter(
    private val registers: List<AppTesteEntity>
    ) : RecyclerView.Adapter<AppTesteListAdapter.AppTesteListViewHolder>() {

    var onItemClick: ((entity: AppTesteEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppTesteListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return AppTesteListViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppTesteListViewHolder, position: Int) {
        holder.bindView(registers[position])
    }

    override fun getItemCount(): Int = registers.size

    inner class AppTesteListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewAppTesteName: TextView = itemView.tv_list_item_name
        private val textViewAppTesteEmail: TextView = itemView.tv_list_item_email
        private val textViewAppTesteTelefone: TextView = itemView.tv_list_item_telefone

        fun bindView(appTeste: AppTesteEntity) {
            textViewAppTesteName.text = appTeste.nome
            textViewAppTesteEmail.text = appTeste.email
            textViewAppTesteTelefone.text = appTeste.telefone

            itemView.setOnClickListener {
                onItemClick?.invoke(appTeste)
            }
        }
    }
}