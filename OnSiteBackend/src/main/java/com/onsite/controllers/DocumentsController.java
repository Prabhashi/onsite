package com.onsite.controllers;

import com.onsite.model.Issue;
import com.onsite.model.Project;
import com.onsite.repository.IssueRepository;
import com.onsite.repository.ProjectRepository;
import com.onsite.tools.DocumentGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by TJR on 3/9/2017.
 */
@RestController
public class DocumentsController {
    @Autowired
    IssueRepository issueRepository;

    @Autowired
    ProjectRepository projectRepository;


    @RequestMapping(value = "/documents/get/{projectId}", method = RequestMethod.GET, produces = "application/pdf")
    public void getFile(
            @PathVariable("projectId") Integer projectId,
            HttpServletResponse response) {
        try {
            // get your file as InputStream
            List<Issue> issues = issueRepository.findByProjectProjectId(projectId);
            Project p = projectRepository.findOne(projectId);
            String fileName = new DocumentGenerator().generateIssueReport(projectId,issues,p);
            InputStream is = new FileInputStream(fileName);
            // copy it to response's OutputStream
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            //log.info("Error writing file to output stream. Filename was '{}'", fileName, ex);
            throw new RuntimeException("IOError writing file to output stream");
        }

    }
}
