/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package functions;

import entities.Employee;
import entities.Meeting;
import java.io.*;
import java.net.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Matthew Lai
 */
public class MeetingPlannerServer implements Runnable{
    
    private Socket socket;
    private BufferedReader in;
    private MeetingPlannerServer thisMPS;
    private boolean close;
    public MeetingPlannerServer()
    {
        thisMPS = this;
        socket = null;
    }
    
    public MeetingPlannerServer(Socket socket)
    {
        thisMPS = this;
        this.socket = socket;
    }
    
    private void runServer()
    {
        close = false;
        try {
            ServerSocket ss = new ServerSocket(8888);
            System.out.println("Server Started");
            while(!close)
            {
                System.out.println("New Connection Made by Server");
                MeetingPlannerServer mPS = new MeetingPlannerServer(ss.accept());
                new Thread(mPS).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(MeetingPlannerServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Server Closing");
    }
    
    private void email(boolean modifyMessage, int meetingID)
    {
//Ideally the call shouldn't be to MainProgram to recieve the meeting but the sql and meetings from the MainProgram should be stored on the server, seperate from the client. 
//But in this case, the MainProgram will run both the Server and the Client at the same time.  I don't have th time nor the inclination to modify the entire program to make it  ideal.
        Meeting meet = MainProgram.getMeetingByID(meetingID);
        System.out.println(meetingID);
        System.out.println(meet.getMeetingID());
        MeetingParticipantControl mpc = MainProgram.getMPC();
        Vector<Employee> participants = mpc.getParticipantList(meet);
        for (int i = 0; i < participants.size(); i++) {
            String name = participants.get(i).getName();
            if(!modifyMessage)
                System.out.println("Hello " + name + ", you are requested to attend a meeting by " + meet.getOwner().getName() + 
                        " on " + meet.getMeetingDate().getMonth() + "/" + meet.getMeetingDate().getDate() + "/" +
                        meet.getMeetingDate().getYear() + " at " + meet.getTimeBegin().getHours()+ ":" 
                        + meet.getTimeBegin().getMinutes() + 
                        ".\nThe meeting will take place in Room " + meet.getMeetingRoom().getRoomName() + ".\n");
            if(modifyMessage)
                System.out.println("Hello " + name + ", the meeting by " + meet.getOwner().getName() + 
                        " has been modified. Here is the new meeting information.\nDate: " +
                        meet.getMeetingDate().getMonth() + "/" + meet.getMeetingDate().getDate() + 
                        "/" + meet.getMeetingDate().getYear() + "\nRoom: " + meet.getMeetingRoom().getRoomName() + "\n");
        }
    }
    
// Should the program ever get a logout function, the thread should be closed upon logging out
    private void runThread()
    {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            boolean exit = false;
            while(!exit)
            {
                String command = in.readLine();
                if(command.equals("emailModify")) email(true, Integer.parseInt(in.readLine()));
                if(command.equals("email")) email(false, Integer.parseInt(in.readLine()));
                if(command.equals("exit")) exit = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(MeetingPlannerServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void run() {
        if(socket == null) runServer();
        else runThread();
    }

    void close() {
        close = true;
        new MeetingPlannerClient("127.0.0.1",8888).close();
    }
}
