package com.mixoor.khademni.payload.response;

public class UserStatic{
    private long project;
    private float earning;
    private long unfinishedProject;

    public UserStatic(long project, float earning, long unfinishedProject) {
        this.project = project;
        this.earning = earning;
        this.unfinishedProject = unfinishedProject;
    }

    public long getProject() {
        return project;
    }

    public void setProject(long project) {
        this.project = project;
    }

    public float getEarning() {
        return earning;
    }

    public void setEarning(float earning) {
        this.earning = earning;
    }

    public long getUnfinishedProject() {
        return unfinishedProject;
    }

    public void setUnfinishedProject(long unfinishedProject) {
        this.unfinishedProject = unfinishedProject;
    }
}
