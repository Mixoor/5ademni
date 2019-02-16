package com.mixoor.khademni.service;


import com.mixoor.khademni.Util.ModelMapper;
import com.mixoor.khademni.config.UserPrincipal;
import com.mixoor.khademni.controller.PortfolioController;
import com.mixoor.khademni.exception.BadRequestException;
import com.mixoor.khademni.exception.UnauthorizedException;
import com.mixoor.khademni.model.Freelancer;
import com.mixoor.khademni.model.Portfolio;
import com.mixoor.khademni.payload.request.PortfolioRequest;
import com.mixoor.khademni.payload.response.PagedResponse;
import com.mixoor.khademni.payload.response.PortfolioResponse;
import com.mixoor.khademni.repository.FreelancerRepository;
import com.mixoor.khademni.repository.PortfolioRepository;
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
public class PortfolioService {

    @Autowired
    PortfolioRepository portfolioRepository;

    @Autowired
    FreelancerRepository freelancerRepository;

    @Autowired
    ProfilePictureService profilePictureService;


    public PortfolioResponse createPortfolio(UserPrincipal userPrincipal, PortfolioRequest request){
        Freelancer freelancer= freelancerRepository.findById(userPrincipal.getId())
                .orElseThrow(()-> new BadRequestException("User invalid "));

        String path = profilePictureService.storeProfilePicture(request.getPath());
        Portfolio portfolio= new Portfolio(freelancer,request.getDescription(),request.getTitle(),path);

        portfolio=portfolioRepository.save(portfolio);
        return ModelMapper.mapPortfolioToResponse(userPrincipal,portfolio);
    }

    public PagedResponse<PortfolioResponse>getAll(UserPrincipal userPrincipal,Long id, int page,int size){

        Freelancer freelancer= freelancerRepository.findById(id).orElseThrow(()-> new BadRequestException("User invalid"));
        Pageable pageable = PageRequest.of(page,size,Sort.Direction.DESC ,"createdAt");

        Page<Portfolio> portfolios = portfolioRepository.findByFreelancer(freelancer,pageable);

        if(portfolios.getTotalElements()==0)
            return  new PagedResponse<>(Collections.emptyList(),portfolios.getNumber(),portfolios.getSize(),portfolios.getTotalElements(),portfolios.getTotalPages(),portfolios.isLast());

        List<PortfolioResponse> portfolioResponses = portfolios.stream()
                .map(portfolio -> ModelMapper.mapPortfolioToResponse(userPrincipal,portfolio)).collect(Collectors.toList());


        return new PagedResponse<PortfolioResponse>(portfolioResponses
                ,portfolios.getNumber(),portfolios.getSize()
                ,portfolios.getTotalElements(),portfolios.getTotalPages()
                ,portfolios.isLast());
    }

    public void deletePortfolio(UserPrincipal userPrincipal, Long id ){
        Freelancer freelancer= freelancerRepository.findById(userPrincipal.getId())
                .orElseThrow(()-> new BadRequestException("User Invalid"));

        Portfolio portfolio= portfolioRepository.findById(id).orElseThrow(()->new BadRequestException("Portfolio Invalid"));

        if(freelancer.equals(portfolio.getFreelancer()))
            throw new UnauthorizedException("Unauthorized Request");

        profilePictureService.deleteFile(portfolio.getPath());
        portfolioRepository.delete(portfolio);

    }







}
