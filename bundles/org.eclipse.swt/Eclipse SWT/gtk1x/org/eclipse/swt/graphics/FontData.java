package org.eclipse.swt.graphics;

/*
 * (c) Copyright IBM Corp. 2000, 2001.
 * All Rights Reserved
 */

import org.eclipse.swt.*;
import org.eclipse.swt.internal.gtk.OS;

/**
 * Instances of this class describe operating system fonts.
 * Only the public API of this type is platform independent.
 * <p>
 * For platform-independent behaviour, use the get and set methods
 * corresponding to the following properties:
 * <dl>
 * <dt>height</dt><dd>the height of the font in points</dd>
 * <dt>name</dt><dd>the face name of the font, which may include the foundry</dd>
 * <dt>style</dt><dd>A bitwise combination of NORMAL, ITALIC and BOLD</dd>
 * </dl>
 * If extra, platform-dependent functionality is required:
 * <ul>
 * <li>On <em>Windows</em>, the data member of the <code>FontData</code>
 * corresponds to a Windows <code>LOGFONT</code> structure whose fields
 * may be retrieved and modified.</li>
 * <li>On <em>X</em>, the fields of the <code>FontData</code> correspond
 * to the entries in the font's XLFD name and may be retrieved and modified.
 * </ul>
 * Application code does <em>not</em> need to explicitly release the
 * resources managed by each instance when those instances are no longer
 * required, and thus no <code>dispose()</code> method is provided.
 *
 * @see Font
 */
public final class FontData {

	String name, locale;
	int height, style;

/**	 
 * Constructs a new un-initialized font data.
 */
public FontData () {
	height = 12;
	style = SWT.ROMAN;
}

/**
 * Constructs a new FontData given a string representation
 * in the form generated by the <code>FontData.toString</code>
 * method.
 * <p>
 * Note that the representation varies between platforms,
 * and a FontData can only be created from a string that was 
 * generated on the same platform.
 * </p>
 *
 * @param string the string representation of a <code>FontData</code> (must not be null)
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT - if the argument is null</li>
 *    <li>ERROR_INVALID_ARGUMENT - if the argument does not represent a valid description</li>
 * </ul>
 *
 * @see #toString
 */
public FontData(String string) {
	int fd = OS.pango_font_description_from_string(string);
	from_os(fd);
	/*
	 * According to the doc, we need to free the font data
	 * obtained from font_describe or description_copy.
	 */
}

/**	 
 * Constructs a new font data given a font name,
 * the height of the desired font in points, 
 * and a font style.
 *
 * @param name the name of the font (must not be null)
 * @param height the font height in points
 * @param style a bit or combination of NORMAL, BOLD, ITALIC
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT - when the font name is null</li>
 *    <li>ERROR_INVALID_ARGUMENT - if the height is negative</li>
 * </ul>
 */
public FontData(String name, int height, int style) {
	if (name == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
	if (height < 0) SWT.error(SWT.ERROR_INVALID_ARGUMENT);

	int dash = name.indexOf('-');
	if (dash != -1) {
		this.name = name.substring(dash + 1);
	} else {
		this.name = name;
	}
	
	this.height = height;
	this.style = style;
}



/*
 * Public getters
 */

/**
 * Returns the height of the receiver in points.
 *
 * @return the height of this FontData
 *
 * @see #setHeight
 */
public int getHeight() {
	return this.height;
}

/**
 * Returns the name of the receiver.
 * On platforms that support font foundries, the return value will
 * be the foundry followed by a dash ("-") followed by the face name.
 *
 * @return the name of this <code>FontData</code>
 *
 * @see #setName
 */
public String getName() {
	return this.name;
}

/**
 * Returns the style of the receiver which is a bitwise OR of 
 * one or more of the <code>SWT</code> constants NORMAL, BOLD
 * and ITALIC.
 *
 * @return the style of this <code>FontData</code>
 * 
 * @see #setStyle
 */
public int getStyle() {
	return this.style;
}

/**
 * We need this in FontDialog, so we can't just get rid of it or make it private.
 */
/* FIXME in FontDialog */
public String gtk_getXlfd() { return null; }

/**
 * Returns an integer hash code for the receiver. Any two 
 * objects which return <code>true</code> when passed to 
 * <code>equals</code> must return the same value for this
 * method.
 *
 * @return the receiver's hash
 *
 * @see #equals
 */
public int hashCode () {
	return toString().hashCode();
}

/**
 * Sets the height of the receiver. The parameter is
 * specified in terms of points, where a point is one
 * seventy-second of an inch.
 *
 * @param height the height of the <code>FontData</code>
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_INVALID_ARGUMENT - if the height is negative</li>
 * </ul>
 * 
 * @see #getHeight
 */
public void setHeight(int height) {
	if (height < 0) SWT.error(SWT.ERROR_INVALID_ARGUMENT);
	this.height = height;
}

/**
 * Sets the locale of the receiver.
 * <p>
 * The locale determines which platform character set this
 * font is going to use. Widgets and graphics operations that
 * use this font will convert UNICODE strings to the platform
 * character set of the specified locale.
 * </p>
 * <p>
 * On platforms which there are multiple character sets for a
 * given language/country locale, the variant portion of the
 * locale will determine the character set.
 * </p>
 * 
 * @param locale the <code>String</code> representing a Locale object
 * @see java.util.Locale#toString
 */
public void setLocale(String locale) {
	/* This OS is pure unicode */
}

/**
 * Sets the name of the receiver.
 * <p>
 * Some platforms support font foundries. On these platforms, the name
 * of the font specified in setName() may have one of the following forms:
 * <ol>
 * <li>a face name (for example, "courier")</li>
 * <li>a foundry followed by a dash ("-") followed by a face name (for example, "adobe-courier")</li>
 * </ol>
 * In either case, the name returned from getName() will include the
 * foundry.
 * </p>
 * <p>
 * On platforms that do not support font foundries, only the face name
 * (for example, "courier") is used in <code>setName()</code> and 
 * <code>getName()</code>.
 * </p>
 *
 * @param name the name of the font data (must not be null)
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT - when the font name is null</li>
 * </ul>
 *
 * @see #getName
 */
public void setName(String name) {
	if (name == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
	
	int dash = name.indexOf('-');
	if (dash != -1) {
		this.name = name.substring(dash + 1);
	} else {
		this.name = name;
	}
}

/**
 * Sets the style of the receiver to the argument which must
 * be a bitwise OR of one or more of the <code>SWT</code> 
 * constants NORMAL, BOLD and ITALIC.
 *
 * @param style the new style for this <code>FontData</code>
 *
 * @see #getStyle
 */
public void setStyle(int style) {
	this.style = style;
}

/**
 * Returns a string representation of the receiver which is suitable
 * for constructing an equivalent instance using the 
 * <code>FontData(String)</code> constructor.
 *
 * @return a string representation of the FontData
 *
 * @see FontData
 */
public String toString() {
	int fd = to_os();
	String answer = OS.pango_font_description_to_string(fd);
	OS.pango_font_description_free(fd);
	return answer;
}

/**
 * Compares the argument to the receiver, and returns true
 * if they represent the <em>same</em> object using a class
 * specific comparison.
 *
 * @param object the object to compare with this object
 * @return <code>true</code> if the object is the same as this object and <code>false</code> otherwise
 *
 * @see #hashCode
 */
public boolean equals (Object object) {
	if (object == this) return true;
	if (!(object instanceof FontData)) return false;
	
	int osThis = to_os();
	int osObject = ((FontData)object).to_os();
	boolean answer = OS.pango_font_description_equal(osThis, osObject);
	OS.pango_font_description_free(osThis);
	OS.pango_font_description_free(osObject);
	return answer;
}

/*
 * Initialize the receiver from a given a PandoFontDescription.
 */
void from_os(int fd) {
	setName(OS.pango_font_description_get_family(fd));
	int points = OS.pango_font_description_get_size(fd) / OS.PANGO_SCALE();
	setHeight(points);
	int swtStyle;
	int gtkStyle = OS.pango_font_description_get_style(fd);
	if (gtkStyle==OS.PANGO_STYLE_ITALIC() || gtkStyle==OS.PANGO_STYLE_OBLIQUE()) swtStyle=SWT.ITALIC;
		else swtStyle=SWT.ROMAN;
	/* Anything bolder than NORMAL, is BOLD */
	if (OS.pango_font_description_get_weight(fd) > OS.PANGO_WEIGHT_NORMAL()) swtStyle |= SWT.BOLD;
	setStyle(swtStyle);
}

/*
 * Create an OS resource (a PangoFontDescription) matching the receiver.
 * These resources must be freed after use.
 */
int to_os() {
	int answer = OS.pango_font_description_new();
	OS.pango_font_description_set_family(answer, getName());
	OS.pango_font_description_set_size(answer, getHeight()*OS.PANGO_SCALE());
	OS.pango_font_description_set_stretch(answer, OS.PANGO_STRETCH_NORMAL());
	int style, weight;
	if ((getStyle()&SWT.BOLD) != 0)
		weight = OS.PANGO_WEIGHT_BOLD();
		else weight = OS.PANGO_WEIGHT_NORMAL();
	if ((getStyle()&SWT.ITALIC) != 0)
		style = OS.PANGO_STYLE_ITALIC();
		else style = OS.PANGO_STYLE_NORMAL();
	OS.pango_font_description_set_style(answer, style);
	OS.pango_font_description_set_weight(answer, weight);
	return answer;
}

}
