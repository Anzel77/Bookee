package com.example.test3.DialogFrament;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.test3.R;

import java.util.Objects;

/**
 * @author Karl
 */
public class SearchOptionDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle(R.string.dialog_web_search_option)
                .setItems(R.array.web_search_option, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            Intent searchGutenberg = new Intent(Intent.ACTION_VIEW);
                            searchGutenberg.setData(Uri.parse("http://www.gutenberg.org"));
                            startActivity(searchGutenberg);
                            break;
                        case 1:
                            Intent searchZlibrary = new Intent(Intent.ACTION_VIEW);
                            searchZlibrary.setData(Uri.parse("https://zh.ng1lib.org/"));
                            startActivity(searchZlibrary);
                            break;
                        case 2:
                            Intent searchShuge = new Intent(Intent.ACTION_VIEW);
                            searchShuge.setData(Uri.parse("https://new.shuge.org/"));
                            startActivity(searchShuge);
                            break;
                        default:
                            break;
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

}
