package com.example.kin_si

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.kin_si.Adapter.CategoryAdapter
import com.example.kin_si.Adapter.CategoryOnlineViewItem
import com.example.kin_si.Adapter.CategoryViewItem
import com.example.kin_si.Adapter.PromotionAdapter
import com.example.kin_si.CategoryViewFragment
import com.example.kin_si.R
import com.example.kin_si.utils.FirestoreRepository


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var promotionPager: ViewPager2
    private lateinit var categoryRecycleView: RecyclerView
    private lateinit var categoryImageList: MutableList<CategoryViewItem>
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var categoryImageOnlineList: MutableList<CategoryOnlineViewItem> = mutableListOf<CategoryOnlineViewItem>()
    private var firebaseRepository = FirestoreRepository()
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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val promoImageList = mutableListOf<Int>()
        promoImageList.add(R.drawable.promotion1)
        promoImageList.add(R.drawable.promotion2)
        promoImageList.add(R.drawable.promotion3)
        promoImageList.add(R.drawable.promotion4)
        promoImageList.add(R.drawable.promotion5)
        promoImageList.add(R.drawable.promotion6)
        promoImageList.add(R.drawable.promotion7)
        promotionPager = view.findViewById<ViewPager2>(R.id.promotionViewPager);
        promotionPager.adapter = PromotionAdapter(promoImageList);

        swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipeHomeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener {
            fetchCategoriesDataFromDatabase()
        }

        categoryRecycleView = view.findViewById<RecyclerView>(R.id.recycleView);
        /*categoryImageList = mutableListOf<CategoryViewItem>()
        categoryImageList.add(CategoryViewItem("Dinner", R.drawable.ic_baseline_free_breakfast_24))
        categoryImageList.add(CategoryViewItem("Lunch", R.drawable.ic_baseline_dinner_dining_24))
        categoryImageList.add(CategoryViewItem("Breakfast", R.drawable.ic_baseline_fastfood_24))
        categoryImageList.add(CategoryViewItem("Cafe", R.drawable.ic_baseline_free_breakfast_24))
        categoryImageList.add(CategoryViewItem("HealtyFood", R.drawable.ic_baseline_dinner_dining_24))
        categoryRecycleView.adapter = CategoryAdapter(categoryImageList)*/
        fetchCategoriesDataFromDatabase()

        var viewAllCategoryText = view.findViewById<TextView>(R.id.tv_view_cate);
        viewAllCategoryText.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.fl_container, CategoryViewFragment())
                        .addToBackStack("HomeFragment")
                        .commit()
            }
        }

        var etSearchInput = view.findViewById<EditText>(R.id.etSearchInput);
        etSearchInput.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (etSearchInput.text.length > 0) {
                    var filteredItem = categoryImageOnlineList.filter {
                        it.Type.toLowerCase().contains(etSearchInput.text.toString().toLowerCase())
                    }
                    categoryRecycleView.adapter = CategoryAdapter(filteredItem)
                } else {
                    categoryRecycleView.adapter = CategoryAdapter(categoryImageOnlineList)
                }
            }
        })
    }

    private fun fetchCategoriesDataFromDatabase() {
        firebaseRepository.getSavedCategories().get().addOnSuccessListener { documents ->
            categoryImageOnlineList.clear()
            for (document in documents) {
                categoryImageOnlineList.add(document.toObject(CategoryOnlineViewItem::class.java))
            }
            categoryRecycleView.adapter = CategoryAdapter(categoryImageOnlineList)
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
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                HomeFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}