package com.example.interiewprepandroidapp.ui.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.*
import com.example.interiewprepandroidapp.R
import com.example.interiewprepandroidapp.data.model.Country
import com.example.interiewprepandroidapp.databinding.SearchViewFragmentBinding
import com.example.interiewprepandroidapp.ui.adapter.SearchItemsAdapter
import com.example.interiewprepandroidapp.ui.adapter.SearchItemsListCallback
import com.example.interiewprepandroidapp.ui.viewmodel.SearchViewModel

class SearchViewFragment : Fragment() {

    companion object {
        fun newInstance() = SearchViewFragment()
    }

    private lateinit var viewBinding : SearchViewFragmentBinding

    private val viewModel by viewModels<SearchViewModel>()

    var searchMenuItem : MenuItem? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = SearchViewFragmentBinding.inflate(inflater, container, false)

        val view = viewBinding.root

        setupSearchUI()

        return view

        // return inflater.inflate(R.layout.main_fragment, container, false)
    }

    fun setupSearchUI() {
        val searchItemAdapter = SearchItemsAdapter(object : SearchItemsListCallback {
            override fun onItemSelected(country: Country, index: Int) {
                Toast.makeText(requireContext(), "Item clicked: ${country.value}", Toast.LENGTH_SHORT).show()

                // close out search view too
                // searchMenuItem?.collapseActionView()
            }
        })

        viewBinding.searchResultsList.adapter = searchItemAdapter
        viewBinding.searchResultsList.layoutManager = LinearLayoutManager(requireActivity())
        val divider = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        viewBinding.searchResultsList.addItemDecoration(divider)

        viewModel.filteredResults.observe(viewLifecycleOwner) { filteredResults ->
            searchItemAdapter.submitList(filteredResults)
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            viewBinding.searchProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.seach_view_menu, menu)

        searchMenuItem = menu.findItem(R.id.action_search)

        val searchView = searchMenuItem!!.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // query changed, tell view model
                viewModel.onNewSearchQueryUpdated(newText ?: "")

                return true
            }

        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModelProvider(this).get(SearchViewModel::class.java)

        setHasOptionsMenu(true)
    }
}



