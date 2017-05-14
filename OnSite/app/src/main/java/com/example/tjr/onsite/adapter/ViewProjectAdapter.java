package com.example.tjr.onsite.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tjr.onsite.R;
import com.example.tjr.onsite.app.Globals;
import com.example.tjr.onsite.controllers.ProjectController;
import com.example.tjr.onsite.model.json.Project;
import com.example.tjr.onsite.ui.common.DashBoardActivity;

import java.util.List;

/**
 * Created by Raviyaa on 2017-03-20.
 */

public class ViewProjectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<Project> projects;
    private LayoutInflater inflater;
    private Context context;
    private ViewProjectAdapter viewProjectAdapter;
    private ProjectController projectController;

    public ViewProjectAdapter(List<Project> projects, Context context, ProjectController projectController) {
        this.projects = projects;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.projectController = projectController;
        viewProjectAdapter = this;
    }

    @Override
    public int getItemViewType(int position) {
        //TODO : Change here manager accepted vs client accepted


        if(Globals.role.equals("manager")){
            if(projects.get(position).getManagerAccepted()){
                return 1; // accepted project
            }else{
                return 2; // yet to be accepted
            }
        }else if(Globals.role.equals("client")){
            if(projects.get(position).getClientAccepted()){
                return 1; // accepted project
            }else{
                return 2; // yet to be accepted
            }
        }

        if (projects.get(position).getManagerAccepted()) return 1;// list project

        return 2;// accept project
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        if (viewType == 1) {
            view = inflater.inflate(R.layout.list_project, parent, false);
            return new ViewProjectHolder(view);
        }
        else{
            view = inflater.inflate(R.layout.accept_project, parent, false);
            return new AcceptProjectHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        holder.getItemViewType();
        if (holder.getItemViewType()==1){
            ViewProjectHolder vpHolder=(ViewProjectHolder)holder;
            final Project project = projects.get(position);
            vpHolder.projectName.setText(project.getProjectName());
            vpHolder.companyName.setText(project.getCompanyName());
            vpHolder.location.setText(project.getLocation());
            vpHolder.startDate.setText(project.getStartDate());
            vpHolder.estimatedEndDate.setText(project.getEstimatedCompletionDate());

            if(Globals.role.equals("manager")) {
                if(project.getClient() != null)
                    vpHolder.user.setText(project.getClient().getFullName());
                else
                    vpHolder.user.setText("[Client Unassigned]");
            }else {
                if(project.getManager() != null)
                    vpHolder.user.setText(project.getManager().getFullName());
                else
                    vpHolder.user.setText("[Manager Unassigned]");
            }
            vpHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Globals.projectId = project.getProjectId();
                    Globals.project = project;
                    Intent i = new Intent(context, DashBoardActivity.class);
                    context.startActivity(i);
                }
            });
        }else {
            AcceptProjectHolder acHolder=(AcceptProjectHolder)holder;
            final Project project = projects.get(position);
            acHolder.projectName.setText(project.getProjectName());
            acHolder.companyName.setText(project.getCompanyName());

            if(Globals.role.equals("client"))
                acHolder.assigner.setText(project.getManager().getFullName());
            else
                acHolder.assigner.setText(project.getClient().getFullName());

            acHolder.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Globals.projectId = project.getProjectId();
                    projectController.acceptProject(projects.get(position).getProjectId(),true);
                }
            });
            acHolder.reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    projectController.acceptProject(projects.get(position).getProjectId(),false);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    class ViewProjectHolder extends RecyclerView.ViewHolder {
        private TextView projectName, companyName, location, startDate, estimatedEndDate, user;

        public ViewProjectHolder(View itemView) {
            super(itemView);

            projectName = (TextView) itemView.findViewById(R.id.txt_list_proName);
            companyName = (TextView) itemView.findViewById(R.id.txt_list_comName);
            location = (TextView) itemView.findViewById(R.id.txt_list_loc);
            startDate = (TextView) itemView.findViewById(R.id.txt_list_startDate);
            estimatedEndDate = (TextView) itemView.findViewById(R.id.txt_list_endDate);
            user = (TextView) itemView.findViewById(R.id.txt_list_project_user);
        }
    }
    class AcceptProjectHolder extends RecyclerView.ViewHolder {
        private TextView projectName, companyName, assigner;
        private LinearLayout accept,reject;
        private View itemView;

        public AcceptProjectHolder(View itemView) {
            super(itemView);

            projectName = (TextView) itemView.findViewById(R.id.proName);
            companyName = (TextView) itemView.findViewById(R.id.comName);
            assigner = (TextView) itemView.findViewById(R.id.assigneeId);
            accept = (LinearLayout)itemView.findViewById(R.id.layout_listprojectapproval_yes);
            reject = (LinearLayout)itemView.findViewById(R.id.layout_listprojectapproval_no);
            this.itemView=itemView;
        }
    }
}
