package com.example.kin_si

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.kin_si.Adapter.DiscoverViewAdapter
import com.example.kin_si.Adapter.DiscoverViewItem
import com.example.kin_si.utils.FirestoreRepository


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DiscoverFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DiscoverFragment : Fragment() {
    private var count: Int = 0
    private lateinit var DiscoverPager2: ViewPager2
    private var DiscoverList: MutableList<DiscoverViewItem> = mutableListOf<DiscoverViewItem>()
    private var firebaseRepository = FirestoreRepository()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener {
            fetchCategoriesDataFromDatabase()
        }
        DiscoverPager2 = view.findViewById<ViewPager2>(R.id.tempdiscoverPager);
        fetchCategoriesDataFromDatabase()
        var etSearchInput = view.findViewById<EditText>(R.id.foodSearchInput);
        etSearchInput.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (etSearchInput.text.length > 0) {
                    var filteredItem = DiscoverList.filter {
                        it.Chefname.toLowerCase().contains(etSearchInput.text.toString().toLowerCase()) or
                        it.Foodname.toLowerCase().contains(etSearchInput.text.toString().toLowerCase())
                    }
                    DiscoverPager2.adapter = DiscoverViewAdapter(filteredItem)
                } else {
                    DiscoverPager2.adapter = DiscoverViewAdapter(DiscoverList)
                }
            }
        })
    }

    private fun fetchCategoriesDataFromDatabase() {
        firebaseRepository.getSavedDiscover().get().addOnSuccessListener { documents ->
            DiscoverList.clear()
            for (document in documents) {
                DiscoverList.add(document.toObject(DiscoverViewItem::class.java))
            }
            DiscoverPager2.adapter = DiscoverViewAdapter(DiscoverList)
            // hide pull loading
            swipeRefreshLayout.isRefreshing = false
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DiscoverFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                DiscoverFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
