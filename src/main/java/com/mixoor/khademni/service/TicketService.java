package com.mixoor.khademni.service;

import com.mixoor.khademni.Util.AppConstants;
import com.mixoor.khademni.Util.ModelMapper;
import com.mixoor.khademni.config.UserPrincipal;
import com.mixoor.khademni.exception.BadRequestException;
import com.mixoor.khademni.model.Ticket;
import com.mixoor.khademni.model.User;
import com.mixoor.khademni.payload.request.TicketRequest;
import com.mixoor.khademni.payload.response.PagedResponse;
import com.mixoor.khademni.payload.response.TicketResponse;
import com.mixoor.khademni.repository.TicketRepository;
import com.mixoor.khademni.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {


    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    UserRepository userRepository;

    public Ticket createTicketResponse(UserPrincipal user, TicketRequest ticketRequest) {
        User user1 = userRepository.findById(user.getId())
                .orElseThrow(() ->
                        new BadRequestException("User id invalide "));
        Ticket ticket = ModelMapper .mapRequestToTicket(user1, ticketRequest);
        ticketRepository.save(ticket);
        return ticket;
    }


    public PagedResponse<TicketResponse> getAll(int page, int size) {
        validatePageAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createAt");
        Page<Ticket> tickets = ticketRepository.findAll(pageable);

        if (tickets.getTotalElements() == 0)
            return new PagedResponse<>(Collections.emptyList(), tickets.getNumber()
                    , tickets.getSize(), tickets.getTotalElements(), tickets.getTotalPages()
                    , tickets.isLast());

        List<TicketResponse> ticketResponses = tickets.stream().map((t) -> ModelMapper .mapTicketToResponse(t)
        ).collect(Collectors.toList());

        return new PagedResponse<>(ticketResponses, tickets.getNumber(), tickets.getSize(),
                tickets.getTotalElements(), tickets.getTotalPages(), tickets.isLast());
    }

    public TicketResponse getId(Long id) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new BadRequestException("Ticket doesn't exists"));
        return ModelMapper .mapTicketToResponse(ticket);
    }


    private void validatePageAndSize(int page, int size) {

        if (page < 0)
            new BadRequestException("Page must be postive numbre ");

        if (size > AppConstants.MAX_PAGE_SIZE)
            new BadRequestException("Size of page must be less than " + AppConstants.MAX_PAGE_SIZE);
    }

}
