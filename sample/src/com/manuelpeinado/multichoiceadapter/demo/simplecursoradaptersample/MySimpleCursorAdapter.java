/*
 * Copyright (C) 2013 Manuel Peinado
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
package com.manuelpeinado.multichoiceadapter.demo.simplecursoradaptersample;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.manuelpeinado.multichoiceadapter.MultiChoiceSimpleCursorAdapter;
import com.manuelpeinado.multichoiceadapter.demo.R;

public class MySimpleCursorAdapter extends MultiChoiceSimpleCursorAdapter {

    protected static final String TAG = MySimpleCursorAdapter.class.getSimpleName();
    private static final String[] FROM = { "name" };
    private static final int[] TO = { android.R.id.text1 };

    public MySimpleCursorAdapter(Bundle savedInstanceState, Context context, Cursor cursor) {
        super(savedInstanceState, context, R.layout.mca__simple_list_item_checkable_1, cursor, FROM, TO, 0);
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.my_action_mode, menu);
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        if (item.getItemId() == R.id.menu_share) {
            Toast.makeText(getContext(), "Share", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (item.getItemId() == R.id.menu_discard) {
            discardSelectedItems();
            return true;
        }
        return false;
    }

    private void discardSelectedItems() {
        String whereClause = BuildingsContract._ID + " = ?";
        for (long id : getCheckedItems()) {
            String[] whereArgs = { Long.toString(id) };
            getContext().getContentResolver().delete(BuildingsContract.CONTENT_URI, whereClause, whereArgs);
        }
        finishActionMode();
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }
}
