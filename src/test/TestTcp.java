package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.Socket;
import java.util.Map;

import org.junit.Test;

import core.tcp.ConnectionManager;
import core.tcp.ConnectionUnit;

public class TestTcp {

	
	@Test
	public void testTcpConnection(){
		
		Socket test = new Socket();
		
		ConnectionManager mng = ConnectionManager.GetInstance();
		ConnectionUnit unit = mng.CreateNewUnit(test, null);
		
		assertNotNull(unit.getConnectionId());
		
		Map<String, ConnectionUnit> map = mng.ReadConnectionMap();
		
		boolean cont = map.containsKey(unit.getConnectionId());
		assertEquals(true, cont);
		
		mng.DeleteUnit(unit.getConnectionId());
		
		cont = map.containsKey(unit.getConnectionId());
		assertEquals(false, cont);
		assertEquals(true, unit.getSocket().isClosed());
		
	}
}
