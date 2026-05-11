package com.example.tugas5_pasienapi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.tugas5_pasienapi.network.ApiClient
import com.example.tugas5_pasienapi.network.SessionManager
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sessionManager = SessionManager(this)

        if (sessionManager.fetchAuthToken() != null) {
            moveToPasienActivity()
        }

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan Password tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progressBar.visibility = View.VISIBLE
            btnLogin.visibility = View.GONE

            lifecycleScope.launch {
                try {
                    val response = ApiClient.instance.login(email, password)

                    if (response.isSuccessful && response.body()?.success == true) {
                        val token = response.body()?.data?.token
                        val nama = response.body()?.data?.user?.name

                        if (token != null && nama != null) {
                            sessionManager.saveAuthToken("Bearer $token")
                            sessionManager.saveUserName(nama)

                            Toast.makeText(this@MainActivity, "Login Berhasil", Toast.LENGTH_SHORT).show()
                            moveToPasienActivity()
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "Login Gagal: Cek kembali data Anda", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, "Error koneksi: ${e.message}", Toast.LENGTH_SHORT).show()
                } finally {
                    progressBar.visibility = View.GONE
                    btnLogin.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun moveToPasienActivity() {
        val intent = Intent(this, PasienActivity::class.java)
        startActivity(intent)
        finish()
    }
}