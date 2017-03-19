package com.example.tjr.onsite.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.example.tjr.onsite.R;
import com.example.tjr.onsite.app.MyVolley;
import com.example.tjr.onsite.model.DataSource;
import com.example.tjr.onsite.model.Project;

import org.w3c.dom.Text;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by TJR on 2/3/2017.
 */

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectHolder>{

    private List<Project> projects;
    private LayoutInflater inflater;
    private Context context;
    private ProjectAdapter projectAdapter;
    public ProjectAdapter(List<Project> projects, Context context) {
        this.projects = projects;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        projectAdapter = this;
    }

    @Override
    public ProjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.project_list_item, parent, false);
        return new ProjectHolder(view);
    }

    @Override
    public void onBindViewHolder(ProjectHolder holder, int position) {
        Project project = projects.get(position);
        holder.name.setText(project.name);
        holder.issues.setTextColor(Color.RED);
        holder.issues.setText("Issues " + project.issues);
        holder.location.setText(project.location);

        //Uses static methods of MyVolley
        ImageLoader imageLoader = MyVolley.getImageLoader();
        imageLoader.get(project.imageUrl, ImageLoader.getImageListener(holder.icon,0,0));

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //load next activity
                Log.d(TAG, "onClick: ggg");

            }
        });
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    class ProjectHolder extends RecyclerView.ViewHolder{

        private TextView name,issues,location;
        private ImageView icon;
        private View container;

        public ProjectHolder(View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.txt_project_name);
            issues = (TextView)itemView.findViewById(R.id.txt_project_issues);
            location = (TextView)itemView.findViewById(R.id.txt_project_location);
            icon = (ImageView) itemView.findViewById(R.id.im_project_image);
            container = itemView.findViewById(R.id.project_list_root);


        }
    }
}
