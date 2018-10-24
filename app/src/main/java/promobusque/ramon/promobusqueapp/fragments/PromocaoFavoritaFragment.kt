package promobusque.ramon.promobusqueapp.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_promocao_favorita.*
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //REcycler View
        linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_promocao_favorita, container, false)
    }

    private lateinit var mPromocoesFavoritasDatabaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mFirebaseDatabase = FirebaseDatabase.getInstance()

        mPromocoesFavoritasDatabaseReference = mFirebaseDatabase!!.reference.child("promocoesfavoritas")

        attachDatabaseReadListener()
    }

    fun attachDatabaseReadListener()
    {
        if(mChildEventListener == null) {
            val mChildEventListener = object: ChildEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onChildChanged(dataSnapshot: DataSnapshot, p1: String?) {
                    val promocaoFavorita = dataSnapshot.getValue(PromocaoFavorita::class.java)
                    addItem(promocaoFavorita!!)
                }

                override fun onChildAdded(p0: DataSnapshot, p1: String?) {

                }

                override fun onChildRemoved(p0: DataSnapshot) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            }
        }

        mPromocoesFavoritasDatabaseReference.addChildEventListener(this!!.mChildEventListener!!)
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
