package app.kotlin.notekins.ui.logoutdialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import app.kotlin.notekins.R
import app.kotlin.notekins.entity.Note

class LogoutDialog(private val logOut: (Unit)) : DialogFragment() {

    companion object {
        val TAG = LogoutDialog::class.java.name + "TAG"

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.logout_dialog_title)
            .setMessage(R.string.logout_dialog_message)
            .setPositiveButton(R.string.ok_btn_title) { _, _ -> logOut }
            .setNegativeButton(R.string.logout_dialog_cancel) { _, _ -> dismiss() }
            .create()
}

