package com.example.kin_si

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kin_si.Adapter.CartViewAdapter
import com.example.kin_si.Adapter.CartViewItem
import com.example.kin_si.utils.GlobalBox


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartFragment : Fragment() {
    private lateinit var cartRecycleView: RecyclerView
    private lateinit var cartList: MutableList<CartViewItem>
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*var textView = view.findViewById<TextView>(R.id.tv_cartCount);
        textView.text = GlobalBox.count.toString();*/
        var imageurl = GlobalBox.Imageurl
        var foodname = GlobalBox.Foodname
        var price = GlobalBox.Price
        var total = 0.00
        cartRecycleView = view.findViewById<RecyclerView>(R.id.recycleView);
        cartList = mutableListOf<CartViewItem>()
        for(i in foodname.indices){
            cartList.add(CartViewItem(foodname[i], imageurl[i], price[i]))
            total += price[i]
        }
        cartRecycleView.adapter = CartViewAdapter(cartList)
        var fprice = view.findViewById<Button>(R.id.FPrice);
        fprice.setText("CHECK OUT ($" + total.toString() + ")")

        fprice.setOnClickListener {
            loadFragment(OrderFragment())
        }
    }

    private fun loadFragment(fragment: Fragment?) {
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            if (fragment != null) {
                replace(R.id.fl_wrapper, fragment)
                commit()
            }
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}