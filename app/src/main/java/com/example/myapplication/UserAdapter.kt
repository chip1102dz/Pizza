package com.example.myapplication

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemBinding

class UserAdapter() : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var list = mutableListOf<User>()
    var listGetCheck = mutableListOf<User>()
    fun getListCheck(): MutableList<User>{
        return listGetCheck
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: MutableList<User>){
        this.list = list
        notifyDataSetChanged()
    }

    class UserViewHolder(val itemBinding: ItemBinding) : RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemBinding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user: User = list[position]
        holder.itemBinding.tvName.text = user.name
        holder.itemBinding.tvTuongOt.text = if(user.tuong_ot) "Them tuong ot" else "Khong them tuong ot"
        holder.itemBinding.tvPhoMai.text = if(user.pho_mai) "Them pho mai" else "Khong them pho mai"
        holder.itemBinding.tvDe.text = user.de
        holder.itemBinding.checkbox.isChecked = listGetCheck.contains(user)
        holder.itemBinding.checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                listGetCheck.add(user)
            }else{
                listGetCheck.remove(user)
            }
        }

    }
}