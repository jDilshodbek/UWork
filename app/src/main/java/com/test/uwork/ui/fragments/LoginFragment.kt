package com.test.uwork.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.test.uwork.R
import com.test.uwork.viewmodel.WeatherViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val weatherViewModel: WeatherViewModel by viewModel()
    private val cd = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        enterBtn.setOnClickListener {
            login()
        }
        removeInputErrors()
    }

    private fun login() {
        if (usernameEdit.editText?.text.toString().isBlank()) {
            usernameEdit.requestFocus()
            usernameEdit.error = getString(R.string.username_required)
        } else if (passwordSignEdit.editText?.text!!.length < 6) {
            passwordSignEdit.requestFocus()
            passwordSignEdit.error = getString(R.string.must_be_six)
        } else if (!checkCapitalLetter(passwordSignEdit.editText?.text.toString())) {
            passwordSignEdit.requestFocus()
            passwordSignEdit.error = getString(R.string.must_contain_capital)
        } else if (!checkLowerCaseLetter(passwordSignEdit.editText?.text.toString())) {
            passwordSignEdit.requestFocus()
            passwordSignEdit.error = getString(R.string.must_contain_lowercase)
        } else {
            // all conditions true, login
            sendRequestToNet()
        }
    }

    private fun checkCapitalLetter(password: String): Boolean {
        password.forEach {
            if (it.isUpperCase())
                return true
        }
        return false
    }

    private fun checkLowerCaseLetter(password: String): Boolean {
        password.forEach {
            if (it.isLowerCase())
                return true
        }
        return false
    }


    private fun removeInputErrors() {
        usernameEdit.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                usernameEdit.error = null
            }
        })

        passwordSignEdit.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                passwordSignEdit.error = null
            }
        })
    }

    private fun sendRequestToNet() {
        weatherViewModel.weather.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data ->
                val response = String.format(
                    getString(R.string.weather_result),
                    data.country, data.main.temp, data.clouds.all, data.main.humidity
                )
                Snackbar.make(requireView(), response, Snackbar.LENGTH_LONG).show()
            }, { t ->
                Snackbar.make(requireView(), R.string.no_internet, Snackbar.LENGTH_LONG).show()
            }).let { cd.add(it) }
    }


}