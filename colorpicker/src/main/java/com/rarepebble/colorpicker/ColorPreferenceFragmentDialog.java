package com.rarepebble.colorpicker;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.PreferenceDialogFragmentCompat;

/**
 * Created by t.coulange on 28/08/15.
 */
public class ColorPreferenceFragmentDialog extends PreferenceDialogFragmentCompat {

    public ColorPreferenceFragmentDialog() {
    }

    public static ColorPreferenceFragmentDialog newInstance(String key) {
        ColorPreferenceFragmentDialog fragment = new ColorPreferenceFragmentDialog();
        Bundle b = new Bundle(1);
        b.putString("key", key);
        fragment.setArguments(b);
        return fragment;
    }


    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        super.onPrepareDialogBuilder(builder);
        final ColorPickerView picker = new ColorPickerView(getContext());

        picker.setColor(getColorPreference().getDefaultColor());
        picker.showAlpha(getColorPreference().getShowAlpha());
        picker.showHex(getColorPreference().getShowHex());
        picker.showValue(getColorPreference().getShowValue());
        builder
                .setTitle(null)
                .setView(picker)
                .setPositiveButton(getColorPreference().getPositiveButtonText(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final int color = picker.getColor();
                        getColorPreference().setColor(color);
                    }
                });
        if (getColorPreference().getSelectNoneButtonText() != null) {
            builder.setNeutralButton(getColorPreference().getSelectNoneButtonText(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getColorPreference().onNonButton();
                }
            });
        }
    }

    private ColorPreference getColorPreference() {
        return (ColorPreference) getPreference();
    }

    @Override
    public void onDialogClosed(final boolean b) {

    }
}
