package com.example.fe3hguide.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.fe3hguide.R;

import java.util.List;


/**
 * This class works as a list adapter for the topics in the teatime fragment.
 * Because there's only 1 entry (1 group) in the list, a big part of the code is simplified from
 * what would be a regular BaseExpandableListAdapter.
 */
public class TopicsExpandableListAdapter extends BaseExpandableListAdapter {

    private final Context context;
    private final String title;
    private final List<String> topics;

    public TopicsExpandableListAdapter(Context context, String title, List<String> topics) {
        this.context = context;
        this.title = title;
        this.topics = topics;
    }

    @Override
    // Gets the data associated with the given child within the given group
    public Object getChild(int listPosition, int expandedListPosition) {
        return topics.get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        // The id of the child is just its position in its associated group
        return expandedListPosition;
    }

    @Override
    // Used to create a child item for a group
    public View getChildView(int listPosition, int expandedListPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        String expandedListText = topics.get(expandedListPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.topics_item, null);
        }

        // Gets the TextView where the topic will be
        TextView expandedListTextView = (TextView) convertView.
                findViewById(R.id.expanded_topics_item);

        // The text is just the topic of position expandedListPosition
        expandedListTextView.setText(expandedListText);

        return convertView;
    }

    @Override
    // Returns the number of childen. In this case, the number of topics for a given character
    public int getChildrenCount(int i) {
        return topics.size();
    }

    @Override
    // Returns the number of groups. In this case, there's only one.
    public int getGroupCount() {
        return 1;
    }

    @Override
    // Returns the name of the group with position listPosition
    public Object getGroup(int listPosition) {
        return title;       // There's only 1 group
    }

    @Override
    // Each group is identified with an id
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    // Creates a group view
    public View getGroupView(int listPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.topics_group, null);
        }

        // Sets a TextView with the title of the group
        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.topics_title);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(title);

        return convertView;
    }

    @Override
    // Specifies whether the child at the specified position is selectable or not
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
