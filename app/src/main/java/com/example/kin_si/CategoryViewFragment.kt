package com.example.kin_si

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.kin_si.Adapter.CategoryAdapter2
import com.example.kin_si.Adapter.CategoryViewItem


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CategoryViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoryViewFragment : Fragment() {
    private lateinit var categoryRecycleView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var categoryImageList: MutableList<CategoryViewItem> = mutableListOf<CategoryViewItem>()
    //private var categoryImageOnlineList: MutableList<CategoryOnlineViewItem> = mutableListOf<CategoryOnlineViewItem>()
    //private var firebaseRepository = FirestoreRepository()
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
        return inflater.inflate(R.layout.fragment_category_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryRecycleView = view.findViewById<RecyclerView>(R.id.rcy_category);
        val categoryImageList = mutableListOf<CategoryViewItem>()
        categoryImageList.add(CategoryViewItem("Dinner", R.drawable.dinnern))
        categoryImageList.add(CategoryViewItem("Lunch", R.drawable.lunchn))
        categoryImageList.add(CategoryViewItem("Breakfast", R.drawable.breakfastn))
        categoryImageList.add(CategoryViewItem("Cafe", R.drawable.cafen))
        categoryImageList.add(CategoryViewItem("HealtyFood", R.drawable.healthyfoodn))
        categoryRecycleView.adapter = CategoryAdapter2(categoryImageList)

        /*var backBtn = view.findViewById<ImageView>(R.id.iv_back);
        backBtn.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener {
            fetchCategoriesDataFromDatabase()
        }
        categoryRecycleView = view.findViewById<RecyclerView>(R.id.rcy_category);
        fetchCategoriesDataFromDatabase()
    }

    private fun fetchCategoriesDataFromDatabase() {
        firebaseRepository.getSavedCategories().get().addOnSuccessListener { documents ->
            categoryImageOnlineList.clear()
            for (document in documents) {
                categoryImageOnlineList.add(document.toObject(CategoryOnlineViewItem::class.java))
            }
            //categoryRecycleView.adapter = CategoryAdapter2(categoryImageOnlineList)
            // hide pull loading
            swipeRefreshLayout.isRefreshing = false
        }*/
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CategoryViewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                CategoryViewFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}