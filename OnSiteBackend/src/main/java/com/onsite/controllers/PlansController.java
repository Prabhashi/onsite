package com.onsite.controllers;

import com.onsite.globals.Const;
import com.onsite.model.DesignPlan;
import com.onsite.model.ResponseState;
import com.onsite.model.User;
import com.onsite.repository.PlansRepository;
import com.onsite.repository.ProjectRepository;
import com.onsite.tools.FileHandler;
import org.h2.table.Plan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.security.krb5.internal.crypto.Des;

/**
 * Created by TJR on 3/9/2017.
 */
@RestController
public class PlansController  {

    @Autowired
    ProjectRepository projectRepository ;
    @Autowired
    PlansRepository plansRepository;


    /**
     *  Uploads a file
     * @param planName
     * @param projectId
     * @param image
     * @return
     */
    @RequestMapping(value = "/plan/add",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseState addPlan(
            @RequestParam(value = "planName") String planName,
            @RequestParam(value = "projectId") Integer projectId,
            @RequestParam(value = "image")MultipartFile image
            )
    {
        DesignPlan plan = new DesignPlan();
        plan.setProject(projectRepository.findOne(projectId));

        String fileName = "plan_"+projectId+"_"+System.currentTimeMillis()+".jpg";
        FileHandler.saveFile("PlanImages", fileName, image );
        String imageUrl = Const.HOST_URL+"images/get/plan/"+fileName;
        plan.setPlanImageUrl(imageUrl);

        plan.setPlanName(planName);

        plansRepository.save(plan);

        return new ResponseState("success");

    }

    @RequestMapping(
            value = "plan/get/{planId}",
            method = RequestMethod.GET,
            produces = "application/json")
    public DesignPlan getPlan(@PathVariable(value = "planId") Integer planId){
        return  plansRepository.findOne(planId);
    }


}
