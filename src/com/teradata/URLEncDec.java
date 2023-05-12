package com.teradata;

import java.net.*;
import java.sql.*;
import java.io.UnsupportedEncodingException;

public class URLEncDec {
	/*
	 * URL Encoder 
	 */
	public static String enc( String p1 ,String p2 ) throws SQLException {
		String ret = "";
		try {
			ret = URLEncoder.encode(p1,p2);
		} catch( UnsupportedEncodingException e ) {
			ret = "UTF-16 encoding string is supported";
		}
		return ret;
	} 	
	/*
	 * URL Decoder 
	 */
	public static String dec(String p1 ,String p2) throws SQLException{
		String ret = "";
		try {
			ret = URLDecoder.decode(p1,p2);
		} catch( UnsupportedEncodingException e ) {
			ret = "UTF-16 encoding string is supported";
		}
		return ret;
	} 
}
