package com.example.test3;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

/**
 * @author Karl
 */
public class TextOperateOptionDialogFragment extends DialogFragment {

    // Use this instance of the interface to deliver action events
    TextOperateOptionDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Choose a operation")
                .setItems(R.array.text_operate_option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:

                                break;
                            case 1:

                                break;
                            case 2:

                                break;
                            default:
                                break;
                        }
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the TextOperateOptionDialogListener so we can send events to the host
            listener = (TextOperateOptionDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(requireActivity()
                    + " must implement NoticeDialogListener");
        }
    }

    public interface TextOperateOptionDialogListener {
        /**
         * 修改选中项内容
         *
         * @param dialog TextOperate对话框对象
         */
        void onDialogModifyClick(DialogFragment dialog);

        /**
         * 删除选中项
         *
         * @param dialog TextOperate对话框对象
         */
        void onDialogDeleteClick(DialogFragment dialog);

        /**
         * 复制选中项内容
         *
         * @param dialog TextOperate对话框对象
         */
        void onDialogCopyClick(DialogFragment dialog);
    }
}

