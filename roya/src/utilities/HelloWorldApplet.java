package utilities;

import java.applet.Applet;
import java.awt.Graphics;

public class HelloWorldApplet extends Applet
{
	@Override
	public void init() {System.out.println("aaaaaaaaaaaaaaaa");};
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public void paint (Graphics g)
   {
	
	 String myString = getParameter("string");
      g.drawString ("Hello World", 25, 50);
   }
}