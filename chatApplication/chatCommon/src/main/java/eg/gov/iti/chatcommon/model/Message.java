package eg.gov.iti.chatcommon.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ghazallah
 */
import java.io.Serializable;
import javax.xml.datatype.XMLGregorianCalendar;
public class Message implements Serializable {
    

    private String fromUser;
    private String toUser;
    private int fontsSize;
    private String from;
    private String to;
    private XMLGregorianCalendar date;
    private String fontColor;
    private String fontFamily;
    private String content;
    private String fontWeight;
    private Boolean underline;
    private Boolean bold;
    private Boolean italic;
    private String backgroundColor;

    public String getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType;
    }
    private String chatType;

    public Message() {

    }

    public Message(int fontsSize, XMLGregorianCalendar date, String fontColor, String fontFamily, String fontStyle, String content, String fontWeight, Boolean underline) {
        this.fontsSize = fontsSize;
        this.date = date;
        this.fontColor = fontColor;
        this.fontFamily = fontFamily;
        this.fontFamily = fontStyle;
        this.content = content;
        this.fontWeight = fontWeight;
        this.underline = underline;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getBold() {
        return bold;
    }

    public void setBold(Boolean bold) {
        this.bold = bold;
    }

    public Boolean getItalic() {
        return italic;
    }

    public void setItalic(Boolean italic) {
        this.italic = italic;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getFontsSize() {
        return fontsSize;
    }

    public void setFontsSize(int fontsSize) {
        this.fontsSize = fontsSize;
    }

    public XMLGregorianCalendar getDate() {
        return date;
    }

    public void setDate(XMLGregorianCalendar date) {
        this.date = date;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

   

   

    public String getFontWeight() {
        return fontWeight;
    }

    public void setFontWeight(String fontWeight) {
        this.fontWeight = fontWeight;
    }

    public Boolean getUnderline() {
        return underline;
    }

    public void setUnderline(Boolean underline) {
        this.underline = underline;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }
    
    
    
}