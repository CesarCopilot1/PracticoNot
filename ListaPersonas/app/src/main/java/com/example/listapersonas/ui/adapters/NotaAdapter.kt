package com.example.listapersonas.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.listapersonas.R
import com.example.listapersonas.R.layout.nota_item_layout
import com.example.listapersonas.models.Nota

class NotaAdapter(
    private val notaList: ArrayList<Nota>,
    private val listener: OnNotaClickListener
) : RecyclerView.Adapter<NotaAdapter.NotaViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : NotaViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(nota_item_layout, parent, false)
        return NotaViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notaList.size
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        holder.bind(notaList[position], listener)
    }

    fun itemAdded(nota: Nota) {
        notaList.add(1, nota)
        notifyItemInserted(1)
//        notifyDataSetChanged()
    }

    fun itemDeleted(nota: Nota) {
        val index = notaList.indexOf(nota)
        notaList.removeAt(index)
        notifyItemRemoved(index)
    }

    fun itemUpdated(nota: Nota) {
        val index = notaList.indexOf(nota)
        notaList[index] = nota
        notifyItemChanged(index)
    }

    class NotaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var lblDescripcion = itemView.findViewById<TextView>(R.id.lblDescripcion)
        private var btnEdit = itemView.findViewById<ImageButton>(R.id.btnEdit)
        private var btnEliminar= itemView.findViewById<ImageButton>(R.id.btnEliminar)
//aqui se le aasign los datos al item layout
        fun bind(nota: Nota, listener: OnNotaClickListener) {
            lblDescripcion.text = nota.descripcion
            lblDescripcion.setBackgroundColor(nota.fondo)
    // se le aasigna las funcionalidades  los botones
            btnEdit.setOnClickListener {
                listener.onNotaEditClickListener(nota)
            }
            btnEliminar.setOnClickListener {
                listener.onNotaDeleteClickListener(nota)
            }

        }
    }

    public interface OnNotaClickListener {
        fun onNotaEditClickListener(nota: Nota)
        fun onNotaDeleteClickListener(nota: Nota)
    }
}