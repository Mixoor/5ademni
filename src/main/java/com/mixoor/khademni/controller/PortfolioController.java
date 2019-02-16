package com.mixoor.khademni.controller;

import com.mixoor.khademni.Util.AppConstants;
import com.mixoor.khademni.config.CurrentUser;
import com.mixoor.khademni.config.UserPrincipal;
import com.mixoor.khademni.payload.request.PortfolioRequest;
import com.mixoor.khademni.payload.response.PagedResponse;
import com.mixoor.khademni.payload.response.PortfolioResponse;
import com.mixoor.khademni.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    @Autowired
    PortfolioService portfolioService;



    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public PagedResponse<PortfolioResponse>getAll(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long id,
                                                  @RequestParam(value = "page",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER)int page,
                                                  @RequestParam(value="size",defaultValue = AppConstants.DEFAULT_PAGE_SIZE)int size){
        return portfolioService.getAll(userPrincipal,id,page,size);
    }

    @PostMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createPortfolio(@CurrentUser UserPrincipal userPrincipal, PortfolioRequest request){
        return ResponseEntity.ok(portfolioService.createPortfolio( userPrincipal,request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> delete(@CurrentUser UserPrincipal userPrincipal,@PathVariable Long id){
        portfolioService.deletePortfolio(userPrincipal,id);
        return ResponseEntity.ok().body("Portfolio deleted Successful ");
    }





}
