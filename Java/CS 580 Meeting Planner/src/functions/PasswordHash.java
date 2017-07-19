package functions;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHash {
    public static String getMD5sum(String input){
        String toReturn=null;
        try{
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(input.getBytes());
            byte messageDigest[] = algorithm.digest();
            StringBuffer sb=new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String hex=Integer.toHexString(0xff & messageDigest[i]);
                if(hex.length()==1) sb.append('0');
                sb.append(hex);
            }
            toReturn=sb.toString();
            System.out.println("md5sum:"+toReturn);
        } catch (NoSuchAlgorithmException e){

        }
        return toReturn;
    }
    
    //check if the md5sum of str is equal to md5sumStr
    public static boolean checkPassword(String str,String md5sumStr){
        return getMD5sum(str).equals(md5sumStr);
    }
}
