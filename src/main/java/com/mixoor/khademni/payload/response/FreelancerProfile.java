package com.mixoor.khademni.payload.response;

import com.mixoor.khademni.model.Portfolio;
import com.mixoor.khademni.model.Skill;

import java.util.Date;
import java.util.List;

public class FreelancerProfile extends UserProfile {

    private List<SkillResponse> skillResponses;
    private List<ReviewResponse> reviewResponses;

    private List<ExperienceResponse> experienceResponses;

    private  List<Portfolio> portfolioResponses;


    public FreelancerProfile(Long id, String name, String aboutMe, String country, String city, Date dob, String adresse, String role, String picture, Date replyTime, float rating, List<SkillResponse> skillResponses, List<ReviewResponse> reviewResponses, List<ExperienceResponse> experienceResponses, List<Portfolio> portfolioResponses) {
        super(id, name, aboutMe, country, city, dob, adresse, role, picture, replyTime, rating);
        this.skillResponses = skillResponses;
        this.reviewResponses = reviewResponses;
        this.experienceResponses = experienceResponses;
        this.portfolioResponses = portfolioResponses;
    }

    public List<SkillResponse> getSkillResponses() {
        return skillResponses;
    }

    public void setSkillResponses(List<SkillResponse> skillResponses) {
        this.skillResponses = skillResponses;
    }

    public List<ReviewResponse> getReviewResponses() {
        return reviewResponses;
    }

    public void setReviewResponses(List<ReviewResponse> reviewResponses) {
        this.reviewResponses = reviewResponses;
    }

    public List<ExperienceResponse> getExperienceResponses() {
        return experienceResponses;
    }

    public void setExperienceResponses(List<ExperienceResponse> experienceResponses) {
        this.experienceResponses = experienceResponses;
    }

    public List<Portfolio> getPortfolioResponses() {
        return portfolioResponses;
    }

    public void setPortfolioResponses(List<Portfolio> portfolioResponses) {
        this.portfolioResponses = portfolioResponses;
    }
}
