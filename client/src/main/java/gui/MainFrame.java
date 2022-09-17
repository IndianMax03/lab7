package gui;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;

public class MainFrame {

	private static final JFrame FRAME;
	private static final Font FONT = new Font("TimesRoman", Font.BOLD, 15);

	static {
		FRAME = new JFrame();
		try {
			UIManager.setLookAndFeel(new NimbusLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dimension = toolkit.getScreenSize();
		FRAME.setBounds(dimension.width/2-400, dimension.height/2-200, 800, 400);
		FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private MainFrame(){}

	public static JFrame getFrame(){
		return FRAME;
	}

	public static Font getFONT() {
		return FONT;
	}
}
