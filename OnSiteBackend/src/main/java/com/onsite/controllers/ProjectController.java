package com.onsite.controllers;

import com.onsite.model.*;
import com.onsite.repository.ProjectRepository;
import com.onsite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by TJR on 1/26/2017.
 */

@RestController
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;


    /**
     *   Gets details about the particular project by id
     * @param id
     * @return
     */
    @RequestMapping( value = "/project/get/{id}", method = RequestMethod.GET, produces ="application/json")
    public Project getProjectById(@PathVariable Integer id){
        //
        Project p = projectRepository.findOne(id);
        return p;
    }


    @RequestMapping(value = "/project/involved/{userId}")
    public List<Project> getProjectsByUserId(@PathVariable Integer userId){
        //TODO: rewrite concerning user roles
        User user = userRepository.findOne(userId);

        if("manager".equals(user.getRole())){
            //if the request is from a manager
            return  projectRepository.findByManager(user);
        }

        if("client".equals(user.getRole())){
            //if the request is from a client
            return  projectRepository.findByClient(user);
        }

        return new ArrayList<Project>();
    }


    @RequestMapping(value = "/project/delete/{projectId}")
    public ResponseState deleteProject(@PathVariable Integer pid){
        Project p = projectRepository.findOne(pid );

        if(p != null)
            projectRepository.delete(p.getProjectId());
        return  new ResponseState("success");
    }

    /**
     *  Creates a new project
     *
     * @return
     */
    @RequestMapping(value = "/project/create",
    method = RequestMethod.POST,
    produces = "application/json")
    public ResponseState createProject(
            @RequestParam(value = "projectName") String projectName,
            @RequestParam(value = "companyName") String companyName,
            @RequestParam(value = "location") String location,
            @RequestParam(value = "startDate")Date startDate,
            @RequestParam(value = "estimatedCompletionDate", required = false)Date estimatedCompletionDate,
            @RequestParam(value = "creatorId")Integer creatorId,
            @RequestParam(value = "managerId", required = false)Integer managerId,
            @RequestParam(value = "otherUser", required = false)String otherUsername
            )
    {

        Project p = new Project();
        p.setProjectName(projectName);
        p.setLocation(location);
        p.setCompanyName(companyName);
        p.setStartDate(startDate);
        p.setEstimatedCompletionDate(estimatedCompletionDate);

        //see if the creator is a manager
        User creator = userRepository.findOne(creatorId);

        if("manager".equals(creator.getRole())){
            // if the creator is a manager
            p.setManager(creator);
            p.setManagerAccepted(true);

            User client = userRepository.findByUsername(otherUsername);
            if(client != null) {
                p.setClient(client);
                p.setClientAccepted(false);
            }
        }else if("client".equals(creator.getRole())){
            p.setClient(creator);
            p.setClientAccepted(true);


            User manager = userRepository.findByUsername(otherUsername);
            if(manager != null) {
                p.setManager(manager);
                p.setManagerAccepted(true);
            }
        }
        projectRepository.save(p);
         return  new ResponseState("success");

    }

    /**
     *
     * @param projectId
     * @param projectName
     * @param companyName
     * @param location
     * @param startDate
     * @param estimatedCompletionDate
     * @param managerId
     * @return
     */

    //TODO : Implement Project Settings
    @RequestMapping(value = "/project/edit",
    method = RequestMethod.POST,
    produces = "application/json")
    public ResponseState editProject(
            @RequestParam(value = "projectId") Integer projectId,
            @RequestParam(value = "projectName", required = false) String projectName,
            @RequestParam(value = "companyName", required = false) String companyName,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "startDate", required = false)Date startDate,
            @RequestParam(value = "estimatedCompletionDate", required = false)Date estimatedCompletionDate,
            @RequestParam(value = "managerId", required = false)Integer managerId

            )
    {
       /* Project p = projectRepository.findOne(projectId);
        if(projectName != null)
            p.setProjectName(projectName);
        if(location != null)
            p.setLocation(location);
        if(companyName != null)
            p.setCompanyName(companyName);
        if(startDate != null)
            p.setStartDate(startDate);
        if(estimatedCompletionDate != null)
            p.setEstimatedCompletionDate(estimatedCompletionDate);
        if(managerId != null){
            User manager = userRepository.findOne(managerId);
            UserProject up = new UserProject();
            up.setProject(p);
            up.setRole("manager");
            up.setUser(manager);

            manager.getInvolvedProjects().add(up);
            userRepository.save(manager);
        }


        projectRepository.save(p);*/
         return  new ResponseState("success");
    }


    @RequestMapping(value = "/project/accept",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseState acceptTask(
            @RequestParam(value = "issue") Integer taskId,
            @RequestParam(value = "response") String response,
            @RequestParam(value = "assigneeUsername") Integer userId
    )
    {
        Project project = projectRepository.findOne(taskId);
        User user = userRepository.findOne(userId);


        //handle manager, client acceptance
        switch (user.getRole()){
            case "client":
                if("accepted".equals(response)){
                    project.setClientAccepted(true);
                    project.setClient(user);
                }else if("rejected".equals(response)){
                    project.setClientAccepted(null);
                    project.setClient(null);
                }
                projectRepository.save(project);
                return  new ResponseState("success");
            case "manager":
                if(response.equals("accepted")){
                    project.setManagerAccepted(true);
                    project.setManager(user);
                }else if("rejected".equals(response)){
                    project.setManagerAccepted(null);
                    project.setManager(null);
                }
                projectRepository.save(project);
                return  new ResponseState("success");
        }

        return  new ResponseState("error");

    }


}
