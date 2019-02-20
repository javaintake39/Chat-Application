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

/**
 * ClassName : Message.java 
 * Description : class to represent message in chat
 * @author MotYim
 * @since 11-02-2017
 */
public class Message implements Serializable {

    private int fontsSize;
    private XMLGregorianCalendar date;
    private String fontColor;
    private String fontFamily;
    private String fontStyle;
    private String body;
    private String fontWeight;
    private Boolean underline;
    

    public Message() {

    }

    public Message(int fontsSize, XMLGregorianCalendar date, String fontColor, String fontFamily, String fontStyle, String body, String fontWeight, Boolean underline) {
        this.fontsSize = fontsSize;
        this.date = date;
        this.fontColor = fontColor;
        this.fontFamily = fontFamily;
        this.fontStyle = fontStyle;
        this.body = body;
        this.fontWeight = fontWeight;
        this.underline = underline;
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

    public String getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(String fontStyle) {
        this.fontStyle = fontStyle;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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
    
    
}