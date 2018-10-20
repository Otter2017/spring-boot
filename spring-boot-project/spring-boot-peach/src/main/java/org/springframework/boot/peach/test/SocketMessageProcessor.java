package org.springframework.boot.peach.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketMessageProcessor implements Runnable {
	private Logger log = LoggerFactory.getLogger(SocketMessageProcessor.class);

	private static final int threadSleepInterval = 2000;

	private Socket acceptSocket;

	public SocketMessageProcessor(Socket acceptSocket) {
		this.acceptSocket = acceptSocket;
	}

	@Override
	public void run() {
		try {
			InputStream inputStream = this.acceptSocket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String message = null;
			StringBuilder stringBuilder = new StringBuilder();
			do {
				message = reader.readLine();
				if (message != null && message.length() > 0) {
					stringBuilder.append(message);
				}
			} while (message != null);
			if (stringBuilder.length() > 0) {
				this.log.info("accept message : " + stringBuilder.toString());
				System.out.println();
			}

			reader.close();
			inputStream.close();
		}
		catch (Exception ex) {
			this.log.error("getInputStream Error " + ex.getMessage());
		}
		finally {
			try {
				this.acceptSocket.close();
			}
			catch (Exception ex) {
				this.log.error("Socket close error:" + ex.getMessage());
			}
		}
	}

//    public boolean writeSocketMessageToFile(InputStream inputStream){
//        try {
//            BufferedInputStream reader = new BufferedInputStream(inputStream);
//            if(reader.available()>0){
//                byte[] buffer=new byte[1024];
//                String savePath = SocketServiceConfig.class.getProtectionDomain().getCodeSource().getLocation().getPath();
//                File saveFile=new File()
//                while(reader.read(buffer)>0){
//
//                }
//            } else {
//                return  false;
//            }
//            reader.available()
//
//            reader.read()
//            reader.re
//        } catch (Exception ex){
//
//        }
//
//    }
}
