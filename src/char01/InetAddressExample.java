package char01;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class InetAddressExample {
	public static void main(String[] args) throws SocketException, UnknownHostException {
		Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		if (interfaces == null) {
			System.out.println("this is no interface");
		} else {
			while (interfaces.hasMoreElements()) {
				NetworkInterface networkInterface = interfaces.nextElement();
				Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
				if (!addresses.hasMoreElements()) {
					System.out.println("there has not address");
				}
				while (addresses.hasMoreElements()) {
					InetAddress address = addresses.nextElement();
					System.out.println(address instanceof Inet4Address ? "v4" : "v6");
					System.out.println(address.getHostAddress());
				}
			}
		}
		for (String host : args) {
			System.out.println(host+":");
			InetAddress[] inets=InetAddress.getAllByName(host);
			for(InetAddress inet:inets){
				System.out.println(inet.getHostName()+":"+inet.getHostAddress());
				System.out.println("toString:"+inet.toString());
				System.out.println("caninical:"+inet.getCanonicalHostName());
			}
		}
	}
}
