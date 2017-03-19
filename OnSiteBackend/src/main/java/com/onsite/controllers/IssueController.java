package com.onsite.controllers;

import com.onsite.globals.Const;
import com.onsite.model.*;
import com.onsite.repository.*;
import com.onsite.tools.FileHandler;
import org.h2.table.Plan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by TJR on 1/26/2017.
 */
@RestController
public class IssueController {
    @Autowired
    IssueRepository issueRepository;

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PlansRepository plansRepository;
    /**
     * Creates new project
     *
     * @param reporterId
     * @param projectId
     * @param issueTitle
     * @param description
     * @param issueType
     * @param severity
     * @param tags
     * @param image
     * @return
     */
    @RequestMapping(
            value = "issues/create",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseState createIssue(
            @RequestParam(value = "reporterId") Integer reporterId,
            @RequestParam(value = "assigneeUsername", required = false) String assigneeUsername,
            @RequestParam(value = "projectId") Integer projectId,
            @RequestParam(value = "issueTitle") String issueTitle,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "issueType") String issueType,
            @RequestParam(value = "severity") String severity,
            @RequestParam(value = "tags", required = false) String tags,
            @RequestParam(value = "image", required = false) MultipartFile image

    ) {

        User reporter = userRepository.findById(reporterId);
        User assignee = userRepository.findByUsername(assigneeUsername);
        Project project = projectRepository.findOne(projectId);

        Issue issue = new Issue();
        issue.setImageUrls(new ArrayList<>());
        issue.setReporter(reporter);
        //assign user and wait
        issue.setAssignee(assignee);
        issue.setAssigneeAccepted(false);
        issue.setProject(project);

        issue.setIssueTitle(issueTitle);
        issue.setDescription(description);
        issue.setIssueType(issueType.toLowerCase());
        issue.setSeverity(severity.toLowerCase());
        issue.setReportedDate(new Date());

        //tags must be comma separated
        if (tags != null) {
            String[] tagArray = tags.split("[,]");
            issue.setTags(Arrays.asList(tagArray));
        }
        issueRepository.saveAndFlush(issue);

        //TODO Change this to accept in byte form
        addIssueImage(issue.getIssueId(), image);


        issueRepository.save(issue);
        return new ResponseState("success");
    }

    /**
     * Returns all the issues for the given project
     *
     * @return
     */
    @RequestMapping(
            value = "issues/get/all/{projectId}",
            method = RequestMethod.GET,
            produces = "application/json")
    public List<Issue> getAllIssues(@PathVariable int projectId) {
        return issueRepository.findByProjectProjectId(projectId);
    }



    @RequestMapping(
            value = "issues/get/all/{projectId}/{userId}",
            method = RequestMethod.GET,
            produces = "application/json")
    public List<Issue> getAllIssuesByRole(@PathVariable int projectId,
                                          @PathVariable int userId) {
        User user = userRepository.findOne(userId);
        if(user == null )return null;

        String role = user.getRole();
        switch (role.toLowerCase()){
            case "client":
                return issueRepository.findByReporter(user);
            case "manager":
                return issueRepository.findByProjectProjectId(projectId);
            case "contractor":
                return  issueRepository.findByAssignee(user);

        }
        return new ArrayList<Issue>();
    }




    @RequestMapping(
            value = "issues/get/{issueId}",
            method = RequestMethod.GET,
            produces = "application/json")
    public Issue getIssueById(@PathVariable int issueId) {
        return issueRepository.findOne(issueId);
    }


    @RequestMapping(
            value = "issues/comment/{issueId}",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseState commentOnIssue(
            @PathVariable int issueId,
            @RequestParam(value = "userId") Integer commentorId,
            @RequestParam(value = "commentBody") String body) {
        Issue issue = issueRepository.findOne(issueId);
        User u = userRepository.findOne(commentorId);
        Comment c = new Comment();
        c.setCommentDate(new Date());
        c.setCommentBody(body);
        c.setCommentor(u);

        commentRepository.save(c);
        issue.getComments().add(c);


        issueRepository.save(issue);
        return new ResponseState("success");
    }

    @RequestMapping(
            value = "issues/add_tag/{issueId}",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseState addTag(
            @PathVariable int issueId,
            @RequestParam(value = "tag") String tag) {
        Issue issue = issueRepository.findOne(issueId);
        issue.getTags().add(tag);
        issueRepository.save(issue);
        return new ResponseState("success");
    }


    @RequestMapping(
            value = "issues/add_image/{issueId}",
            method = RequestMethod.POST,
            produces = "application/json"
    )
    public ResponseState addIssueImage(@PathVariable int issueId,
                                       @RequestParam(value = "image") MultipartFile image) {
        String fileName = issueId + "_" + System.currentTimeMillis() + ".jpg";
        int response = FileHandler.saveFile("IssueImages", fileName, image);
        if (response == FileHandler.SUCCESS) {
            Issue issue = issueRepository.findOne(issueId);
            issue.getImageUrls().add(Const.HOST_URL + "/images/get/issue/" + fileName);
            issueRepository.save(issue);
            return new ResponseState("success");
        } else
            return new ResponseState("failed");

    }

    @RequestMapping(
            value = "issues/edit/{issueId}",
            method = RequestMethod.POST,
            produces = "application/json"
    )
    public ResponseState editIssue(
            @PathVariable int issueId,
            @RequestParam(value = "assigneeUsername", required = false) String assigneeUsername,
            @RequestParam(value = "issueTitle", required = false) String issueTitle,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "issueType", required = false) String issueType,
            @RequestParam(value = "severity", required = false) String severity,
            @RequestParam(value = "tags", required = false) String tags,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "planId", required = false) Integer planId,
            @RequestParam(value = "state" ,required = false) String state,
            @RequestParam(value = "locX", required = false) Float locX,
            @RequestParam(value = "locY", required = false) Float locY


    ) {


        Issue issue = issueRepository.findOne(issueId);
        User user = userRepository.findByUsername(assigneeUsername);
        if(issue.getImageUrls() == null)
        issue.setImageUrls(new ArrayList<>());

        if(assigneeUsername != null) {
            issue.setAssignee(user);
            issue.setAssigneeAccepted(false);
        }

        if (issueTitle != null)
            issue.setIssueTitle(issueTitle);
        if (description != null)
            issue.setDescription(description);
        if (issueType != null)
            issue.setIssueType(issueType);
        if (severity != null)
            issue.setSeverity(severity);
        if (planId != null)
            issue.setPlan(plansRepository.findOne(planId));
        if(locX != null && locY != null)
        {
            issue.setLocationX(locX);
            issue.setLocationY(locY);
        }

        if(state != null){
            issue.setStatus(state);
        }
        //tags must be comma separated
        if (tags != null) {
            String[] tagArray = tags.split("[,]");
            issue.setTags(Arrays.asList(tagArray));
        }
        issueRepository.save(issue);
        return new ResponseState("success");

    }

    @RequestMapping(value = "/issue/accept",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseState acceptTask(
            @RequestParam(value = "issue") Integer taskId,
            @RequestParam(value = "response") String response,
            @RequestParam(value = "assigneeUsername") Integer assigneeId
    )
    {
        Issue issue = issueRepository.findOne(taskId);
        User assignee = userRepository.findOne(assigneeId);

        switch (response.toLowerCase()){
            case "accepted":
                issue.setAssignee(assignee);
                issue.setAssigneeAccepted(true);
                issueRepository.save(issue);
                return new ResponseState("success");
            case "rejected":
                issue.setAssigneeAccepted(null);
                issue.setAssignee(null);
                issueRepository.save(issue);
                return new ResponseState("success");

        }
        return  new ResponseState("error");

    }
}
