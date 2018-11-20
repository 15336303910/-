package interfaces.hwinterface.socketClient.sender;

import interfaces.hwinterface.interfaceUtil.pojo.DataStruct;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSocketSender {
	private static final int port = 6010;
	private static final int cache = 2048;

	public static synchronized DataStruct sendMeassage(String equtIp,
			DataStruct dataStruct) throws Exception {
		if ((equtIp == null) || ("".equals(equtIp))) {
			return null;
		}
		InetAddress IPAddress = InetAddress.getByName(equtIp);

		DatagramSocket clientSocket = new DatagramSocket();
		byte[] receiveData = new byte[2048];

		byte[] dataArray = dataStruct.getByteArray();
		int cmd = dataStruct.cmd;
		int tryTimes = 1;
		boolean isSuccess = false;
		try {
			DatagramPacket sendPacket = new DatagramPacket(dataArray,
					dataArray.length, IPAddress, 6010);
			clientSocket.send(sendPacket);

			clientSocket.setSoTimeout(2000);
			DatagramPacket receivePacket = new DatagramPacket(receiveData,
					receiveData.length);
			clientSocket.receive(receivePacket);
		} catch (Exception e) {
			return null;
		} finally {
			isSuccess = (dataStruct.set_all(receiveData))
					&& (dataStruct.dataCheck(cmd));
			if (isSuccess) {
				clientSocket.close();
			} else if (++tryTimes > 3) {
				clientSocket.close();
				return null;
			}

		}

		return dataStruct;
	}
}