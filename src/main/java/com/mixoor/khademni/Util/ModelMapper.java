package com.mixoor.khademni.Util;

import com.mixoor.khademni.model.*;
import com.mixoor.khademni.payload.request.*;
import com.mixoor.khademni.payload.response.*;
import com.mixoor.khademni.repository.DocumentRepository;
import com.mixoor.khademni.repository.SkillRepository;
import com.mixoor.khademni.service.DocumentStorageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class ModelMapper {


    @Autowired
    private static  DocumentStorageService documentStorageService;

    @Autowired
    private static  SkillRepository skillRepository;

    @Autowired
    private static  DocumentRepository documentRepository;


    public static  Job mapJobRequestToJob(JobRequest jobRequest, Client client) {

        Job job = new Job();

        // Setting attributes
        job.setTitle(jobRequest.getTitle());
        job.setContent(jobRequest.getDescription());
        job.setDelai(jobRequest.getDelai());
        job.setBudget(jobRequest.getBudget());
        job.setClient(client);


        if (!jobRequest.getFiles().isEmpty())
            jobRequest.getFiles().forEach(f -> {
                UploadFileResponse file = documentStorageService.storeFile(f);
                Document d = new Document();
                d.setFileName(file.getFileName());
                d.setFileType(file.getFileType());
                d.setUser(client);
                job.addFile(d);
                d.setSize(file.getSize());
            });


        if (!jobRequest.getSkills().isEmpty())
            jobRequest.getSkills().forEach(s -> {
                Skill skill = skillRepository.findByName(s)
                        .orElse(skillRepository.save(new Skill(s)));
                job.addSkill(skill);
            });


        return job;


    }

    public static  JobResponse mapJobtoJobResponse(Job job, Client client) {

        UserSummary userSummary = mapClientUserSummary(client);

        List<SkillResponse> skillResponse = job.getSkills().stream()
                .map(skill -> new SkillResponse(skill.getName()))
                .collect(Collectors.toList());

        List<UploadFileResponse> files = documentRepository.findByJob(job).stream()
                .map(document -> new UploadFileResponse(document.getId()
                        , document.getFileName(),
                        document.getFileType(),
                        documentStorageService.DownloadLink(document.getFileName() + document.getFileType()),
                        document.getSize()))
                .collect(Collectors.toList());

        JobResponse jobResponse = new JobResponse(job.getId(), job.getTitle()
                , job.getContent(), job.getBudget(), job.getDelai()
                , job.isAvailble(), userSummary, job.getCreatedAt(), skillResponse, files);

        return jobResponse;
    }


    public static  UserSummary mapClientUserSummary(Client client) {

        UserSummary userSummary = new UserSummary(client.getId(), client.getName()
                , client.getPath(), client.getScore(), "Client");

        return userSummary;

    }

    public static  UserSummary mapFreelancerToUserSummary(Freelancer freelancer) {

        UserSummary userSummary = new UserSummary(freelancer.getId(), freelancer.getName()
                , freelancer.getPath(), freelancer.getRating(), "Freelancer");

        return userSummary;

    }

    public static  UserSummary mapUserToUserSummary(User user) {

        if (user instanceof Client)
            return mapClientUserSummary((Client) user);

        if (user instanceof Freelancer)
            return mapFreelancerToUserSummary((Freelancer) user);

        return new UserSummary(user.getId(), user.getName(), user.getPath(), 0, user.getRole().toString());


    }


    public static  ContractResponse mapJobToContract(Job job) {

        UserSummary freelancerUserSummary = mapFreelancerToUserSummary(job.getFreelancer());
        UserSummary clientUserSummary = mapClientUserSummary(job.getClient());

        return new ContractResponse(clientUserSummary, freelancerUserSummary
                , job.getTitle(), job.getContent()
                , job.getBudget(), job.getDelai());

    }


    public static  Application mapRequestToApplication(ApplicationRequest applicationRequest, Freelancer freelancer, Job job) {

        Application application = new Application(new ApplicationId(applicationRequest.getFreelancerId(), applicationRequest.getJobId())
                , freelancer, job, applicationRequest.getContent(), applicationRequest.getBudget(), applicationRequest.getTime());

        return application;
    }

    public static  ApplicationResponse mapApplicationToResponse(User freelancer, Application application) {

        ApplicationResponse applicationResponse =
                new ApplicationResponse(application.getJob().getId(), application.getFreelancer().getId(), mapFreelancerToUserSummary((Freelancer) freelancer), application.getContent()
                        , application.getBudget(), application.getTime());
        return applicationResponse;
    }


    public static  Comment mapRequestToComment(CommentRequest commentRequest, Post post, User user) {

        Comment comment = new Comment(user, post, commentRequest.getContent());
        return comment;
    }

    public static  CommentResponse mapCommentToResponse(Comment comment, User user) {
        UserSummary commentaire = null;
        if (user instanceof Client)
            commentaire = mapClientUserSummary((Client) user);
        if (user instanceof Freelancer)
            commentaire = mapFreelancerToUserSummary((Freelancer) user);

        return new CommentResponse(comment.getId(), commentaire, comment.getContent(), comment.getCreatedAt());
    }


    public static  Post mapResquestToPost(PostRequest postRequest, User user) {

        Post post = new Post(postRequest.getTitle(), postRequest.getContent(), postRequest.getCover(),
                postRequest.getCategories(), user);
        return post;
    }

    public static  PostResponse mapPostToResponse(Post post, long comment, User user) {

        UserSummary userSummary = mapUserToUserSummary(user);
        return new PostResponse(post.getId(), post.getTitle(), post.getContent(), post.getCategorie()
                , post.getCreatedAt(), comment, userSummary);
    }


    public static  Project mapRequestToProject(ProjectRequest projectRequest, User user) {

        Project project = new Project(projectRequest.getTitle(), projectRequest.getContent(), user);
        return project;
    }

    public static  ProjectResponse mapProjectToResponse(Project project, long c) {
        UserSummary userSummary = mapUserToUserSummary(project.getCreated());
        return new ProjectResponse(project.getId(), project.getTitle(), project.getContent()
                , project.getCreatedAt(), c, userSummary);

    }

    public static  SkillResponse mapSkillToResponse(Skill skill) {
        return new SkillResponse(skill.getName());
    }

    public static  Skill mapRequestToSkill(SkillRequest skillRequest) {
        return new Skill(skillRequest.getName());
    }

    public static  ExperienceResponse mapExperienceToResponse(Freelancer freelancer, Experience experience) {
        return new ExperienceResponse(experience.getId(), experience.getCompanyName(), experience.getStartDate()
                , experience.getEndDate(), experience.getDescription(), experience.getPosition(), mapFreelancerToUserSummary(freelancer));
    }

    public static  UserProfile mapUserToProfile(User user) {

        if (user instanceof Freelancer) {
            Freelancer freelancer = (Freelancer) user;
            return new UserProfile(freelancer.getId(), freelancer.getName(), freelancer.getAboutMe(), freelancer.getCountry()
                    , freelancer.getCity(), freelancer.getDob(), freelancer.getAdresse(),
                    freelancer.getRole().getName().name().equalsIgnoreCase(RoleName.ROLE_CLIENT.name()) ? "Client" : "Freelancer", freelancer.getPath(), freelancer.getReplyTime(), freelancer.getRating());
        } else {
            Client freelancer = (Client) user;
            return new UserProfile(freelancer.getId(), freelancer.getName(), freelancer.getAboutMe(), freelancer.getCountry()
                    , freelancer.getCity(), freelancer.getDob(), freelancer.getAdresse(),
                    freelancer.getRole().getName().name().equalsIgnoreCase(RoleName.ROLE_CLIENT.name()) ? "Client" : "Freelancer", freelancer.getPath(), freelancer.getScore());
        }

    }

    public static  LanguageResponse mapLanguageToResponse(Language language) {
        return new LanguageResponse(language.getName());
    }

    public static  TicketResponse mapTicketToResponse(Ticket ticket) {
        UserSummary userSummary = mapUserToUserSummary(ticket.getUser());
        return new TicketResponse(ticket.getId(), ticket.getSubject(), ticket.getContent(),
                userSummary);
    }

    public static  Ticket mapRequestToTicket(User user, TicketRequest ticketRequest) {
        return new Ticket(ticketRequest.getSubject(), ticketRequest.getContent(), user);
    }


    public static  Conversation mapResquestToConversation(ConversationRequest request, User user1, User user2) {
        return new Conversation(
                user1, user2,
                0

        );
    }


    public static  ConversationResponse mapConversationToResponse(Conversation conversation) {
        return new ConversationResponse(conversation.getId(),
                mapUserToUserSummary(conversation.getUser1()),
                mapUserToUserSummary(conversation.getUser2()),
                conversation.getStatus()

        );
    }

    public static  MessageResponse mapMessageToResponse(Message message) {
        return new MessageResponse(
                message.getId(),
                mapUserToUserSummary(message.getSender()),
                message.getMessage(), mapConversationToResponse(message.getConversation()), message.getDocument().getFileName() + message.getDocument().getFileType(),
                message.getStatus()
        );
    }


    public static  Message mapRequestToMessage(MessageRequest request, Document document, User sender, User receiver, Conversation conversation, int s) {
        return new Message(
                request.getContent(),
                s,
                sender,
                receiver,
                document,
                conversation

        );
    }

    public static  NotificationResponse mapResponseFromNotification(Notification notification) {

        return new NotificationResponse(notification.getId(),
                mapUserToUserSummary(notification.getSender()),
                notification.getDescription(),
                notification.getUrl(),
                notification.getIsRead()
        );
    }

}
