package com.vistapp.visitapp.utils;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Santiago Cirillo on 20/05/2017.
 */

public  abstract class OnSpinnerItemSelectedListener implements TextWatcher {
    /**
     * Listener used to capture "on selected item" event on MaterialBetterSpinner.
     * Created by douglas on 22/09/15.
     */
        @Override
        public final void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // nothing here
        }

        @Override
        public final void onTextChanged(CharSequence s, int start, int before, int count) {
            // nothing here
        }

        @Override
        public final void afterTextChanged(Editable editable) {
            onItemSelected(editable.toString());
        }

        protected abstract void onItemSelected(String string);
}
