package ink.domytattoo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class SearchFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var rootView = inflater?.inflate(R.layout.fragment_search, container, false)
        return rootView
    }


//    companion object {
//
//        private val ARG_PARAM1 = "param1"
//        private val ARG_PARAM2 = "param2"
//
//        // TODO: Rename and change types and number of parameters
//        fun newInstance(param1: String, param2: String): SearchFragment {
//            val fragment = SearchFragment()
//            val args = Bundle()
//            args.putString(ARG_PARAM1, param1)
//            args.putString(ARG_PARAM2, param2)
//            fragment.arguments = args
//            return fragment
//        }
//    }
}
