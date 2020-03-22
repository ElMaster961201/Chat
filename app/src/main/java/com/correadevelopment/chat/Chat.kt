package com.correadevelopment.chat

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import de.hdodenhof.circleimageview.CircleImageView

class Chat : AppCompatActivity() {

    private var fotoPerfil: CircleImageView? = null
    private var nombre:TextView? = null
    private var rvMensajes: RecyclerView? = null
    private var txtMensaje: EditText? = null
    private var btnEnviar: Button? = null
    private var adapter:AdapterMensajes? = null
    private var btnEnviarFoto: ImageButton? = null

    private var database: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null
    private var storage: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private var PHOTO_SEND: Int? = 1
    private var PHOTO_PERFIL: Int? = 2
    private var fotoPerfilCadena: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        fotoPerfil = findViewById(R.id.fotoPerfil)
        nombre = findViewById(R.id.nombre)
        nombre?.setText(intent.getStringExtra("U"))
        rvMensajes = findViewById(R.id.rvMensajes)
        txtMensaje = findViewById(R.id.txtMensaje)
        btnEnviar = findViewById(R.id.btnEnviar)
        btnEnviarFoto = findViewById(R.id.btnEnviarFoto)
        fotoPerfilCadena = ""

        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        databaseReference = database?.getReference(intent.getStringExtra("C")) //Sala de chat (Nombre)

        adapter = AdapterMensajes(this)
        var l = LinearLayoutManager(this)
        rvMensajes?.setLayoutManager(l)
        rvMensajes?.setAdapter(adapter)

        btnEnviar?.setOnClickListener {
            if (txtMensaje?.text.toString() != ""){
                databaseReference?.push()?.setValue(modelMensajeEnviar(txtMensaje?.text.toString(),"",
                    nombre?.text.toString(),fotoPerfilCadena,"1",ServerValue.TIMESTAMP))
                txtMensaje?.setText("")
            }else{
                Toast.makeText(this,"Debe escribir un mensaje.", Toast.LENGTH_SHORT).show()
            }
        }

        btnEnviarFoto?.setOnClickListener {
            var i: Intent = Intent(Intent.ACTION_GET_CONTENT)
            i.setType("image/jpeg")
            i.putExtra(Intent.EXTRA_LOCAL_ONLY,true)
            startActivityForResult(Intent.createChooser(i,"Selecciona una foto"), PHOTO_SEND!!)
        }

        fotoPerfil?.setOnClickListener {
            var i: Intent = Intent(Intent.ACTION_GET_CONTENT)
            i.setType("image/jpeg")
            i.putExtra(Intent.EXTRA_LOCAL_ONLY,true)
            startActivityForResult(Intent.createChooser(i,"Selecciona una foto de perfil"), PHOTO_PERFIL!!)
        }

        // Accion para recorrer la pantalla de chat automaticamente.
        adapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver(){
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                setScrollbar()
            }
        })

        databaseReference?.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val m: modelMensajeRecibir? = p0.getValue(modelMensajeRecibir::class.java)
                if (m != null) {
                    adapter?.addMensaje(m)
                    setScrollbar()
                }
            }
        })
    }

    private fun setScrollbar(){
        rvMensajes?.scrollToPosition(adapter?.itemCount!! - 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PHOTO_SEND && resultCode == Activity.RESULT_OK){
            var u: Uri? = data?.data
            storageReference = storage?.getReference(intent.getStringExtra("C")+"/imagenes") //Carpeta de Imagenes
            var fotoReferencia: StorageReference? = storageReference?.child(u?.lastPathSegment!!)

            // Codigo para descargar una foto.
            fotoReferencia!!.putFile(u!!).continueWithTask{ task ->
                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }
                fotoReferencia.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    databaseReference?.push()?.setValue(modelMensajeEnviar(nombre?.text.toString()
                            +" te ha enviado una foto", task.result.toString(),
                        nombre?.text.toString(),fotoPerfilCadena,"2",ServerValue.TIMESTAMP))
                }
            }
        }else{
            if (requestCode == PHOTO_PERFIL && resultCode == Activity.RESULT_OK){
                var u: Uri? = data?.data
                storageReference = storage?.getReference(intent.getStringExtra("C")+"/foto_perfil") //Carpeta de Imagenes
                var fotoReferencia: StorageReference? = storageReference?.child(u?.lastPathSegment!!)

                // Codigo para descargar una foto.
                fotoReferencia!!.putFile(u!!).continueWithTask{ task ->
                    if (!task.isSuccessful) {
                        task.exception?.let { throw it }
                    }
                    fotoReferencia.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        fotoPerfilCadena = task.result.toString()
                        databaseReference?.push()?.setValue(modelMensajeEnviar(nombre?.text.toString()
                                +" ha actualizado su foto de perfil", task.result.toString(),
                            nombre?.text.toString(),fotoPerfilCadena,"2",ServerValue.TIMESTAMP))
                        Glide.with(this).load(task.result.toString()).into(fotoPerfil)
                    }
                }
            }

        }
    }
}
