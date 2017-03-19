package com.example.tjr.onsite.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.tjr.onsite.R;
import com.example.tjr.onsite.app.MyVolley;
import com.example.tjr.onsite.model.Issue;
import com.example.tjr.onsite.ui.common.IssueHomeActivity;
import com.example.tjr.onsite.ui.common.IssuesActivity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by TJR on 2/23/2017.
 */

public class IssueAdapter extends RecyclerView.Adapter<IssueAdapter.IssueHolder> {
    private List<Issue> issues;
    private LayoutInflater inflater;
    private IssuesActivity context;
    private IssueAdapter adapter;
    public IssueAdapter(List<Issue> issues , IssuesActivity context ) {
        this.issues = issues;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.adapter = this;
    }

    @Override
    public IssueHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.issue_list_item, parent, false);
        return new IssueHolder(view);
    }

    @Override
    public void onBindViewHolder(IssueHolder holder, int position) {
        final Issue issue = issues.get(position);
        holder.name.setText(issue.name);
        holder.comments.setText("Comments :"+issue.comments);
        holder.severity.setText("Severity :" +issue.severity);
        String state = issue.resolved;
        if(state.equalsIgnoreCase("resolved")) {
            holder.state.setBackgroundColor(Color.GREEN);
            holder.state.setText("State: Resolved");
        }
        else if(state.equalsIgnoreCase("unresolved")) {
            holder.state.setBackgroundColor(Color.RED);
            holder.state.setText("State: Unresolved");
        }
        else {
            holder.state.setBackgroundColor(Color.RED);
            holder.state.setText("State: Unresolved");
        }
        //image download
        ImageLoader imageLoader = MyVolley.getImageLoader();
if(issue.imageUrl != null)
        imageLoader.get(issue.imageUrl,ImageLoader.getImageListener(holder.issueImage,0,0));

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent issueHomeIntent = new Intent(context, IssueHomeActivity.class);
                issueHomeIntent.putExtra("issueId", issue.issueId);
                context.startActivity(issueHomeIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return issues.size();
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    public void sortListBy(String attribute,boolean ascending) {
        Collections.sort(issues, new IssueComparator(attribute,ascending));
    }

    class IssueHolder extends RecyclerView.ViewHolder{
        TextView name,severity,comments,state;
        ImageView issueImage;
        View container;
        public IssueHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.txt_issues_name);
            severity = (TextView) itemView.findViewById(R.id.txt_issues_severity);
            comments = (TextView) itemView.findViewById(R.id.txt_issues_comments);
            state = (TextView) itemView.findViewById(R.id.txt_issues_state);
            issueImage = (ImageView) itemView.findViewById(R.id.im_issue_image);
            container = itemView;
        }
    }

    class IssueComparator implements Comparator<Issue>{
        String attribute ;
        int sortCoefficient;
        public IssueComparator(String attrib, boolean ascending) {
            attribute = attrib.toLowerCase();
            if(ascending)
                sortCoefficient = 1;
            else
                sortCoefficient = -1;
        }

        @Override
        public int compare(Issue issue, Issue t1) {
            switch(attribute){
                case "date":
                    //TODO : Change this statement once Date of issue has been set
                    return 0;
                    //return issue.dateReported.compareTo(t1.dateReported);
                case "type":
                    return issue.type.compareTo(t1.type)*sortCoefficient;
                case "severity":

                    return issue.severityInt.compareTo(t1.severityInt)*sortCoefficient;
                case "state":
                    return issue.resolved.compareTo(t1.resolved)*sortCoefficient;
            }
            return 0;
        }

    }
}
