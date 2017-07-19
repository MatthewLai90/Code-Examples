package entities;

public class MeetingParticipant {
    private Meeting meeting;
    private Employee participant;

    public MeetingParticipant(Employee participant, Meeting meeting){
        this.participant = participant;
        this.meeting = meeting;

    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public Employee getParticipant() {
        return participant;
    }

    public void setParticipant(Employee participant) {
        this.participant = participant;
    }

    
}
