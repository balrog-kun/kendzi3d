//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.04.09 at 02:06:43 PM CEST 
//


package kendzi.josm.kendzi3d.dto.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PointModel complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PointModel">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="matcher" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="model" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="translateX" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="translateY" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="translateZ" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="scale" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PointModel", namespace = "")
public class PointModel {

    @XmlAttribute
    protected Long id;
    @XmlAttribute
    protected String matcher;
    @XmlAttribute
    protected String model;
    @XmlAttribute
    protected Double translateX;
    @XmlAttribute
    protected Double translateY;
    @XmlAttribute
    protected Double translateZ;
    @XmlAttribute
    protected String scale;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(Long value) {
        this.id = value;
    }

    /**
     * Gets the value of the matcher property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMatcher() {
        return matcher;
    }

    /**
     * Sets the value of the matcher property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMatcher(String value) {
        this.matcher = value;
    }

    /**
     * Gets the value of the model property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the value of the model property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModel(String value) {
        this.model = value;
    }

    /**
     * Gets the value of the translateX property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTranslateX() {
        return translateX;
    }

    /**
     * Sets the value of the translateX property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTranslateX(Double value) {
        this.translateX = value;
    }

    /**
     * Gets the value of the translateY property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTranslateY() {
        return translateY;
    }

    /**
     * Sets the value of the translateY property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTranslateY(Double value) {
        this.translateY = value;
    }

    /**
     * Gets the value of the translateZ property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTranslateZ() {
        return translateZ;
    }

    /**
     * Sets the value of the translateZ property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTranslateZ(Double value) {
        this.translateZ = value;
    }

    /**
     * Gets the value of the scale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScale() {
        return scale;
    }

    /**
     * Sets the value of the scale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScale(String value) {
        this.scale = value;
    }

}
