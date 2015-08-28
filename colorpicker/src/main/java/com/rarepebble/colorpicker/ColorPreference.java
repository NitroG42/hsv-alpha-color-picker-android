/*
 * Copyright (C) 2015 Martin Stone
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rarepebble.colorpicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.preference.DialogPreference;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class ColorPreference extends DialogPreference {
    private final String selectNoneButtonText;
    private final Integer defaultColor;
    private final String noneSelectedSummaryText;
    private final boolean showAlpha;
    private final boolean showHex;
    private final boolean showValue;
    private View thumbnail;

    public ColorPreference(Context context) {
        this(context, null);
    }

    public ColorPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ColorPreference, 0, 0);
            selectNoneButtonText = a.getString(R.styleable.ColorPreference_colorpicker_selectNoneButtonText);
            noneSelectedSummaryText = a.getString(R.styleable.ColorPreference_colorpicker_noneSelectedSummaryText);
            defaultColor = a.hasValue(R.styleable.ColorPreference_colorpicker_defaultColor)
                    ? a.getColor(R.styleable.ColorPreference_colorpicker_defaultColor, Color.GRAY)
                    : null;
            showAlpha = a.getBoolean(R.styleable.ColorPreference_colorpicker_showAlpha, true);
            showHex = a.getBoolean(R.styleable.ColorPreference_colorpicker_showHex, true);
            showValue = a.getBoolean(R.styleable.ColorPreference_colorpicker_showValue, true);
        } else {
            selectNoneButtonText = null;
            defaultColor = null;
            noneSelectedSummaryText = null;
            showAlpha = true;
            showHex = true;
            showValue = true;
        }
    }

    @Override
    public void onBindViewHolder(final PreferenceViewHolder holder) {
        try {
            thumbnail = addThumbnail(holder.itemView);
            showColor(getPersistedIntDefaultOrNull());
        } catch (Exception e) {

        }
        super.onBindViewHolder(holder);
    }

    private View addThumbnail(View view) {
        LinearLayout widgetFrameView = ((LinearLayout) view.findViewById(android.R.id.widget_frame));
        widgetFrameView.setVisibility(View.VISIBLE);
        widgetFrameView.removeAllViews();
        LayoutInflater.from(getContext()).inflate(
                isEnabled()
                        ? R.layout.color_preference_thumbnail
                        : R.layout.color_preference_thumbnail_disabled,
                widgetFrameView);
        return widgetFrameView.findViewById(R.id.thumbnail);
    }

    private Integer getPersistedIntDefaultOrNull() {
        return getSharedPreferences().contains(getKey())
                ? Integer.valueOf(getPersistedInt(Color.GRAY))
                : defaultColor;
    }

    private void showColor(Integer color) {
        if (thumbnail != null) {
            thumbnail.setVisibility(color == null ? View.GONE : View.VISIBLE);
            thumbnail.findViewById(R.id.colorPreview).setBackgroundColor(color == null ? 0 : color);
        }
        if (noneSelectedSummaryText != null) {
            setSummary(color == null ? noneSelectedSummaryText : null);
        }
    }

    private void removeSetting() {
        getSharedPreferences()
                .edit()
                .remove(getKey())
                .commit();
    }

    public int getDefaultColor() {
        return getPersistedInt(defaultColor == null ? Color.GRAY : defaultColor);
    }

    public boolean getShowAlpha() {
        return showAlpha;
    }

    public boolean getShowHex() {
        return showHex;
    }

    public boolean getShowValue() {
        return showValue;
    }

    public void setColor(final int color) {
        persistInt(color);
        showColor(color);
    }

    public String getSelectNoneButtonText() {
        return selectNoneButtonText;
    }

    public void onNonButton() {
        removeSetting();
        showColor(null);
    }
}
