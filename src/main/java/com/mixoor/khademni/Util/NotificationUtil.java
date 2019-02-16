package com.mixoor.khademni.Util;

import com.mixoor.khademni.model.NotificationType;

public class NotificationUtil {

    static String commentMessage="has Commented your post";
    static String jobMessage="has invited your to apply for ";
    static String applicationMessage="has applied to ";
    static String postMessage="has created a post";
    static String acceptedMessage="has accepted your offer";
    static String refusedMessage="has refused your offer";
    static String reviewMessage="has add review in your profile";
    static String replayMessage="has add replay to your review";
    static String voteMessage="has voted for your project";


    public static  String getMessage(int type){
        if(NotificationType.values()[type]==NotificationType.POST)
            return postMessage;

        else if (NotificationType.values()[type]==NotificationType.COMMENT)
            return commentMessage;

        else if (NotificationType.values()[type]==NotificationType.JOB)
            return jobMessage;

        else if (NotificationType.values()[type]==NotificationType.APPLICATION)
            return applicationMessage;

        else if (NotificationType.values()[type]==NotificationType.ACCEPTED)
            return acceptedMessage;

        else if (NotificationType.values()[type]==NotificationType.REFUSED)
            return refusedMessage;
        else if (NotificationType.values()[type]==NotificationType.REVIEW)
            return reviewMessage;
        else if (NotificationType.values()[type]==NotificationType.REPLAY)
            return replayMessage;
        else if (NotificationType.values()[type]==NotificationType.VOTE)
                    return voteMessage;

        else
            return replayMessage;

    }
}
