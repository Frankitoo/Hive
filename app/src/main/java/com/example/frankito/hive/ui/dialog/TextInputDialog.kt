package com.example.frankito.hive.ui.dialog

import android.content.Context
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.frankito.hive.R


fun showAddPlayerDialog(context: Context, nameListener: (String) -> Unit) {
    val title = context.getString(R.string.new_player)
    showTextInputDialog(context, title, null, nameListener)
}

private fun showTextInputDialog(context: Context, title: String?, text: String?, inputTextListener: (String) -> Unit) {

    val dialog = AlertDialog.Builder(context)
    val inflater = LayoutInflater.from(context)

    val dialogView = inflater.inflate(R.layout.dialog_add_player, null)
    dialog.setView(dialogView)

    val etTitle = dialogView.findViewById<TextView>(R.id.etTitle)
    etTitle.text = title

    val etName = dialogView.findViewById<EditText>(R.id.etName)
    etName.setText(text)

    val createdDialog = dialog.create() as AlertDialog
    createdDialog.show()


    val addPlayerButton = dialogView.findViewById<Button>(R.id.add_new_player_button)
    addPlayerButton.setOnClickListener{
        inputTextListener(etName.text.toString())
        createdDialog.dismiss()
    }

    addPlayerButton.isEnabled = false

    etName.addTextChangedListener(object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun afterTextChanged(s: Editable) {
            addPlayerButton.isEnabled = !(TextUtils.isEmpty(s))
        }
    })

}