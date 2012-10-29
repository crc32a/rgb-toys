package org.exp.rgb.gui.tools;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import org.exp.math.debug.Debug;

public class LogPane {

    private JTextPane textPane;
    private StyledDocument doc;
    private Style red;
    private Style green;
    private Style blue;
    private Style white;
    private Style curStyle;

    public LogPane(JTextPane textPane) {
        this.textPane = textPane;
        this.doc = textPane.getStyledDocument();

        this.red = doc.addStyle("red", null);
        StyleConstants.setForeground(red, Color.red);

        this.green = doc.addStyle("green", null);
        StyleConstants.setForeground(green, Color.green);

        this.blue = doc.addStyle("blue", null);
        StyleConstants.setForeground(blue, Color.BLUE);

        this.white = doc.addStyle("white", null);
        StyleConstants.setForeground(white, Color.WHITE);

    }

    public void write(String format, Object... args) {
        String msg = String.format(format, args);
        try {
            doc.insertString(doc.getLength(), msg, curStyle);
        } catch (BadLocationException ex) {
            Logger.getLogger(LogPane.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void writeException(Throwable ex) {
        String exceptionMessage = Debug.getEST(ex);
        Style origStyle = curStyle;
        curStyle = red;
        write(exceptionMessage);
        curStyle = origStyle;
    }

    public void redWrite(String fmt, Object... args) {
        Style origStyle = curStyle;
        curStyle = red;
        write(fmt, args);
        curStyle = origStyle;
    }

    public void greenWrite(String fmt, Object... args) {
        Style origStyle = curStyle;
        curStyle = green;
        write(fmt, args);
        curStyle = origStyle;
    }

    public void blueWrite(String fmt, Object... args) {
        Style origStyle = curStyle;
        curStyle = blue;
        write(fmt, args);
        curStyle = origStyle;
    }

    public void whiteWrite(String fmt, Object... args) {
        Style origStyle = curStyle;
        curStyle = white;
        write(fmt, args);
        curStyle = origStyle;
    }

    public void setRed() {
        this.curStyle = red;
    }

    public void setGreen() {
        this.curStyle = green;
    }

    public void clear() {
        textPane.setText("");
    }
}
