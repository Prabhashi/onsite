package com.onsite.controllers;

import com.onsite.model.Project;
import com.onsite.model.ResponseState;
import com.onsite.model.Task;
import com.onsite.model.User;
import com.onsite.repository.ProjectRepository;
import com.onsite.repository.TaskRepository;
import com.onsite.repository.UserRepository;
import com.onsite.tools.StringSplitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by TJR on 3/9/2017.
 */
@RestController
public class ScheduleController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    ProjectRepository projectRepository;


    //TODO : Get Critical path




    /**
     *  Creates a new task
     *
     * @return
     */
    @RequestMapping(value = "/task/create",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseState createTask(
            @RequestParam(value = "assigneeId", required = false) Integer assigneeId,
            @RequestParam(value = "projectId") Integer projectId,
            @RequestParam(value = "predecessorIds", required = false) String predecessorIds,
            @RequestParam(value = "taskName") String taskName,
            @RequestParam(value = "startDate")Date startDate,
            @RequestParam(value = "estimatedEndDate")Date estimatedEndDate,
            @RequestParam(value = "cost")Double cost,
            @RequestParam(value ="description") String description
    )
    {
        Task t = new Task();

        User assignee = userRepository.findById(assigneeId);
        Project project = projectRepository.findOne(projectId);
        t.setProject(project);
        t.setAssignee(assignee);

        t.setTaskName(taskName);
        t.setStartDate(startDate);
        t.setEstimatedEndDate(estimatedEndDate);
        t.setCost(cost);
        t.setDescription(description);

        taskRepository.save(t);
        return  new ResponseState("success");
    }

    @RequestMapping(value = "/task/edit",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseState editTask(
            @RequestParam(value = "taskId") Integer taskId,
            @RequestParam(value = "assigneeId", required = false) Integer assigneeId,
            @RequestParam(value = "predecessorIds", required = false) String predecessorIds,
            @RequestParam(value = "taskName", required = false) String taskName,
            @RequestParam(value = "startDate", required = false)Date startDate,
            @RequestParam(value = "estimatedEndDate", required = false)Date estimatedEndDate,
            @RequestParam(value = "cost", required = false)Double cost,
            @RequestParam(value = "completed", required = false)Boolean completed,
            @RequestParam(value = "description", required = false)String description

    )
    {
        Task t = taskRepository.findOne(taskId);

        User assignee = userRepository.findById(assigneeId);
        t.setAssignee(assignee);

        t.setTaskName(taskName);
        t.setStartDate(startDate);
        t.setEstimatedEndDate(estimatedEndDate);
        t.setCost(cost);
        t.setCompleted(completed);
        t.setDescription(description);
        taskRepository.save(t);
        return new ResponseState("success");
    }

    /**
     *  Get All Tasks for particular project
     *
     */
    @RequestMapping(value = "/task/all/{projectId}/{userId}",
            method = RequestMethod.GET,
            produces = "application/json")
    public List<Task> testRequest(
            @PathVariable Integer projectId,
            @PathVariable Integer userId
    )
    {
        User user = userRepository.findOne(userId);

        switch (user.getRole().toLowerCase()){
            case "client":
            case "manager":
                //all tasks are visible to manager and client
                return taskRepository.findByProjectProjectId(projectId);
            case "contractor":
                //only the assigned tasks are visible for the contractor
                return  taskRepository.findByAssignee(user);
        }
        return null;
    }


    @RequestMapping(value = "/task/get/{taskId}",
            method = RequestMethod.GET,
            produces = "application/json")
    public Task getTask(
            @PathVariable Integer taskId
    )
    {
       return taskRepository.findOne(taskId);

    }
//mark as completed
    @RequestMapping(
            value = "/task/markascompleted/{taskId}",
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseState markAsCompleted(
            @PathVariable Integer taskId
    )
    {
       Task task = taskRepository.findOne(taskId);
       task.setCompleted(true);
       taskRepository.save(task);
        return new ResponseState("success");

    }

    @RequestMapping(value = "/task/accept",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseState acceptTask(
            @RequestParam(value = "taskId") Integer taskId,
            @RequestParam(value = "response") String response,
            @RequestParam(value = "assigneeUsername") String assigneeUsername
    )
    {
       Task task = taskRepository.findOne(taskId);
       User assignee = userRepository.findByUsername(assigneeUsername);

       switch (response){
           case "accepted":
               task.setAssignee(assignee);
               task.setAssigneeAccepted(true);
               taskRepository.save(task);
               return new ResponseState("success");
           case "rejected":
               task.setAssignee(null);
               task.setAssigneeAccepted(null);
               taskRepository.save(task);
               return new ResponseState("success");

       }
       return  new ResponseState("error");

    }

//deletion
@RequestMapping(
        value = "/task/delete/{taskId}",
        method = RequestMethod.DELETE,
        produces = "application/json")
public ResponseState deleteTask(
        @PathVariable Integer taskId
)
{
    taskRepository.deleteByTaskId(taskId);
    return new ResponseState("success");

}



}
