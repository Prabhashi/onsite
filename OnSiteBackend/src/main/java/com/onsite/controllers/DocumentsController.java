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


}
