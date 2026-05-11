package com.example.tugas5_pasienapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tugas5_pasienapi.model.Pasien

class PasienAdapter(private val listPasien: List<Pasien>) : RecyclerView.Adapter<PasienAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama: TextView = view.findViewById(R.id.tvNama)
        val tvKelamin: TextView = view.findViewById(R.id.tvKelamin)
        val tvLahir: TextView = view.findViewById(R.id.tvLahir)
        val tvAlamat: TextView = view.findViewById(R.id.tvAlamat)
        val tvTelepon: TextView = view.findViewById(R.id.tvTelepon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pasien, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pasien = listPasien[position]
        holder.tvNama.text = pasien.nama
        holder.tvKelamin.text = "Kelamin: ${pasien.jenis_kelamin}"
        holder.tvLahir.text = "Lahir: ${pasien.tanggal_lahir}"
        holder.tvAlamat.text = "Alamat: ${pasien.alamat}"
        holder.tvTelepon.text = "Telp: ${pasien.no_telepon}"
    }

    override fun getItemCount(): Int = listPasien.size
}