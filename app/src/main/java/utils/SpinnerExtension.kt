package utils

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner

fun Spinner.setFixedOnItemSelectedListener(doStuff: (Int) -> Unit) {


    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

        var first = true

        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            if (!first) {
                doStuff(position)
            } else {
                first = false
            }
        }
    }

}
