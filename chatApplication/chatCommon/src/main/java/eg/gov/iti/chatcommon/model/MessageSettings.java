/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.gov.iti.chatcommon.model;

import java.sql.Timestamp;

/**
 *
 * @author ghazallah
 */
public class MessageSettings {
    private String fontStyle;
    private String fontColor;
    private String  textBackground;
    private float fontSize;
    private boolean italic;
    private boolean bold;
    private boolean underline;
    private Timestamp timeStamp;

    public MessageSettings() {
    }

    public MessageSettings(String fontStyle, String fontColor, String textBackground, float fontSize, boolean italic, boolean bold, boolean underline, Timestamp timeStamp) {
        this.fontStyle = fontStyle;
        this.fontColor = fontColor;
        this.textBackground = textBackground;
        this.fontSize = fontSize;
        this.italic = italic;
        this.bold = bold;
        this.underline = underline;
        this.timeStamp = timeStamp;
    }

    public String getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(String fontStyle) {
        this.fontStyle = fontStyle;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public String getTextBackground() {
        return textBackground;
    }

    public void setTextBackground(String textBackground) {
        this.textBackground = textBackground;
    }

    public float getFontSize() {
        return fontSize;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    public boolean isItalic() {
        return italic;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public boolean isUnderline() {
        return underline;
    }

    public void setUnderline(boolean underline) {
        this.underline = underline;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    
    
    
    
}
