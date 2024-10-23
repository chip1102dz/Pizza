package com.example.myapplication

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var db: UserDatabase
    private var list = mutableListOf<User>()
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        userAdapter = UserAdapter()
        db = UserDatabase(this)
        recyclerView = activityMainBinding.rcv
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
        recyclerView.adapter = userAdapter

        activityMainBinding.btnSUBMIT.setOnClickListener {
            themduLieu()
        }
        activityMainBinding.btnOK.setOnClickListener {
            xoaDuLieu()
        }
        getAllUser()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun themduLieu() {
        val name = activityMainBinding.edtName.text.toString()
        val tuongot = activityMainBinding.cbTuongOt.isChecked
        val phomai = activityMainBinding.cbPhoMai.isChecked

        val de = when{
            activityMainBinding.rdbDeDay.isChecked -> "Đế dày"
            activityMainBinding.rdbDeVua.isChecked -> "Đế vừa"
            else -> "Đế mỏng"
        }
        if(TextUtils.isEmpty(name)|| TextUtils.isEmpty(de)){
            Toast.makeText(this, "Hãy điền đủ thông tin !", Toast.LENGTH_SHORT).show();
            return;
        }
        val user = User(name = name, tuong_ot = tuongot, pho_mai = phomai, de = de)
        db.insertData(user)
        activityMainBinding.edtName.setText("")
        activityMainBinding.cbTuongOt.isChecked = false
        activityMainBinding.cbPhoMai.isChecked = false
        activityMainBinding.rdbDeDay.isChecked = false
        activityMainBinding.rdbDeMong.isChecked = false
        activityMainBinding.rdbDeVua.isChecked = false

        getAllUser()
    }

    private fun getAllUser() {
        list = db.getAllData()
        userAdapter.setData(list)
    }
    fun xoaDuLieu(){
        val list = userAdapter.getListCheck()
        list.forEach { user ->
            db.deleteData(user.id)
        }
        getAllUser()
    }
}