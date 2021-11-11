package com.allam.act.imgrecycler


import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.allam.act.R

class ImgActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_img_recycler)

        val rvContacts: RecyclerView = findViewById(R.id.rvContacts)

        rvContacts.adapter = ContactAdapter(this, createContacts())
        rvContacts.layoutManager = LinearLayoutManager(this)
    }

    private fun createContacts(): List<Contact> {
        val contacts = mutableListOf<Contact>()
        for (i in 1..150) contacts.add(Contact("Person #$i", i))
        return contacts
    }
}



class ContactAdapter(private val context: Context, private val contacts: List<Contact>)
    : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView
        val tvAge: TextView
        val ivProfile: ImageView

        init {
            tvName = view.findViewById(R.id.tvName)
            tvAge = view.findViewById(R.id.tvAge)
            ivProfile = view.findViewById(R.id.ivProfile)
        }
    }

    private val TAG = "ContactAdapter"

    // Usually involves inflating a layout from XML and returning the holder - THIS IS EXPENSIVE
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i(TAG, "onCreateViewHolder")
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false))
    }

    // Returns the total count of items in the list
    override fun getItemCount() = contacts.size

    // Involves populating data into the item through holder - NOT expensive
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder at position $position")
        val contact = contacts[position]

//        holder.bind(contact)
        holder.tvName.text = contact.name
        holder.tvAge.text = "Age: ${contact.age}"
        Glide.with(context).load(contact.imageUrl).into(holder.ivProfile)
    }

}

data class Contact(val name: String, val age: Int) {
    val imageUrl = "https://allam0053.github.io/api/drawable/Rain.png"
}