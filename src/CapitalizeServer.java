import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
/**
 * A server program which accepts requests from clients to
 * capitalize strings.  When clients connect, a new thread is
 * started to handle an interactive dialog in which the client
 * sends in a string and the server thread sends back the
 * capitalized version of the string.
 *
 * The program is runs in an infinite loop, so shutdown in platform
 * dependent.  If you ran it from a console window with the "java"
 * interpreter, Ctrl+C generally will shut it down.
 */
public class CapitalizeServer {
	 static Robot robot ;
	 
	 static int[] allVK_KEYS = {
		 KeyEvent.VK_0,
		 KeyEvent.VK_1,
		 KeyEvent.VK_2,
		 KeyEvent.VK_3,
		 KeyEvent.VK_4,
		 KeyEvent.VK_5,
		 KeyEvent.VK_6,
		 KeyEvent.VK_7,
		 KeyEvent.VK_8,
		 KeyEvent.VK_9,
		 KeyEvent.VK_A,
		 KeyEvent.VK_B,
		 KeyEvent.VK_C,
		 KeyEvent.VK_D,
		 KeyEvent.VK_E,
		 KeyEvent.VK_F,
		 KeyEvent.VK_G,
		 KeyEvent.VK_H,
		 KeyEvent.VK_I,
		 KeyEvent.VK_J,
		 KeyEvent.VK_K,
		 KeyEvent.VK_L,
		 KeyEvent.VK_M,
		 KeyEvent.VK_N,
		 KeyEvent.VK_O,
		 KeyEvent.VK_P,
		 KeyEvent.VK_Q,
		 KeyEvent.VK_R,
		 KeyEvent.VK_S,
		 KeyEvent.VK_T,
		 KeyEvent.VK_U,
		 KeyEvent.VK_V,
		 KeyEvent.VK_W,
		 KeyEvent.VK_X,
		 KeyEvent.VK_Y,
		 KeyEvent.VK_Z,
		 KeyEvent.VK_SEMICOLON,
		 KeyEvent.VK_F1,
		 KeyEvent.VK_F2,
		 KeyEvent.VK_F3,
		 KeyEvent.VK_F4,
		 KeyEvent.VK_F5,
		 KeyEvent.VK_F6,
		 KeyEvent.VK_F7,
		 KeyEvent.VK_F8,
		 KeyEvent.VK_F9,
		 KeyEvent.VK_F10,
		 KeyEvent.VK_F11,
		 KeyEvent.VK_F12,
		 KeyEvent.VK_F13,
		 KeyEvent.VK_COLON,		 
		 KeyEvent.VK_LEFT,
		 KeyEvent.VK_UP,
		 KeyEvent.VK_DOWN,
		 KeyEvent.VK_RIGHT,
		 KeyEvent.VK_SPACE
		 
	 };
	 static int[] iplayer1 = {allVK_KEYS[0],allVK_KEYS[1],allVK_KEYS[2],allVK_KEYS[3],allVK_KEYS[4],allVK_KEYS[5],allVK_KEYS[6],allVK_KEYS[7],allVK_KEYS[8],allVK_KEYS[9],allVK_KEYS[10],allVK_KEYS[11],allVK_KEYS[12],allVK_KEYS[13]};
	 static int[] iplayer2 = {allVK_KEYS[14],allVK_KEYS[15],allVK_KEYS[16],allVK_KEYS[17],allVK_KEYS[18],allVK_KEYS[19],allVK_KEYS[20],allVK_KEYS[21],allVK_KEYS[22],allVK_KEYS[23],allVK_KEYS[24],allVK_KEYS[25],allVK_KEYS[26],allVK_KEYS[27]};
	 static int[] iplayer3 = {allVK_KEYS[28],allVK_KEYS[29],allVK_KEYS[30],allVK_KEYS[31],allVK_KEYS[32],allVK_KEYS[33],allVK_KEYS[34],allVK_KEYS[35],allVK_KEYS[36],allVK_KEYS[37],allVK_KEYS[38],allVK_KEYS[39],allVK_KEYS[40],allVK_KEYS[41]};
	 static int[] iplayer4 = {allVK_KEYS[42],allVK_KEYS[43],allVK_KEYS[44],allVK_KEYS[45],allVK_KEYS[46],allVK_KEYS[47],allVK_KEYS[48],allVK_KEYS[49],allVK_KEYS[50],allVK_KEYS[51],allVK_KEYS[52],allVK_KEYS[53],allVK_KEYS[54],allVK_KEYS[55]};
	 
//	 static char[] player1 = {'a','z','e','r','t','y','u','i','o','p','q','s','d','f'};
//	 static char[] player2 = {'g','h','j','k','l','m','w','x','c','v','b','n',',',';'};
//	 static char[] player3 = {'&','é','(','-','è','=','5','à',')','0','1','2','3','4'};
	 //static char[] player4 = {'','','','','','','','','','','','','',''};
	 static HashMap<String,Boolean> player_dispo;
	 static boolean p1 = false;
	 static boolean p2 = false;
	 static boolean p3 = false;
	 static boolean p4 = false;
    /**
     * Application method to run the server runs in an infinite loop
     * listening on port 9898.  When a connection is requested, it
     * spawns a new thread to do the servicing and immediately returns
     * to listening.  The server keeps a unique client number for each
     * client that connects just to show interesting logging
     * messages.  It is certainly not necessary to do this.
     */
    public static void main(String[] args) throws Exception {
    	player_dispo = new HashMap<>();
    	player_dispo.put("a1", false);
    	player_dispo.put("a2", false);
    	player_dispo.put("a3", false);
    	player_dispo.put("a4", false);
    	robot = new Robot();
    	try{
    	Runtime.getRuntime().exec("pcsx");
    	}catch(Exception e){
    		System.out.println("Can't launch pcsx !!");
    	}
        System.out.println("The capitalization server is running.");
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(9898);
        try {
            while (true) {
            	if(!p1) clientNumber = 0;
            	if(!p2) clientNumber = 1;
            	if(!p3) clientNumber = 2;
            	if(!p4) clientNumber = 3;
                new Capitalizer(listener.accept(), clientNumber).start();
                
            }
        } finally {
            listener.close();
        }
    }

    /**
     * A private thread to handle capitalization requests on a particular
     * socket.  The client terminates the dialogue by sending a single line
     * containing only a period.
     */
    private static class Capitalizer extends Thread {
        private Socket socket;
        private int clientNumber;

        public Capitalizer(Socket socket, int clientNumber) {
            this.socket = socket;
            if(clientNumber>4){
            	log("too much client");
            }
            player_dispo.put("a"+(this.clientNumber+1), true);
            
            log("New connection with client# " + clientNumber + " at " + socket + " " + player_dispo.get("a"+(this.clientNumber+1)));
        }

        /**
         * Services this thread's client by first sending the
         * client a welcome message then repeatedly reading strings
         * and sending back the capitalized version of the string.
         */
        public void run() {
            try {

                // Decorate the streams so we can send characters
                // and not just bytes.  Ensure output is flushed
                // after every newline.
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // Send a welcome message to the client.
                out.println("Player " + (clientNumber+1));

                // Get messages from the client, line by line; return them
                // capitalized
                while (true) {
                    String input = in.readLine();
                    if (input == null || input.equals(".")) {
                        break;
                    }
//                    out.println(input.toUpperCase());
//                    log("" + clientNumber + " say " + input);
                    
                    	int ks = 0;
                    	if(input.contains("d")){
//                    		log(clientNumber + " => " + (clientNumber%3));
                    		switch(clientNumber){
	                    		case 0 : 
//	                    			ks = KeyEvent.getExtendedKeyCodeForChar(player1[Integer.valueOf(input.replaceAll("d", ""))] );
	                    			ks = iplayer1[Integer.valueOf(input.replaceAll("d", ""))];
	                    			log("Player 1 : "+KeyEvent.getKeyText(ks));
	                    			break;
	                    		case 1 : 
//	                    			ks = KeyEvent.getExtendedKeyCodeForChar(player2[Integer.valueOf(input.replaceAll("d", ""))] );
	                    			ks = iplayer2[Integer.valueOf(input.replaceAll("d", ""))];
	                    			log("Player 2 : "+KeyEvent.getKeyText(ks));
	                    			break;
	                    		case 2 : 
//	                    			ks = KeyEvent.getExtendedKeyCodeForChar(player3[Integer.valueOf(input.replaceAll("d", ""))] );
	                    			ks = iplayer3[Integer.valueOf(input.replaceAll("d", ""))];
	                    			log("Player 3 : "+KeyEvent.getKeyText(ks));
	                    			break;
	                    		case 3 : 
//	                    			ks = KeyEvent.getExtendedKeyCodeForChar(player3[Integer.valueOf(input.replaceAll("d", ""))] );
	                    			ks = iplayer4[Integer.valueOf(input.replaceAll("d", ""))];
	                    			log("Player 4 : "+KeyEvent.getKeyText(ks));
	                    			break;
                    		}                    		
                    		robot.keyPress(ks);
                    	}else if(input.contains("u")){
                    		switch(clientNumber){
                    		case 0 : 
//                    			ks = KeyEvent.getExtendedKeyCodeForChar(player1[Integer.valueOf(input.replaceAll("u", ""))] );
                    			ks = iplayer1[Integer.valueOf(input.replaceAll("u", ""))];
                    			break;
                    		case 1 : 
//                    			ks = KeyEvent.getExtendedKeyCodeForChar(player2[Integer.valueOf(input.replaceAll("u", ""))] );
                    			ks = iplayer2[Integer.valueOf(input.replaceAll("u", ""))];
                    			break;
                    		case 2 : 
//                    			ks = KeyEvent.getExtendedKeyCodeForChar(player3[Integer.valueOf(input.replaceAll("u", ""))] );
                    			ks = iplayer3[Integer.valueOf(input.replaceAll("u", ""))];
                    			break;
                    		case 3 : 
//                    			ks = KeyEvent.getExtendedKeyCodeForChar(player3[Integer.valueOf(input.replaceAll("u", ""))] );
                    			ks = iplayer4[Integer.valueOf(input.replaceAll("u", ""))];
                    			break;	
                    		}
                    		robot.keyRelease(ks);
                    	}
                    	else if(input.contains("a")){
                    		log("" + clientNumber + " is asking to be player " + input.replaceAll("a", ""));
                    		out.println(player_dispo.get(input));
                    	}
                    	else if(input.contains("p")){
                    		player_dispo.put("a"+(this.clientNumber+1), false);
                    		this.clientNumber = Integer.parseInt(input.replaceAll("p", ""))-1;                    		
                    		out.println("Player "+(this.clientNumber+1));
                    		player_dispo.put("a"+(this.clientNumber+1), true);
                    		
                    	}
                    		log( input );                    		
//                    		robot.delay(10);                    		
                    
                   
                }
            } catch (IOException e) {
                log("Error handling client# " + clientNumber + ": " + e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    log("Couldn't close a socket, what's going on?");
                }
                log("Connection with client# " + clientNumber + " closed");
                player_dispo.put("a"+(clientNumber+1), false);
            }
        }

        /**
         * Logs a simple message.  In this case we just write the
         * message to the server applications standard output.
         */
        private void log(String message) {
            System.out.println(message);
        }
    }
}