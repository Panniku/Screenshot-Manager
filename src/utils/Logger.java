package utils;

import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Logger {

	private JTextPane textPane;
	private JScrollPane scrollPane;
	private static StyledDocument document;
	private static Style verbose;
	private static Style attempt;
	private static Style warning;
	private static Style success;
	private static Style error;
	
	public Logger(JTextPane textPane, JScrollPane scrollPane) {
		this.textPane = textPane;
		this.scrollPane = scrollPane;
		//
		document = textPane.getStyledDocument();

		//document.putProperty(DefaultEditorKit., Boolean.TRUE);

        // Set a large width to avoid wrapping
        StyleConstants.setLineSpacing(document.addStyle("default", null), 0.0f);
        StyleConstants.setLeftIndent(document.addStyle("default", null), 0.0f);
        StyleConstants.setRightIndent(document.addStyle("default", null), 0.0f);
        StyleConstants.setFirstLineIndent(document.addStyle("default", null), 0.0f);
		
		// Set the attribute set to the documentument
		//document.setParagraphAttributes(0, document.getLength(), attrs, false);
		//
		
        verbose = textPane.addStyle("verbose", null);
        attempt = textPane.addStyle("attempt", null);
        warning = textPane.addStyle("warning", null);
        success = textPane.addStyle("success", null);
        error = textPane.addStyle("error", null);
        //
        StyleConstants.setForeground(verbose, Color.decode("#dae1e5"));
        StyleConstants.setForeground(warning, Color.decode("#e19f3e"));
        StyleConstants.setForeground(attempt, Color.decode("#2d5b85"));
        StyleConstants.setForeground(success, Color.decode("#68d150"));
        StyleConstants.setForeground(error, Color.decode("#a8372d"));
	}
	
	public void logV(String string) {
		appendText(string, verbose);
	}
	
	public void logA(String string) {
		appendText(string, attempt);
	}
	
	public void logW(String string) {
		appendText(string, warning);
	}
	
	public void logS(String string) {
		appendText(string, success);
	}
	
	public void logE(String string) {
		appendText(string, error);
	}
	
	
	public void appendText(String text, Style style) {
        try {
        	document.insertString(document.getLength(), text, style);
        	scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
        } catch (BadLocationException e) {
	        e.printStackTrace();    
        }
	}
	
	
	
}
