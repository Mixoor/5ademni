package com.mixoor.khademni.controller;

import com.mixoor.khademni.Util.AppConstants;
import com.mixoor.khademni.config.CurrentUser;
import com.mixoor.khademni.config.UserPrincipal;
import com.mixoor.khademni.model.Ticket;
import com.mixoor.khademni.payload.request.TicketRequest;
import com.mixoor.khademni.payload.response.ApiResponse;
import com.mixoor.khademni.payload.response.PagedResponse;
import com.mixoor.khademni.payload.response.TicketResponse;
import com.mixoor.khademni.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping("/api")
public class TicketController {

    @Autowired
    TicketService ticketService;


    @PostMapping("/ticket")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createTicket(@CurrentUser UserPrincipal userPrincipal, @Valid TicketRequest request) {

        Ticket ticket = ticketService.createTicketResponse(userPrincipal, request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(ticket.getId()).toUri();
        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Ticket Created successfully "));
    }


    @GetMapping("/tickets")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public PagedResponse<TicketResponse> getAll(@CurrentUser UserPrincipal userPrincipal, @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return ticketService.getAll(page, size);
    }

    @GetMapping("/ticket/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public TicketResponse getById(@CurrentUser UserPrincipal user, @PathVariable(value = "id") Long id) {
        return ticketService.getId(id);
    }


}
