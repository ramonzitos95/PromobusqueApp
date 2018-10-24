package promobusque.ramon.promobusqueapp.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*
import promobusque.ramon.promobusqueapp.R
import promobusque.ramon.promobusqueapp.modelos.PromocaoFavorita
import promobusque.ramon.promobusqueapp.ui.PromocoesFavoritasRecyclerAdapter

class PromocaoFavoritaFragment : Fragment() {

    private var contexto: Context? = null
    private var mFirebaseDatabase: FirebaseDatabase? = null
    private lateinit var promocoesFavoritas: MutableList<PromocaoFavorita>
    private var mChildEventListener: ChildEventListener? = null
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: PromocoesFavoritasRecyclerAdapter
    private lateinit var mPromocoesFavoritasDatabaseReference: DatabaseReference
    private var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, view: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_promocao_favorita, view, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        promocoesFavoritas = mutableListOf()

        recyclerView = view.findViewById(R.id.recycler_view_favorito)!!

        //Recycler View
        linearLayoutManager = LinearLayoutManager(activity)
        recyclerView?.layoutManager = linearLayoutManager

        mFirebaseDatabase = FirebaseDatabase.getInstance()

        mPromocoesFavoritasDatabaseReference = mFirebaseDatabase!!.reference.child("promocoesfavoritas")

        attachDatabaseReadListener()

        setAdapter()
    }

    fun attachDatabaseReadListener()
    {
        if(mChildEventListener == null) {
            mChildEventListener = object: ChildEventListener{
                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d("FirebaseDatabase", "Erro no RealtimeFirebase: " + databaseError.message)
                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onChildChanged(dataSnapshot: DataSnapshot, p1: String?) {
                    val promocaoFavorita = dataSnapshot.getValue(PromocaoFavorita::class.java)
                    addItem(promocaoFavorita!!)
                }

                override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                    val promocaoFavorita = dataSnapshot.getValue(PromocaoFavorita::class.java)
                    addItem(promocaoFavorita!!)
                }

                override fun onChildRemoved(p0: DataSnapshot) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            }

            mPromocoesFavoritasDatabaseReference.addChildEventListener(mChildEventListener!!)
        }
    }

    private fun setAdapter(){
        val promocoesFavoritasRef = mFirebaseDatabase?.reference?.database?.getReference("promocoesfavoritas");

        if(promocoesFavoritas != null && promocoesFavoritas.isNotEmpty())
        {
            adapter = PromocoesFavoritasRecyclerAdapter(promocoesFavoritas)
            recyclerView?.setAdapter(adapter)
        }
    }

    private fun addItem(promocaoFavorita: PromocaoFavorita) {
        promocoesFavoritas.add(promocaoFavorita)
        adapter.notifyDataSetChanged()
    }

    override fun onAttach(context: Context) {
        contexto = context
        super.onAttach(context)
    }

    override fun onDetach() {
        if (mChildEventListener != null) {
            mPromocoesFavoritasDatabaseReference.removeEventListener(mChildEventListener!!)
            mChildEventListener = null
        }
        super.onDetach()
    }

}
