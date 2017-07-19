/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package functions;

import entities.Employee;
import entities.Room;
import java.io.IOException;
import java.io.*;
import java.net.*;
import java.sql.Time;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Matthew Lai
 */
public class MeetingPlannerClient {

    Socket socket;
    MeetingPlannerClient(String host, int port) {
        
        try {
            socket = new Socket(host, port);
            System.out.println("Client-Server Connection Established");
        } catch (IOException ex) {
            Logger.getLogger(MeetingPlannerClient.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        
    }
    
    void sendEmail(int mod, int meetingID)
    {
        boolean modify = mod==1;
        PrintWriter out = null;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException ex) {
            Logger.getLogger(MeetingPlannerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(modify) out.println("emailModify");
        else out.println("email");
        out.println(meetingID);
    }

    void sendEmail(int mod, int ownerID, Time timeBegin, int meetingRoomID) {
        boolean modify = mod==1;
        
        PrintWriter out = null;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException ex) {
            Logger.getLogger(MeetingPlannerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        out.println("email");
        out.println(modify);
        out.println(ownerID);
        out.println(timeBegin.getTime());
        out.println(meetingRoomID);
    } 

    void close() {
        try {
            new PrintWriter(socket.getOutputStream(), true).println("exit");
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(MeetingPlannerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
