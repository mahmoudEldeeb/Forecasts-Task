package com.deeb.forcasttask.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.deeb.domain.models.ForecastModel
import com.deeb.forcasttask.R
import com.deeb.forcasttask.databinding.FragmentForecastsBinding
import com.deeb.forcasttask.ui_models.UiDataState
import com.deeb.forcasttask.views.adapters.ForecastsAdapter
import com.deeb.forcasttask.views.view_models.ForecastsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForecastsFragment : Fragment() {
    private val forecastsViewModel: ForecastsViewModel by viewModels()
    private val forecastsAdapter by lazy { ForecastsAdapter() }
    private var _binding: FragmentForecastsBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForecastsBinding.inflate(inflater, container, false)
         return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchView()
        setupRecyclerView()
        setupForecastsObserver()
        binding.btRetry.setOnClickListener {
            binding.stSearch.query.toString().let {forecastsViewModel.getForecasts(it)}

        }

    }
    private fun setupRecyclerView(){
        binding.resForecasts.adapter=forecastsAdapter
    }
    private fun setupSearchView() {
        binding.stSearch.isSubmitButtonEnabled = true
        binding.stSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { forecastsViewModel.getForecasts(cityName = it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }
    private fun setupForecastsObserver(){
        forecastsViewModel.forecastsLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is UiDataState.Idel -> {
                }
                is UiDataState.Loading -> showLoading()
                is UiDataState.Success -> handelSuccess(it.data as List<ForecastModel>)
                is UiDataState.Warning -> handelDataWithWarning(it.data as List<ForecastModel>)
                is UiDataState.Error -> handelError(it.message)
            }
        }
        }

    private fun handelError(message: String?) {
        var msg=message?:getString(R.string.some_thing_wrong)
        binding.txError.text=msg
        forecastsAdapter.clear()
        showRetryAndError()
        hideLoading()

    }

    private fun handelDataWithWarning(list: List<ForecastModel>) {
        forecastsAdapter.addValues(list)
        hideLoading()
        showSnackBarWarrning()

    }

    private fun handelSuccess(list: List<ForecastModel>) {
        forecastsAdapter.addValues(list)
        hideLoading()
    }

    private fun showRetryAndError() {
        binding.txError.visibility=View.VISIBLE
        binding.btRetry.visibility=View.VISIBLE
    }

    private fun hideRetryAndError() {
        binding.txError.visibility=View.GONE
        binding.btRetry.visibility=View.GONE
    }
    private fun showLoading() {
        binding.load.visibility = View.VISIBLE
        forecastsAdapter.clear()
        hideRetryAndError()
    }

    private fun hideLoading() {
        binding.load.visibility=View.GONE
    }

    private fun showSnackBarWarrning() {
       var snackBar = Snackbar.make(
           requireView(),
           getString(R.string.not_accurate_data)
           , Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction( getString(R.string.dismiss)) { snackBar.dismiss() }
        snackBar.show()
        }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}