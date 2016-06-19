import junit.framework.*;
import com.walmart.app.CommandManager;

public class TestCommandManager extends TestCase{

	public void testParseCommand() {
		CommandManager cm = new CommandManager();

		assertEquals(0, cm.parseCommand("hello"));
		assertEquals(1, cm.parseCommand("1"));
		assertEquals(0, cm.parseCommand("0"));
		assertEquals(2, cm.parseCommand("2"));
		assertEquals(25, cm.parseCommand("25"));
		assertEquals(-1, cm.parseCommand(""));
		assertEquals(-1, cm.parseCommand(null));
	}

}
