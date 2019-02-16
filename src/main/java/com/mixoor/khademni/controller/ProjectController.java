package com.mixoor.khademni.controller;


import com.mixoor.khademni.Util.AppConstants;
import com.mixoor.khademni.config.CurrentUser;
import com.mixoor.khademni.config.UserPrincipal;
import com.mixoor.khademni.payload.request.ProjectRequest;
import com.mixoor.khademni.payload.response.PagedResponse;
import com.mixoor.khademni.payload.response.ProjectResponse;
import com.mixoor.khademni.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;




    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public PagedResponse<ProjectResponse> getProjects(@CurrentUser UserPrincipal userPrincipal,
                                                      @RequestParam(value = "page",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER)int page,
                                                      @RequestParam(value = "size",defaultValue = AppConstants.DEFAULT_PAGE_SIZE)int size){
        return projectService.getProjects(userPrincipal,page,size);

    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ProjectResponse getProject(@CurrentUser UserPrincipal userPrincipal,@PathVariable Long id){
        return projectService.getProjectById(userPrincipal,id);
    }

    @PostMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createProject(@CurrentUser UserPrincipal userPrincipal, ProjectRequest request){
        ProjectResponse projectResponse=projectService.createProject(userPrincipal,request);
        return  ResponseEntity.ok().body(projectResponse);
    }

    @PostMapping("/vote")
    @PreAuthorize("isAuthenticated()")
    public long castVote(@CurrentUser UserPrincipal userPrincipal,Long id){
        return  projectService.castVote(userPrincipal,id);
    }

    @DeleteMapping("/vote")
    @PreAuthorize("isAuthenticated()")
    public long DeleteVote(@CurrentUser UserPrincipal userPrincipal,Long id){
        return  projectService.deleteVote(userPrincipal,id);
    }











}
