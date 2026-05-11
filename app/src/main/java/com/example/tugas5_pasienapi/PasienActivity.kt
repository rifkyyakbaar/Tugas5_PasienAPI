package com.example.tugas5_pasienapi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugas5_pasienapi.network.ApiClient
import com.example.tugas5_pasienapi.network.SessionManager
import kotlinx.coroutines.launch

class PasienActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var rvPasien: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pasien)

        sessionManager = SessionManager(this)
        rvPasien = findViewById(R.id.rvPasien)
        progressBar = findViewById(R.id.progressBar)
        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        tvWelcome.text = "Halo, ${sessionManager.fetchUserName()}"

        rvPasien.layoutManager = LinearLayoutManager(this)

        fetchDataPasien()

        btnLogout.setOnClickListener {
            sessionManager.clearSession()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun fetchDataPasien() {
        val token = sessionManager.fetchAuthToken()

        if (token != null) {
            progressBar.visibility = View.VISIBLE

            lifecycleScope.launch {
                try {
                    val response = ApiClient.instance.getPasien(token)
                    if (response.isSuccessful) {
                        val listPasien = response.body()?.data ?: emptyList()
                        rvPasien.adapter = PasienAdapter(listPasien)
                    } else {
                        Toast.makeText(this@PasienActivity, "Gagal ambil data", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@PasienActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                } finally {
                    progressBar.visibility = View.GONE
                }
            }
        }
    }
}