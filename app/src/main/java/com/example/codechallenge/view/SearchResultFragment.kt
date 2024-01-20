package com.example.codechallenge.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codechallenge.adapter.SearchedBookAdapter
import com.example.codechallenge.data.model.Book
import com.example.codechallenge.network.Resource
import com.example.codechallenge.util.visibile
import com.example.codingchallenge.R
import com.example.codingchallenge.databinding.FragmentSearchResultBinding


class SearchResultFragment : Fragment() {

    private lateinit var binding: FragmentSearchResultBinding
    private lateinit var searchedBooksAdapter: SearchedBookAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchResultBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).supportActionBar?.hide() // hide toolbar
        (requireActivity() as MainActivity).binding.bottomNav.visibile(false) // hide bottom nav

        setSearchedBookAdapter()

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.noDataImg.visibile(false)
                binding.resultRecycler.visibile(true)
            }

            override fun afterTextChanged(s: Editable?) {
                val searchedBooks: MutableList<Book> =
                    if (s.toString().trim().length >= 3) {
                        binding.pbSearchedBook.visibile(true)
                        search(s.toString().trim())
                    } else {
                        ArrayList()
                    }
                binding.pbSearchedBook.visibile(false)

                if ((s.toString().trim().length >= 3) && search(s.toString().trim()).size == 0) {
                    return
                }
                searchedBooksAdapter.updateSearchedBook(searchedBooks)

            }
        })

        binding.btnBack.setOnClickListener {
            (requireActivity() as MainActivity).navController.navigateUp()
        }
    }

    private fun search(s: String): MutableList<Book> {
        val searchedBooks: MutableList<Book> = ArrayList()
        (requireActivity() as MainActivity).sharedBookViewModel.bookListResponse.observe(
            viewLifecycleOwner,
            {
                if (it is Resource.Success) {
                    it.value.forEach {
                        if (it.title?.contains(s, true) == true) {
                            searchedBooks.add(it)
                        }
                    }

                    if (searchedBooks.size == 0) {
                        binding.noDataImg.visibile(true)
                        binding.resultRecycler.visibile(false)
                        return@observe
                    }
                } else {
                    binding.noDataImg.visibile(true)
                    binding.resultRecycler.visibile(false)
                }
            })

        return searchedBooks
    }

    private fun setSearchedBookAdapter() {
        searchedBooksAdapter = SearchedBookAdapter({ bookId -> openBookDetails(bookId) })
        binding.resultRecycler.apply {
            adapter = searchedBooksAdapter
            layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)

        }

    }

    private fun openBookDetails(bookId: String) {
        val bundle = Bundle()
        bundle.putString("bookId", bookId)
        (requireActivity() as MainActivity).navController.navigate(
            R.id.action_searchResultFragment_to_bookDetailsBottomSheetFragment,
            bundle
        )
    }

}