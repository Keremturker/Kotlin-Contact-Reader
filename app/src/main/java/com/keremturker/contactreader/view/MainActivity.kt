package com.keremturker.contactreader.view

import android.Manifest
import android.content.ContentResolver
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.keremturker.contactreader.Function
import com.keremturker.contactreader.R
import com.keremturker.contactreader.adapter.ContactAdapter
import com.keremturker.contactreader.model.Contact


private const val PERMISSIONS_REQUEST_READ_CONTACTS = 100

//region Permission
val permissions = arrayOf(
    Manifest.permission.READ_CONTACTS
)
val contact_sort = "display_name ASC"


lateinit var contactList: ArrayList<Contact>
lateinit var rv_contact: RecyclerView
lateinit var btn_contact: Button

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_contact = findViewById(R.id.btn_contact)
        rv_contact = findViewById(R.id.rv_contact)

        rv_contact.layoutManager = LinearLayoutManager(this)




        btn_contact.setOnClickListener {

            //Permission Check- if permission is PERMISSION_GRANTED
            if (Function.hasPermissions(this@MainActivity, *permissions)) {

                contactList = getContacts()
                rv_contact.adapter = ContactAdapter(contactList)
            } else { // if permission is PERMISSION_DENIED
                requestPermissions(
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    PERMISSIONS_REQUEST_READ_CONTACTS
                )


            }


        }


    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {

            //permission is PERMISSION_GRANTED
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                contactList = getContacts()
                rv_contact.adapter = ContactAdapter(contactList)


                //permission is PERMISSION_DENIED
            } else {

            }

        }


    }


    private fun getContacts(): ArrayList<Contact> {
        val resolver: ContentResolver = contentResolver;
        val cursor = resolver.query(
            ContactsContract.Contacts.CONTENT_URI, null, null, null,
            contact_sort
        )
        contactList = ArrayList()
        if (cursor!!.count > 0) {
            while (cursor.moveToNext()) {
                val contact_id =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val contact_name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY))


                val phone_number = contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contact_id,
                    null,
                    null
                )
                var contact_number = ""

                phone_number?.let {
                    while (it.moveToNext()) {

                        contact_number =
                            it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    }
                }


                var contact = Contact(contact_id, contact_name, contact_number)
                contactList.add(contact)


            }
        } else {
            //   toast("No contacts available!")
        }
        cursor?.close()
        return contactList
    }
}